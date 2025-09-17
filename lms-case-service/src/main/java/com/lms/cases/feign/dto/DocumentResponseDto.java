package com.lms.cases.feign.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDto {
    private Long documentId;
    private Long caseId;
    private Long uploadedBy;
    private String title;
    private String docType;
    private String remarks;
    private String filePath;
    private LocalDateTime uploadedAt;
}
