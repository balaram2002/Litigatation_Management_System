package com.lms.cases.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.lms.cases.enums.EventType;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long scheduleId;

	@Column(name = "case_id", nullable = false)
	private Long caseId; // FK to Case

	@Enumerated(EnumType.STRING)
	@Column(name = "event_type")
	private EventType eventType; // HEARING, DEADLINE, OTHER

	@Column(name = "due_date")
	private LocalDateTime dueDate;

	private String remarks;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_by")
	private Long deletedBy;
}
