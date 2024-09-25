package umc.kkijuk.server.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.kkijuk.server.record.domain.ForeignLanguage;

import java.time.YearMonth;

@Data
@Builder
@AllArgsConstructor
@Getter
public class ForeignResponse {
    private Long foreignLanguageId;
    private String foreignName;
    private String administer;
    private String foreignNumber;
    private YearMonth acquireDate;
    private String foreignRank;

    public ForeignResponse(ForeignLanguage foreignLanguage) {
        this.foreignLanguageId = foreignLanguage.getId();
        this.foreignName = foreignLanguage.getForeignName();
        this.administer = foreignLanguage.getAdminister();
        this.foreignNumber = foreignLanguage.getForeignNumber();
        this.acquireDate = foreignLanguage.getAcquireDate();
        this.foreignRank = foreignLanguage.getForeignRank();
    }
}
