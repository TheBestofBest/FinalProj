package com.app.businessBridge.domain.schedule.response;

import com.app.businessBridge.domain.schedule.dto.ScheduleDTO;
import com.app.businessBridge.domain.schedule.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleResponse {

    @Getter
    public static class getSchedule{
        private ScheduleDTO scheduleDTO;
        public getSchedule(Schedule schedule){
            this.scheduleDTO = new ScheduleDTO(schedule);
        }
    }

    @Getter
    public static class getSchedules {
        private List<ScheduleDTO> scheduleDTOs;
        public getSchedules(List<Schedule> schedules){
            this.scheduleDTOs = schedules.stream().map(ScheduleDTO::new).toList();
        }
    }
}
