package ioinformarics.oss.jackson.module.jsonld.annotation;

import ioinformarics.oss.jackson.module.jsonld.internal.AnnotationConstants;

import java.lang.annotation.*;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface JsonldTypeFromJavaClass {
    String namespace() default AnnotationConstants.UNASSIGNED;
    String namespacePrefix() default AnnotationConstants.UNASSIGNED;
}
