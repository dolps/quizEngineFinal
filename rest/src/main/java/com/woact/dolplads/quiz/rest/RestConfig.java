package com.woact.dolplads.quiz.rest;

import com.woact.dolplads.quiz.rest.resource.QuizResource;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dolplads on 07/11/2016.
 */

@ApplicationPath("/api")
public class RestConfig extends Application {

    public RestConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/quiz/api");
        beanConfig.setResourcePackage("com.woact.dolplads.quiz");

        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(QuizResource.class);
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        resources.add(ObjectMapperContextResolver.class);

        return resources;
    }
}
