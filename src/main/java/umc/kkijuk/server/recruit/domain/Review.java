package umc.kkijuk.server.recruit.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Review {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDate date;
}
