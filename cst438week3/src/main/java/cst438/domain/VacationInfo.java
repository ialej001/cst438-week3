package cst438.domain;

public class VacationInfo {
   private String level;
   private String email;
   
   public VacationInfo() {
      this.level = "1";
      this.email = "email@email.com";
   }

   public VacationInfo(String level, String email) {
      this.level = level;
      this.email = email;
   }
   
   public String getLevel() {
      return this.level;
   }
   public void setLevel(String level) {
      this.level = level;
   }
   public String getEmail() {
      return this.email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
}
