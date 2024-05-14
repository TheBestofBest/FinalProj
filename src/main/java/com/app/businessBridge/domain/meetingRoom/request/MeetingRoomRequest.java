package com.app.businessBridge.domain.meetingRoom.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingRoomRequest {
    @Getter
    @Setter
    public static class Create {
        @NotBlank
        private String name;
    }

    @Getter
    @Setter
    public static class Invite {
        @NotBlank
        private String username;
    }
}
