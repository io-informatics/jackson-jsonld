package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

import java.util.Optional;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class HydraCollectionBuilder<T> extends JsonldGraphBuilder<T> {

    private  Long totalItems;
    private  Integer itemsPerPage;
    private  String firstPage;
    private  String nextPage;
    private  String previousPage;
    private  String lastPage;
    private  boolean isPaged = false;


    public HydraCollectionBuilder<T> totalItems(Long totalItems) {
        this.totalItems = totalItems;
        isPaged = true;
        return this;
    }

    public HydraCollectionBuilder<T> itemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        isPaged = true;
        return this;
    }

    public HydraCollectionBuilder<T> firstPage(String firstPage) {
        this.firstPage = firstPage;
        isPaged = true;
        return this;
    }

    public HydraCollectionBuilder<T> nextPage(String nextPage) {
        this.nextPage = nextPage;
        isPaged = true;
        return this;
    }

    public HydraCollectionBuilder<T> previousPage(String previousPage) {
        this.previousPage = previousPage;
        isPaged = true;
        return this;
    }

    public HydraCollectionBuilder<T> lastPage(String lastPage) {
        this.lastPage = lastPage;
        isPaged = true;
        return this;
    }

    public JsonldResource build(Iterable<T> elements) {
        return new HydraCollection(buildElements(elements), buildContext(elements).orElse(null),
                isPaged? "hydra:PagedCollection": "hydra:Collection", graphId, totalItems, itemsPerPage, firstPage, nextPage, previousPage, lastPage);
    }

    protected Optional<JsonNode> buildContext(Iterable<T> elements) {
        Optional<ObjectNode> hydraContext = JsonldContextFactory.fromAnnotations(HydraCollection.CollectionContainer.class);
        Optional<ObjectNode> mergedContext = hydraContext.map(it -> (ObjectNode)it.setAll(JsonldContextFactory.fromAnnotations(elements).orElse(emptyNode())));
        return JsonldContextFactory.multiContext(Optional.ofNullable(context), mergedContext);
    }

    private ObjectNode emptyNode() {
        return JsonNodeFactory.withExactBigDecimals(true).objectNode();
    }
}
