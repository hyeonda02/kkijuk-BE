package umc.kkijuk.server.member.controller.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberStateResponse {
    private LocalDate deleteDate;

    public MemberStateResponse(){
    }
    @Builder
    public MemberStateResponse(LocalDate deleteDate) {
        this.deleteDate = deleteDate;
    }
}
