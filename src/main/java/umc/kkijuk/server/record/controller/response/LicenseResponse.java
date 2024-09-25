package umc.kkijuk.server.record.controller.response;

import lombok.*;
import umc.kkijuk.server.record.domain.License;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@Getter
@Builder
public class LicenseResponse {
    private Long licenseId;
    private String licenseName;
    private String administer;
    private String licenseNumber;
    private YearMonth acquireDate;

    public LicenseResponse(License license) {
        this.licenseId = license.getId();
        this.licenseName = license.getLicenseName();
        this.administer = license.getAdminister();
        this.licenseNumber = license.getLicenseNumber();
        this.acquireDate = license.getAcquireDate();
    }
}
