package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.modulejsonld.model.AbstractParent;
import ioinformarics.oss.jackson.modulejsonld.model.Child;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

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
        while(fields.hasNext()) {
            counter++;
            final String fieldName = fields.next();
            final int childIndex = Arrays.binarySearch(Child.JSON_PROPERTY_FIELDS, fieldName);
            final int parentIndex = Arrays.binarySearch(AbstractParent.JSON_PROPERTY_FIELDS, fieldName);
            assertTrue(childIndex != -1 || parentIndex != -1);
        }
        assertEquals(Child.JSON_PROPERTY_FIELDS.length + AbstractParent.JSON_PROPERTY_FIELDS.length, counter);
    }
}