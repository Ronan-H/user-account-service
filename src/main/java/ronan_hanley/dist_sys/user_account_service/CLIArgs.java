package ronan_hanley.dist_sys.user_account_service;

import com.beust.jcommander.Parameter;

public class CLIArgs {
    @Parameter(
            names = {"--usage", "-u"},
            description = "Print usage instructions and exit"
    )
    Boolean printUsage = false;

    @Parameter(
            names = {"--port", "-p"},
            description = "Port to run the User Account Service on"
    )
    Integer port = 8080;

    @Parameter(
            names = {"--grpc-host", "-gh"},
            description = "IP address/domain of gRPC password service host"
    )
    String grpcHost = "127.0.0.1";

    @Parameter(
            names = {"--grpc-port", "-gp"},
            description = "Port of gRPC password service host"
    )
    Integer grpcPort = 50051;

    @Parameter(
            names = {"--num-dummy-users", "-du"},
            description = "Number of random dummy users to create on startup (seeded)"
    )
    Integer numDummyUsers = 5;
}
