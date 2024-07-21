package umc.kkijuk.server.review.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;
import umc.kkijuk.server.review.mock.FakeReviewRepository;
import umc.kkijuk.server.review.service.port.ReviewRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    private ReviewService reviewService;

    @BeforeEach
    void Init() {
        ReviewRepository reviewRepository = new FakeReviewRepository();
        reviewService = ReviewServiceImpl.builder()
                .reviewRepository(reviewRepository)
                .build();
    }

    @Test
    void crate_새로운_review_만들기() {
        //given
        ReviewCreate reviewCreate = ReviewCreate.builder()
                .title("new-title")
                .content("new-content")
                .date(LocalDate.of(2024, 7, 21))
                .build();

        Recruit recruit = Recruit.builder().id(3L).build();

        //when
        Review result = reviewService.create(recruit, reviewCreate);

        //then
        assertAll(
            () -> assertThat(result.getRecruitId()).isEqualTo(recruit.getId()),
            () -> assertThat(result.getTitle()).isEqualTo(reviewCreate.getTitle()),
            () -> assertThat(result.getContent()).isEqualTo(reviewCreate.getContent()),
            () -> assertThat(result.getDate()).isEqualTo(LocalDate.of(2024, 7, 21))
        );
    }

    @Test
    void crate_새로운_review_만들기_nullable() {
        //given
        ReviewCreate reviewCreate = ReviewCreate.builder()
                .title("new-title")
                .date(LocalDate.of(2024, 7, 21))
                .build();

        Recruit recruit = Recruit.builder().id(3L).build();

        //when
        Review result = reviewService.create(recruit, reviewCreate);

        //then
        assertAll(
                () -> assertThat(result.getRecruitId()).isEqualTo(recruit.getId()),
                () -> assertThat(result.getTitle()).isEqualTo(reviewCreate.getTitle()),
                () -> assertThat(result.getContent()).isNull(),
                () -> assertThat(result.getDate()).isEqualTo(LocalDate.of(2024, 7, 21))
        );
    }
}