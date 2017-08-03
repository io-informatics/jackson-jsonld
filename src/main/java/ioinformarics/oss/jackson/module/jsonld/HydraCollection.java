package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

/**
 * @author Alexander De Leon
 */
public class HydraCollection extends BeanJsonldResource {

    HydraCollection(Iterable<?> graph, JsonNode context, String type, String id) {
        super(new CollectionContainer(graph), context, type, id);
    }

    HydraCollection(Iterable<?> graph,
                    JsonNode context,
                    String type,
                    String id,
                    Long totalItems,
                    Integer itemsPerPage,
                    String firstPage,
                    String nextPage,
                    String previousPage,
                    String lastPage) {
        super(new CollectionContainer(graph, totalItems, itemsPerPage, firstPage, nextPage, previousPage, lastPage), context, type, id);
    }

    static class CollectionContainer {
        @JsonldProperty("hydra:member")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final Iterable<?> member;

        @JsonldProperty("hydra:totalItems")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final Long totalItems;

        @JsonldProperty("hydra:itemsPerPage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final Integer itemsPerPage;

        @JsonldProperty("hydra:firstPage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final String firstPage;

        @JsonldProperty("hydra:nextPage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final String nextPage;

        @JsonldProperty("hydra:previousPage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final String previousPage;

        @JsonldProperty("hydra:lastPage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public final String lastPage;

        CollectionContainer(Iterable<?> member) {
            this(member, null,null,null,null,null,null);
        }

        public CollectionContainer(Iterable<?> member, Long totalItems, Integer itemsPerPage, String firstPage, String nextPage, String previousPage, String lastPage) {
            this.member = member;
            this.totalItems = totalItems;
            this.itemsPerPage = itemsPerPage;
            this.firstPage = firstPage;
            this.nextPage = nextPage;
            this.previousPage = previousPage;
            this.lastPage = lastPage;
        }
    }

    public interface Builder {
        static <T> HydraCollectionBuilder<T> create() {
            return new HydraCollectionBuilder<T>();
        }
    }

}
