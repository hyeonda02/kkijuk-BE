package umc.kkijuk.server.detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.*;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;

import java.util.List;

public interface BaseCareerDetailRepository extends JpaRepository<BaseCareerDetail,Long> {
    List<BaseCareerDetail> findByCompetition(Competition competition);

    List<BaseCareerDetail> findByActivity(Activity activity);

    List<BaseCareerDetail> findByCircle(Circle circle);

    List<BaseCareerDetail> findByProject(Project project);

    List<BaseCareerDetail> findByEduCareer(EduCareer edu);

    List<BaseCareerDetail> findByEmployment(Employment emp);
}
