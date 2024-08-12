package umc.kkijuk.server.careerdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;

public interface CareerDetailRepository extends JpaRepository<CareerDetail, Long>, JpaSpecificationExecutor<CareerDetail> {
}
