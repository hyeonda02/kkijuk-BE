package umc.kkijuk.server.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.kkijuk.server.record.domain.File;

@Data
@Getter
@Builder
@AllArgsConstructor
public class FileResponse {
    private Long memberId;
    private String title;
    private String keyName;

    public FileResponse(File file){
        this.memberId = file.getMemberId();
        this.title = file.getTitle();
        this.keyName = file.getKeyName();
    }
}
