package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.CategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Repository.CategoryRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findById(long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);

        if (!categoryOptional.isPresent()){
            throw new CustomNotFoundException("Category id not found - " + id);
        }

        return categoryOptional.get();
    }

    @Override
    @Transactional
    public Category save(Category obj) {
        return categoryRepo.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Category> checkIfExists = categoryRepo.findById(id);
        if (!checkIfExists.isPresent()){
            throw new CustomNotFoundException("Category with that ID was not found: " + id);
        }
        categoryRepo.deleteById(id);
    }
}
