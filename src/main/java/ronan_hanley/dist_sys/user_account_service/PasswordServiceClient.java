package ronan_hanley.dist_sys.user_account_service;

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
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
    private final PasswordServiceGrpc.PasswordServiceStub asyncClientStub;

    /** Construct client for accessing password service server using the existing channel. */
    public PasswordServiceClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // no TLS encryption
                .usePlaintext()
                .build();
        clientStub = PasswordServiceGrpc.newBlockingStub(channel);
        asyncClientStub = PasswordServiceGrpc.newStub(channel);
    }

    public void generateHashPairAsync(NewUser newUser, StreamObserver<HashResponse> responseObserver) {
        logger.info(String.format("Generating hash pair"));

        // build hash request
        HashRequest hashRequest = HashRequest.newBuilder()
                .setUserId(newUser.getUserDetails().getUserId())
                .setPassword(newUser.getPassword())
                .build();

        HashResponse hashResponse;
        try {
            asyncClientStub.hash(hashRequest, responseObserver);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
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
