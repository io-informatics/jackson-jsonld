package ioinformarics.oss.jackson.module.jsonld.annotation;

import java.lang.annotation.*;

/**
 * @author Alexander De Leon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface JsonldType {
    String value();
}
