package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
@JsonPropertyOrder({"@context", "@type", "@id"})
public class JsonldResource {

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final Object scopedObj;

    @JsonProperty("@context")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final JsonNode context;

    @JsonProperty("@type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final String type;

    @JsonProperty("@id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final String id;


    JsonldResource(Object scopedObj, JsonNode context, String type, String id) {
        this.scopedObj = scopedObj;
        this.context = context;
        this.type = type;
        this.id = id;
    }

    public interface Builder {
        static <T> JsonldResourceBuilder<T> create() {
            return new JsonldResourceBuilder<T>();
        }
    }
}
