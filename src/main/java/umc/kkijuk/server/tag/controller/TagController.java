package umc.kkijuk.server.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.controller.response.TagResponse;
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

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @PostMapping("/tag")
    @Operation(summary = "태그 추가 API", description = "태그 - 태그를 생성하는 API")
    public TagResponse<TagResponseDto.ResultTagDto> create(@RequestBody @Valid TagRequestDto.CreateTagDto request) {
        Tag createTag = tagService.createTag(requestMember,request);
        return TagResponse.success(HttpStatus.OK,"태그를 성공적으로 생성했습니다.", TagConverter.toTagResult(createTag));
    }
    @GetMapping("/tag")
    @Operation(summary = "태그 조회 API", description = "태그 - 태그 조회하는 API")
    public TagResponse<TagResponseDto.ResultTagDtoList>  read() {
        return TagResponse.success(HttpStatus.OK, "모든 태그를 성공적으로 조회했습니다.", tagService.findAllTags(requestMember));
    }
    @DeleteMapping("/tag/{tagId}")
    @Operation(summary = "태그 삭제 API",description = "태그 - 태그를 삭제하는 API")
    @Parameter(name = "tagId",description = "태그 Id, path variable 입니다. 존재하는 태그 Id 값을 넘겨 주세요.",example = "1")
    public TagResponse<Object> delete(@PathVariable Long tagId) {
        tagService.delete(requestMember,tagId);
        return TagResponse.success(HttpStatus.OK, "태그 삭제가 성공적으로 이루어졌습니다.", null);
    }

}
