package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
