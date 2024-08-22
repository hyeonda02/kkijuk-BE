package umc.kkijuk.server.member.controller.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberEmailResponse {
    private String email;

    public MemberEmailResponse(){
    }
    @Builder
    public MemberEmailResponse(String email) {
        this.email = email;
    }
}
