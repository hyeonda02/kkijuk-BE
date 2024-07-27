package umc.kkijuk.server.introduce.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
    Optional<Introduce> findByRecruitId(Long recruitId);
}
