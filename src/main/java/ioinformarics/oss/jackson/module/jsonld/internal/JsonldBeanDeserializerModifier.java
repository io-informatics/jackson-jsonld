package ioinformarics.oss.jackson.module.jsonld.internal;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Alexander De Leon
 */
public class JsonldBeanDeserializerModifier extends BeanDeserializerModifier {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Supplier<Object> contextSupplier;

    public JsonldBeanDeserializerModifier(Supplier<Object> contextSupplier){
        this.contextSupplier = contextSupplier;
    }

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        if(beanDesc.getClassInfo().hasAnnotation(JsonldType.class)){
            return new JsonldDelegatingDeserializer(deserializer);
        }
        return  deserializer;
    }


    class JsonldDelegatingDeserializer extends DelegatingDeserializer {

        public JsonldDelegatingDeserializer(JsonDeserializer<?> delegatee) {
            super(delegatee);
        }

        @Override
        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Object input = parseJsonldObject(jp);
            if(input == null) {
                return super.deserialize(jp, ctxt);
            }
            try {
                JsonLdOptions options = new JsonLdOptions();
                Object context = contextSupplier.get();
                if(context instanceof JsonNode){
                    context = parseJsonldObject(initParser(mapper.treeAsTokens((JsonNode)context)));
                }
                Object obj = JsonLdProcessor.compact(input, context, options);
                JsonParser newParser = initParser(mapper.getFactory().createParser(mapper.valueToTree(obj).toString()));
                return super.deserialize(newParser, ctxt);
            } catch (JsonLdError e) {
                throw new JsonGenerationException("Failed to flatten json-ld", e);
            }
        }

        @Override
        protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
            return new JsonldDelegatingDeserializer(newDelegatee);
        }

        private Object parseJsonldObject(JsonParser jp) throws IOException {
            Object rval = null;
            final JsonToken initialToken = jp.getCurrentToken();

            if (initialToken == JsonToken.START_ARRAY) {
                jp.setCodec(mapper);
                rval = jp.readValueAs(List.class);
            } else if (initialToken == JsonToken.START_OBJECT) {
                jp.setCodec(mapper);
                rval = jp.readValueAs(Map.class);
            } else if (initialToken == JsonToken.VALUE_STRING) {
                jp.setCodec(mapper);
                rval = jp.readValueAs(String.class);
            } else if (initialToken == JsonToken.VALUE_FALSE || initialToken == JsonToken.VALUE_TRUE) {
                jp.setCodec(mapper);
                rval = jp.readValueAs(Boolean.class);
            } else if (initialToken == JsonToken.VALUE_NUMBER_FLOAT
                    || initialToken == JsonToken.VALUE_NUMBER_INT) {
                jp.setCodec(mapper);
                rval = jp.readValueAs(Number.class);
            } else if (initialToken == JsonToken.VALUE_NULL) {
                rval = null;
            }
            return rval;
        }
    }

    private static JsonParser initParser(JsonParser jp) throws IOException{
        jp.nextToken(); //put the parser at the start token
        return jp;
    }
}
