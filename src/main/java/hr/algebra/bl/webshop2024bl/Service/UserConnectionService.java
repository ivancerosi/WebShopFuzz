package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.UserConnection;

import java.util.List;

public interface UserConnectionService {
    List<UserConnection> findAll();
    UserConnection findById(long id);
    UserConnection save(UserConnection obj);
    void deleteById(long id);
}
