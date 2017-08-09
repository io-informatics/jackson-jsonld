package ioinformarics.oss.jackson.module.jsonld.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.module.jsonld.JsonldContextFactory;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldTypeFromJavaClass;
import ioinformarics.oss.jackson.module.jsonld.internal.AnnotationConstants;

import java.util.Optional;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
public abstract class JsonldResourceUtils {

    public static Optional<ObjectNode> getContext(Object scopedObj) {
        return JsonldContextFactory.fromAnnotations(scopedObj);
    }

    public static Optional<String> dynamicTypeLookup(Class<?> objType){
        Optional<String> typeFromAnnotation = Optional.ofNullable(objType.getAnnotation(JsonldType.class))
                    .map(JsonldType::value);
        return typeFromAnnotation.isPresent() ? typeFromAnnotation : typeFromJavaClass(objType);
    }

    private static Optional<String> typeFromJavaClass(Class<?> objType) {
        return Optional.ofNullable(objType.getAnnotation(JsonldTypeFromJavaClass.class))
                .map((t) -> {
                    String prefix = t.namespace();
                    if(prefix.equals(AnnotationConstants.UNASSIGNED)) {
                        prefix = t.namespacePrefix().equals(AnnotationConstants.UNASSIGNED) ? "" : t.namespacePrefix() + ":";
                    }
                    return prefix + objType.getSimpleName();

                });
    }
}
