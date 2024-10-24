package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {
    Optional<File> findByTitle(String title);

    boolean existsByTitle(String title);
}
