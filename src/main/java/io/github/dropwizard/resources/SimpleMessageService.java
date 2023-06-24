package io.github.dropwizard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.github.dropwizard.api.SimpleMessage;

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
public class SimpleMessageService {

    @GET
    public SimpleMessage getMessage() {
        return new SimpleMessage("This is the way.");
    }
}
