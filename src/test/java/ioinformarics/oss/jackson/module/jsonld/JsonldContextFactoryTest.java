package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonldContextFactoryTest {

    @Test
    public void fromAnnotationsGeneratesContextsForAncestorsAsWell() {
        final Child child = new Child();
        final Optional<ObjectNode> result = JsonldContextFactory.fromAnnotations(child);
        assertTrue(result.isPresent());
        final Iterator<String> fields = result.get().fieldNames();
        int counter = 0;
        final Set<String> fieldNames = new HashSet<>(Arrays.asList("name", "description", "version"));
        while (fields.hasNext()) {
            counter++;
            final String fieldName = fields.next();
            assertTrue(fieldNames.contains(fieldName));
        }
        assertEquals(fieldNames.size(), counter);
    }

    @JsonldType("http://example.com/schema#Parent")
    public static class Parent {

        @JsonldId
        Integer id;

        @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#label")
        String name;
    }

    @JsonldType("http://example.com/schema#Child")
    public static class Child extends Parent {

        @JsonldProperty("http://www.w3.org/2000/01/rdf-schema#comment")
        String description;

        @JsonldProperty("http://example.com/schema#version")
        Long version;
    }
}