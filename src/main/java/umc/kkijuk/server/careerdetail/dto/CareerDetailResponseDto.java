package umc.kkijuk.server.careerdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class CareerDetailResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CareerDetailResult{
        Long id;
        Long careerId;
        String title;
        String content;
        LocalDate startDate;
        LocalDate endDate;
        List<CareerTag> careerTagList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CareerTag{
        Long id;
        String tagName;
    }

}
