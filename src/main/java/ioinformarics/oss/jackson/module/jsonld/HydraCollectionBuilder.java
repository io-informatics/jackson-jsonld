package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class HydraCollectionBuilder<T> extends JsonldGraphBuilder<T> {

    public JsonldResource build(Iterable<T> elements) {
        return new HydraCollection(buildElements(elements), resourceBuilder.buildMultiContext(context, buildContext(elements)), "hydra:Collection", graphId);
    }

    @Override
    protected ObjectNode buildContext(Iterable<T> elements) {
        ObjectNode context = super.buildContext(elements);
        context.setAll(JsonldResourceBuilder.generateContext(HydraCollection.CollectionContainer.class));
        return context;
    }
}
