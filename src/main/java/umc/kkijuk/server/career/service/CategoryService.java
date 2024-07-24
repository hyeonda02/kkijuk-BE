package umc.kkijuk.server.career.service;

import umc.kkijuk.server.career.domain.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long value);
}
