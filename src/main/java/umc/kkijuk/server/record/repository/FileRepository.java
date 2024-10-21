package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.File;

public interface FileRepository extends JpaRepository<File,Long> {
}
