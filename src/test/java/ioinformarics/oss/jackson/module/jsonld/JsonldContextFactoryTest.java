package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ioinformarics.oss.jackson.module.jsonld.testobjects.Child;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

import java.util.*;


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

    @Test
    public void testContextForPackage() {
        ObjectNode context = JsonldContextFactory.fromPackage("ioinformarics.oss.jackson.module.jsonld.testobjects");
        System.out.println("Context: "+context);
        ObjectNode innerContext = (ObjectNode) context.get("@context");
        assertNotNull(innerContext);
        // from: ioinformarics.oss.jackson.module.jsonld.testobjects.Parent
        assertThat(innerContext.get("name"), is(TextNode.valueOf("http://www.w3.org/2000/01/rdf-schema#label")));
        // from: ioinformarics.oss.jackson.module.jsonld.testobjects.internal.TestObject
        assertThat(innerContext.get("url"), is(TextNode.valueOf("http://schema.org/url")));
    }

}