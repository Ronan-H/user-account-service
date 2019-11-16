package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.*;

import java.util.List;

public interface UserDB {
    void createUser(NewUser newUser);
    User getUser(Integer id);
    void updateUser(NewUser updatedUser);
    boolean deleteUser(Integer id);
    List<User> getAllUsers();
    boolean isValidUser(NewUser userLogin);
}
