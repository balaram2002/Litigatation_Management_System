package com.lms.cases.mapper;

import com.lms.cases.dto.ScheduleRequestDto;
import com.lms.cases.dto.ScheduleResponseDto;
import com.lms.cases.entity.Schedule;

public class ScheduleMapper {

    public static Schedule toEntity(ScheduleRequestDto dto) {
        return Schedule.builder()
                .caseId(dto.getCaseId())
                .eventType(dto.getEventType())
                .dueDate(dto.getDueDate())
                .remarks(dto.getRemarks())
                .build();
    }

    public static ScheduleResponseDto toResponseDto(Schedule schedule) {
        return ScheduleResponseDto.builder()
                .scheduleId(schedule.getScheduleId())
                .caseId(schedule.getCaseId())
                .eventType(schedule.getEventType())
                .dueDate(schedule.getDueDate())
                .remarks(schedule.getRemarks())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}
