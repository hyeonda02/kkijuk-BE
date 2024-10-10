package umc.kkijuk.server.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.tag.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsByNameAndMemberId(String name, Long memberId);
    List<Tag> findAllTagByMemberId(Long MemberId);

}
