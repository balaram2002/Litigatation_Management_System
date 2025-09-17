package com.lms.cases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.cases.entity.Case;
import com.lms.cases.enums.Status;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long>{

	List<Case> findByStatus(Status status);
	List<Case> findAllByDeletedAtIsNull();
	List<Case> findByAdvocateId(Long userId);

}
