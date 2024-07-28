package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.RecruitStatus;
import umc.kkijuk.server.recruit.domain.ValidRecruitDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
@Builder
public class ValidRecruitListResponse {
    private final ValidRecruitList unapplied;
    private final ValidRecruitList planned;
    private final ValidRecruitList applying;
    private final ValidRecruitList rejected;
    private final ValidRecruitList accepted;

    public static ValidRecruitListResponse from(List<ValidRecruitDto> dto) {
        ValidRecruitListResponse response = ValidRecruitListResponse.builder()
                .unapplied(new ValidRecruitList(new ArrayList<>()))
                .planned(new ValidRecruitList(new ArrayList<>()))
                .applying(new ValidRecruitList(new ArrayList<>()))
                .rejected(new ValidRecruitList(new ArrayList<>()))
                .accepted(new ValidRecruitList(new ArrayList<>()))
                .build();

        Map<RecruitStatus, Consumer<ValidRecruitDto>> statusActions = Map.of(
                RecruitStatus.UNAPPLIED, item -> response.getUnapplied().addItem(item),
                RecruitStatus.PLANNED, item -> response.getPlanned().addItem(item),
                RecruitStatus.APPLYING, item -> response.getApplying().addItem(item),
                RecruitStatus.ACCEPTED, item -> response.getAccepted().addItem(item),
                RecruitStatus.REJECTED, item -> response.getRejected().addItem(item)
        );

        dto.forEach(item -> statusActions.getOrDefault(item.getStatus(), i -> {}).accept(item));

        return response;
    }
}
