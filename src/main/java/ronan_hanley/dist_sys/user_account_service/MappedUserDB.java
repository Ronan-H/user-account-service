package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.HashPair;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedUserDB implements UserDB {
    private static MappedUserDB instance;

    private Map<Integer, User> users;
    private int nextKey = 0;

    private MappedUserDB() {
        users = new HashMap<>();
    }

    public static MappedUserDB getInstance() {
        if (instance == null) {
            instance = new MappedUserDB();
        }

        return instance;
    }

    @Override
    public void createUser(NewUser newUser) {
        // TODO use grpc-password-service here
        users.put(newUser.getUserDetails().getUserId(), new User(newUser));
    }

    @Override
    public User getUser(Integer id) {
        return users.get(id);
    }

    @Override
    public void updateUser(NewUser updatedUser) {
        // TODO: use grpc-password-service to obtain a HashPair
        User user = new User(updatedUser.getUserDetails(), new HashPair(new byte[0], new byte[0]));
        users.put(user.getUserDetails().getUserId(), user);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return users.remove(id) != null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean isValidUser(NewUser userLogin) {
        // TODO: use grpc-password-service to validate the login
        return false;
    }
}
