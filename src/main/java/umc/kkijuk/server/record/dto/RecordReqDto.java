package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RecordReqDto {
    private String address;
    private String profileImageUrl;
}
