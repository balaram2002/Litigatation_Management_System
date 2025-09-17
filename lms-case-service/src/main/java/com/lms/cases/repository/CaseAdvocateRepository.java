package com.lms.cases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.cases.entity.CaseAdvocate;

@Repository
public interface CaseAdvocateRepository extends JpaRepository<CaseAdvocate, Long> {
	List<CaseAdvocate> findByCaseId(Long caseId);

}
