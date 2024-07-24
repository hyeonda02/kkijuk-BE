package umc.kkijuk.server.career.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.controller.exception.CareerValidationException;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.career.repository.CareerRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareerServiceUnitTest {
    @Mock
    private CareerRepository careerRepository;
    @InjectMocks
    private CareerServiceImpl careerService;
    private Career career1;
    private Career career2;
    private Category category1;
    private Category category2;
    @BeforeEach
    void init() {
        category1 = Category.builder()
                .id(1L)
                .name("동아리")
                .build();

        category2 = Category.builder()
                .id(2L)
                .name("대외활동")
                .build();

        career1 = Career.builder()
                .id(1L)
                .name("test")
                .alias("alias")
                .summary("summary")
                .current(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();

        career2 = Career.builder()
                .id(2L)
                .name("test2")
                .alias("alias2")
                .summary("summary2")
                .current(false)
                .category(category2)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();
    }
    @Test
    void read_getCareerGroupedBy_Category_성공() {
        //given
        List<Career> careerList = Arrays.asList(career1, career2);
        when(careerRepository.findAll()).thenReturn(careerList);
        //when
        List<? extends CareerResponseDto.CareerGroupedByCategoryDto> result = (List<? extends CareerResponseDto.CareerGroupedByCategoryDto>) careerService.getCareerGroupedBy("category");
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        verify(careerRepository, times(1)).findAll();
    }
    @Test
    void delete_기존_career_삭제() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(career1));
        //when
        careerService.deleteCareer(1L);
        //then
        verify(careerRepository,times(1)).delete(career1);
    }

    @Test
    void delete_삭제시_없는_careerId_요청은_에러() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(CareerValidationException.class, ()->{
            careerService.deleteCareer(1L);});
        verify(careerRepository,never()).delete(any(Career.class));
    }
    @Test
    void update_존재하지_않는_careerId_수정시_에러() {
        // given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isCurrent(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(1)
                .build();
        when(careerRepository.findById(999L)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(CareerValidationException.class, () -> careerService.updateCareer(999L, updateCareerDto));
        verify(careerRepository, never()).save(any(Career.class));
    }

}