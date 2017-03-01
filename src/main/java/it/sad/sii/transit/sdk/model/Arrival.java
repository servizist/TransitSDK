package it.sad.sii.transit.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.bz.sii.common.conversions.DateTimeDeserializer;
import it.bz.sii.common.conversions.DateTimeSerializer;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lconcli on 10/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Arrival implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String lineId;
    private String runId;
    private String carrierId;
    private String carrierName;
    private String waypointId;
    private String comCode;
    private String originDE;
    private String originIT;
    private String lastTxPoint;
    private String viaDE;
    private String viaIT;
    private String delay;
    private String laneDE;
    private String laneIT;

    private DateTime scheduledTime;
    private DateTime estimatedTime;

    private Integer delayInSeconds;
    private Integer dueIn;

    private Long lastTxTime;

    private List<ViaInfo> viaInfo;
    private List<String> additionalInfoIT;
    private List<String> additionalInfoDE;

    private Boolean cancelled;

    private TransportType transportType;

    public Arrival() {
    }

    public Arrival(String lineId, String runId, String carrierId, String carrierName, String comCode, String waypointId,
                   TransportType transportType, DateTime scheduledTime, DateTime estimatedTime, Integer dueIn,
                   String delay, Integer delayInSeconds, String originDE, String originIT, Long lastTxTime,
                   String lastTxPoint, String viaIT, String viaDE, List<ViaInfo> viaInfo, List<String> additionalInfoIT,
                   List<String> additionalInfoDE, Boolean isCancelled, String laneDE,
                   String laneIT) {
        this.lineId = lineId;
        this.runId = runId;
        this.carrierId = carrierId;
        this.carrierName = carrierName;
        this.comCode = comCode;
        this.waypointId = waypointId;
        this.transportType = transportType;
        this.scheduledTime = scheduledTime;
        this.estimatedTime = estimatedTime;
        this.dueIn = dueIn;
        this.delay = delay;
        this.delayInSeconds = delayInSeconds;
        this.originIT = originIT;
        this.originDE = originDE;
        this.lastTxTime = lastTxTime;
        this.lastTxPoint = lastTxPoint;
        this.viaIT = viaIT;
        this.viaDE = viaDE;
        this.viaInfo = viaInfo;
        this.additionalInfoIT = additionalInfoIT;
        this.additionalInfoDE = additionalInfoDE;
        this.cancelled = isCancelled;
        this.laneDE = laneDE;
        this.laneIT = laneIT;
    }

    public String getKey() {
        return String.format("W%s-L%s-R%s-%d", waypointId, lineId, runId, scheduledTime.getSecondOfDay());
    }

    public String getLineId() {
        return lineId;
    }

    public String getRunId() {
        return runId;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime getScheduledTime() {
        return scheduledTime;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime getEstimatedTime() {
        return estimatedTime;
    }

    public String getWaypointId() {
        return waypointId;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public String getComCode() {
        return comCode;
    }

    public Integer getDelayInSeconds() {
        return delayInSeconds;
    }

    public Integer getDueIn() {
        return dueIn;
    }

    public String getOriginDE() {
        return originDE;
    }

    public String getOriginIT() {
        return originIT;
    }

    public Long getLastTxTime() {
        return lastTxTime;
    }

    public String getLastTxPoint() {
        return lastTxPoint;
    }

    public String getViaDE() {
        return viaDE;
    }

    public String getViaIT() {
        return viaIT;
    }

    public String getDelay() {
        return delay;
    }

    public List<ViaInfo> getViaInfo() {
        return viaInfo;
    }

    public List<String> getAdditionalInfoIT() {
        return additionalInfoIT;
    }

    public List<String> getAdditionalInfoDE() {
        return additionalInfoDE;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public String getLaneDE() {
        return laneDE;
    }

    public String getLaneIT() {
        return laneIT;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String jsonOut = super.toString();
        try {
            jsonOut = ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonOut;
    }
}