package com.javaee2.dolplads;

import com.javaee2.dolplads.health.TemplateHealthCheck;
import com.javaee2.dolplads.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class GameApplication extends Application<GameConfiguration> {

    public static void main(final String[] args) throws Exception {
        new GameApplication().run(args);
    }

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public void initialize(final Bootstrap<GameConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final GameConfiguration configuration,
                    final Environment environment) {
        final HelloWorldResource res = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(res);


        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        environment.jersey().register(client);
        //client.target("").request("").accept().buildGet().submit().get().readEntity();
    }

}
