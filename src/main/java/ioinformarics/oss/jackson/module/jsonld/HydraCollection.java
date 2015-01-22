package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class HydraCollection extends JsonldResource {

    HydraCollection(Iterable<JsonldResource> graph, JsonNode context, String type, String id) {
        super(new CollectionContainer(graph), context, type, id);
    }

    static class CollectionContainer {
        @JsonldProperty("hydra:member")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final Iterable<?> member;

        CollectionContainer(Iterable<?> member) {
            this.member = member;
        }
    }

    public interface Builder {
        static <T> HydraCollectionBuilder<T> create() {
            return new HydraCollectionBuilder<T>();
        }
    }

}
