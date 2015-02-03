package ioinformarics.oss.jackson.module.jsonld.annotation;

import java.lang.annotation.*;
import java.util.Map;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Repeatable(JsonldLinks.class)
public @interface JsonldLink {
    String rel();
    String name();
    String href();
}
