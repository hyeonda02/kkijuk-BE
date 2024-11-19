package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Award;
import umc.kkijuk.server.record.domain.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {
    Optional<File> findByMemberIdAndFileTitle(Long memberId, String fileTitle);
    boolean existsByMemberIdAndFileTitle(Long memberId, String fileTitle);
    boolean existsByMemberIdAndUrlTitle(Long memberId, String urlTitle);
    Optional<File> findByMemberIdAndUrlTitle(Long memberId, String urlTitle);
    List<File> findByRecordId(Long recordId);
}
