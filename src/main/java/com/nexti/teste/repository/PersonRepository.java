package com.nexti.teste.repository;

import com.nexti.teste.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

    @Query("SELECT p FROM Person p WHERE p.demissionDate IS NULL OR " +
            "p.demissionDate > CURRENT_TIMESTAMP")
    List<Person> findAllActives();

    @Query("SELECT p FROM Person p WHERE p.demissionDate < CURRENT_TIMESTAMP")
    List<Person> findAllInactives();

    @Modifying
    @Query(value = "UPDATE person p SET p.workplace_id = ?1 where p.id = ?2", nativeQuery = true)
    void workplaceTransfer(Long workplaceId, Long id);

    @Query(value = "SELECT w.id AS workplaceId, w.name AS workplaceName, " +
            " w.start_date AS startDate, w.finish_date AS finishDate, " +
            " s.id AS serviceTypeId, s.name AS serviceTypeName, p.* " +
            "From person p " +
            "INNER JOIN workplace w ON p.workplace_id = w.id " +
            "INNER JOIN service_type s ON w.service_type_id = s.id " +
            "AND p.demission_date > CURRENT_TIMESTAMP OR p.demission_date IS NULL " +
            "AND p.admission_date < CURRENT_TIMESTAMP " +
            "WHERE p.workplace_id = ?1 " +
            " GROUP BY p.id", nativeQuery = true)
    List<Person> findPersonsWithWorkplaceId(Long id);

}
