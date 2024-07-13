package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.SubcategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
import hr.algebra.dal.webshop2024dal.Repository.SubcategoryRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepo;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepo) {
        this.subcategoryRepo = subcategoryRepo;
    }

    @Override
    public List<Subcategory> findAll() {
        return subcategoryRepo.findAll();
    }

    @Override
    public Subcategory findById(long id) {
        Optional<Subcategory> subcatOptional = subcategoryRepo.findById(id);

        if (!subcatOptional.isPresent()){
            throw new CustomNotFoundException("Subcategory id not found - " + id);
        }
        return subcatOptional.get();
    }

    @Override
    @Transactional
    public Subcategory save(Subcategory obj) {
        return subcategoryRepo.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Subcategory> checkIfExists = subcategoryRepo.findById(id);
        if (!checkIfExists.isPresent()){
            throw new CustomNotFoundException("Subcategory with that ID was not found: " + id);
        }
        subcategoryRepo.deleteById(id);
    }
}
