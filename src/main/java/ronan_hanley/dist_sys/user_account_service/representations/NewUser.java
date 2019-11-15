package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;

public class NewUser {
    @NotNull
    private UserDetails userDetails;

    @NotNull
    private HashPair hashPair;

    public NewUser(UserDetails userDetails, HashPair hashPair) {
        this.userDetails = userDetails;
        this.hashPair = hashPair;
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
