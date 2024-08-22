package umc.kkijuk.server.tag.dto.converter;

import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {
    public static Tag toTag(Long memberId,TagRequestDto.CreateTagDto request) {
        return Tag.builder()
                .name(request.getTagName())
                .memberId(memberId)
                .build();
    }
    public static TagResponseDto.ResultTagDto toTagResult(Tag tag) {
        return TagResponseDto.ResultTagDto.builder()
                .id(tag.getId())
                .tagName(tag.getName())
                .build();
    }

    public static TagResponseDto.ResultTagDtoList toResultTagDtoList(List<Tag> tagList) {
        return TagResponseDto.ResultTagDtoList.builder()
                .count(tagList.size())
                .tagList(tagList.stream().map(
                        value -> TagResponseDto.ResultTagDto.builder()
                                .tagName(value.getName())
                                .id(value.getId())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

}
