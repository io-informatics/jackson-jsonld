package ioinformarics.oss.jackson.module.jsonld;

/**
 * @author Alexander De Leon <me@alexdeleon.name>
 */
public interface JsonldResource {
    public interface Builder {
        static <T> JsonldResourceBuilder<T> create() {
            return new JsonldResourceBuilder<T>();
        }
    }
}
