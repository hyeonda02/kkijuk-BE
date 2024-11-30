package umc.kkijuk.server.record.controller.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResumeResponse {
    private Long id;
    private String category;
    private String name;
    private String alias;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;

}
