package io.github.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleMessage {

    private String message;

    public SimpleMessage() {
    }

    public SimpleMessage(String s) {
        this.message = s;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

}
