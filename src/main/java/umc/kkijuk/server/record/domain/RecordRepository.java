package umc.kkijuk.server.record.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.record.domain.Record;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

}
