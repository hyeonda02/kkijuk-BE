package umc.kkijuk.server.introduce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.introduce.domain.MasterQuestion;

public interface MasterQuestionRepository extends JpaRepository<MasterQuestion, Long> {
}
