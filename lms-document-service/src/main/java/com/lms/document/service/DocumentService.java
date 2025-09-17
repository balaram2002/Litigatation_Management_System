package com.lms.document.service;

import com.lms.document.dto.DocumentResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    DocumentResponseDto uploadDocument(Long caseId, Long uploadedBy, String title,
                                       String docType, String remarks, MultipartFile fileData);

    Optional<DocumentResponseDto> getDocumentById(Long id);

    List<DocumentResponseDto> getDocumentsByCaseId(Long caseId);

    List<DocumentResponseDto> getDocumentsByUserId(Long userId);

    DocumentResponseDto updateDocument(Long id, Long caseId, Long uploadedBy, String title,
                                       String docType, String remarks, MultipartFile fileData);

    void deleteDocument(Long id);
}
