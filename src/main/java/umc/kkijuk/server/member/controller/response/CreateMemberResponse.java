package umc.kkijuk.server.member.controller.response;

import lombok.Data;

@Data
public class CreateMemberResponse {
    private Long id;
    private String message;

    public CreateMemberResponse(String message) {
        this.message = message;
    }

}
