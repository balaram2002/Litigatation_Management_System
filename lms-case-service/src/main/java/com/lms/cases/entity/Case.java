package com.lms.cases.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.lms.cases.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Case {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "case_id")
	private Long caseId;

	private String title;
	private String description;

	@Enumerated(EnumType.STRING)
	private Status status; // OPEN / CLOSED

	@Column(name = "advocate_id")
	private Long advocateId; // FK to user-service

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_by")
	private Long deletedBy;
	private LocalDateTime deletedAt; // when it was deleted
}
