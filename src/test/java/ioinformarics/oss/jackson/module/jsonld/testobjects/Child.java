package ioinformarics.oss.jackson.module.jsonld.testobjects;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
@JsonldType("http://example.com/schema#Child")
public class Child extends Parent {

    @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#comment")
    String description;

    @JsonldProperty("http://example.com/schema#version")
    Long version;
}
