package com.lms.cases.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.cases.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	  List<Schedule> findByCaseId(Long caseId);

//	List<Hearing> findByAdvocateIdAndDateAfter(Long advocateId, LocalDateTime now);
//
//	List<Hearing> findByCaseIdAndDateAfter(Long caseId, LocalDateTime now);
//
//	List<Hearing> findByDateBetween(LocalDateTime start, LocalDateTime end);
//
//	// If you want client-wise search later (requires clientId relation)
//	List<Hearing> findByAdvocateIdAndDateBetween(Long advocateId, LocalDateTime start, LocalDateTime end);
}
