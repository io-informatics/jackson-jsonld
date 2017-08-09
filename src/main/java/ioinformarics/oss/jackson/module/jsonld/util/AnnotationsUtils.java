package ioinformarics.oss.jackson.module.jsonld.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
public abstract class AnnotationsUtils {

    public static boolean isAnnotationPresent(Class<?> type, Class<? extends Annotation> annotationType) {
        return isAnnotationPresent(type, annotationType, new ArrayList<>());
    }

    protected static boolean isAnnotationPresent(Class<?> type, Class<? extends Annotation> annotationType, List<Class<?>> ignore) {
        if(type.isAnnotationPresent(annotationType)) {
            return true;
        }
        if(type.getAnnotations().length == 0) {
            return false;
        }
        for(Annotation a : type.getAnnotations()) {
            if(!ignore.contains(a.annotationType())) {
                ignore.add(type);
                if(isAnnotationPresent(a.annotationType(), annotationType, ignore)) {
                    return true;
                }
            }
        }
        return false;
    }

}
