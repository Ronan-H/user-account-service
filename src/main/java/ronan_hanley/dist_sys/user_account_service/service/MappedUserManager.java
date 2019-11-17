package ronan_hanley.dist_sys.user_account_service.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import ronan_hanley.dist_sys.user_account_service.proto.HashResponse;
import ronan_hanley.dist_sys.user_account_service.proto.PasswordServiceGrpc;
import ronan_hanley.dist_sys.user_account_service.representations.HashPairRep;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MappedUserManager implements UserManager {
    private static final Logger logger = Logger.getLogger(MappedUserManager.class.getName());

    private Map<Integer, User> users;
    private PasswordServiceClient passwordServiceClient;

    public MappedUserManager(PasswordServiceClient passwordServiceClient) {
        this.passwordServiceClient = passwordServiceClient;
        users = new HashMap<>();
    }

    @Override
    public void createUserAsync(NewUser newUser) {
        StreamObserver<HashResponse> responseObserver = new StreamObserver<HashResponse>() {
            @Override
            public void onNext(HashResponse hashResponse) {
                logger.info("Received hash response");
                HashPairRep hashPair = new HashPairRep(hashResponse.getHashPair());
                users.put(newUser.getUserDetails().getUserId(), new User(newUser.getUserDetails(), hashPair));
            }

            @Override
            public void onError(Throwable throwable) {
                Status status = Status.fromThrowable(throwable);

                logger.log(Level.WARNING, "RPC Error: {0}", status);
            }

            @Override
            public void onCompleted() {
                logger.info("Hash completed");
            }
        };

        passwordServiceClient.generateHashPairAsync(newUser, responseObserver);
    }

    @Override
    public User getUser(Integer id) {
        return users.get(id);
    }

    @Override
    public void updateUser(NewUser updatedUser) {
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
    public boolean isValidUser(NewUser userLogin) {
        User dbUser = users.get(userLogin.getUserDetails().getUserId());

        if (dbUser == null) {
            return false;
        }

        if (!userLogin.getUserDetails().equals(dbUser.getUserDetails())) {
            return false;
        }

        return passwordServiceClient.verifyPassword(userLogin.getPassword(), dbUser.getHashPairRep());
    }

    @Override
    public boolean userExists(Integer id) {
        return getUser(id) != null;
    }
}
