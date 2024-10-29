package umc.kkijuk.server.record.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UrlReqDto {
    @NotNull
    private String urlTitle;
    @NotNull
    private String url;
}
