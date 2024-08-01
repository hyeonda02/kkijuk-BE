package umc.kkijuk.server.career.service;

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
public class CareerServiceUnitTest {
    @Mock
    private CareerRepository careerRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CareerServiceImpl careerService;
    private Long testMemberId = 333L;
    private Member testRequestMember;
    private Member testRequestMember2;
    private Career career1;
    private Career career2;
    private Category category1;
    private Category category2;
    @BeforeEach
    void init() {
        testRequestMember = Member.builder()
                .id(testMemberId)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();

        testRequestMember2 = Member.builder()
                .id(444L)
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
                .memberId(testMemberId)
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
                .memberId(testMemberId)
                .name("test2")
                .alias("alias2")
                .summary("summary2")
                .unknown(false)
                .category(category2)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();
    }
    @Test
    @DisplayName("unkonwn 값이 true 이면, endDate의 값이 현재 날짜로 지정됩니다.")
    void create_새로운_career_생성_unknown값이_true일때() {
        //given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        Career newCareerInstance = Career.builder()
                .id(3L)
                .memberId(testMemberId)
                .name("test3")
                .alias("test3")
                .summary("test3")
                .unknown(true)
                .category(category1)
                .startdate(LocalDate.of(2024,4,1))
                .enddate(LocalDate.now())
                .year(2024)
                .build();

        when(careerRepository.save(any(Career.class))).thenReturn(newCareerInstance);
        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test3")
                .alias("test3")
                .summary("test3")
                .isUnknown(true)
                .startDate(LocalDate.of(2024,4,1))
                .endDate(null)
                .category(1)
                .build();
        //when
        Career newCareer = careerService.createCareer(testRequestMember, requestDto);
        //then
        assertAll(
                () -> assertThat(newCareer.getMemberId()).isEqualTo(testMemberId),
                () -> assertThat(newCareer.getId()).isEqualTo(3L),
                () -> assertThat(newCareer.getName()).isEqualTo("test3"),
                () -> assertThat(newCareer.getAlias()).isEqualTo("test3"),
                () -> assertThat(newCareer.getSummary()).isEqualTo("test3"),
                () -> assertThat(newCareer.getStartdate()).isEqualTo(LocalDate.of(2024,4,1)),
                () -> assertThat(newCareer.getEnddate()).isEqualTo(LocalDate.now()),
                () -> assertThat(newCareer.getYear()).isEqualTo(2024),
                () -> assertThat(newCareer.getCategory().getId()).isEqualTo(1L)
        );
        verify(categoryRepository).findById(1L);
        verify(careerRepository).save(any(Career.class));
    }
    @Test
    @DisplayName("unknown 값이 false 이면, EndDate의 값을 사용자가 지정한 날짜로 지정합니다.")
    void create_새로운_career_생성_unknown값이_false일때() {
        //given
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        Career newCareerInstance = Career.builder()
                .id(4L)
                .memberId(testMemberId)
                .name("test4")
                .alias("test4")
                .summary("test4")
                .unknown(false)
                .category(category2)
                .startdate(LocalDate.of(2024,4,1))
                .enddate(LocalDate.of(2024,4,2))
                .year(2024)
                .build();
        when(careerRepository.save(any(Career.class))).thenReturn(newCareerInstance);
        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test4")
                .alias("test4")
                .summary("test4")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,1))
                .endDate(LocalDate.of(2024,4,2))
                .category(2)
                .build();
        //when
        Career newCareer = careerService.createCareer(testRequestMember, requestDto);
        //then
        assertAll(
                () -> assertThat(newCareer.getMemberId().equals(testMemberId)),
                () -> assertThat(newCareer.getId()).isEqualTo(4L),
                () -> assertThat(newCareer.getName()).isEqualTo("test4"),
                () -> assertThat(newCareer.getAlias()).isEqualTo("test4"),
                () -> assertThat(newCareer.getSummary()).isEqualTo("test4"),
                () -> assertThat(newCareer.getStartdate()).isEqualTo(LocalDate.of(2024,4,1)),
                () -> assertThat(newCareer.getEnddate()).isEqualTo(LocalDate.of(2024,4,2)),
                () -> assertThat(newCareer.getYear()).isEqualTo(2024),
                () -> assertThat(newCareer.getCategory().getId()).isEqualTo(2L)
        );
        verify(categoryRepository).findById(2L);
        verify(careerRepository).save(any(Career.class));

    }
    @Test
    @DisplayName("활동 목록들을 카테고리 별로 조회합니다.")
    void read_getCareerGroupedBy_Category_성공() {
        //given
        List<Career> careerList = Arrays.asList(career1, career2);
        when(careerRepository.findAllCareerByMemberId(testMemberId)).thenReturn(careerList);
        //when
        List<? extends CareerResponseDto.CareerGroupedByCategoryDto> result = (List<? extends CareerResponseDto.CareerGroupedByCategoryDto>) careerService.getCareerGroupedBy(testRequestMember,"category");
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        verify(careerRepository, times(1)).findAllCareerByMemberId(testMemberId);
    }
    @Test
    void delete_기존_career_삭제() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(career1));
        //when
        careerService.deleteCareer(testRequestMember,1L);
        //then
        verify(careerRepository,times(1)).delete(career1);
    }
    @Test
    void delete_삭제시_없는_careerId_요청은_에러() {
        //given
        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, ()->{
            careerService.deleteCareer(testRequestMember,1L);});
        verify(careerRepository,never()).delete(any(Career.class));
    }
    @Test
    @DisplayName("update - 활동 데이터 수정 성공, unknown 값이 true 이면, endDate의 값이 현재 날짜로 수정되어야 합니다.")
    void update_기존_Career_데이터_수정_성공() {
        //given

        Career updateCareer = Career.builder()
                .id(1L)
                .memberId(testMemberId)
                .name("update test")
                .alias("update alias")
                .summary("update summary")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2021,1,1))
                .enddate(LocalDate.now())
                .year(2024)
                .build();

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
        Career updatedCareer = careerService.updateCareer(testRequestMember, 1L ,updateRequestDto);

        //then
        assertAll(
                () -> assertThat(updatedCareer.getMemberId().equals(testMemberId)),
                () -> assertThat(updatedCareer.getId()).isEqualTo(1L),
                () -> assertThat(updatedCareer.getName()).isEqualTo("update test"),
                () -> assertThat(updatedCareer.getAlias()).isEqualTo("update alias"),
                () -> assertThat(updatedCareer.getSummary()).isEqualTo("update summary"),
                () -> assertThat(updatedCareer.getStartdate()).isEqualTo(LocalDate.of(2021,1,1)),
                () -> assertThat(updatedCareer.getEnddate()).isEqualTo(LocalDate.now()),
                () -> assertThat(updatedCareer.getYear()).isEqualTo(2024),
                () -> assertThat(updatedCareer.getCategory().getId()).isEqualTo(2L)
        );

        verify(categoryRepository,times(1)).findById(2L);
        verify(careerRepository,times(1)).save(any(Career.class));

    }
    @Test
    @DisplayName("update - null 입력시 기존 내용을 유지해야 합니다.")
    void update_기존_Career_데이터_수정_성공_nuallable_검사() {
        //given
        Career originalCareer = Career.builder()
                .id(1L)
                .memberId(testMemberId)
                .name("test")
                .alias("alias")
                .summary("summary")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();

        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto
                .builder()
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        when(careerRepository.save(any(Career.class))).thenReturn(originalCareer);
        //when
        Career updatedCareer = careerService.updateCareer(testRequestMember, 1L, updateRequestDto);
        //then
        assertAll(
                () -> assertThat(updatedCareer.getMemberId().equals(testMemberId)),
                () -> assertThat(updatedCareer.getId()).isEqualTo(career1.getId()),
                () -> assertThat(updatedCareer.getName()).isEqualTo(career1.getName()),
                () -> assertThat(updatedCareer.getAlias()).isEqualTo(career1.getAlias()),
                () -> assertThat(updatedCareer.getSummary()).isEqualTo(career1.getSummary()),
                () -> assertThat(updatedCareer.getStartdate()).isEqualTo(career1.getStartdate()),
                () -> assertThat(updatedCareer.getEnddate()).isEqualTo(career1.getEnddate()),
                () -> assertThat(updatedCareer.getYear()).isEqualTo(career1.getYear()),
                () -> assertThat(updatedCareer.getCategory().getId()).isEqualTo(1L)

        );
        verify(categoryRepository, never()).findById(any(Long.class));
        verify(careerRepository, times(1)).findById(any(Long.class));
        verify(careerRepository, times(1)).save(any(Career.class));
    }
    @Test
    @DisplayName("update - 공백을 입력하면 summary 항목을 제외한 나머지 항목들은 기존 값을 유지해야 합니다.")
    void update_기존_Career_데이터_수정_성공_summary는_공백_입력시_공백값으로_변경() {
        //given
        Career updateCareer = Career.builder()
                .id(1L)
                .memberId(testMemberId)
                .name("test")
                .alias("alias")
                .summary("     ")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();

        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("")
                .summary("     ")
                .alias("")
                .build();
        when(careerRepository.save(any(Career.class))).thenReturn(updateCareer);
        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        Career updatedCareer = careerService.updateCareer(testRequestMember, 1L, updateRequestDto);
        //then
        assertAll(
                () -> assertThat(updatedCareer.getMemberId().equals(testMemberId)),
                () -> assertThat(updatedCareer.getId()).isEqualTo(1L),
                () -> assertThat(updatedCareer.getName()).isEqualTo(career1.getName()),
                () -> assertThat(updatedCareer.getAlias()).isEqualTo(career1.getAlias()),
                () -> assertThat(updatedCareer.getSummary()).isEqualTo("     "),
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
    @DisplayName("update - 날짜 기간이 올바르지 않으면 ( 예 : 2024-01-01 ~ 2023-01-01 ) CareerValidationException 에러가 발생합니다.")
    void update_기존_Career_데이터_수정_실패_날짜기간이_올바르지_않을_경우_에러() {
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
        assertThrows(CareerValidationException.class, () -> careerService.updateCareer(testRequestMember, 1L, updateRequestDto));
        verify(careerRepository, never()).save(any(Career.class));
        verify(careerRepository, times(1)).findById(any(Long.class));
        verify(categoryRepository, never()).findById(any(Long.class));
    }
    @Test
    @DisplayName("update - memberId가 일치하지 않으면, OwnerMismatchException 에러가 발생합니다.")
    void update_기존_Career_데이터_수정_실패_memberId가_일치하지_않을_경우_에러() {
        //given
        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(1)
                .build();
        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        //then
        assertThrows(OwnerMismatchException.class, () -> careerService.updateCareer(testRequestMember2, 1L, updateRequestDto));
        verify(careerRepository, never()).save(any(Career.class));

    }
    @Test
    @DisplayName("update - careerId가 존재하지 않으면, ResourceNotFoundException 에러가 발생합니다.")
    void update_기존_Career_데이터_수정_실패_careerId가_없는_경우_에러() {
        // given
        CareerRequestDto.UpdateCareerDto updateCareerDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(1)
                .build();
        when(careerRepository.findById(999L)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(ResourceNotFoundException.class, () -> careerService.updateCareer(testRequestMember,999L, updateCareerDto));
        verify(careerRepository, never()).save(any(Career.class));
    }
    @Test
    @DisplayName("update - categoryId가 유효하지 않으면, ResourceNotFoundException 에러가 발생합니다.")
    void update_기존_Career_데이터_수정_실패_categoryId가_유효하지_않을_경우_에러() {
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
        assertThrows(ResourceNotFoundException.class, () -> careerService.updateCareer(testRequestMember, 1L, updateRequestDto));
        verify(careerRepository, never()).save(any(Career.class));
        verify(categoryRepository, times(1)).findById(any(Long.class));
        verify(careerRepository, times(1)).findById(any(Long.class));

    }

}