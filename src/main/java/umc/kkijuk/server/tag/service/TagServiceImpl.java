package umc.kkijuk.server.tag.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.InvalidTagNameException;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;
import umc.kkijuk.server.tag.dto.converter.TagConverter;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Override
    @Transactional
    public Tag createTag(TagRequestDto.CreateTagDto request) {
        String tagName = request.getTagName();
        if (tagName == null || tagName.trim().isEmpty() || tagRepository.existsByName(tagName)) {
            throw new InvalidTagNameException("태그 이름은 공백일 수 없으며, 이미 존재하는 이름은 사용할 수 없습니다.");
        }
        Tag hashTag = TagConverter.toTag(request);
        return tagRepository.save(hashTag);
    }
    @Override
    public TagResponseDto.ResultTagDtoList  findAllTags() {
        return TagConverter.toResultTagDtoList(tagRepository.findAll());
    }
    @Override
    @Transactional
    public void delete(Long tagId) {
        Tag deleteTag = tagRepository.findById(tagId).get();
        tagRepository.delete(deleteTag);
    }

}
