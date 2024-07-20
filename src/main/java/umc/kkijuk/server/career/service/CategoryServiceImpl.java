package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.repository.CategoryRepository;

import java.util.Optional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Optional<Category> findById(Long value) {
        return categoryRepository.findById(value);
    }
}
