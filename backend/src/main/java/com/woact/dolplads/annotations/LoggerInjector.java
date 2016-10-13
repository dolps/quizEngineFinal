package com.woact.dolplads.annotations;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 */
@Singleton
public class LoggerInjector implements Serializable {

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
                .getName());
    }
}
