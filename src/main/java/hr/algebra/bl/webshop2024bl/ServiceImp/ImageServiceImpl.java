package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.ImageService;
import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Repository.ImageRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepo;

    public ImageServiceImpl(ImageRepository imageRepo) {
        this.imageRepo = imageRepo;
    }

    @Override
    public List<Image> findAll() {
        return imageRepo.findAll();
    }

    @Override
    public Image findById(long id) {
        Optional<Image> imageOptional = imageRepo.findById(id);

        if (!imageOptional.isPresent()){
            throw new CustomNotFoundException("Image id not found - " + id);
        }
        return imageOptional.get();
    }

    @Override
    @Transactional
    public Image save(Image obj) {
        return imageRepo.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Image> checkIfExists = imageRepo.findById(id);
        if (!checkIfExists.isPresent()){
            throw new CustomNotFoundException("Image with that ID was not found: " + id);
        }
        imageRepo.deleteById(id);
    }
}
