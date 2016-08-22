package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

/**
 * Created by lconcli on 10/11/15.
 */
public class BilingualString implements Serializable {
    private String stringIT;
    private String stringDE;

    public BilingualString() {
        super();
    }

    public BilingualString(String stringIT, String stringDE) {
        this.stringIT = stringIT;
        this.stringDE = stringDE;
    }

    public String getStringIT() {
        return stringIT;
    }

    public String getStringDE() {
        return stringDE;
    }
}
