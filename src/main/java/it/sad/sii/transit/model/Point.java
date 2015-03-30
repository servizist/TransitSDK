package it.sad.sii.transit.model;

import java.io.Serializable;

public class Point implements Serializable {

   public String descriptionDe;
   public Number latitude;
   public Number longitude;
   public Number id;
   public Number nodeId;
   public String descriptionIt;
   public String ipAddress;
   public int numberOfShownPassages;
   public String displayIdentifier;

   public Point() {
   }

   public Point(Number longitude, Number latitude) {
      this.longitude = longitude;
      this.latitude = latitude;
   }

   public Point(Number longitude, Number latitude, String descriptionDe, String descriptionIt) {
      this.longitude = longitude;
      this.latitude = latitude;
      this.descriptionIt = descriptionIt;
      this.descriptionDe = descriptionDe;
   }

   public double x() {
      return longitude.doubleValue();
   }

   public double y() {
      return latitude.doubleValue();
   }

   public void x(double p0) {
      longitude = p0;
   }

   public void y(double p0) {
      latitude = p0;
   }
}
