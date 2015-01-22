package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class JsonldGraphBuilder<T> {

    protected String context;
    protected String graphType;
    protected String graphId;
    protected JsonldResourceBuilder<T> resourceBuilder;
    protected Function<T, String> typeSupplier;

    JsonldGraphBuilder() {
        resourceBuilder = new JsonldResourceBuilder<T>();
    }

    public JsonldGraphBuilder<T> context(String context){
        this.context = context;
        return this;
    }

    public JsonldGraphBuilder<T> type(String type){
        this.graphType = type;
        return this;
    }

    public JsonldGraphBuilder<T> id(String id){
        this.graphId = id;
        return this;
    }

    public JsonldGraphBuilder<T> elementId(Function<T,String> idSupplier){
        this.resourceBuilder.id(idSupplier);
        return this;
    }

    public JsonldGraphBuilder<T> elementType(Function<T,String> typeSupplier){
        this.typeSupplier = typeSupplier;
        return this;
    }

    public JsonldResource build(Iterable<T> elements) {
        return new JsonldGraph(buildElements(elements), resourceBuilder.buildMultiContext(context, buildContext(elements)), graphType, graphId);
    }

    protected ObjectNode buildContext(Iterable<T> elements) {
        //WARN: Not a deep merge
        ObjectNode mergedContext = JsonNodeFactory.withExactBigDecimals(true).objectNode();
        elements.forEach(e -> mergedContext.setAll(resourceBuilder.generateContext(e.getClass())));
        return mergedContext;
    }

    protected String getType(T e) {
        return typeSupplier.apply(e);
    }

    protected Iterable<JsonldResource> buildElements(Iterable<T> elements) {
        ArrayList<JsonldResource> list = new ArrayList<>();
        elements.forEach(e -> list.add(new JsonldResource(e,null, resourceBuilder.getType(e),  resourceBuilder.getId(e))));
        return list;
    }


}
