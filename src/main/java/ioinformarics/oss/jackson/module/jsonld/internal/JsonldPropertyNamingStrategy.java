package ioinformarics.oss.jackson.module.jsonld.internal;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldPropertyType;

import java.util.Optional;

/**
 * @author Alexander De Leon
 */
public class JsonldPropertyNamingStrategy extends PropertyNamingStrategy {

    @Override
    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        String name = config instanceof DeserializationConfig? jsonldName(field): null;
        return Optional.ofNullable(name).orElse(super.nameForField(config, field, defaultName));
    }

    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        String name = config instanceof DeserializationConfig? jsonldName(method): null;
        return Optional.ofNullable(name).orElse(super.nameForGetterMethod(config, method, defaultName));
    }

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        String name = config instanceof DeserializationConfig? jsonldName(method): null;
        return Optional.ofNullable(name).orElse(super.nameForSetterMethod(config, method, defaultName));
    }

    @Override
    public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
        String name = config instanceof DeserializationConfig? jsonldName(ctorParam): null;
        return Optional.ofNullable(name).orElse(super.nameForConstructorParameter(config, ctorParam, defaultName));
    }

    private String jsonldName(AnnotatedMember member){
        JsonldProperty jsonldProperty = member.getAnnotation(JsonldProperty.class);
        return jsonldProperty != null? jsonldProperty.value() : null;
    }
}
