package umc.kkijuk.server.introduce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;

import java.util.List;
import java.util.Optional;

public interface MasterIntroduceRepository extends JpaRepository<MasterIntroduce, Long> {
    Optional<MasterIntroduce> findByMemberId(Long memberId);
    @Query("SELECT m FROM MasterIntroduce m " +
            "JOIN m.masterQuestion mq " +
            "WHERE mq.content LIKE %:keyword%")
    List<MasterIntroduce> searchMasterIntroduceByKeyword(String keyword);
}
