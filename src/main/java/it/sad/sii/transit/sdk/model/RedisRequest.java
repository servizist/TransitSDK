package it.sad.sii.transit.sdk.model;

/**
 * Created by mmutschl on 22/08/16.
 */
public class RedisRequest {

    private String fileName;
    private String notifyAddress;
    private String notifyLanguage;

    public RedisRequest(String fileName, String notifyAddress, String notifyLanguage) {
        this.fileName = fileName;
        this.notifyAddress = notifyAddress;
        this.notifyLanguage = notifyLanguage;
    }

    public RedisRequest() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNotifyAddress() {
        return notifyAddress;
    }

    public void setNotifyAddress(String notifyAddress) {
        this.notifyAddress = notifyAddress;
    }

    public String getNotifyLanguage() {
        return notifyLanguage;
    }

    public void setNotifyLanguage(String notifyLanguage) {
        this.notifyLanguage = notifyLanguage;
    }
}
