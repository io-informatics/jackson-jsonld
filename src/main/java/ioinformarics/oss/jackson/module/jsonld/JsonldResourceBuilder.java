package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class JsonldResourceBuilder<T> {

    private String _context;
    private String _type;
    private String _id;
    private Function<T, String> _idSupplier;

    JsonldResourceBuilder(){
    }

    public JsonldResourceBuilder<T> context(String context){
        this._context = context;
        return this;
    }

    public JsonldResourceBuilder<T> type(String type){
        this._type = type;
        return this;
    }

    public JsonldResourceBuilder<T> id(String id){
        this._id = id;
        return this;
    }

    public JsonldResourceBuilder<T> id(Function<T, String> idSupplier){
        this._idSupplier = idSupplier;
        return this;
    }

    public JsonldResource build(T scopedObj) {
        if(scopedObj == null){
            return null;
        }
        if(Map.class.isAssignableFrom(scopedObj.getClass())){
            return new MapJsonldResource((Map)scopedObj, getContext(scopedObj), getType(scopedObj), getId(scopedObj));
        }
        return new BeanJsonldResource(scopedObj, getContext(scopedObj), getType(scopedObj), getId(scopedObj));
    }

    protected JsonNode getContext(T scopedObj) {
        return buildContext(_context, scopedObj.getClass());
    }

    protected String getId(T scopedObj){
        return Optional.ofNullable(_id).orElse(Optional.ofNullable(_idSupplier).map(f -> f.apply(scopedObj)).orElse(null));
    }

    protected String getType(T scopedObj) {
        return Optional.ofNullable(_type).orElse(dynamicTypeLookup(scopedObj.getClass()));
    }

    static String dynamicTypeLookup(Class<?> objType){
        JsonldType type = objType.getAnnotation(JsonldType.class);
        return type == null? null : type.value();
    }

    static ObjectNode generateContext(Class<?> objType) {
        ObjectNode generatedContext = JsonNodeFactory.withExactBigDecimals(true).objectNode();
        //TODO: This is bad...it does not consider other Jackson annotations. Need to use a AnnotationIntrospector?
        Arrays.asList(objType.getDeclaredFields()).forEach( field -> {
            JsonldProperty jsonldProperty = field.getAnnotation(JsonldProperty.class);
            if(jsonldProperty != null){
                generatedContext.set(field.getName(), TextNode.valueOf(jsonldProperty.value()));
            }
        });
        //add links
        JsonldLink[] links = objType.getAnnotationsByType(JsonldLink.class);
        if(links != null){
            for(int i=0; i < links.length; i++){
                ObjectNode linkNode = JsonNodeFactory.withExactBigDecimals(true).objectNode();
                linkNode.set("@id", new TextNode(links[i].rel()));
                linkNode.set("@type", new TextNode("@id"));
                generatedContext.set(links[i].name(), linkNode);
            }
        }
        return generatedContext;
    }

    protected JsonNode buildContext(String context, Class<?> objType){
        ObjectNode generatedContext = generateContext(objType);
        return context != null && context.length() > 0? buildMultiContext(context, generatedContext):generatedContext;
    }


    protected JsonNode buildMultiContext(String context, ObjectNode generatedContext){
        return generatedContext == null || generatedContext.size() == 0 ? TextNode.valueOf(context) : JsonNodeFactory.withExactBigDecimals(true).arrayNode().add(context).add(generatedContext);
    }
}
