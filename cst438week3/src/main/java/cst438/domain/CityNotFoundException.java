package cst438.domain;

public class CityNotFoundException extends Exception {
   private static final long serialVersionUID = 1L;

   public CityNotFoundException(String errorMessage) {
      super(errorMessage);
   }
}
