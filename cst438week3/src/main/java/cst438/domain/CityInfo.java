package cst438.domain;

public class CityInfo {
   public long id;
   public String cityName;
   public String countryCode;
   public String countryName;
   public String district;
   public String population;
   public String localTemp;
   public String localTime;
   
   
   public CityInfo(long id, String cityName, String countryCode, 
         String countryName, String district, String population, String localTemp,
         String localTime) {
      this.id = id;
      this.cityName = cityName;
      this.countryCode = countryCode;
      this.countryName = countryName;
      this.district = district;
      this.population = population;
      this.localTemp = localTemp;
      this.localTime = localTime;
   }
}
