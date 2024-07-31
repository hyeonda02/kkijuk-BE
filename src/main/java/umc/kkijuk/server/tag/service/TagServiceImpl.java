package umc.kkijuk.server.tag.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.InvalidTagNameException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;
import umc.kkijuk.server.tag.dto.converter.TagConverter;
import umc.kkijuk.server.tag.repository.TagRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Override
    @Transactional
    public Tag createTag(Member requestMember, TagRequestDto.CreateTagDto request) {
        String tagName = request.getTagName();
        if (tagName==null || tagName.trim().isEmpty() || tagRepository.existsByName(tagName)) {
            throw new InvalidTagNameException("태그 이름은 비워둘 수 없으며, 이미 존재하는 이름은 사용할 수 없습니다.");
        }
        Tag newTag = TagConverter.toTag(requestMember.getId(), request);
        return tagRepository.save(newTag);
    }
    @Override
    public TagResponseDto.ResultTagDtoList  findAllTags(Member requestMember) {
        return TagConverter.toResultTagDtoList(tagRepository.findAllTagByMemberId(requestMember.getId()));
    }
    @Override
    @Transactional
    public void delete(Member requestMember, Long tagId) {
        Tag deleteTag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", tagId));
        if(!deleteTag.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        tagRepository.delete(deleteTag);
    }


}
