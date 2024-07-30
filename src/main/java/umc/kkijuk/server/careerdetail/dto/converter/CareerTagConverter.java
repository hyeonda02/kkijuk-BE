package umc.kkijuk.server.careerdetail.dto.converter;

import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.tag.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class CareerTagConverter {
    public static List<CareerTag> toCareerTagList(List<Tag> tagList) {
        return tagList.stream()
                .map(tag -> CareerTag.builder().tag(tag).build()).collect(Collectors.toList());
    }
}
