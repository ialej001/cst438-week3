package cst438.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
   @Query(value="select Code, Name from country where Code=?1", nativeQuery=true)
   Country findByCode(String code);
}
