package com.app.businessBridge.domain.chattingRoom.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChattingRoomRequest {
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

    @Getter
    @Setter
    public static class Message {
        @NotBlank
        private Long roomId;
        @NotBlank
        private String username;
        @NotBlank
        private String content;
    }
}
