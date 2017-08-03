package ioinformarics.oss.jackson.module.jsonld.internal;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.util.AnnotationsUtils;

/**
 * @author Alexander De Leon
 */
public class JsonldResourceSerializerModifier extends BeanSerializerModifier {


    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if(AnnotationsUtils.isAnnotationPresent(beanDesc.getBeanClass(), JsonldResource.class) && serializer instanceof BeanSerializerBase){
            return new JsonldResourceSerializer((BeanSerializerBase) serializer);
        }
        return serializer;
    }


}
