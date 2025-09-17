package com.lms.user.feign.dto;

import java.time.LocalDateTime;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseAdvocateResponseDto {
    private Long id;
    private Long caseId;
    private Long advocateId;
    private AdvocateRole roleInCase;
    private LocalDateTime assignedAt;
    private LocalDateTime unassignedAt;
}
