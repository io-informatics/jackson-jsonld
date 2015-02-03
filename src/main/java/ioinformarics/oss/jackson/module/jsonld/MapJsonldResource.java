package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class MapJsonldResource extends HashMap<Object, Object> implements JsonldResource {
    public MapJsonldResource(Map map) {
        super(map);
    }

    public MapJsonldResource(Map scopedObj, JsonNode context, String type, String id){
        this(scopedObj);
        if (isNotEmpty(context)) {
            put("@context", context);
        }
        if(id != null) {
            put("@id", id);
        }
        if(type != null) {
            put("@type", type);
        }
    }

    private boolean isNotEmpty(JsonNode context) {
        if(context == null){
            return false;
        }
        if(context.isArray()){
            return ((ArrayNode)context).size() != 0;
        }
        if(context.isObject()){
            return ((ObjectNode)context).size() != 0;
        }
        return true;
    }

}

