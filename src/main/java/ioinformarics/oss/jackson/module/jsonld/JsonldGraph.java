package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class JsonldGraph extends BeanJsonldResource {


    JsonldGraph(Iterable<JsonldResource> graph, JsonNode context, String type, String id) {
        super(new JsonldGraphContainer(graph), context, type, id);
    }


    static class JsonldGraphContainer {
        @JsonProperty("@graph")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final Iterable<?> graph;

        JsonldGraphContainer(Iterable<?> graph) {
            this.graph = graph;
        }
    }

    public interface Builder {
        static <T> JsonldGraphBuilder<T> create() {
            return new JsonldGraphBuilder<T>();
        }
    }
}



