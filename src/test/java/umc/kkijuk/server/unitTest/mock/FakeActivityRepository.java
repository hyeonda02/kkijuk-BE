package umc.kkijuk.server.unitTest.mock;

import umc.kkijuk.server.career.domain.Activity;
import umc.kkijuk.server.career.repository.ActivityRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeActivityRepository implements ActivityRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(1);
    private final List<Activity> data = new ArrayList<>();

    @Override
    public Optional<Activity> findById(Long activityId) {
        return data.stream()
                .filter(item -> Objects.equals(item.getId(), activityId))
                .findAny();
    }

    @Override
    public List<Activity> findByMemberId(Long memberId) {
        return data.stream()
                .filter(item -> Objects.equals(item.getMemberId(), memberId))
                .toList();
    }

    @Override
    public List<Activity> findByMemberIdAndNameContaining(Long memberId, String keyword) {
        return data.stream()
                .filter(item -> Objects.equals(item.getMemberId(), memberId) &&
                        item.getName() != null &&
                        item.getName().contains(keyword))
                .toList();
    }

    public Activity save(Activity activity) {
        if (activity.getId() == null || activity.getId() == 0) {
            Activity newActivity = Activity.builder()
                    .memberId(activity.getMemberId())
                    .name(activity.getName())
                    .alias(activity.getAlias())
                    .unknown(activity.getUnknown())
                    .startDate(activity.getStartdate())
                    .endDate(activity.getEnddate())
                    .organizer(activity.getOrganizer())
                    .role(activity.getRole())
                    .teamSize(activity.getTeamSize())
                    .contribution(activity.getContribution())
                    .isTeam(activity.getIsTeam())
                    .build();

            setEndDate(newActivity);
            assignId(newActivity, autoGeneratedId.getAndIncrement());
            data.add(newActivity);
            return newActivity;
        } else {
            data.removeIf(a -> a.getId().equals(activity.getId()));
            data.add(activity);
            return activity;
        }
    }

    @Override
    public void delete(Activity activity) {
        data.removeIf(item -> Objects.equals(item.getId(), activity.getId()));
    }
    private void assignId(Activity activity, Long id) {
        try {
            var field = Activity.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(activity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("id 할당 실패 : ", e);
        }
    }
    public List<Activity> findAll() {
        return new ArrayList<>(data);
    }
    public void clear() {
        data.clear();
        autoGeneratedId.set(0);
    }
    private void setEndDate(Activity activity){
        if (activity.getUnknown()) {
            activity.setEnddate(LocalDate.now());
            activity.setYear(LocalDate.now().getYear());
        } else {
            activity.setYear(activity.getEnddate().getYear());
        }

    }
}