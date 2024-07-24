package umc.kkijuk.server.career.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import umc.kkijuk.server.career.controller.exception.CareerExceptionControllerAdvice;
import umc.kkijuk.server.career.controller.exception.CareerValidationException;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
public class CareerServiceTest {
    @Autowired
    private CareerService careerService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private CareerExceptionControllerAdvice careerExceptionControllerAdvice;
    private Career career1;
    private Career career2;


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

        career1 = Career.builder().id(1L).name("tet1")
                .alias("alias1")
                .summary("summary1")
                .current(false)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .category(category1)
                .build();

        career2 = Career.builder().id(2L).name("test2")
                .alias("alias2")
                .summary("summary2")
                .current(false)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .category(category2)
                .build();

        careerRepository.save(career1);
        careerRepository.save(career2);
    }
    @Test
    void update_기존_career_수정_null_입력시_기존값_유지() {
        //given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder().build();
        //when
        Career updateCareer = careerService.updateCareer(career2.getId(), updateCareerDto);
        //then
        assertAll(
                () -> assertThat(updateCareer.getId()).isEqualTo(career2.getId()),
                () -> assertThat(updateCareer.getName()).isEqualTo(career2.getName()),
                () -> assertThat(updateCareer.getAlias()).isEqualTo(career2.getAlias()),
                () -> assertThat(updateCareer.getSummary()).isEqualTo(career2.getSummary()),
                () -> assertThat(updateCareer.getCurrent()).isEqualTo(career2.getCurrent()),
                () -> assertThat(updateCareer.getStartdate()).isEqualTo(career2.getStartdate()),
                () -> assertThat(updateCareer.getEnddate()).isEqualTo(career2.getEnddate()),
                () -> assertThat(updateCareer.getYear()).isEqualTo(career2.getYear()),
                () -> assertThat(updateCareer.getCategory().getId()).isEqualTo(career2.getCategory().getId())
        );
    }

    @Test
    void create_새로운_career_만들기() {
        //given
        CareerRequestDto.CreateCareerDto careerCreateDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test3")
                .alias("alias3")
                .isCurrent(false)
                .summary("summary3")
                .startDate(LocalDate.of(2024, 4, 10))
                .endDate(LocalDate.of(2024, 7, 20))
                .category(1)
                .build();
        //when
        Career newCareer = careerService.createCareer(careerCreateDto);
        //then
        assertAll(
                () -> assertThat(newCareer.getId()).isEqualTo(3L),
                () -> assertThat(newCareer.getName()).isEqualTo("test3"),
                () -> assertThat(newCareer.getAlias()).isEqualTo("alias3"),
                () -> assertThat(newCareer.getSummary()).isEqualTo("summary3"),
                () -> assertThat(newCareer.getCurrent()).isEqualTo(false),
                () -> assertThat(newCareer.getStartdate()).isEqualTo(LocalDate.of(2024, 4, 10)),
                () -> assertThat(newCareer.getEnddate()).isEqualTo(LocalDate.of(2024, 7, 20)),
                () -> assertThat(newCareer.getYear()).isEqualTo(2024),
                () -> assertThat(newCareer.getCategory().getId()).isEqualTo(1L),
                () -> assertThat(newCareer.getCategory().getName()).isEqualTo("동아리")
        );
    }
    @Test
    void update_기존_career_수정() {
        //given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isCurrent(true)
                .startDate(LocalDate.of(2021,01,01))
                .category(2)
                .build();
        //when
        Career updateCareer = careerService.updateCareer(career2.getId(), updateCareerDto);
        //then
        assertAll(
                () -> assertThat(updateCareer.getId()).isEqualTo(2L),
                () -> assertThat(updateCareer.getName()).isEqualTo("update test"),
                () -> assertThat(updateCareer.getSummary()).isEqualTo("update summary"),
                () -> assertThat(updateCareer.getAlias()).isEqualTo("update alias"),
                () -> assertThat(updateCareer.getCurrent()).isEqualTo(true),
                () -> assertThat(updateCareer.getStartdate()).isEqualTo(LocalDate.of(2021,01,01)),
                () -> assertThat(updateCareer.getEnddate()).isEqualTo(LocalDate.now()),
                () -> assertThat(updateCareer.getYear()).isEqualTo(LocalDate.now().getYear()),
                () -> assertThat(updateCareer.getCategory().getId()).isEqualTo(2L),
                () -> assertThat(updateCareer.getCategory().getName()).isEqualTo("대외활동")
        );

    }
    @Test
    void update_수정시_없는_careerId_요청은_에러() {
        //given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isCurrent(true)
                .startDate(LocalDate.of(2021,01,01))
                .category(2)
                .build();
        //when
        //then
        assertThrows(CareerValidationException.class, () -> careerService.updateCareer(999L,updateCareerDto));
    }
    @Test
    void update_수정시_날짜_형식이_잘못된_경우_에러() {
        //given
         CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                 .careerName("update test")
                 .summary("update summary")
                 .alias("update alias")
                 .isCurrent(true)
                 .startDate(LocalDate.of(2024543,01,01))
                 .category(2)
                 .build();
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () -> careerService.updateCareer(career2.getId(), updateCareerDto));

    }
    @Test
    @DisplayName("예 ) 시작 날짜 : 2024-01-01 , 종료 날짜 : 2023-01-01")
    void update_수정시_날짜_기간이_잘못된_경우_에러() {
        //given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isCurrent(false)
                .startDate(LocalDate.of(2024,01,01))
                .endDate(LocalDate.of(2023,01,01))
                .category(2)
                .build();
        //when
        //then
        assertThrows(CareerValidationException.class, () -> careerService.updateCareer(career2.getId(), updateCareerDto));


    }

    @Test
    void delete_기존_career_삭제(){
        //given
        careerService.deleteCareer(career1.getId());
        //when
        //then
        Optional<Career> deletedCareer = careerRepository.findById(career1.getId());
        assertThat(deletedCareer).isEmpty();

    }
    @Test
    void delete_삭제시_없는_careerId_요청은_에러() {
        //given
        //when
        //then
        assertThrows(CareerValidationException.class, () -> careerService.deleteCareer(999L));
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
