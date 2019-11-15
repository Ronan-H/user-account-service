package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;

public class HashPair {
    @NotNull
    private byte[] hashedPassword;

    @NotNull
    private byte[] salt;

    public HashPair(byte[] hashedPassword, byte[] salt) {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
