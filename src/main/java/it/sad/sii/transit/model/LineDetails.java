package it.sad.sii.transit.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineDetails {
	
	public List<Point> forwardWaypoints;
   public List<Point> backwardWaypoints;

	public String description;

   public String forwardTerminusIt;
   public String forwardTerminusDe;
   public String backwardTerminusIt;
   public String backwardTerminusDe;

	public boolean urban;
	
	public boolean mixed;
	
	public Number carrierId;
	
	public boolean citybus;
	
	public boolean nightliner;
	
	public Map<String, Zone> zones = new HashMap<String, Zone>();
	
	public String commercialCode;
}
