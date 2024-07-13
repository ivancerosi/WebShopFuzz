package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.UserConnectionService;
import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
import hr.algebra.dal.webshop2024dal.Repository.UserConnectionRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserConnectionServiceImpl implements UserConnectionService {
    private final UserConnectionRepository userConnectionRepository;

    public UserConnectionServiceImpl(UserConnectionRepository userConnectionRepository) {
        this.userConnectionRepository = userConnectionRepository;
    }

    @Override
    public List<UserConnection> findAll() {
        return userConnectionRepository.findAll();
    }

    @Override
    public UserConnection findById(long id) {
        Optional<UserConnection> userConnectionOptional = userConnectionRepository.findById(id);

        if (!userConnectionOptional.isPresent()){
            throw new CustomNotFoundException("Connection id not found - " + id);
        }

        return userConnectionOptional.get();
    }

    @Override
    @Transactional
    public UserConnection save(UserConnection obj) {
        return  userConnectionRepository.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<UserConnection> checkIfExists = userConnectionRepository.findById(id);
        if (!checkIfExists.isPresent()){
            throw new CustomNotFoundException("Connection with that ID was not found: " + id);
        }
        userConnectionRepository.deleteById(id);
    }
}
