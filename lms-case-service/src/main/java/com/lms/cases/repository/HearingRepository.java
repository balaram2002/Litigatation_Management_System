package com.lms.cases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.cases.entity.Hearing;

@Repository
public interface HearingRepository extends JpaRepository<Hearing, Long> {
	List<Hearing> findByCaseId(Long caseId);

	boolean existsByCaseIdAndClientId(Long caseId, Long userId);
	
}
