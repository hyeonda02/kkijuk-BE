package umc.kkijuk.server.tag.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import umc.kkijuk.server.common.domian.exception.InvalidTagNameException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;

    private Tag tag1;
    private Tag tag2;
    @BeforeEach
    void init() {
        tag1 = Tag.builder()
                .name("test tag1")
                .build();
        tag2 = Tag.builder()
                .name("test tag2")
                .build();
        tagRepository.save(tag1);
        tagRepository.save(tag2);
    }

    @Test
    void create_새로운_tag_만들기() {
        //given
        TagRequestDto.CreateTagDto request = TagRequestDto.CreateTagDto.builder()
                .tagName("test tag3")
                .build();
        //when
        Tag newTag = tagService.createTag(request);
        //then
        assertAll(
                ()->assertThat(newTag.getId()).isEqualTo(3L),
                ()->assertThat(newTag.getName()).isEqualTo("test tag3")
        );
    }
    @Test
    void create_새로운_tag_만들기_null_입력시_에러() {
        //given
        TagRequestDto.CreateTagDto request = TagRequestDto.CreateTagDto.builder()
                .tagName("")
                .build();
        //when
        //then
        assertThrows(InvalidTagNameException.class, () -> tagService.createTag(request));

    }
    @Test
    void create_새로운_tag_만들기_빈_문자열_입력시_에러() {
        //given
        TagRequestDto.CreateTagDto request = TagRequestDto.CreateTagDto.builder()
                .tagName("          ")
                .build();
        //when
        //then
        assertThrows(InvalidTagNameException.class, () -> tagService.createTag(request));

    }
    @Test
    void create_새로운_tag_만들기_존재하는_tagName_입력시_에러() {
        //given
        TagRequestDto.CreateTagDto request = TagRequestDto.CreateTagDto.builder()
                .tagName("test tag1")
                .build();
        //when
        //then
        assertThrows(InvalidTagNameException.class, () -> tagService.createTag(request));


    }
    @Test
    void delete_기존_tag_삭제하기() {
        //given
        Long tagId = 1L;
        //when
        tagService.delete(tagId);
        //then
        Optional<Tag>  deletedTag = tagRepository.findById(tagId);
        assertThat(deletedTag).isEmpty();

    }
    @Test
    void delete_기존_tag_삭제하기_없는_tagId_입력시_에러() {
        //given
        Long tagId = 999L;
        //when
        //given
        assertThrows(ResourceNotFoundException.class, () -> tagService.delete(tagId));

    }
    @Test
    void read_태그_전부_조회하기() {
        //given
        //when
        TagResponseDto.ResultTagDtoList tagListResult = tagService.findAllTags();
        List<TagResponseDto.ResultTagDto> tagList = tagListResult.getTagList();

        TagResponseDto.ResultTagDto tag1 = tagList.get(0);
        TagResponseDto.ResultTagDto tag2 = tagList.get(1);

        //then
        assertAll(
                () -> assertThat(tagListResult.getCount()).isEqualTo(2),
                () -> assertThat(tagListResult.getTagList().size()).isEqualTo(2),
                () -> assertThat(tagList).isNotEmpty(),
                () -> assertThat(tagList.size()).isEqualTo(2),
                () -> assertThat(tag1.getId()).isEqualTo(1L),
                () -> assertThat(tag1.getTagName()).isEqualTo("test tag1"),
                () -> assertThat(tag2.getId()).isEqualTo(2L),
                () -> assertThat(tag2.getTagName()).isEqualTo("test tag2")

        );


    }

}
