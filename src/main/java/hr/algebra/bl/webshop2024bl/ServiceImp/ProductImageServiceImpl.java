package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.ProductImageService;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;
import hr.algebra.dal.webshop2024dal.Repository.ProductImageRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepo;

    public ProductImageServiceImpl(ProductImageRepository productImageRepo) {
        this.productImageRepo = productImageRepo;
    }

    @Override
    public List<ProductImage> findAll() {
        return productImageRepo.findAll();
    }

    @Override
    public ProductImage findById(long id) {
        Optional<ProductImage> productImgOptional = productImageRepo.findById(id);

        if (!productImgOptional.isPresent()){
            throw new CustomNotFoundException("Product image id not found - " + id);
        }
        return productImgOptional.get();
    }

    @Override
    public List<ProductImage> findByProduct_ProductId(Long productId) {
        return productImageRepo.findByProduct_ProductId(productId);
    }

    @Override
    @Transactional
    public ProductImage save(ProductImage obj) {
        return productImageRepo.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<ProductImage> checkIfExists = productImageRepo.findById(id);
        if (!checkIfExists.isPresent()){
            throw new CustomNotFoundException("Product Image with that ID was not found: " + id);
        }
        productImageRepo.deleteById(id);
    }
}
