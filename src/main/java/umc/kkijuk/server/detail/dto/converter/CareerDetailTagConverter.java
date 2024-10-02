package umc.kkijuk.server.detail.dto.converter;

import umc.kkijuk.server.detail.domain.mapping.CareerDetailTag;
import umc.kkijuk.server.tag.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class CareerDetailTagConverter {
    public static List<CareerDetailTag> toCareerDetailTagList(List<Tag> tagList) {
        return tagList.stream()
                .map(tag -> CareerDetailTag.builder().tag(tag).build()).collect(Collectors.toList());
    }
}
