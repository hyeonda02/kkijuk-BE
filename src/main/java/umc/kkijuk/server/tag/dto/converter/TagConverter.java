package umc.kkijuk.server.tag.dto.converter;

import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {
    public static Tag toTag(TagRequestDto.CreateTagDto request) {
        return Tag.builder()
                .name(request.getTagName())
                .build();
    }
    public static TagResponseDto.ResultTagDto toTagResult(Tag hashTag) {
        return TagResponseDto.ResultTagDto.builder()
                .id(hashTag.getId())
                .tagName(hashTag.getName())
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
