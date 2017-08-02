package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alexander De Leon
 */
public class JsonldContextFactory {

    public static Optional<ObjectNode> fromAnnotations(Object instance) {
        return fromAnnotations(instance.getClass());
    }

    public static Optional<ObjectNode> fromAnnotations(Iterable<?> instances) {
        ObjectNode mergedContext = JsonNodeFactory.withExactBigDecimals(true).objectNode();
        instances.forEach(e -> fromAnnotations(e).map(mergedContext::setAll));
        return mergedContext.size() != 0 ? Optional.of(mergedContext) : Optional.empty();
    }

    public static Optional<ObjectNode> fromAnnotations(Class<?> objType) {
        ObjectNode generatedContext = JsonNodeFactory.withExactBigDecimals(true).objectNode();
        //TODO: This is bad...it does not consider other Jackson annotations. Need to use a AnnotationIntrospector?
        final Map<String, TextNode> fieldContexts = generateContextsForFields(objType);
        fieldContexts.forEach(generatedContext::set);
        //add links
        JsonldLink[] links = objType.getAnnotationsByType(JsonldLink.class);
        if (links != null) {
            for (int i = 0; i < links.length; i++) {
                com.fasterxml.jackson.databind.node.ObjectNode linkNode = JsonNodeFactory.withExactBigDecimals(true)
                                                                                         .objectNode();
                linkNode.set("@id", new TextNode(links[i].rel()));
                linkNode.set("@type", new TextNode("@id"));
                generatedContext.set(links[i].name(), linkNode);
            }
        }
        //Return absent optional if context is empty
        return generatedContext.size() != 0 ? Optional.of(generatedContext) : Optional.empty();
    }

    private static Map<String, TextNode> generateContextsForFields(Class<?> objType) {
        final Map<String, TextNode> contexts = new HashMap<>();
        Class<?> currentClass = objType;
        while (currentClass != null && !currentClass.equals(Object.class)) {
            final Field[] fields = currentClass.getDeclaredFields();
            for (Field f : fields) {
                final JsonldProperty jsonldProperty = f.getAnnotation(JsonldProperty.class);
                // Most concrete field overrides any field with the same name defined higher up the hierarchy
                if (jsonldProperty != null && !contexts.containsKey(f.getName())) {
                    contexts.put(f.getName(), TextNode.valueOf(jsonldProperty.value()));
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return contexts;
    }


    public static Optional<JsonNode> multiContext(Optional<String> externalContext,
                                                  Optional<ObjectNode> internalContext) {
        if (internalContext.isPresent()) {
            return externalContext.isPresent() ?
                   Optional.of((JsonNode) buildMultiContext(externalContext.get(), internalContext.get())) :
                   internalContext.map(it -> (JsonNode) it);
        }
        return externalContext.map(TextNode::valueOf);
    }

    private static ArrayNode buildMultiContext(String context, JsonNode generatedContext) {
        return JsonNodeFactory.withExactBigDecimals(true).arrayNode().add(context).add(generatedContext);
    }
}
