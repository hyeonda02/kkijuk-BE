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
import umc.kkijuk.server.common.controller.ExceptionControllerAdvice;
import umc.kkijuk.server.common.domian.exception.CareerValidationException;
import umc.kkijuk.server.career.controller.response.CareerGroupedByResponse;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.common.domian.response.ErrorResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CareerServiceTest {
    @Autowired
    private CareerService careerService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private ExceptionControllerAdvice exceptionControllerAdvice;
    private Career career1;
    private Career career2;
    private Category category1;
    private Category category2;


    @BeforeEach
    void init() {

        category1 = Category.builder()
                .name("동아리")
                .build();
        category2 = Category.builder()
                .name("대외활동")
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        career1 = Career.builder()
                .name("test1")
                .alias("alias1")
                .summary("summary1")
                .current(false)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .category(category1)
                .build();

        career2 = Career.builder()
                .name("test2")
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
    void create_새로운_career_만들기() {
        //given
        CareerRequestDto.CreateCareerDto careerCreateDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test3")
                .alias("alias3")
                .isCurrent(false)
                .summary("summary3")
                .startDate(LocalDate.of(2024, 4, 10))
                .endDate(LocalDate.of(2024, 7, 20))
                .category(Math.toIntExact(category1.getId()))
                .build();
        //when
        Career newCareer = careerService.createCareer(careerCreateDto);
        //then
        assertAll(
                () -> assertThat(newCareer.getId()).isEqualTo(3L),
                () -> assertThat(newCareer.getName()).isEqualTo("test3"),
                () ->assertThat(newCareer.getAlias()).isEqualTo("alias3"),
                () ->assertThat(newCareer.getSummary()).isEqualTo("summary3"),
                () ->assertThat(newCareer.getStartdate()).isEqualTo(LocalDate.of(2024,4,10)),
                () ->assertThat(newCareer.getEnddate()).isEqualTo(LocalDate.of(2024,7,20)),
                () ->assertThat(newCareer.getYear()).isEqualTo(2024),
                () ->assertThat(newCareer.getCategory().getId()).isEqualTo(1L)
        );
    }
    @Test
    void update_기존_career_수정_null_입력시_기존값_유지() {
        //given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder().build();
        //when
        Career updateCareer = careerService.updateCareer(2L, updateCareerDto);
        //then
        assertAll(
                () -> assertThat(updateCareer.getId()).isEqualTo(2L),
                () -> assertThat(updateCareer.getName()).isEqualTo("test2"),
                () -> assertThat(updateCareer.getAlias()).isEqualTo("alias2"),
                () -> assertThat(updateCareer.getSummary()).isEqualTo("summary2"),
                () -> assertThat(updateCareer.getCurrent()).isEqualTo(false),
                () -> assertThat(updateCareer.getStartdate()).isEqualTo(LocalDate.of(2024, 4, 10)),
                () -> assertThat(updateCareer.getEnddate()).isEqualTo(LocalDate.of(2024, 7, 20)),
                () -> assertThat(updateCareer.getYear()).isEqualTo(2024),
                () -> assertThat(updateCareer.getCategory().getId()).isEqualTo(2L)
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
                .category(2)
                .startDate(LocalDate.of(2021,01,01))
                .build();

        //when
        Career updateCareer = careerService.updateCareer(2L, updateCareerDto);
        //then
        assertAll(
                () -> assertThat(updateCareer.getId()).isEqualTo(2L),
                () -> assertThat(updateCareer.getName()).isEqualTo("update test"),
                () -> assertThat(updateCareer.getSummary()).isEqualTo("update summary"),
                () -> assertThat(updateCareer.getAlias()).isEqualTo("update alias"),
                () -> assertThat(updateCareer.getCurrent()).isEqualTo(true),
                () -> assertThat(updateCareer.getStartdate()).isEqualTo(LocalDate.of(2021,01,01)),
                () -> assertThat(updateCareer.getEnddate()).isEqualTo(LocalDate.now()),
                () -> assertThat(updateCareer.getCategory().getId()).isEqualTo(2L),
                () -> assertThat(updateCareer.getYear()).isEqualTo(LocalDate.now().getYear())
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
        assertThrows(ResourceNotFoundException.class, () -> careerService.updateCareer(999L,updateCareerDto));
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
                 .category(Math.toIntExact(category2.getId()))
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
                .category(Math.toIntExact(category2.getId()))
                .build();
        //when
        //then
        assertThrows(CareerValidationException.class, () -> careerService.updateCareer(career2.getId(), updateCareerDto));

    }

    @Test
    void delete_기존_career_삭제(){
        //given
        Long targetId = career1.getId();
        //when
        careerService.deleteCareer(targetId);
        //then
        Optional<Career> deletedCareer = careerRepository.findById(targetId);
        assertThat(deletedCareer).isEmpty();

    }
    @Test
    void delete_삭제시_없는_careerId_요청은_에러() {
        //given
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> careerService.deleteCareer(999L));
    }


    @Test
    void read_조회시_없는_queryString_요청은_에러() {
        //given
        String status = "test";
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> careerService.getCareerGroupedBy(status));
    }

    @Test
    void read_queryString값이_category일때_조회결과_검증() {
        //given
        String status = "category";
        //when
        List<? extends CareerGroupedByResponse> groupedCareerList = careerService.getCareerGroupedBy(status);
        //then
        CareerResponseDto.CareerGroupedByCategoryDto groupedResult1 = (CareerResponseDto.CareerGroupedByCategoryDto) groupedCareerList.get(0);
        CareerResponseDto.CareerGroupedByCategoryDto groupedResult2 = (CareerResponseDto.CareerGroupedByCategoryDto) groupedCareerList.get(1);

        assertThat(groupedCareerList).isNotEmpty();
        assertThat(groupedCareerList.size()).isEqualTo(2);

        assertThat(groupedResult1.getCategoryName()).isEqualTo("대외활동");
        assertThat(groupedResult2.getCategoryName()).isEqualTo("동아리");

        assertThat(groupedResult1.getCount()).isEqualTo(1);
        assertThat(groupedResult2.getCount()).isEqualTo(1);

    }
    @Test
    void read_queryString_값이_year_일때_조회결과_검증() {
        //given
        String status = "year";
        //when
        List<? extends CareerGroupedByResponse> groupedCareerList = careerService.getCareerGroupedBy(status);
        //then
        assertThat(groupedCareerList).isNotEmpty();
        assertThat(groupedCareerList.size()).isEqualTo(1);

        CareerResponseDto.CareerGroupedByYearDto groupedResult = (CareerResponseDto.CareerGroupedByYearDto) groupedCareerList.get(0);
        assertThat(groupedResult.getYear()).isEqualTo(2024);
        assertThat(groupedResult.getCount()).isEqualTo(2);
    }



}
