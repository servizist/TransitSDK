package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

public class LineEdge implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idA = "";
    private String idB = "";
    private long durationInMillis = 0;
    private int lineId = 0;
    private boolean forward;

    public LineEdge() {
    }

    public LineEdge(String idA, String idB, long durationInMillis, int lineId, boolean forward) {
        setIdA(idA);
        setIdB(idB);
        setDurationInMillis(durationInMillis);
        setLineId(lineId);
        setForward(forward);
    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public String getIdB() {
        return idB;
    }

    public void setIdB(String idB) {
        this.idB = idB;
    }

    public long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

}
