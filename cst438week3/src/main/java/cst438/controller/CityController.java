package cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import cst438.domain.CityInfo;
import cst438.domain.CityNotFoundException;
import cst438.domain.VacationInfo;
import cst438.service.CityService;

@Controller
public class CityController {
   @Autowired
   private CityService cityService;
   
   @GetMapping("/cities/{city}")
   public String getCityInfo(@PathVariable("city") String cityName, Model model) {
      CityInfo thisCity;
      VacationInfo vacationInfo;
      try {
         thisCity = cityService.getCityInfo(cityName);
         vacationInfo = new VacationInfo();
         model.addAttribute("thisCityInfo", thisCity);
         model.addAttribute("vacationOption", vacationInfo);
         return "city";
      } catch (CityNotFoundException ex) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found", ex);
      }
   }
   
   @PostMapping("/cities/reservation")
   public String createReservation(
         @RequestParam("city") String cityName,
         @RequestParam("level") String level,
         @RequestParam("email") String email,
         Model model) {
      
      model.addAttribute("city", cityName);
      model.addAttribute("level", level);
      model.addAttribute("email", email);
      cityService.requestReservation(cityName, level, email);
      
      return "request_reservation";
   }
}
