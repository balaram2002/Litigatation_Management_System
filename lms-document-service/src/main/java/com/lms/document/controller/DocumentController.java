package com.lms.document.controller;

import com.lms.document.dto.DocumentResponseDto;
import com.lms.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // ADMIN & ADVOCATE can upload documents
    @PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
    @PostMapping
    public ResponseEntity<DocumentResponseDto> upload(
            @RequestParam("caseId") Long caseId,
            @RequestParam("uploadedBy") Long uploadedBy,
            @RequestParam("title") String title,
            @RequestParam("docType") String docType,
            @RequestParam(value = "remarks", required = false) String remarks,
            @RequestParam("fileData") MultipartFile fileData
    ) {
        return ResponseEntity.ok(documentService.uploadDocument(caseId, uploadedBy, title, docType, remarks, fileData));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','ADVOCATE')")
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> getById(@PathVariable Long id) {
        return documentService.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','ADVOCATE')")
    @GetMapping("/case/{caseId}")
    public ResponseEntity<List<DocumentResponseDto>> getByCase(@PathVariable Long caseId) {
        return ResponseEntity.ok(documentService.getDocumentsByCaseId(caseId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','ADVOCATE')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentResponseDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(documentService.getDocumentsByUserId(userId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> update(
            @PathVariable Long id,
            @RequestParam("caseId") Long caseId,
            @RequestParam("uploadedBy") Long uploadedBy,
            @RequestParam("title") String title,
            @RequestParam("docType") String docType,
            @RequestParam(value = "remarks", required = false) String remarks,
            @RequestParam(value = "fileData", required = false) MultipartFile fileData
    ) {
        return ResponseEntity.ok(documentService.updateDocument(id, caseId, uploadedBy, title, docType, remarks, fileData));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
