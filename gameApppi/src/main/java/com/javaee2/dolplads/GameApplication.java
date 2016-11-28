package com.javaee2.dolplads;

import com.javaee2.dolplads.core.Game;
import com.javaee2.dolplads.client.QuizServiceResource;
import com.javaee2.dolplads.db.GameDAO;
import com.javaee2.dolplads.health.TemplateHealthCheck;
import com.javaee2.dolplads.resources.GameResource;
import com.javaee2.dolplads.resources.HelloWorldResource;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


import javax.ws.rs.client.Client;

public class GameApplication extends Application<GameConfiguration> {

    public static void main(final String[] args) throws Exception {
        new GameApplication().run(args);
    }

    private final HibernateBundle<GameConfiguration> hibernate = new ScanningHibernateBundle<GameConfiguration>("com.javaee2.dolplads/core") {
        @Override
        public DataSourceFactory getDataSourceFactory(GameConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public void initialize(final Bootstrap<GameConfiguration> bootstrap) {
        // TODO: application initialization
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final GameConfiguration configuration,
                    final Environment environment) throws Exception {
        final HelloWorldResource res = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
        final GameResource gameResource = new GameResource();
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(res);
        environment.jersey().register(gameResource);


        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());

        final GameDAO dao = new GameDAO(hibernate.getSessionFactory());

        environment.jersey().register(new QuizServiceResource(client, dao));
    }

}
