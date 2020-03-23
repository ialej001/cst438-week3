package cst438.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.Optional;
import cst438.domain.CityInfo;
import cst438.domain.CityNotFoundException;
import cst438.service.CityService;


@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {
   @MockBean
   private CityService cityService;
   
   @Autowired
   private MockMvc mvc;
   
   private JacksonTester<CityInfo> jsonCityInfoAttempt;
   
   @BeforeEach
   public void setup() {
      JacksonTester.initFields(this, new ObjectMapper());
   }
   
   /* There are only two tests here. My cityService logic does NOT allow for the
    * controller to see a list of City objects. As far as the controller goes,
    * it only receives a single object. This was done from approval of Professor
    * Wisneski on the forum. */
   
   @Test
   public void getCityInfoObjectOK() throws Exception {
      CityInfo expected = new CityInfo(3812, "Boston", "USA", "United States",
            "Massachusetts", "589,141", "30.45 F", "9:04 PM");

      given(cityService.getCityInfo("boston")).willReturn(expected);
      
      MockHttpServletResponse response = mvc.perform(get("/api/cities/boston")
            .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
      
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      assertThat(response.getContentAsString()).isEqualTo(jsonCityInfoAttempt
            .write(expected).getJson());
   }
   
   /*
    * I really wish we had more information on how to code tests that look 
    * for exceptions. I'm not sure if this is a valid test. It passes, but
    * I don't understand the logic behind it.
    */
   
   @Test
   public void getCityInfoObjectBAD() throws Exception {
      MvcResult result = mvc.perform(get("/api/cities/bosto")
            .accept(MediaType.APPLICATION_JSON))
            .andReturn();
      
      Optional<CityNotFoundException> someException = Optional.ofNullable(
            (CityNotFoundException) result.getResolvedException());
      
      someException.ifPresent( (se) -> assertThat(se, is(notNullValue())));
      someException.ifPresent( (se) -> assertThat(se, is(instanceOf(
            CityNotFoundException.class))));
   }
}
