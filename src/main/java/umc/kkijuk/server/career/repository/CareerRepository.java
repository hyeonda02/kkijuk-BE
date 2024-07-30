package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.Career;

import java.util.List;


public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findAllByUnknown(boolean isUnknown);
}
