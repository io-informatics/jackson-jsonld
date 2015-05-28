package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldBeanDeserializerModifier;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldPropertyNamingStrategy;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldResourceSerializerModifier;

import java.util.function.Supplier;

/**
 * @author Alexander De Leon
 */
public class JsonldModule extends SimpleModule {

    public JsonldModule(Supplier<Object> contextSupplier){
        setNamingStrategy(new JsonldPropertyNamingStrategy());
        setDeserializerModifier(new JsonldBeanDeserializerModifier(contextSupplier));
        setSerializerModifier(new JsonldResourceSerializerModifier());
    }
}
