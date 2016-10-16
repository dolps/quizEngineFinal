package com.woact.dolplads.exam2016.backend.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by dolplads on 12/10/2016.
 */
public class InjectorImplementation {
    public <T> T createInstance(Class<T> klass) throws IllegalArgumentException {

        if (klass == null) {
            throw new IllegalArgumentException("Null input");
        }

        T t;

        try {
            t = klass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to instantiate object for " + klass.getName() + " : " + e.getMessage());
        }


        for (Field field : klass.getDeclaredFields()) {

            Annotation annotation = field.getAnnotation(DolpLogger.class);
            if (annotation == null) {
                continue;
            }

            try {
                Class<?> typeToInject = field.getType();
                Object objectToInject = createInstance(typeToInject); //Beware infinite recursion...
                field.setAccessible(true); //needed, otherwise fails on private fields
                field.set(t, objectToInject);
            } catch (Exception e) {
                throw new IllegalArgumentException("Not possible to inject " + field.getName() + " due to: " + e.getMessage());
            }
        }

        return t;
    }
}
