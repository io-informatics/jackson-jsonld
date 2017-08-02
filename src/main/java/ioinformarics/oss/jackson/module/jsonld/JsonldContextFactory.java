package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldNamespace;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Stream;

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
        generateNamespaces(objType).forEach((name, uri) -> generatedContext.set(name, new TextNode(uri)));
        //TODO: This is bad...it does not consider other Jackson annotations. Need to use a AnnotationIntrospector?
        final Map<String, JsonNode> fieldContexts = generateContextsForFields(objType);
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

    private static Map<String, JsonNode> generateContextsForFields(Class<?> objType) {
        final Map<String, JsonNode> contexts = new HashMap<>();
        Class<?> currentClass = objType;
        Optional<JsonldNamespace> namespace = Optional.ofNullable(currentClass.getAnnotation(JsonldNamespace.class));
        while (currentClass != null && !currentClass.equals(Object.class)) {
            final Field[] fields = currentClass.getDeclaredFields();
            for (Field f : fields) {
                final JsonldProperty jsonldProperty = f.getAnnotation(JsonldProperty.class);
                Optional<String> propertyId = Optional.empty();
                // Most concrete field overrides any field with the same name defined higher up the hierarchy
                if (jsonldProperty != null && !contexts.containsKey(f.getName())) {
                    propertyId = Optional.of(jsonldProperty.value());
                }
                else if(jsonldProperty == null && namespace.map(JsonldNamespace::applyToProperties).orElse(false)) {
                    propertyId = Optional.of(namespace.get().name() + ":" + f.getName());
                }
                propertyId.ifPresent((id) -> {
                    if(isRelation(f)) {
                        ObjectNode node = JsonNodeFactory.withExactBigDecimals(true).objectNode();
                        node.set("@id", TextNode.valueOf(id));
                        node.set("@type", TextNode.valueOf("@id"));
                        contexts.put(f.getName(), node);
                    }
                    else {
                        contexts.put(f.getName(), TextNode.valueOf(id));
                    }
                });
            }
            currentClass = currentClass.getSuperclass();
            if(!namespace.isPresent()) {
                namespace = Optional.ofNullable(currentClass.getAnnotation(JsonldNamespace.class));
            }
        }
        return contexts;
    }

    private static boolean isRelation(Field field) {
        Class<?> type = field.getType();
        if(Collection.class.isAssignableFrom(type)) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        if(type.isArray()) {
            type = type.getComponentType();
        }
        return Stream.concat(Stream.of(type),
                Stream.concat(ClassUtils.getAllSuperclasses(type).stream(), ClassUtils.getAllInterfaces(type).stream()))
                .flatMap(currentClass -> Stream.concat(
                        Stream.of(currentClass.getDeclaredFields()),
                        Stream.of(currentClass.getDeclaredMethods())
                ))
                .anyMatch(p -> p.getAnnotation(JsonldId.class) != null);
    }

    private static Map<String, String> generateNamespaces(Class<?> objType) {
        JsonldNamespace[] namespaceAnnotations = objType.getAnnotationsByType(JsonldNamespace.class);
        Map<String, String> namespaces = new HashMap<>(namespaceAnnotations.length);
        Arrays.asList(namespaceAnnotations).forEach((ns) -> namespaces.put(ns.name(), ns.uri()));
        return namespaces;
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
