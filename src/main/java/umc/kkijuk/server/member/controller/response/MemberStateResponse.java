package umc.kkijuk.server.member.controller.response;

import lombok.Builder;
import lombok.Data;
import umc.kkijuk.server.member.domain.State;

import java.time.LocalDate;

@Data
public class MemberStateResponse {
    private State memberState;

    public MemberStateResponse(){
    }
    @Builder
    public MemberStateResponse(State memberState) {
        this.memberState = memberState;
    }
}
