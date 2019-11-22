package ronan_hanley.dist_sys.user_account_service;

import com.beust.jcommander.Parameter;

public class CLIArgs {
    @Parameter(
            names = {"--usage", "-u"},
            description = "Print usage instructions and exit"
    )
    Boolean printUsage = false;

    @Parameter(
            names = {"--grpc-host", "-h"},
            description = "IP address/domain of gRPC password service host"
    )
    String grpcHost = "127.0.0.1";

    @Parameter(
            names = {"--grpc-port", "-p"},
            description = "Port of gRPC password service host"
    )
    Integer grpcPort = 50051;

    @Parameter(
            names = {"--num-dummy-users", "-du"},
            description = "Number of random dummy users to create on startup (seeded)"
    )
    Integer numDummyUsers = 5;
}
