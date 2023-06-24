package io.github.dropwizard;

import io.github.dropwizard.resources.SimpleMessageService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConnectionTestApplication extends Application<ConnectionTestConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ConnectionTestApplication().run(args);
    }

    @Override
    public String getName() {
        return "ConnectionTest";
    }

    @Override
    public void initialize(final Bootstrap<ConnectionTestConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ConnectionTestConfiguration configuration,
            final Environment environment) {
        final SimpleMessageService messageService = new SimpleMessageService();
        environment.jersey().register(messageService);
    }

}
