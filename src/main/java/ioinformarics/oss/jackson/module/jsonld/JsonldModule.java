package ioinformarics.oss.jackson.module.jsonld;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldBeanDeserializerModifier;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldPropertyNamingStrategy;
import ioinformarics.oss.jackson.module.jsonld.internal.JsonldResourceSerializerModifier;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Alexander De Leon
 */
public class JsonldModule extends SimpleModule {

    /**
     * Create a JsonldModule configured with a function which supplies the @context structure of your application.
     * This constructor is useful if you want to construct your context dynamically. If the context is static is better to use the other constructors of this class.
     *
     * @param contextSupplier a function from () to Object which supplies the default Jsonld context of your application.
     */
    public JsonldModule(Supplier<Object> contextSupplier){
        setNamingStrategy(new JsonldPropertyNamingStrategy());
        setDeserializerModifier(new JsonldBeanDeserializerModifier(contextSupplier));
        setSerializerModifier(new JsonldResourceSerializerModifier());
    }

    /**
     * Creates a JsonldModule configured with an empty application context.
     */
    public JsonldModule(){
        this(() -> Collections.emptyMap());
    }

    /**
     * Creates a JsonldModule configured with a Jsonld Context specified in the context argument (Json Object)
     * @param context an ObjectNode with the structure of your default @context
     */
    public JsonldModule(ObjectNode context){
        this(() -> context);
    }

    /**
     * Creates a JsonldModule configured with a Jsonld Context specified in the context argument (Map)
     * @param context a Map with the structure of your default @context 
     */
    public JsonldModule(Map<String, Object> context){
        this(() -> context);
    }
}
