package it.sad.sii.transit.sdk.model;

/**
 * Created by mmutschl on 22/08/16.
 */
public class RedisResponse {

    public enum Status {
        ACCEPTED("ACCEPTED"),
        PROCESSING("PROCESSING"),
        SUCCESSFUL("SUCCESSFUL"),
        FAILED("FAILED"),
        ERROR("ERROR"),
        PATTERN_MISMATCH("PATTERN_MISMATCH");

        private final String name;

        Status(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private String fileName;
    private Integer validationId;
    private Status status;
    private String message;

    public RedisResponse() {
    }

    public RedisResponse(String fileName, Integer validationId, Status status, String message) {
        this.fileName = fileName;
        this.validationId = validationId;
        this.status = status;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getValidationId() {
        return validationId;
    }

    public void setValidationId(Integer validationId) {
        this.validationId = validationId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
