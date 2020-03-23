package cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import cst438.domain.CityInfo;
import cst438.domain.CityNotFoundException;
import cst438.service.CityService;
import org.springframework.http.ResponseEntity;

@RestController
public class CityRestController {
   @Autowired
   private CityService cityService;
   
   @GetMapping("/api/cities/{city}")
   public ResponseEntity<CityInfo> getWeather(@PathVariable("city") String cityName) {
      try {
         return new ResponseEntity<CityInfo>(cityService.getCityInfo(cityName), HttpStatus.OK);
      } catch (CityNotFoundException ex) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found", ex);
      }
   }
}