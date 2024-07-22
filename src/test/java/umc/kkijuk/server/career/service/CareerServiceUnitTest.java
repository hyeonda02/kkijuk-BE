package umc.kkijuk.server.career.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.controller.exception.CareerNotFoundException;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.repository.CareerRepository;

import javax.swing.text.html.Option;
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
    @BeforeEach
    void init() {
        career = Career.builder()
                .id(1L)
                .name("test")
                .alias("alias")
                .summary("summary")
                .current(false)
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
        assertThrows(CareerNotFoundException.class, ()->{
            careerService.deleteCareer(1L);});
        verify(careerRepository,never()).delete(any(Career.class));
    }
}
