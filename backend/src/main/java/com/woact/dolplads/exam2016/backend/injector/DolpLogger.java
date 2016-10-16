package com.woact.dolplads.injector;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Stereotype;
import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * Created by dolplads on 12/10/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DolpLogger {
}
