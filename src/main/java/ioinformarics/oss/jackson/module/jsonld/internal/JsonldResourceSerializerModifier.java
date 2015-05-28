package ioinformarics.oss.jackson.module.jsonld.internal;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import ioinformarics.oss.jackson.module.jsonld.BeanJsonldResource;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;

import java.io.IOException;
import java.util.*;

/**
 * @author Alexander De Leon
 */
public class JsonldResourceSerializerModifier extends BeanSerializerModifier {


    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if(BeanJsonldResource.class.isAssignableFrom(beanDesc.getBeanClass()) && serializer instanceof BeanSerializerBase){
            return new BeanSerializer((BeanSerializerBase) serializer) {
                @Override
                protected void serializeFields(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
                    super.serializeFields(bean, jgen, provider);
                    getLinks((BeanJsonldResource)bean).ifPresent(linksMap ->
                            linksMap.forEach((key, value) -> {
                                try {
                                    jgen.writeFieldName(key);
                                    jgen.writeString(value);
                                } catch (Exception e) {
                                }
                            }));
                }
            };
        }
        return serializer;
    }

    protected Optional<Map<String,String>> getLinks(BeanJsonldResource resource) {
        Map<String,String> linksNodes = null;
        Class<?> beanType = resource.scopedObj.getClass();
        JsonldLink[] links = beanType.getAnnotationsByType(JsonldLink.class);
        if(links != null){
            linksNodes = new HashMap<>(links.length);
            for(int i=0; i < links.length; i++){
                linksNodes.put(links[i].name(), links[i].href());
            }
        }
        return Optional.ofNullable(linksNodes);
    }
}
