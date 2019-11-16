package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;

public class User {
    @NotNull
    private UserDetails userDetails;

    @NotNull
    private HashPair hashPair;

    public User(UserDetails userDetails, HashPair hashPair) {
        this.userDetails = userDetails;
        this.hashPair = hashPair;
    }

    public User() {}

    /**
     * Temporary constructor while I figure out the GRPC stuff
     * TODO use grpc-password-service here
     * @param newUser
     */
    public User(NewUser newUser) {
        this.userDetails = newUser.getUserDetails();
        this.hashPair = new HashPair(new byte[0], new byte[0]);
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public HashPair getHashPair() {
        return hashPair;
    }

    public void setHashPair(HashPair hashPair) {
        this.hashPair = hashPair;
    }
}
