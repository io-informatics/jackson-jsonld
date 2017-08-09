package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Alexander De Leon
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

    public JsonldResource build(T ... elements) {
        return build(Arrays.asList(elements));
    }

    public JsonldResource build(Iterable<T> elements) {
        return new JsonldGraph(elements, Optional.ofNullable(context).map(c -> TextNode.valueOf(c)).orElse(null), graphType, graphId);

    }

    protected String getType(T e) {
        return typeSupplier.apply(e);
    }



}
