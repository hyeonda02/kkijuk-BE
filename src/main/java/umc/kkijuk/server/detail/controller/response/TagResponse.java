package umc.kkijuk.server.detail.controller.response;

import lombok.*;
import umc.kkijuk.server.tag.domain.Tag;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TagResponse {
    private Long id;
    private String tagName;
    public TagResponse(Tag tag) {
       this.id = tag.getId();
       this.tagName = tag.getName();
    }

}
