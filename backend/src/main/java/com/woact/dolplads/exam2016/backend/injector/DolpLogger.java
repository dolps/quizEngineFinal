package com.woact.dolplads.exam2016.backend.injector;

import java.lang.annotation.*;

/**
 * Created by dolplads on 12/10/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DolpLogger {
}
