package cst438.service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cst438.domain.City;
import cst438.domain.CityInfo;
import cst438.domain.CityNotFoundException;
import cst438.domain.CityRepository;
import cst438.domain.Country;
import cst438.domain.CountryRepository;
import cst438.domain.TempAndTime;

@Service
public class CityService {
   @Autowired
   private CityRepository cityRepository;
   @Autowired
   private CountryRepository countryRepository;
   @Autowired
   private WeatherService weatherService;
   @Autowired
   private RabbitTemplate rabbitTemplate;
   @Autowired
   private FanoutExchange fanout;
   
   public CityService() {
      
   }
   
   public CityService(CityRepository cityRepository,
         CountryRepository countryRepository,
         WeatherService weatherService) {
      this.cityRepository = cityRepository;
      this.countryRepository = countryRepository;
      this.weatherService = weatherService;
   }
   
   public CityInfo getCityInfo(String cityName) throws CityNotFoundException{
      List<City> results = cityRepository.findByName(cityName);
      
      if (results.isEmpty()) {
         throw new CityNotFoundException("City not found");
      } else {
         // let's create our objects by calling the necessary repositories
         City thisCity = results.get(0);
         String countryCode = thisCity.getCountryCode();
         Country cityCountry = countryRepository.findByCode(countryCode);
         TempAndTime tempTime = weatherService.getTempAndTime(cityName);

         // reformat the temperature and time so it's human readable
         double temp = (tempTime.temp - 273.15) * 9.0/5.0 + 32.0;
         DecimalFormat df = new DecimalFormat("###.##");
         String tempString = df.format(temp) + " F";
         
         OffsetDateTime zonedTime = Instant.ofEpochSecond(tempTime.time)
               .atOffset(ZoneOffset.ofTotalSeconds(tempTime.timezone));
         DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a");
         String time = zonedTime.format(format);
         
         // create and return the cityInfo class
         CityInfo thisCityInfo = new CityInfo(
               thisCity.getId(),
               thisCity.getCityName(), 
               countryCode, 
               cityCountry.getCountryName(), 
               thisCity.getDistrict(), 
               String.format("%,d", thisCity.getPopulation()), 
               tempString, 
               time);
         
         return thisCityInfo;
      }
   }
   
   public void requestReservation(
         String cityName,
         String level,
         String email) {
      
      String msg = "{\"cityName\": \""+ cityName + 
            "\" \"level\": \""+level+
            "\" \"email\": \""+email+"\"}" ;
     System.out.println("Sending message:"+msg);
     rabbitTemplate.convertSendAndReceive(
             fanout.getName(), 
             "",   // routing key none.
             msg);
   }
}

