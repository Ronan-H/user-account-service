package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.HashPairRep;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedUserDB implements UserDB {
    private static MappedUserDB instance;

    private Map<Integer, User> users;
    private PasswordServiceClient passwordServiceClient;
    private int nextKey = 0;

    private MappedUserDB() {
        users = new HashMap<>();
        passwordServiceClient = new PasswordServiceClient("localhost", 50051);
    }

    public static MappedUserDB getInstance() {
        if (instance == null) {
            instance = new MappedUserDB();
        }

        return instance;
    }

    @Override
    public void createUser(NewUser newUser) {
        HashPairRep hashPair = passwordServiceClient.generateHashPair(newUser);
        users.put(newUser.getUserDetails().getUserId(), new User(newUser.getUserDetails(), hashPair));
    }

    @Override
    public User getUser(Integer id) {
        return users.get(id);
    }

    @Override
    public void updateUser(NewUser updatedUser) {
        HashPairRep hashPair = passwordServiceClient.generateHashPair(updatedUser);
        User user = new User(updatedUser.getUserDetails(), hashPair);
        users.replace(user.getUserDetails().getUserId(), user);
    }

    @Override
    public void deleteUser(Integer id) {
        users.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean isValidUser(NewUser userLogin) {
        User dbUser = users.get(userLogin.getUserDetails().getUserId());

        if (dbUser == null) {
            return false;
        }

        return passwordServiceClient.verifyPassword(userLogin.getPassword(), dbUser.getHashPairRep());
    }

    @Override
    public boolean userExists(Integer id) {
        return getUser(id) != null;
    }
}
