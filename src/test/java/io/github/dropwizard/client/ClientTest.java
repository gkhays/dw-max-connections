package io.github.dropwizard.client;

import static org.assertj.core.api.Assertions.assertThat;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.dropwizard.ConnectionTestApplication;
import io.github.dropwizard.ConnectionTestConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ClientTest {
    private static final String CONFIG = ResourceHelpers.resourceFilePath("test-connections.yml");

    static final DropwizardAppExtension<ConnectionTestConfiguration> APP = new DropwizardAppExtension<>(
            ConnectionTestApplication.class, CONFIG);

    @Test
    public void testExceededConnections() {
        Client client = APP.client();
        client.property(ClientProperties.CONNECT_TIMEOUT, 0);
        client.property(ClientProperties.READ_TIMEOUT, 0);
        final Response testResponse = client.target("http://localhost:" + APP.getLocalPort() + "/message")
                .request()
                .get();

        assertThat(testResponse)
                .extracting(Response::getStatus)
                .isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void testForceExceededConnections() {
        for (int i = 0; i < 2048; i++) {
            ClientThread t = new ClientThread(APP, i);
            t.run();
        }
    }
}
