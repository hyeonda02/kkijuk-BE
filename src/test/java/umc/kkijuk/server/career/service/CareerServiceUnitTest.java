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
import umc.kkijuk.server.career.repository.CareerRepository;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareerServiceUnitTest {
    @Mock
    private CareerRepository careerRepository;
    @InjectMocks
    private CareerServiceImpl careerService;
    private Career career;
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

        career = Career.builder()
                .id(1L)
                .name("test")
                .alias("alias")
                .summary("summary")
                .current(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .build();
    }

    @Test
    void delete_기존_career_삭제() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(career));
        //when
        careerService.deleteCareer(1L);
        //then
        verify(careerRepository,times(1)).delete(career);
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