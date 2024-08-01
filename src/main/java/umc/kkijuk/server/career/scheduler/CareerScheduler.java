package umc.kkijuk.server.career.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.repository.CareerRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CareerScheduler {
    private final CareerRepository careerRepository;
    @Scheduled(cron = "0 * * * * ?")
    public void updateUnkownEndDates() {
        List<Career> careers = careerRepository.findAllByUnknown(true);
        LocalDate now = LocalDate.now();
        for (Career career : careers) {
            career.setEnddate(now);
            careerRepository.save(career);
        }

    }

}
