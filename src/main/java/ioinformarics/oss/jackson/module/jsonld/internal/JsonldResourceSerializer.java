package ioinformarics.oss.jackson.module.jsonld.internal;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.util.JsonldResourceUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
public class JsonldResourceSerializer extends BeanSerializer {

    public JsonldResourceSerializer(BeanSerializerBase src) {
        super(src);
    }

    @Override
    protected void serializeFields(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        Optional<String> type = JsonldResourceUtils.dynamicTypeLookup(bean.getClass());
        if(type.isPresent()) {
            jgen.writeStringField("@type", type.get());
        }
        Optional<ObjectNode> context = JsonldResourceUtils.getContext(bean);
        if(context.isPresent()) {
            jgen.writeObjectField("@context", context.get());
        }
        super.serializeFields(bean, jgen, provider);
        getLinks(bean).ifPresent(linksMap ->
                linksMap.forEach((key, value) -> {
                    try {
                        jgen.writeStringField(key, value);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    protected Optional<Map<String,String>> getLinks(Object resource) {
        Map<String,String> linksNodes = null;
        Class<?> beanType = resource.getClass();
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
