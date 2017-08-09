package ioinformarics.oss.jackson.module.jsonld.testobjects;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
@JsonldType("http://example.com/schema#Parent")
public class Parent {

    @JsonldId
    Integer id;

    @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#label")
    String name;
}
