package umc.kkijuk.server.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.tag.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsByName(String name);
    List<Tag> findAll();
}
