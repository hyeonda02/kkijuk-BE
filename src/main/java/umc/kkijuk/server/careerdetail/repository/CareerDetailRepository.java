package umc.kkijuk.server.careerdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;

public interface CareerDetailRepository extends JpaRepository<CareerDetail,Long> {
}
