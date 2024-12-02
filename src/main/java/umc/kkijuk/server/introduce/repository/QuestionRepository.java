package umc.kkijuk.server.introduce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.introduce.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
