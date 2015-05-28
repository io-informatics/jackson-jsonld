package ioinformarics.oss.jackson.module.jsonld.annotation;

import java.lang.annotation.*;

/**
 * @author Alexander De Leon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Inherited
public @interface JsonldProperty {
    String value();
}
