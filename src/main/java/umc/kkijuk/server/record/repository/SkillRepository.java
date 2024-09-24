package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
