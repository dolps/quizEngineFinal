package com.woact.dolplads.exam2016.backend.annotations;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 */
public class LoggerInjector implements Serializable {

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
                .getName());
    }
}
