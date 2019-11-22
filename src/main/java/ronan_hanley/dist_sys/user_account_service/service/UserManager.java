package ronan_hanley.dist_sys.user_account_service.service;

import ronan_hanley.dist_sys.user_account_service.representations.*;

import java.util.List;

public interface UserManager {
    void createUserAsync(NewUser newUser);
    User getUser(Integer id);
    void updateUser(NewUser updatedUser);
    void deleteUser(Integer id);
    List<User> getAllUsers();
    User findUserByUsername(String userName);
    boolean loginPasswordMatchesUser(LoginUser loginUser, User dbUser);
    boolean userExists(Integer id);
}
