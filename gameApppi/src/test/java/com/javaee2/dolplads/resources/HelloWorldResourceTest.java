package com.javaee2.dolplads.resources;

import com.javaee2.dolplads.GameApplication;
import com.javaee2.dolplads.GameConfiguration;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dolplads on 25/11/2016.
 */
public class HelloWorldResourceTest {

    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final GameApplication application = new GameApplication();
    private final GameConfiguration config = new GameConfiguration();

    @Before
    public void setup() throws Exception {
        when(environment.jersey()).thenReturn(jersey);
    }

    @Test
    public void buildsAThingResource() throws Exception {
        application.run(config, environment);
        config.setTemplate("template");
        verify(jersey).register(isA(HelloWorldResource.class));
    }
}
