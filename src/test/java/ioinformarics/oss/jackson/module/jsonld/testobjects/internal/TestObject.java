package ioinformarics.oss.jackson.module.jsonld.testobjects.internal;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
@JsonldResource
public class TestObject {

    @JsonldId
    public String id;

    @JsonldProperty("http://schema.org/url")
    public String url;

    @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#label")
    String name;
}
