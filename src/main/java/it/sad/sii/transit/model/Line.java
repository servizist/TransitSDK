package it.sad.sii.transit.model;

import java.io.Serializable;


public class Line implements Serializable {

   public String id;
	
	public String description;
	
	public boolean urban;
	
	public boolean mixed;
	
	public Number carrierId;
	
	public boolean citybus;
	
	public boolean nightliner;
	
	public String commercialCode;

   public boolean waypointclockAllowed = false;
}
