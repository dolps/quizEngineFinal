package com.javaee2.dolplads.client;

import com.javaee2.dolplads.GameConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.client.Client;

/**
 * Created by dolplads on 27/11/2016.
 */
public class IntegrationTestUtils {
    public static Client buildClient(DropwizardAppRule<GameConfiguration> rule) {
        return new JerseyClientBuilder(rule.getEnvironment()).build("test client");
    }
}
