package cst438.domain;

import javax.persistence.*;

@Entity
@Table(name = "country")
public class Country {
   @Id
   private String Code;
   
   private String Name;
   
   public Country() {
      
   }
   
   public Country(String countryCode, String countryName) {
      super();
      this.Code = countryCode;
      this.Name = countryName;
   }

   public String getCountryCode() {
      return this.Code;
   }

   public void setCountryCode(String countryCode) {
      this.Code = countryCode;
   }

   public String getCountryName() {
      return this.Name;
   }

   public void setCountryName(String countryName) {
      this.Name = countryName;
   }
}
