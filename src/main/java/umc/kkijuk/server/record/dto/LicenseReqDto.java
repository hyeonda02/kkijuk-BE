package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.record.domain.LicenseTag;

import java.time.YearMonth;

@Builder
@AllArgsConstructor
@Getter
public class LicenseReqDto {
    private LicenseTag licenseTag;
    private String licenseName;
    private String administer;
    private String licenseNumber;
    private String licenseGrade;
    private YearMonth acquireDate;
}
