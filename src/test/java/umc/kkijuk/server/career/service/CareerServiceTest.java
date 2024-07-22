package umc.kkijuk.server.career.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import umc.kkijuk.server.career.controller.CareerController;
import umc.kkijuk.server.career.controller.exception.CareerExceptionControllerAdvice;
import umc.kkijuk.server.career.controller.exception.CareerNotFoundException;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class CareerServiceTest {
    @Autowired
    private CareerService careerService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private CareerExceptionControllerAdvice careerExceptionControllerAdvice;
    private Career career;


    @BeforeEach
    void init() {
        Category category1 = Category.builder()
                .name("동아리")
                .build();
        Category category2 = Category.builder()
                .name("대외활동")
                .build();
        Category category3 = Category.builder()
                .name("공모전/대회")
                .build();
        Category category4 = Category.builder()
                .name("프로젝트")
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        career = Career.builder().id(1L).name("tet1")
                .alias("alias2")
                .summary("summary")
                .current(false)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .category(category1)
                .build();
        careerRepository.save(career);
    }

    @Test
    void create_새로운_career_만들기() {
        //given
        CareerRequestDto.CareerDto careerCreateDto = CareerRequestDto.CareerDto.builder()
                .careerName("test")
                .alias("alias")
                .isCurrent(false)
                .startDate(LocalDate.of(2024, 4, 10))
                .endDate(LocalDate.of(2024, 7, 20))
                .category(1)
                .build();
        //when
        Career career = careerService.createCareer(careerCreateDto);
        //then
        assertAll(
                () -> assertThat(career.getId()).isEqualTo(2L),
                () -> assertThat(career.getName()).isEqualTo("test"),
                () -> assertThat(career.getAlias()).isEqualTo("alias"),
                () -> assertThat(career.getCurrent()).isEqualTo(false),
                () -> assertThat(career.getStartdate()).isEqualTo(LocalDate.of(2024, 4, 10)),
                () -> assertThat(career.getEnddate()).isEqualTo(LocalDate.of(2024, 7, 20)),
                () -> assertThat(career.getYear()).isEqualTo(2024),
                () -> assertThat(career.getCategory().getId()).isEqualTo(1L),
                () -> assertThat(career.getCategory().getName()).isEqualTo("동아리")
        );
    }
    @Test
    void delete_기존_career_삭제(){
        //given
        careerService.deleteCareer(career.getId());
        //when
        //then
        Optional<Career> deletedCareer = careerRepository.findById(career.getId());
        assertThat(deletedCareer).isEmpty();

    }
    @Test
    void delete_삭제시_없는_careerId_요청은_에러() {
        //given
        //when
        //then
        assertThrows(CareerNotFoundException.class, () -> careerService.deleteCareer(999L));
    }
    @Test
    void CareerExceptionControllerAdvice가_올바른_예외_응답을_반환하는지_검증() {
        //given
        InvalidFormatException invalidFormatException = mock(InvalidFormatException.class);
        when(invalidFormatException.getMessage()).thenReturn(CareerResponseMessage.CAREER_FOTMAT_INVALID);

        //when
        ResponseEntity<CareerResponse<?>> responseEntiy = careerExceptionControllerAdvice.handleInvalidFormatExceptions(invalidFormatException);

        //then
        assertEquals(HttpStatus.BAD_REQUEST,responseEntiy.getStatusCode());
        CareerResponse<?> responseBody = responseEntiy.getBody();
        assertEquals(CareerResponseMessage.CAREER_FOTMAT_INVALID,responseBody.getMessage());
    }


}
