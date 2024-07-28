package umc.kkijuk.server.member.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class MemberFieldResponse{
    private List<String> field;

    public MemberFieldResponse(List<String> field) {
        this.field = field;
    }
}
