package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;

@Builder
@AllArgsConstructor
@Getter
public class LicenseReqDto {
    private String licenseName;
    private String administer;
    private String licenseNumber;
    private YearMonth acquireDate;
}
