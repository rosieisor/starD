package com.web.stard.controller;

import com.web.stard.domain.*;
import com.web.stard.service.MemberService;
import com.web.stard.service.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;

    /* 사용자 일정 조회 (전체) */
    @GetMapping("/all")
    public List<Schedule> getAllToDoListByMember(@RequestParam int year, @RequestParam int month, Authentication authentication) {
        return scheduleService.getAllScheduleListByMember(year, month, authentication);
    }

    /* 사용자 일정 조회 (스터디별) */
    @GetMapping("/{studyId}")
    public List<Schedule> getToDoListByMemberAndStudy(@PathVariable Long studyId, @RequestParam int year, @RequestParam int month) {
        return scheduleService.getScheduleListByStudy(studyId, year, month);
    }

    /* 일정 등록 */
    @PostMapping
    public Schedule registerSchedule(@RequestParam Long studyId, @RequestBody Schedule schedule,
                                     Authentication authentication) {
        // TODO : 일정 권한 확인 (스터디원인지)

        return scheduleService.registerSchedule(studyId, schedule);
    }

    /* 일정 수정 */
    @PostMapping("/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Long scheduleId, @RequestParam String title,
                                   Authentication authentication) {
        // TODO : 일정 권한 확인 (스터디원인지)

        return scheduleService.updateSchedule(scheduleId, title);
    }

    /* 일정 삭제 */
    @DeleteMapping("/{scheduleId}")
    public boolean deleteSchedule(@PathVariable Long scheduleId, Authentication authentication) {
        // TODO : 일정 권한 확인 (스터디원인지)

        return scheduleService.deleteSchedule(scheduleId);
    }
}