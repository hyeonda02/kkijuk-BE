package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Award;
import umc.kkijuk.server.record.domain.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByMemberId(Long memberId);
}
