package com.nexti.teste.repository;

import com.nexti.teste.domain.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {


}
