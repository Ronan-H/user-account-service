package ronan_hanley.dist_sys.user_account_service;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import ronan_hanley.dist_sys.user_account_service.proto.*;
import ronan_hanley.dist_sys.user_account_service.representations.HashPairRep;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;

import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordServiceClient {
    private static final Logger logger = Logger.getLogger(PasswordServiceClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub clientStub;

    /** Construct client for accessing password service server using the existing channel. */
    public PasswordServiceClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // no TLS encryption
                .usePlaintext()
                .build();
        clientStub = PasswordServiceGrpc.newBlockingStub(channel);
    }

    public HashPairRep generateHashPair(NewUser newUser) {
        logger.info(String.format("Generating hash pair"));

        // build hash request
        HashRequest hashRequest = HashRequest.newBuilder()
                .setUserId(newUser.getUserDetails().getUserId())
                .setPassword(newUser.getPassword())
                .build();

        HashResponse hashResponse;
        try {
            // get response
            hashResponse = clientStub.hash(hashRequest);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }

        return new HashPairRep(hashResponse.getHashPair());
    }

    public boolean verifyPassword(String pass, HashPairRep hashPairRep) {
        // build validation request
        ValidateRequest validateRequest = ValidateRequest.newBuilder()
            .setPassword(pass)
            .setHashPair(hashPairRep.toHashPairGRPC()
        ).build();

        ValidateResponse validateResponse;
        try {
            validateResponse = clientStub.validate(validateRequest);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return false;
        }

        // print response
        logger.info("Valid: " + validateResponse.getValid());

        return validateResponse.getValid();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}