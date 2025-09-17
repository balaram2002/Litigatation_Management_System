package com.lms.cases.service;

import com.lms.cases.dto.ScheduleRequestDto;
import com.lms.cases.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto dto, Long userId, List<String> roles);

    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto, Long userId, List<String> roles);

    void deleteSchedule(Long id, Long userId, List<String> roles);

    ScheduleResponseDto getScheduleById(Long id, Long userId, List<String> roles);

    List<ScheduleResponseDto> getSchedulesByCase(Long caseId, Long userId, List<String> roles);
}
