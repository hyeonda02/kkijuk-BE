package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import umc.kkijuk.server.career.domain.Career;

import java.util.List;


public interface CareerRepository extends JpaRepository<Career, Long>, JpaSpecificationExecutor<Career> {
    List<Career> findAllByUnknown(boolean isUnknown);
    List<Career> findAllCareerByMemberId(Long memberId);
}
