package umc.kkijuk.server.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class MemberFieldDto {
    private Long id; //jwt토큰으로 받을땐 제거
    private List<String> field;

    @Builder
    public MemberFieldDto(Long id, List<String> field) {
        this.id = id;
        this.field = field;
    }
}
