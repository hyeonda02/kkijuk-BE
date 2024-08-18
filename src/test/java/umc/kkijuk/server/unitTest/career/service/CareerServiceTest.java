package umc.kkijuk.server.unitTest.career.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;
import umc.kkijuk.server.career.service.CareerServiceImpl;
import umc.kkijuk.server.careerdetail.repository.CareerDetailRepository;
import umc.kkijuk.server.common.domian.exception.CareerValidationException;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareerServiceTest {
    @InjectMocks
    private CareerServiceImpl careerService;
    @Mock
    private CareerRepository careerRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CareerDetailRepository careerDetailRepository;
    private final Long testMemberId1 = 1L;
    private final Long testMemberId2 = 2L;

    private Member testRequestMember1;
    private Member testRequestMember2;
    private Career career1;
    private Career career2;
    private Career career3;
    private Career updateCareer;
    private Category category1;
    private Category category2;
    private CareerRequestDto.UpdateCareerDto updateCareerDto;
    @BeforeEach
    void init() {
        testRequestMember1 = Member.builder()
                .id(testMemberId1)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();

        testRequestMember2 = Member.builder()
                .id(testMemberId2)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();

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
                .memberId(testMemberId1)
                .name("test")
                .alias("alias")
                .summary("summary")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();

        career2 = Career.builder()
                .id(2L)
                .memberId(testMemberId2)
                .name("test2")
                .alias("alias2")
                .summary("summary2")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.now())
                .year(2024)
                .build();

        career3 = Career.builder()
                .id(3L)
                .memberId(testMemberId1)
                .name("test3")
                .alias("alias3")
                .summary("summary3")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2022, 4, 10))
                .enddate(LocalDate.of(2022, 8, 10))
                .year(2022)
                .build();

        updateCareer = Career.builder()
                .id(1L)
                .memberId(testMemberId1)
                .name("update test")
                .alias("update alias")
                .summary("update summary")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2021,1,1))
                .enddate(LocalDate.now())
                .year(2024)
                .build();

        updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(1)
                .build();
    }
    @Test
    @DisplayName("[createCareer] 새로운 career 생성 - unknown 값이 false 일 경우 ")
    void createCareerUnknownFalse(){
        //given
        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .summary("summary")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,10))
                .endDate(LocalDate.of(2024,7,20))
                .category(1)
                .build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(careerRepository.save(any(Career.class))).thenReturn(career1);
        //when
        Career newCareer = careerService.createCareer(testRequestMember1, requestDto);
        //then
        assertAll(
                () -> assertThat(newCareer.getMemberId()).isEqualTo(testMemberId1),
                () -> assertThat(newCareer.getId()).isEqualTo(1L),
                () -> assertThat(newCareer.getName()).isEqualTo("test"),
                () -> assertThat(newCareer.getAlias()).isEqualTo("alias"),
                () -> assertThat(newCareer.getSummary()).isEqualTo("summary"),
                () -> assertThat(newCareer.getStartdate()).isEqualTo(LocalDate.of(2024,4,10)),
                () -> assertThat(newCareer.getEnddate()).isEqualTo(LocalDate.of(2024,7,20)),
                () -> assertThat(newCareer.getYear()).isEqualTo(2024),
                () -> assertThat(newCareer.getCategory().getId()).isEqualTo(1L)
        );
        verify(categoryRepository).findById(1L);
        verify(categoryRepository,times(1)).findById(anyLong());
        verify(careerRepository,times(1)).save(any(Career.class));

    }
    @Test
    @DisplayName("[createCareer] 새로운 career 생성 - unknown 값이 true 일 경우 endDate를 현재 시간으로 설정합니다.")
    void createCareerUnknownTrue() {
        //given
        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test2")
                .alias("alias2")
                .summary("true")
                .isUnknown(true)
                .startDate(LocalDate.of(2024, 4, 10))
                .category(2)
                .build();
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        when(careerRepository.save(any(Career.class))).thenReturn(career2);
        //when
        Career newCareer = careerService.createCareer(testRequestMember2, requestDto);
        //then
        assertAll(
                () -> assertThat(newCareer.getMemberId()).isEqualTo(testMemberId2),
                () -> assertThat(newCareer.getId()).isEqualTo(2L),
                () -> assertThat(newCareer.getName()).isEqualTo("test2"),
                () -> assertThat(newCareer.getAlias()).isEqualTo("alias2"),
                () -> assertThat(newCareer.getSummary()).isEqualTo("summary2"),
                () -> assertThat(newCareer.getStartdate()).isEqualTo(LocalDate.of(2024, 4, 10)),
                () -> assertThat(newCareer.getEnddate()).isEqualTo(LocalDate.now()),
                () -> assertThat(newCareer.getYear()).isEqualTo(2024),
                () -> assertThat(newCareer.getCategory().getId()).isEqualTo(2L)
        );
        verify(categoryRepository).findById(2L);
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(careerRepository, times(1)).save(any(Career.class));
    }
    @Test
    @DisplayName("[createCareer] 없는 category의 경우 ResourceNotFoundException 발생 ")
    void createCareerResourceNotFoundException() {
        //given
        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .summary("summary")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,10))
                .endDate(LocalDate.of(2024,7,20))
                .category(5)
                .build();
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerService.createCareer(testRequestMember1, requestDto);
        });
        verify(careerRepository, never()).save(any(Career.class));
    }
    @Test
    @DisplayName("[deleteCareer] career 삭제하기 정상 요청")
    void deleteCareer() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(career1));
        //when
        careerService.deleteCareer(testRequestMember1, 1L);
        //then
        verify(careerRepository, times(1)).delete(any(Career.class));
    }
    @Test
    @DisplayName("[deleteCareer] 없는 리소스 요청의 경우 ResourceNotFoundException 발생")
    void deleteCareerResourceNotFoundException() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, ()->{
            careerService.deleteCareer(testRequestMember1,1L);});
        verify(careerRepository,never()).delete(any(Career.class));
    }
    @Test
    @DisplayName("[deleteCareer] 다른 사용자의 요청의 경우 OwnerMismatchException 발생 ")
    void deleteCareerOwnerMismatchException() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(career1));
        //when
        //then
        assertThrows(OwnerMismatchException.class, () ->{
            careerService.deleteCareer(testRequestMember2, 1L);
        });
        verify(careerRepository, never()).delete(any(Career.class));
    }
    @Test
    @DisplayName("[updateCareer] update 정상 요청")
    void updateCareer() {
        //given
        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(2)
                .build();

        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        when(careerRepository.save(any(Career.class))).thenReturn(updateCareer);
        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        Career updatedCareer = careerService.updateCareer(testRequestMember1, 1L, updateRequestDto);
        //then
        assertAll(
                () -> assertThat(updatedCareer.getMemberId().equals(testMemberId1)),
                () -> assertThat(updatedCareer.getId()).isEqualTo(1L),
                () -> assertThat(updatedCareer.getName()).isEqualTo("update test"),
                () -> assertThat(updatedCareer.getAlias()).isEqualTo("update alias"),
                () -> assertThat(updatedCareer.getSummary()).isEqualTo("update summary"),
                () -> assertThat(updatedCareer.getStartdate()).isEqualTo(LocalDate.of(2021,1,1)),
                () -> assertThat(updatedCareer.getEnddate()).isEqualTo(LocalDate.now()),
                () -> assertThat(updatedCareer.getYear()).isEqualTo(2024),
                () -> assertThat(updatedCareer.getCategory().getId()).isEqualTo(2L)
        );

        verify(categoryRepository,times(1)).findById(anyLong());
        verify(careerRepository, times(1)).findById(anyLong());
        verify(careerRepository,times(1)).save(any(Career.class));

    }
    @Test
    @DisplayName("[updateCareer] null 입력시 기존 내용을 유지해야 합니다. ( 그러나 summary는 null로 변경 )")
    void updateCareerNullable() {
        //given
        Career updateCareer = Career.builder()
                .id(1L)
                .memberId(testMemberId1)
                .name("test")
                .alias("alias")
                .summary("")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();

        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("")
                .summary("")
                .alias("")
                .build();

        when(careerRepository.save(any(Career.class))).thenReturn(updateCareer);
        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        Career updatedCareer = careerService.updateCareer(testRequestMember1, 1L, updateRequestDto);
        //then
        assertAll(
                () -> assertThat(updatedCareer.getMemberId().equals(testMemberId1)),
                () -> assertThat(updatedCareer.getId()).isEqualTo(1L),
                () -> assertThat(updatedCareer.getName()).isEqualTo(career1.getName()),
                () -> assertThat(updatedCareer.getAlias()).isEqualTo(career1.getAlias()),
                () -> assertThat(updatedCareer.getSummary()).isEqualTo(""),
                () -> assertThat(updatedCareer.getStartdate()).isEqualTo(career1.getStartdate()),
                () -> assertThat(updatedCareer.getEnddate()).isEqualTo(career1.getEnddate()),
                () -> assertThat(updatedCareer.getYear()).isEqualTo(career1.getYear()),
                () -> assertThat(updatedCareer.getCategory().getId()).isEqualTo(1L)
        );
        verify(categoryRepository, never()).findById(any(Long.class));
        verify(careerRepository, times(1)).findById(any(Long.class));
        verify(careerRepository).save(any(Career.class));

    }
    @Test
    @DisplayName("[updateCareer] 없는 리소스(카테고리) 요청의 경우 ResourceNotFoundException 발생 ")
    void updateCareerCategoryResourceNotFoundException() {
        //given
        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(false)
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2024,1,1))
                .category(6)
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        //then
        assertThrows(ResourceNotFoundException.class,
                () -> careerService.updateCareer(testRequestMember1, 1L, updateRequestDto));
        verify(careerRepository, never()).save(any(Career.class));
        verify(categoryRepository, times(1)).findById(any(Long.class));
        verify(careerRepository, times(1)).findById(any(Long.class));


    }
    @Test
    @DisplayName("[updateCareer] 없는 리소스(활동) 요청의 경우 ResourceNotFoundException 발생 ")
    void updateCareerCareerResourceNotFoundException() {
        // given
        when(careerRepository.findById(999L)).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ResourceNotFoundException.class, () -> careerService.updateCareer(testRequestMember1,999L, updateCareerDto));
        verify(careerRepository, times(1)).findById(anyLong());
        verify(careerRepository, never()).save(any(Career.class));
    }
    @Test
    @DisplayName("[updateCareer] 다른 사용자의 요청의 경우 OwnerMismatchException 발생 ")
    void updateCareerCareerOwnerMismatchException() {
        //given
        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        //then
        assertThrows( OwnerMismatchException.class,
                () -> careerService.updateCareer(testRequestMember2, 1L, updateCareerDto));
        verify(careerRepository, times(1)).findById(anyLong());
        verify(careerRepository, never()).save(any(Career.class));

    }
    @Test
    @DisplayName("[updateCareer] 날짜 기간이 올바르지 않은 요청의 경우 ( 예 : 2024-01-01 ~ 2023-01-01 ) CareerValidationException 에러가 발생 ")
    void updateCareerCareerValidationException() {
        //given
        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(false)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2023,1,1))
                .category(1)
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        //then
        assertThrows(CareerValidationException.class, () -> careerService.updateCareer(testRequestMember1, 1L, updateRequestDto));
        verify(careerRepository, never()).save(any(Career.class));
        verify(careerRepository, times(1)).findById(any(Long.class));
        verify(categoryRepository, never()).findById(any(Long.class));
    }
    @Test
    @DisplayName("[getCareerGroupedBy] 활동 목록들을 카테고리 별로 조회")
    void getCareerGroupedByCategory() {
        //given
        List<Career> careerList = Arrays.asList(career1, career3);
        when(careerRepository.findAllCareerByMemberId(testMemberId1)).thenReturn(careerList);
        //when
        List<CareerResponseDto.CareerGroupedByCategoryDto> result = careerService.getCareerGroupedByCategory(testRequestMember1);
        //then
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0).getCount()).isEqualTo(1),
                () -> assertThat(result.get(1).getCount()).isEqualTo(1),
                () -> assertThat(result.get(0).getCategoryName()).isEqualTo("대외활동"),
                () -> assertThat(result.get(1).getCategoryName()).isEqualTo("동아리")
        );
        verify(careerRepository, times(1)).findAllCareerByMemberId(testMemberId1);
    }
    @Test
    @DisplayName("[getCareerGroupedBy] 활동 목록들을 연도 별로 조회")
    void getCareerGroupedByYear() {
        //given
        List<Career> careerList = Arrays.asList(career1, career3);
        when(careerRepository.findAllCareerByMemberId(testMemberId1)).thenReturn(careerList);
        //when
        List<CareerResponseDto.CareerGroupedByYearDto> result = careerService.getCareerGroupedByYear(testRequestMember1);
        //then
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0).getCount()).isEqualTo(1),
                () -> assertThat(result.get(1).getCount()).isEqualTo(1),
                () -> assertThat(result.get(0).getYear()).isEqualTo(2024),
                () -> assertThat(result.get(1).getYear()).isEqualTo(2022)
        );
        verify(careerRepository, times(1)).findAllCareerByMemberId(testMemberId1);
    }

}
