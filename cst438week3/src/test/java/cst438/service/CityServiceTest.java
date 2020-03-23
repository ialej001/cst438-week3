package cst438.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import cst438.domain.City;
import cst438.domain.CityInfo;
import cst438.domain.CityNotFoundException;
import cst438.domain.CityRepository;
import cst438.domain.Country;
import cst438.domain.CountryRepository;
import cst438.domain.TempAndTime;
import cst438.service.CityService;
import cst438.service.WeatherService;

@SpringBootTest
public class CityServiceTest {
   // class to be tested
   private CityService cityService;
   
   @Mock
   private WeatherService weatherService;
   
   @Mock 
   private CityRepository cityRepository;
   
   @Mock
   private CountryRepository countryRepository;
   
   @BeforeEach
   public void setUpEach() {
      MockitoAnnotations.initMocks(this);
      cityService = new CityService(cityRepository, countryRepository, 
            weatherService);
   }
   
   @Test
   public void getCityInfoTest() {
      List<City> testCityInfo = Arrays.asList(new City(3812, "Boston", "USA", 
            "Massachusetts", 589141));
      
      given(cityRepository.findByName("Boston")).willReturn(testCityInfo);
      
      given(countryRepository.findByCode("USA")).willReturn(new Country("USA", "United States"));
      
      given(weatherService.getTempAndTime("Boston"))
      .willReturn(new TempAndTime(276, 1584383012, -14400));
      
      CityInfo actual;
      try {
         actual = cityService.getCityInfo("Boston");
      } catch (CityNotFoundException e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found", e);
      }
      
      assertThat(actual.cityName).isEqualTo("Boston");
      assertThat(actual.population).isEqualTo("589,141");
      assertThat(actual.countryName).isEqualTo("United States");
      assertThat(actual.localTime).isEqualTo("2:23 PM");
      assertThat(actual.localTemp).isEqualTo("37.13 F");
      assertThat(actual.population).isEqualTo("589,141");
   }
}
