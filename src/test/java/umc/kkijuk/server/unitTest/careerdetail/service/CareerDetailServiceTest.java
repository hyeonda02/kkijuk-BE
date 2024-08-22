package umc.kkijuk.server.unitTest.careerdetail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.repository.CareerDetailRepository;
import umc.kkijuk.server.careerdetail.repository.CareerTagRepository;
import umc.kkijuk.server.careerdetail.service.CareerDetailServiceImpl;
import umc.kkijuk.server.common.domian.exception.CareerValidationException;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareerDetailServiceTest {
    @InjectMocks
    private CareerDetailServiceImpl careerDetailService;
    @Mock
    private CareerRepository careerRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private CareerDetailRepository careerDetailRepository;
    @Mock
    private CareerTagRepository careerTagRepository;
    private Long testMemberId1 = 1L;
    private Long testMemberId2 = 2L;
    private Member testRequestMember1;
    private Member testRequestMember2;

    private Career career1;
    private Career career2;
    private Category category1;
    private CareerDetail careerDetail1;
    private CareerDetail careerDetail2;
    private CareerDetail updateDetail;
    private CareerTag careerTag1;
    private CareerTag careerTag2;
    private Tag tag1;
    private Tag tag2;
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
                .careerDetailList(new ArrayList<>())
                .year(2024)
                .build();

        career2 = Career.builder()
                .id(2L)
                .memberId(testMemberId1)
                .name("test2")
                .alias("alias2")
                .summary("summary2")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .careerDetailList(new ArrayList<>())
                .year(2024)
                .build();

        tag1 = Tag.builder()
                .id(1L)
                .name("test tag1")
                .build();

        tag2 = Tag.builder()
                .id(2L)
                .name("test tag2")
                .build();

        careerTag1 = CareerTag.builder()
                .id(1L)
                .tag(tag1)
                .build();

        careerTag2 = CareerTag.builder()
                .id(2L)
                .tag(tag2)
                .build();

        careerDetail1 = CareerDetail.builder()
                .id(1L)
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1)
                .careerTagList(new ArrayList<>(Arrays.asList(careerTag1,careerTag2)))
                .career(career1)
                .build();


        careerDetail2 = CareerDetail.builder()
                .id(2L)
                .title("test title2")
                .content("test content2")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1).careerTagList(Arrays.asList(careerTag1,careerTag2))
                .career(career2)
                .build();

        updateDetail = CareerDetail.builder()
                .id(1L)
                .title("update title1")
                .content("update content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1).careerTagList(Arrays.asList(careerTag2))
                .career(career1)
                .build();



    }
    @Test
    @DisplayName("[create] 새로운 활동기록 생성 성공")
    void createCareerDetail() {
        //given
        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(1L,2L))
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));
        when(tagRepository.findById(2L)).thenReturn(Optional.of(tag2));
        when(careerDetailRepository.save(any(CareerDetail.class))).thenReturn(careerDetail1);

        //when
        CareerDetail newCareerDetail = careerDetailService.create(testRequestMember1, createRequestDto, 1L);


        //then
        assertAll(
                () -> assertThat(newCareerDetail.getTitle()).isEqualTo("test title1"),
                () -> assertThat(newCareerDetail.getContent()).isEqualTo("test content1"),
                () -> assertThat(newCareerDetail.getCareer()).isEqualTo(career1),
                () -> assertThat(newCareerDetail.getStartDate()).isEqualTo(LocalDate.of(2024,1,1)),
                () -> assertThat(newCareerDetail.getEndDate()).isEqualTo(LocalDate.of(2024,1,2)),
                () -> assertThat(newCareerDetail.getMemberId()).isEqualTo(testMemberId1),
                () -> assertThat(newCareerDetail.getCareerTagList()).isEqualTo(Arrays.asList(careerTag1,careerTag2))
        );

        verify(careerRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(2)).findById(any(Long.class));
        verify(careerDetailRepository, times(1)).save(any(CareerDetail.class));
    }
    @Test
    @DisplayName("[create] 다른 사용자의 요청의 경우 OwnerMismatchException 발생")
    void createCareerDetailOwnerMismatchException() {
        //given
        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(tag1.getId(),tag2.getId()))
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        assertThrows(OwnerMismatchException.class, () -> {
                    careerDetailService.create(testRequestMember2, createRequestDto, 1L);
                });
        //then
        verify(careerRepository, times(1)).findById(anyLong());
        verify(tagRepository, never()).findById(anyLong());
        verify(careerDetailRepository,never()).save(any(CareerDetail.class));

    }
    @Test
    @DisplayName("[create] 없는 리소스(tag) 요청의 경우 ResourceNotFoundException 발생")
    void createCareerDetailTagResourceNotFoundException() {
        //given
        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(3L))
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career1));
        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            careerDetailService.create(testRequestMember1, createRequestDto, 1L);
        });
        //then
        verify(careerRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).findById(3L);
        verify(careerDetailRepository,never()).save(any(CareerDetail.class));

    }
    @Test
    @DisplayName("[create] 없는 리소스(career) 요청의 경우 ResourceNotFoundException 발생")
    void createCareerDetailCareerResourceNotFoundException() {
        //given
        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(tag1.getId(),tag2.getId()))
                .build();

        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            careerDetailService.create(testRequestMember1, createRequestDto, 555L);
        });
        //then
        verify(careerRepository, times(1)).findById(anyLong());
        verify(tagRepository, never()).findById(anyLong());
        verify(careerDetailRepository,never()).save(any(CareerDetail.class));

    }
    @Test
    @DisplayName("[delete] 존재하는 활동기록 삭제 ")
    void deleteCareerDetail() {
        //given
        when(careerDetailRepository.findById(1L)).thenReturn(Optional.of(careerDetail1));
        //when
        careerDetailService.delete(testRequestMember1, 1L,1L);
        //then
        verify(careerDetailRepository,times(1)).delete(careerDetail1);
    }
    @Test
    @DisplayName("[delete] 없는 리소스 요청의 경우 ResourceNotFoundException 발생")
    void deleteCareerDetailResourceNotFoundException() {
        //given
        when(careerDetailRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () ->
                careerDetailService.delete(testRequestMember1, 1L,333L));
        verify(careerDetailRepository, never()).delete(any(CareerDetail.class));
        verify(careerDetailRepository,times(1)).findById(any(Long.class));
    }
    @Test
    @DisplayName("[delete] 다른 사용자의 요청의 경우 OwnerMismatchException 발생")
    void deleteOwnerMismatchException() {
        //given
        when(careerDetailRepository.findById(1L)).thenReturn(Optional.of(careerDetail1));
        //when
        //then
        assertThrows(OwnerMismatchException.class, () ->{
            careerDetailService.delete(testRequestMember2, 1L,1L);
        });
        verify(careerDetailRepository, times(1)).findById(anyLong());
        verify(careerDetailRepository, never()).delete(any(CareerDetail.class));

    }
    @Test
    @DisplayName("[delete] 주어진 활동 기록 Id가 해당 활동에 속하지 않을 경우 CareerValidationException 발생")
    void deleteCareerDetailCareerValidationException() {
        //given
        when(careerDetailRepository.findById(2L)).thenReturn(Optional.of(careerDetail2));
        //when
        //then
        assertThrows(CareerValidationException.class, () ->{
            careerDetailService.delete(testRequestMember1, 1L,2L);
        });
        verify(careerDetailRepository, times(1)).findById(anyLong());
        verify(careerDetailRepository, never()).delete(any(CareerDetail.class));


    }

    @Test
    @DisplayName("[update] 활동 기록 업데이트 성공")
    void updateCareerDetail() {
        // given
        CareerTag existingCareerTag1 = CareerTag.builder()
                .id(1L)
                .careerDetail(careerDetail1)
                .tag(tag1)
                .build();

        CareerDetail existingCareerDetail = CareerDetail.builder()
                .id(1L)
                .title("existing title")
                .content("existing content")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 1, 2))
                .memberId(testMemberId1)
                .career(career1)
                .careerTagList(new ArrayList<>(Arrays.asList(existingCareerTag1)))
                .build();

        CareerTag updatedCareerTag2 = CareerTag.builder()
                .id(2L)
                .careerDetail(existingCareerDetail)
                .tag(tag2)
                .build();

        CareerDetail updatedCareerDetail = CareerDetail.builder()
                .id(1L)
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .memberId(testMemberId1)
                .career(career1)
                .careerTagList(new ArrayList<>(Arrays.asList(updatedCareerTag2)))
                .build();

        when(careerDetailRepository.findById(1L)).thenReturn(Optional.of(existingCareerDetail));
        when(tagRepository.findById(tag1.getId())).thenReturn(Optional.of(tag1));
        when(tagRepository.findById(tag2.getId())).thenReturn(Optional.of(tag2));
        when(careerDetailRepository.save(any(CareerDetail.class))).thenReturn(updatedCareerDetail);

        // when
        CareerDetail actualUpdatedCareerDetail = careerDetailService.update(testRequestMember1,
                CareerDetailRequestDto.CareerDetailUpdate.builder()
                        .title("updated title")
                        .content("updated content")
                        .startDate(LocalDate.of(2024, 2, 1))
                        .endDate(LocalDate.of(2024, 3, 1))
                        .tagList(Arrays.asList(tag1.getId(), tag2.getId()))
                        .build(),
                1L, 1L);

        // then
        assertAll(
                () -> assertThat(actualUpdatedCareerDetail.getTitle()).isEqualTo("updated title"),
                () -> assertThat(actualUpdatedCareerDetail.getContent()).isEqualTo("updated content"),
                () -> assertThat(actualUpdatedCareerDetail.getCareer()).isEqualTo(career1),
                () -> assertThat(actualUpdatedCareerDetail.getStartDate()).isEqualTo(LocalDate.of(2024, 2, 1)),
                () -> assertThat(actualUpdatedCareerDetail.getEndDate()).isEqualTo(LocalDate.of(2024, 3, 1)),
                () -> assertThat(actualUpdatedCareerDetail.getMemberId()).isEqualTo(testMemberId1),
                () -> assertThat(actualUpdatedCareerDetail.getCareerTagList()).containsExactlyInAnyOrderElementsOf(Arrays.asList(updatedCareerTag2))
        );

        verify(careerDetailRepository, times(1)).findById(anyLong());
        verify(careerDetailRepository, times(1)).save(any(CareerDetail.class));
        verify(tagRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("[update] 없는 리소스(careerDetail) 요청의 경우 ResourceNotFoundException 발생")
    void updateCareerDetailResourceNotFoundException() {
        //given
        CareerDetailRequestDto.CareerDetailUpdate updateRequest = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(tag1.getId(), tag2.getId()))
                .build();
        when(careerDetailRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () ->{
            careerDetailService.update(testRequestMember1,updateRequest, 1L,333L);
        });
        verify(careerDetailRepository, never()).save(any(CareerDetail.class));
        verify(careerDetailRepository,times(1)).findById(any(Long.class));
    }
    @Test
    @DisplayName("[update] 주어진 활동 기록 Id가 해당 활동에 속하지 않을 경우 CareerValidationException 발생")
    void updateCareerDetailCareerValidationException() {
        //given
        CareerDetailRequestDto.CareerDetailUpdate updateRequest = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(tag1.getId()))
                .build();
        when(careerDetailRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(careerDetail1));
        //when
        //then
        assertThrows(CareerValidationException.class, () ->{
            careerDetailService.update(testRequestMember1,updateRequest, 333L,1L);
        });
        verify(careerDetailRepository, never()).save(any(CareerDetail.class));
        verify(careerDetailRepository,times(1)).findById(any(Long.class));

    }
    @Test
    @DisplayName("[update] 다른 사용자의 요청의 경우 OwnerMismatchException 발생")
    void updateOwnerMismatchException() {
        //given
        CareerDetailRequestDto.CareerDetailUpdate updateRequest = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(tag1.getId()))
                .build();
        when(careerDetailRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(careerDetail1));
        //when
        //then
        assertThrows(OwnerMismatchException.class, () ->{
            careerDetailService.update(testRequestMember2, updateRequest, 1L,1L);
        });
        verify(careerDetailRepository, never()).save(any(CareerDetail.class));
        verify(careerDetailRepository,times(1)).findById(any(Long.class));


    }
    @Test
    @DisplayName("[update] 없는 리소스(tag) 요청의 경우 ResourceNotFoundException 발생")
    void updateCareerDetailTagResourceNotFoundException() {
        //given
        CareerTag existingCareerTag1 = CareerTag.builder()
                .id(1L)
                .careerDetail(careerDetail1)
                .tag(tag1)
                .build();

        CareerDetail existingCareerDetail = CareerDetail.builder()
                .id(1L)
                .title("existing title")
                .content("existing content")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 1, 2))
                .memberId(testMemberId1)
                .career(career1)
                .careerTagList(new ArrayList<>(Arrays.asList(existingCareerTag1)))
                .build();

        CareerDetailRequestDto.CareerDetailUpdate updateRequest = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(3L))
                .build();

        when(careerDetailRepository.findById(1L)).thenReturn(Optional.of(existingCareerDetail));
        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            careerDetailService.update(testRequestMember1, updateRequest, 1L,1L);
        });
        //then
        verify(careerDetailRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).findById(3L);
        verify(careerDetailRepository,never()).save(any(CareerDetail.class));
    }
}
