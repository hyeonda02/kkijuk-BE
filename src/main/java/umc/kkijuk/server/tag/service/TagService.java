package umc.kkijuk.server.tag.service;

import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;


public interface TagService {
    Tag createTag(Member member, TagRequestDto.CreateTagDto request);
    TagResponseDto.ResultTagDtoList findAllTags(Member member);
    void delete(Member member, Long tagId);
}
