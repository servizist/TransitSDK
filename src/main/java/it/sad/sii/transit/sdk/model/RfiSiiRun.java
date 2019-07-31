package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

/**
 * Created by lconcli on 06/18/19.
 */

public class RfiSiiRun implements Serializable {
    private static final long serialVersionUID = 1L;

    private String RFICode;
    private String lineId;
    private String runId;
    private String direction;
    private String carrierId;

    public RfiSiiRun() {}

    public RfiSiiRun(String RFICode, String lineId, String runId, String direction, String carrierId) {
        this.RFICode = RFICode;
        this.lineId = lineId;
        this.runId = runId;
        this.direction = direction;
        this.carrierId = carrierId;
    }

    public String getRFICode() {
        return RFICode;
    }

    public void setRFICode(String RFICode) {
        this.RFICode = RFICode;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }
}