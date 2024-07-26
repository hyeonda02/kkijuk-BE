package umc.kkijuk.server.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;
import umc.kkijuk.server.tag.dto.converter.TagConverter;
import umc.kkijuk.server.tag.service.TagService;

@io.swagger.v3.oas.annotations.tags.Tag(name="tag",description = "태그 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class TagController {

    private final TagService tagService;

    @PostMapping("/tag")
    @Operation(summary = "태그 추가 API", description = "태그 - 태그를 생성하는 API")
    public ResponseEntity<TagResponseDto.ResultTagDto> create(@RequestBody @Valid TagRequestDto.CreateTagDto request) {
        Tag createTag = tagService.createTag(request);
        return ResponseEntity
                .ok()
                .body(TagConverter.toTagResult(createTag));
    }
    @GetMapping("/tag")
    @Operation(summary = "태그 조회 API", description = "태그 - 태그 조회하는 API")
    public ResponseEntity<TagResponseDto.ResultTagDtoList>  read() {
        return ResponseEntity
                .ok()
                .body(tagService.findAllTags());
    }
    @DeleteMapping("/tag/{tagId}")
    @Operation(summary = "태그 삭제 API",description = "태그 - 태그를 삭제하는 API")
    @Parameter(name = "tagId",description = "태그 Id, path variable 입니다. 존재하는 태그 Id 값을 넘겨 주세요.",example = "1")
    public ResponseEntity<Object> delete(@PathVariable Long tagId) {
        tagService.delete(tagId);
        return ResponseEntity.ok().body(null);
    }

}
