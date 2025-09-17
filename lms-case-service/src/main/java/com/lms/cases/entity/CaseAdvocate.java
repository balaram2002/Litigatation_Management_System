package com.lms.cases.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.lms.cases.enums.AdvocateRole;

import java.time.LocalDateTime;

@Entity
@Table(name = "case_advocates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseAdvocate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "case_id", nullable = false)
	private Long caseId; // FK to Case

	@Column(name = "advocate_id", nullable = false)
	private Long advocateId; // from user-service

	@Enumerated(EnumType.STRING)
	@Column(name = "role_in_case")
	private AdvocateRole roleInCase; // LEAD / ASSIST

	@CreationTimestamp
	@Column(name = "assigned_at", updatable = false)
	private LocalDateTime assignedAt;

	@Column(name = "unassigned_at")
	private LocalDateTime unassignedAt;
}
