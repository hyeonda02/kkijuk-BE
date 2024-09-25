package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.License;

public interface LicenseRepository extends JpaRepository<License, Long> {
}
