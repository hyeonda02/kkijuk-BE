package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.tag.domain.Tag;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FindTagResponse {
    private Long tagId;
    private String tagName;
    public FindTagResponse(Tag tag) {
        this.tagId = tag.getId();
        this.tagName = tag.getName();
    }
}
