package ioinformarics.oss.jackson.module.jsonld.annotation;

import java.lang.annotation.*;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface JsonldNamespace {
    String name();
    String uri();
    boolean applyToProperties() default true;
}
