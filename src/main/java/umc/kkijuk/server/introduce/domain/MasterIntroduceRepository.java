package umc.kkijuk.server.introduce.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.introduce.dto.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface MasterIntroduceRepository extends JpaRepository<MasterIntroduce, Long> {
    Optional<MasterIntroduce> findByMemberId(Long memberId);
}
