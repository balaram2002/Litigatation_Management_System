package com.lms.document.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "case_id", nullable = false)
    private Long caseId; // FK to Case

    @Column(name = "uploaded_by", nullable = false)
    private Long uploadedBy; // FK to User

    @Column(nullable = false)
    private String title;

    @Column(name = "doc_type")
    private String docType; // e.g., Petition, Evidence, Order, etc.

    @Column(name = "file_path")
    private String filePath; // path if storing in FS/cloud
    @Column(name = "file_data", columnDefinition = "bytea")
    private byte[] fileData;

    private String remarks;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

