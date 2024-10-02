package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
