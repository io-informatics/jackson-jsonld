package ioinformarics.oss.jackson.module.jsonld;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldPropertyType;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

/**
 * @author Alexander De Leon
 */
public class JsonldResourceBuilder<T> {

	private String _context;
	private String _type;
	private String _id;
	private Function<T, String> _idSupplier;

	public JsonldResourceBuilder(){
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

	public JsonldResource build(T scopedObj, boolean graphTriggered) {

		JsonNode context = graphTriggered? null : getContext(scopedObj).orElse(null);

		if(scopedObj == null){
			return null;
		}
		if(Map.class.isAssignableFrom(scopedObj.getClass())){
			return new MapJsonldResource((Map)scopedObj, context, getType(scopedObj), getId(scopedObj));
		}
		return new BeanJsonldResource(scopedObj,  context, getType(scopedObj), getId(scopedObj));
	}

	protected Optional<JsonNode> getContext(T scopedObj) {
		return JsonldContextFactory.multiContext(Optional.ofNullable(_context), JsonldContextFactory.fromAnnotations(scopedObj));
	}

	public String getId(T scopedObj){
		return Optional.ofNullable(_id).orElse(Optional.ofNullable(_idSupplier).map(f -> f.apply(scopedObj)).orElse(null));
	}

	public String getType(T scopedObj) {
		return Optional.ofNullable(_type).orElse(dynamicTypeLookup(scopedObj.getClass()));
	}

	static String dynamicTypeLookup(Class<?> objType){
		if( objType.isInstance(JsonldType.class) )
		{
			JsonldType type = objType.getAnnotation(JsonldType.class);
			return type == null? null : type.value();
		}
		else if( objType.isInstance(JsonldPropertyType.class) )
		{
			JsonldPropertyType type = objType.getAnnotation(JsonldPropertyType.class);
			return type == null? null : type.id();
		}
		return null;
	}

}
