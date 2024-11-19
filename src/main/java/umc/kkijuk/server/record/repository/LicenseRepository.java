package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Award;
import umc.kkijuk.server.record.domain.License;

import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {
    List<License> findByRecordId(Long recordId);
}
