package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Alexander De Leon
 */
public class JsonldModuleTest {


    @Test
    public void testSerializeBean() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonldModule());

        Person alex = new Person();
        alex.id = "mailto:me@alexdeleon.name";
        alex.name = "Alex De Leon";
        alex.jobtitle = "Software Developer";
        alex.url = "http://alexdeleon.name";

        JsonldResourceBuilder builder = JsonldResource.Builder.create();
        builder.context("http://json-ld.org/contexts/person.jsonld");
        objectMapper.writer().writeValue(System.out, builder.build(alex));

    }


    @JsonldType("http://schema.org/Person")
    public class Person {
        @JsonldId
        public  String id;
        @JsonldProperty("http://schema.org/name")
        public String name;
        @JsonldProperty("http://schema.org/jobTitle")
        public String jobtitle;
        @JsonldProperty("http://schema.org/url")
        public String url;

    }

}
