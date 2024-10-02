package umc.kkijuk.server.detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.BaseCareer;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;

import java.util.List;

public interface BaseCareerDetailRepository extends JpaRepository<BaseCareerDetail,Long> {
    List<BaseCareerDetail> findByBaseCareer(BaseCareer baseCareer);

}
