package ronan_hanley.dist_sys.user_account_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import ronan_hanley.dist_sys.user_account_service.proto.HashResponse;
import ronan_hanley.dist_sys.user_account_service.representations.HashPairRep;
import ronan_hanley.dist_sys.user_account_service.representations.LoginUser;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedUserManager implements UserManager {
    private Map<Integer, User> users;
    private PasswordServiceClient passwordServiceClient;

    public MappedUserManager(PasswordServiceClient passwordServiceClient) {
        this.passwordServiceClient = passwordServiceClient;
        users = new HashMap<>();
    }

    @Override
    public void createUserAsync(NewUser newUser) throws StatusRuntimeException {
        StreamObserver<HashResponse> responseObserver = new StreamObserver<HashResponse>() {
            @Override
            public void onNext(HashResponse hashResponse) {
                // convert HashPair proto object to HashPairRep
                HashPairRep hashPair = new HashPairRep(hashResponse.getHashPair());
                // create User object based on newUser and add to map
                users.put(newUser.getUserDetails().getUserId(), new User(newUser.getUserDetails(), hashPair));
            }

            @Override
            public void onError(Throwable throwable) {
                throw new StatusRuntimeException(Status.UNAVAILABLE);
            }

            @Override
            public void onCompleted() {}
        };

        // generate a HashPair from the new user's password (asynchronous)
        passwordServiceClient.generateHashPairAsync(newUser, responseObserver);
    }

    @Override
    public User getUser(Integer id) {
        return users.get(id);
    }

    @Override
    public void updateUser(NewUser updatedUser) throws StatusRuntimeException{
        // creating a user same as updating one?
        createUserAsync(updatedUser);
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
    public User findUserByUsername(String userName) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getUserDetails().getUserName().equals(userName)) {
                // found user matching userName
                return user;
            }
        }

        // user with that userName not found, return null
        return null;
    }

    @Override
    public boolean loginPasswordMatchesUser(LoginUser loginUser, User dbUser) {
        // check if the login attempt password matches the user in the db's hash
        // (check hash(password, salt) == stored hash)
        return passwordServiceClient.verifyPassword(loginUser.getPassword(), dbUser.getHashPairRep());
    }

    @Override
    public boolean userExists(Integer id) {
        return getUser(id) != null;
    }
}
