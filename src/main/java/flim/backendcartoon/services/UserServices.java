package flim.backendcartoon.services;


import flim.backendcartoon.entities.User;

import java.util.List;

public interface UserServices {
    void createUser(User user);
    User findUserById(String id);
    User findUserByPhoneNumber(String phoneNumber);
    List<User> findAllUsers();
    User findUserById_ttt(String id);
    void updateUser(User user);

}
