package umc.kkijuk.server.review.domain;

import org.springframework.beans.factory.annotation.Value;
import umc.kkijuk.server.recruit.domain.RecruitStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RecruitReviewDtoInterface {
    Long getMemberId();

    // For recruit
    Long getRecruitId();
    String getRecruitTitle();
    @Value("#{@stringListToStringConverter.convertToEntityAttribute(target.tags)}")
    List<String> getTags();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    RecruitStatus getStatus();


    Long getReviewId();
    String getReviewTitle();
    String getReviewContent();
    LocalDate getReviewDate();
}