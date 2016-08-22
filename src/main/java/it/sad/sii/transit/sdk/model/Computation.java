package it.sad.sii.transit.sdk.model;

import java.io.Serializable;

public class Computation implements Serializable {
	private static final long serialVersionUID = 3237701133278044484L;

	private Long id;

	private String processName;
	private String startTime;
	private String endTime;
	private String status;
	private String fromDate;
	private String toDate;
	private String pid;

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("id: ").append(id);
		buffer.append(", processName: ").append(processName);
		buffer.append(", startTime: ").append(startTime);
		buffer.append(", endTime: ").append(endTime);
		buffer.append(", status: ").append(status);
		buffer.append(", fromDate: ").append(fromDate);
		buffer.append(", toDate: ").append(toDate);
		buffer.append(", pid: ").append(pid);

		return buffer.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}