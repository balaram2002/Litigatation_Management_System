package com.lms.cases.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hearings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hearing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hearing_id")
	private Long hearingId;

	@Column(name = "case_id", nullable = false)
	private Long caseId; // FK to Case

	@Column(name = "advocate_id")
	private Long advocateId; // from user-service

	@Column(name = "client_id")
	private Long clientId; // FK to users where role = CLIENT

	private LocalDateTime date;
	private String location;
	private String notes;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private Long deletedBy;
}
