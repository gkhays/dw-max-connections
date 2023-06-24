package io.github.dropwizard.client;

import java.io.InputStream;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.dropwizard.ConnectionTestConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;

public class ClientThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientThread.class);

    private int count;
    private DropwizardAppExtension<ConnectionTestConfiguration> testApp;

    public ClientThread(DropwizardAppExtension<ConnectionTestConfiguration> app, int i) {
        this.testApp = app;
        this.count = i;
    }

    /**
     * @see <a href=
     *      "https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/client.html#d0e5384">Closing
     *      connections</a>
     */
    @Override
    public void run() {
        final Response testResponse = testApp.client().target("http://localhost:" + testApp.getLocalPort() + "/message")
                .request()
                .get();
        LOGGER.info("Starting thread #{}", count);
        // Read the entity into an input stream so we can keep it open.
        InputStream is = testResponse.readEntity(InputStream.class);
        LOGGER.info("Connection is still open...");
        // LOGGER.info("String response: {}", testResponse.readEntity(String.class));
        LOGGER.info(testResponse.getStatusInfo().toString());
        // LOGGER.info("Now the connection is closed.");
    }

}
