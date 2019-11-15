package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.HashPair;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MappedUserDB implements UserDB {
    private static MappedUserDB instance;

    private MappedUserDB() {}

    public static MappedUserDB getInstance() {
        if (instance == null) {
            instance = new MappedUserDB();
        }

        return instance;
    }

    private Map<Integer, User> users;
    private int nextKey = 0;

    @Override
    public void createUser(NewUser newUser) {
        // TODO: use grpc-password-service to obtain a HashPair
        User user = new User(newUser.getUserDetails(), new HashPair(new byte[0], new byte[0]));
        users.put(user.getUserDetails().getUserId(), user);
    }

    @Override
    public User getUser(Integer id) {
        return users.get(id);
    }

    @Override
    public void updateUser(Integer id, NewUser updatedUser) {
        // TODO: use grpc-password-service to obtain a HashPair
        User user = new User(updatedUser.getUserDetails(), new HashPair(new byte[0], new byte[0]));
        users.put(user.getUserDetails().getUserId(), user);
    }

    @Override
    public void deleteUser(Integer id) {
        users.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public boolean isValidUser(NewUser userLogin) {
        // TODO: use grpc-password-service to validate the login
        return false;
    }
}
