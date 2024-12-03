package umc.kkijuk.server.introduce.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Builder
@AllArgsConstructor
public class FindIntroduceResponse {
    private Long introId;
    private String title;
    private String content;
    private LocalDate createdDate;
}
