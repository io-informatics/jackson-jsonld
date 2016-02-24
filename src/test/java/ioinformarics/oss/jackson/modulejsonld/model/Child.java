package ioinformarics.oss.jackson.modulejsonld.model;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://example.com/schema#Child")
public class Child extends AbstractParent {

    public static final String[] JSON_PROPERTY_FIELDS = {"description", "version"};

    @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#comment")
    private String description;

    @JsonldProperty("http://example.com/schema#version")
    private Long version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
