package cst438.domain;

import javax.persistence.*;

@Entity
@Table(name="city")
public class City {
   @Id
   @GeneratedValue
   private long ID;

   private String Name;
   
   @Column(name = "countrycode")
   private String CountryCode;
   
   private String District;
   
   private long Population;
   
   public City() {
      
   }
   
   public City(long id, String cityName, String countryCode, String district, 
         long population) {
      super();
      this.ID = id;
      this.Name = cityName;
      this.CountryCode = countryCode;
      this.District = district;
      this.Population = population;
   }

   public long getId() {
      return ID;
   }

   public void setId(long id) {
      this.ID = id;
   }

   public String getCityName() {
      return this.Name;
   }

   public void setCityName(String cityName) {
      this.Name = cityName;
   }

   public String getCountryCode() {
      return this.CountryCode;
   }

   public void setCountryCode(String countryCode) {
      this.CountryCode = countryCode;
   }

   public String getDistrict() {
      return this.District;
   }

   public void setDistrict(String district) {
      this.District = district;
   }

   public long getPopulation() {
      return this.Population;
   }

   public void setPopulation(long population) {
      this.Population = population;
   }
}
