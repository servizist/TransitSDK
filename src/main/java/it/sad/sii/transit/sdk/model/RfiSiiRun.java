package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

/**
 * Created by lconcli on 06/18/19.
 */

public class RfiSiiRun implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String lineId;
    private String runId;
    private String carrierId;

    public RfiSiiRun(String lineId, String runId, String carrierId) {}

    public String getLineId() {
        return lineId;
    }

    public String getRunId() {
        return runId;
    }

    public String getCarrierId() {
        return carrierId;
    }
}