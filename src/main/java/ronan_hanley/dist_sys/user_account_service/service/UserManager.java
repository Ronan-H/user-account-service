package ronan_hanley.dist_sys.user_account_service.service;

import io.grpc.StatusRuntimeException;
import ronan_hanley.dist_sys.user_account_service.representations.*;

import java.util.List;

/**
 * Interface defining something that can manage a pool of users
 *
 * (Implementation: MappedUserManager)
 */
public interface UserManager {
    void createUserAsync(NewUser newUser) throws StatusRuntimeException;
    User getUser(Integer id);
    void updateUserAsync(NewUser updatedUser) throws StatusRuntimeException;
    void deleteUser(Integer id);
    List<User> getAllUsers();

    boolean userExists(Integer id);
    User findUserByUsername(String userName);
    boolean loginPasswordMatchesUser(LoginUser loginUser, User dbUser);
}
