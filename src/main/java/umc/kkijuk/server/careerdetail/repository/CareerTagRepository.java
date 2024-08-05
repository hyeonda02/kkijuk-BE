package umc.kkijuk.server.careerdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;

public interface CareerTagRepository extends JpaRepository<CareerTag, Long> {
}
