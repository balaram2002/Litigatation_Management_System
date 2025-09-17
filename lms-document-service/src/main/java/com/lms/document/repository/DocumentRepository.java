package com.lms.document.repository;

import com.lms.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCaseId(Long caseId);
    List<Document> findByUploadedBy(Long uploadedBy);
}

