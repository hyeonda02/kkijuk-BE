package umc.kkijuk.server.tag.service;

import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;


public interface TagService {
    Tag createTag(TagRequestDto.CreateTagDto request);
    TagResponseDto.ResultTagDtoList findAllTags();
    void delete(Long tagId);
}
