package com.lms.document.dto;

import lombok.*;
import java.time.LocalDateTime;

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

