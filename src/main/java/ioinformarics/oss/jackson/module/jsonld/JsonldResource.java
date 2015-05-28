package ioinformarics.oss.jackson.module.jsonld;

/**
 * @author Alexander De Leon
 */
public interface JsonldResource {
    public interface Builder {
        static <T> JsonldResourceBuilder<T> create() {
            return new JsonldResourceBuilder<T>();
        }
    }
}
