package umc.kkijuk.server.unitTest.record.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.service.CareerService;
import umc.kkijuk.server.career.service.CareerServiceImpl;
import umc.kkijuk.server.common.domian.exception.IntroFoundException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.repository.MemberRepository;
import umc.kkijuk.server.record.domain.Education;
import umc.kkijuk.server.record.domain.EducationRepository;
import umc.kkijuk.server.record.domain.Record;
import umc.kkijuk.server.record.domain.RecordRepository;
import umc.kkijuk.server.record.dto.EducationReqDto;
import umc.kkijuk.server.record.dto.EducationResDto;
import umc.kkijuk.server.record.dto.RecordReqDto;
import umc.kkijuk.server.record.dto.RecordResDto;
import umc.kkijuk.server.record.service.RecordService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RecordServiceTest {
    @Mock
    private RecordRepository recordRepository;
    @Mock
    private EducationRepository educationRepository;
    @Mock
    private CareerRepository careerRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private RecordService recordService;

    private Member requestMember;
    private Career career1;
    private Career career2;
    private Career career3;
    private Category category1;
    private Category category2;
    private Record record;
    private Education education;
    private Education education1;

    @BeforeEach
    public void Init() {
        requestMember = Member.builder()
                .id(1L)
                .name("홍길동")
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
                .memberId(requestMember.getId())
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
                .memberId(requestMember.getId())
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
                .memberId(requestMember.getId())
                .name("test3")
                .alias("alias3")
                .summary("summary3")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2022, 4, 10))
                .enddate(LocalDate.of(2022, 8, 10))
                .year(2022)
                .build();

        record = Record.builder()
                .memberId(requestMember.getId())
                .address("sample address")
                .profileImageUrl("sampleProfileImageUrl")
                .educations(new ArrayList<>())
                .build();

        education = Education.builder()
                .record(record)
                .state("state")
                .major("major")
                .schoolName("schoolName")
                .category("category")
                .admissionDate(YearMonth.of(2020, 8))
                .graduationDate(YearMonth.of(2024, 8))
                .build();

        education1 = Education.builder()
                .record(record)
                .state("state1")
                .major("major1")
                .schoolName("schoolName1")
                .category("category1")
                .admissionDate(YearMonth.of(2020, 8))
                .graduationDate(YearMonth.of(2024, 8))
                .build();

    }

    @Test
    @DisplayName("이력서 생성 테스트")
    void saveRecord() {

        RecordReqDto recordReqDto = RecordReqDto.builder()
                .address("address")
                .profileImageUrl("profileImageUrl")
                .build();

        when(recordRepository.save(any(Record.class))).thenReturn(record);
        RecordResDto result = recordService.saveRecord(requestMember, recordReqDto);
        //then
        assertAll(
                () -> assertThat(result.getAddress()).isEqualTo("address"),
                () -> assertThat(result.getProfileImageUrl()).isEqualTo("profileImageUrl"),
                () -> assertThat(result.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(result.getName()).isEqualTo("홍길동"),
                () -> assertThat(result.getPhone()).isEqualTo("000-0000-0000")
        );
    }

    @Test
    @DisplayName("이미 이력서가 존재할 경우 IntroFoundException 발생 ")
    void saveRecordIntroFoundException() {
        when(recordRepository.existsByMemberId(requestMember.getId())).thenReturn(true);

        RecordReqDto recordReqDto = RecordReqDto.builder()
                .address("address")
                .profileImageUrl("profileImageUrl")
                .build();

        assertThrows(IntroFoundException.class, () -> {
            recordService.saveRecord(requestMember, recordReqDto);
        });

        verify(recordRepository, never()).save(any(Record.class));
    }

    @Test
    @DisplayName("학력 생성 테스트")
    void saveEducation(){
        EducationReqDto educationReqDto = EducationReqDto.builder()
                .state("state")
                .major("major")
                .schoolName("schoolName")
                .category("category")
                .admissionDate(YearMonth.of(2020, 8))
                .graduationDate(YearMonth.of(2025, 8))
                .build();
        // Arrange
        when(recordRepository.findById(record.getId())).thenReturn(Optional.of(record));
        when(educationRepository.save(any(Education.class))).thenReturn(education);

        // Act
        EducationResDto result = recordService.saveEducation(requestMember, record.getId(),
                educationReqDto);
        // Assert
        assertAll(
                () -> assertThat(result.getCategory()).isEqualTo("category"),
                () -> assertThat(result.getState()).isEqualTo("state"),
                () -> assertThat(result.getSchoolName()).isEqualTo("schoolName"),
                () -> assertThat(result.getIsCurrent()).isEqualTo(true),
                () -> assertThat(result.getMajor()).isEqualTo("major")
        );
    }

    @Test
    @DisplayName("학력 정상 삭제 확인")
    void deleteEducation() {
        // Arrange
        when(educationRepository.findById(education.getId())).thenReturn(Optional.of(education));

        // Act
        Long deletedEducationId = recordService.deleteEducation(requestMember, education.getId());

        // Assert
        assertThat(deletedEducationId).isEqualTo(education.getId());
        verify(educationRepository, times(1)).delete(education);
    }

    @Test
    @DisplayName("존재하지 않는 학력 삭제 시도시 ResourceNotFoundException 발생")
    void deleteEducationResourceNotFoundException() {
        // Arrange
        Long nonExistentEducationId = 999L;
        when(educationRepository.findById(nonExistentEducationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recordService.deleteEducation(requestMember, nonExistentEducationId);
        });

        verify(educationRepository, never()).delete(any(Education.class));
    }

    @Test
    @DisplayName("이력서 업데이트 테스트")
    void updateRecord() {
        // Arrange
        RecordReqDto recordReqDto = RecordReqDto.builder()
                .address("new address")
                .profileImageUrl("newProfileImageUrl")
                .build();

        List<Career> careers = Arrays.asList(career1);
        when(recordRepository.findById(record.getId())).thenReturn(Optional.of(record));
        when(memberRepository.findById(requestMember.getId())).thenReturn(Optional.of(requestMember));
        when(careerRepository.findAll()).thenReturn(careers);

        // Act
        RecordResDto result = recordService.updateRecord(requestMember, record.getId(), recordReqDto);

        // Assert
        assertAll(
                () -> assertThat(result.getAddress()).isEqualTo("new address"),
                () -> assertThat(result.getProfileImageUrl()).isEqualTo("newProfileImageUrl"),
                () -> assertThat(result.getActivitiesAndExperiences()).hasSize(1),
                () -> assertThat(result.getJobs()).isEmpty()
        );
    }

    @Test
    @DisplayName("이력서 Id 다르면 ResourceNotFoundException 발생")
    void updateRecordResourceNotFoundException() {
        // Arrange
        Long nonExistentRecordId = 999L;
        when(recordRepository.findById(nonExistentRecordId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recordService.updateRecord(requestMember, nonExistentRecordId, new RecordReqDto("adress", "URL"));
        });

        verify(recordRepository, never()).save(any(Record.class));
    }


}