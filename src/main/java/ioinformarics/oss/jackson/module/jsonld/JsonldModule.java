package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldBeanDeserializerModifier;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldPropertyNamingStrategy;

import java.util.function.Supplier;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public class JsonldModule extends SimpleModule {

    public JsonldModule(Supplier<Object> contextSupplier){
        setNamingStrategy(new JsonldPropertyNamingStrategy());
        setDeserializerModifier(new JsonldBeanDeserializerModifier(contextSupplier));
    }
}
