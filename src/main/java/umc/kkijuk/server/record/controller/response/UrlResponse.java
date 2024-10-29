package umc.kkijuk.server.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.kkijuk.server.record.domain.File;

@Data
@AllArgsConstructor
@Builder
@Getter
public class UrlResponse {
    private Long memberId;
    private String urlTitle;
    private String url;

    public UrlResponse(File file) {
        this.memberId = file.getMemberId();
        this.urlTitle = file.getUrlTitle();
        this.url = file.getUrl();
    }
}
