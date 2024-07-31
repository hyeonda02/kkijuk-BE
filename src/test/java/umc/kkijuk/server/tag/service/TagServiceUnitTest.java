package umc.kkijuk.server.tag.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.common.domian.exception.InvalidTagNameException;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceUnitTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;
    private Long testMemberId = 333L;
    private Member testMember;
    private Member testMember2;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    @BeforeEach
    void init() {
        testMember = Member.builder()
                .id(testMemberId)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();

        testMember2 = Member.builder()
                .id(444L)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();


        tag1 = Tag.builder()
                .id(1L)
                .memberId(testMemberId)
                .name("test tag1")
                .build();

        tag2 = Tag.builder()
                .id(2L)
                .memberId(testMemberId)
                .name("test tag2")
                .build();

        tag3 = Tag.builder()
                .id(3L)
                .memberId(testMember2.getId())
                .name("test tag3")
                .build();
    }
    @Test
    void create_tag_생성_성공() {
        //given
        Tag newTag = Tag.builder()
                .id(4L)
                .memberId(testMemberId)
                .name("test tag4")
                .build();

        TagRequestDto.CreateTagDto requestDto = TagRequestDto.CreateTagDto.builder()
                .tagName("test tag4")
                .build();

        when(tagRepository.save(any(Tag.class))).thenReturn(newTag);
        //when
        Tag tag = tagService.createTag(testMember, requestDto);
        //then
        assertAll(
                () -> assertThat(tag.getMemberId().equals(testMemberId)),
                () -> assertThat(tag.getId()).isEqualTo(4L),
                () -> assertThat(tag.getName()).isEqualTo("test tag4")
        );
        verify(tagRepository).save(any(Tag.class));
    }
    @Test
    void create_tag_생성_존재하는_이름입력시_생성_실패() {
        //given
        TagRequestDto.CreateTagDto requestDto = TagRequestDto.CreateTagDto.builder()
                .tagName("test tag2")
                .build();
        when(tagRepository.existsByName(requestDto.getTagName())).thenReturn(true);
        //when
        //then
        assertThrows(InvalidTagNameException.class, () -> {
            tagService.createTag(testMember, requestDto);
        });
        verify(tagRepository, times(1)).existsByName(requestDto.getTagName());
        verify(tagRepository, never()).save(any(Tag.class));

    }
    @Test
    void create_tag_생성_공백입력시_생성_실패() {
        //given
        TagRequestDto.CreateTagDto requestDto = TagRequestDto.CreateTagDto.builder()
                .tagName("         ")
                .build();
        //when
        //then
        assertThrows(InvalidTagNameException.class, () -> {
            tagService.createTag(testMember, requestDto);
        });
        verify(tagRepository, never()).save(any(Tag.class));

    }
    @Test
    void delete_tag_삭제_성공() {
        //given
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag1));
        //when
        tagService.delete(testMember, 1L);
        //then
        verify(tagRepository,times(1)).delete(tag1);
    }
    @Test
    void delete_tag_존재하지_않는_tagId_입력시_삭제_실패() {
        //given
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () ->{
            tagService.delete(testMember, 1L);
        });
        verify(tagRepository, never()).delete(any(Tag.class));

    }
    @Test
    void delete_tag_memberId가_같지_않을_경우_실패() {
        //given
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));
        //when
        //then
        assertThrows(OwnerMismatchException.class, () -> tagService.delete(testMember2, 1L));
        verify(tagRepository, never()).save(any(Tag.class));
    }
    @Test
    void read_memberId로_존재하는_tag_모두_조회() {
        //given
        List<Tag> tagList = Arrays.asList(tag1, tag2);
        when(tagRepository.findAllTagByMemberId(testMemberId)).thenReturn(tagList);
        //when
        TagResponseDto.ResultTagDtoList allTags = tagService.findAllTags(testMember);
        //then
        assertAll(
                () -> assertThat(allTags).isNotNull(),
                () -> assertThat(allTags.getTagList()).hasSize(tagList.size()),
                () -> assertThat(allTags.getTagList()).extracting("tagName").contains(tagList.get(0).getName(),tagList.get(1).getName())


        );
        verify(tagRepository, times(1)).findAllTagByMemberId(testMemberId);
    }


}
