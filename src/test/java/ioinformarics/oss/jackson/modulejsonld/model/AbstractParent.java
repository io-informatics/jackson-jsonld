package ioinformarics.oss.jackson.modulejsonld.model;


import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

public abstract class AbstractParent {

    public static final String[] JSON_PROPERTY_FIELDS = {"name"};

    @JsonldId
    private Integer id;

    @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#label")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
