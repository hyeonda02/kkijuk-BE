package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;

@Getter
@Builder
@AllArgsConstructor
public class ForeignReqDto {
    private String foreignName;
    private String administer;
    private String foreignNumber;
    private YearMonth acquireDate;
    private String foreignRank;
}
