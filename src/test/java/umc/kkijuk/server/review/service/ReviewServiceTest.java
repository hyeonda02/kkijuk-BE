package umc.kkijuk.server.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.common.domian.exception.ReviewRecruitNotMatchException;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;
import umc.kkijuk.server.review.domain.ReviewUpdate;
import umc.kkijuk.server.review.mock.FakeReviewRepository;
import umc.kkijuk.server.review.service.port.ReviewRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    private ReviewService reviewService;

    @BeforeEach
    void Init() {
        ReviewRepository reviewRepository = new FakeReviewRepository();
        reviewService = ReviewServiceImpl.builder()
                .reviewRepository(reviewRepository)
                .build();

        Review review = Review.builder()
                .recruitId(3L)
                .title("test-title")
                .content("test-content")
                .date(LocalDate.of(2024, 7, 21))
                .build();

        reviewRepository.save(review);
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
    void create_새로운_review_만들기_nullable() {
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

    @Test
    void update_존재하던_review_수정() {
        //given
        Recruit recruit = Recruit.builder().id(3L).build();
        Long reviewId = 1L;
        ReviewUpdate reviewUpdate = ReviewUpdate.builder()
                .title("changed-title")
                .content("changed-content")
                .date(LocalDate.of(2024, 7, 30))
                .build();

        //when
        Review result = reviewService.update(recruit, reviewId, reviewUpdate);

        //then
        assertAll(
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getRecruitId()).isEqualTo(3L),
                () -> assertThat(result.getTitle()).isEqualTo("changed-title"),
                () -> assertThat(result.getContent()).isEqualTo("changed-content"),
                () -> assertThat(result.getDate()).isEqualTo(LocalDate.of(2024, 7, 30))
        );
    }

    @Test
    void update_존재하던_review_수정_content_이미존재했지만_null로변경() {
        //given
        Recruit recruit = Recruit.builder().id(3L).build();
        Long reviewId = 1L;
        ReviewUpdate reviewUpdate = ReviewUpdate.builder()
                .title("changed-title")
                .date(LocalDate.of(2024, 7, 30))
                .build();

        //when
        Review result = reviewService.update(recruit, reviewId, reviewUpdate);

        //then
        assertAll(
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getRecruitId()).isEqualTo(3L),
                () -> assertThat(result.getTitle()).isEqualTo("changed-title"),
                () -> assertThat(result.getContent()).isNull(),
                () -> assertThat(result.getDate()).isEqualTo(LocalDate.of(2024, 7, 30))
        );
    }

    @Test
    void delete_존재하던_review_제거() {
        //given
        ReviewCreate reviewCreate = ReviewCreate.builder()
                .title("new-title")
                .content("new-content")
                .date(LocalDate.of(2024, 7, 21))
                .build();

        Recruit recruit = Recruit.builder().id(3L).build();
        Review review = reviewService.create(recruit, reviewCreate);

        //when
        reviewService.delete(recruit, review.getId());

        //then
        assertThatThrownBy(
            () -> reviewService.getById(review.getId())).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_존재하지않은_review에대한_제거요청() {
        //given
        Recruit recruit = Recruit.builder().id(3L).build();

        //when
        //then
        assertThatThrownBy(
                () -> reviewService.delete(recruit, 3333L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_review의_공고ID가_요청한_공고ID와_다른경우() {
        //given
        ReviewCreate reviewCreate = ReviewCreate.builder()
                .title("new-title")
                .content("new-content")
                .date(LocalDate.of(2024, 7, 21))
                .build();
        Recruit recruit_1 = Recruit.builder().id(3L).build();
        Recruit recruit_2 = Recruit.builder().id(4L).build();
        Review review = reviewService.create(recruit_1, reviewCreate);

        //when
        //then
        assertThatThrownBy(
                () -> reviewService.delete(recruit_2, review.getId())).isInstanceOf(ReviewRecruitNotMatchException.class);
    }

    @Test
    void findAllByRecruitId_공고의_모든_review찾기() {
        //given
        Recruit recruit = Recruit.builder().id(3333L).build();
        ReviewCreate reviewCreate = ReviewCreate.builder()
                .title("new-title")
                .content("new-content")
                .date(LocalDate.of(2024, 7, 21))
                .build();
        for (int i = 0; i < 10; i++)
            reviewService.create(recruit, reviewCreate);

        //when
        List<Review> reviews = reviewService.findAllByRecruitId(recruit.getId());

        //then
        assertThat(reviews.size()).isEqualTo(10);
    }
}