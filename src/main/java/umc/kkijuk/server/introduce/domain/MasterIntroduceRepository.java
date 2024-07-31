package umc.kkijuk.server.introduce.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MasterIntroduceRepository extends JpaRepository<MasterIntroduce, Long> {
    Optional<List<MasterIntroduce>> findAllByMemberId(Long memberId);
}
