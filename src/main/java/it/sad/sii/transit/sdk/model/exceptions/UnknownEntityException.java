package it.sad.sii.transit.sdk.model.exceptions;

/**
 * Created by ldematte on 11/17/16.
 */
public class UnknownEntityException extends Exception {

    private final String entityId;

    public UnknownEntityException(String entityId) {
        super("ID: " + entityId);
        this.entityId = entityId;
    }

    public UnknownEntityException() {
        this.entityId = "";
    }

    public String getEntityId() {
        return entityId;
    }
}
