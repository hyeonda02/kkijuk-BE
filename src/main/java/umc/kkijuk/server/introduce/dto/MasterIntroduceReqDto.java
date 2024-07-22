package umc.kkijuk.server.introduce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MasterIntroduceReqDto {
    private String oneLiner;
    private String subTitle;
    private String content;
}
