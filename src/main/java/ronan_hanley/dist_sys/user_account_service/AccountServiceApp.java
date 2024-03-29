package ronan_hanley.dist_sys.user_account_service;

import com.beust.jcommander.JCommander;
import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Environment;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.service.*;

import java.util.logging.Logger;

public class AccountServiceApp extends Application<Configuration> {
    private static final Logger logger = Logger.getLogger(AccountServiceApp.class.getName());
    private CLIArgs jcArgs;

    public AccountServiceApp(CLIArgs jcArgs) {
        this.jcArgs = jcArgs;
    }

    @Override
    public void run(Configuration config, Environment env) {
        // from https://stackoverflow.com/questions/20028451/change-dropwizard-default-ports
        // set the port to listen on
        ((HttpConnectorFactory) ((DefaultServerFactory) config.getServerFactory()).getApplicationConnectors().get(0)).setPort(jcArgs.port);

        // register health check with using gRPC password service
        env.healthChecks().register("GRPCHealthCheck", new GRPCHealthCheck(jcArgs.grpcHost, jcArgs.grpcPort));

        // run health check
        HealthCheck.Result result = env.healthChecks().runHealthCheck("GRPCHealthCheck");
        if (result.isHealthy()) {
            //  gRPC connection/stub method calls successful
            logger.info("GRPCHealthCheck: OK");
        }
        else {
            // gRPC failure, exit program
            logger.severe(String.format("GRPCHealthCheck: FAIL with message \"%s\"", result.getMessage()));
            logger.severe("Please review your gRPC password service setup and try again. Exiting...");
            System.exit(1);
        }

        // initialise password service client, user manager, REST controllers
        PasswordServiceClient passwordServiceClient = new PasswordServiceClient(jcArgs.grpcHost, jcArgs.grpcPort);
        UserManager userManager = new MappedUserManager(passwordServiceClient);
        env.jersey().register(new UserRESTController(userManager));
        env.jersey().register(new LoginRESTController(userManager));

        if (jcArgs.numDummyUsers > 0) {
            // generate dummy users
            DummyUserGenerator dummyUserGenerator = new DummyUserGenerator(3);
            NewUser[] dummyUsers = dummyUserGenerator.generateDummyUsers(jcArgs.numDummyUsers);

            StringBuilder toLog = new StringBuilder();
            for (NewUser dummyUser : dummyUsers) {
                userManager.createUserAsync(dummyUser);
                toLog.append(String.format("\t%nCreated dummy user: %s", dummyUser.toString()));
            }

            // print dummy user info
            logger.info(toLog.toString());
        }
    }


    public static void main(String[] rawArgs) throws Exception {
        // use JCommander to parse command line arguments
        CLIArgs jcArgs = new CLIArgs();

        JCommander jc = JCommander.newBuilder()
            .addObject(jcArgs)
            .build();

        jc.parse(rawArgs);
        jc.setProgramName("RESTful User Account Service");

        if (jcArgs.printUsage) {
            // print command line argument usage and exit
            StringBuilder rawUsage = new StringBuilder();
            // store usage instructions in a StringBuilder
            jc.usage(rawUsage);
            // adjust instructions since this will be run from a jar
            String usage = rawUsage.toString().replace(
                    "RESTful User Account Service", "java -jar user-account-service.jar");
            System.out.println(usage);
            System.out.println("(since you specified --usage/-u, all other arguments are ignored)");
            System.out.println("Exiting...");
            System.exit(0);
        }

        // log command line arguments for debugging purposes
        StringBuilder toLog = new StringBuilder();
        toLog.append("Starting server with arguments...\n");
        toLog.append("\tUser Account Service Port = " + jcArgs.port).append("\n");
        toLog.append("\tgRPC Host = " + jcArgs.grpcHost).append("\n");
        toLog.append("\tgRPC Port = " + jcArgs.grpcPort).append("\n");
        toLog.append("\tNumber of Dummy Users = " + jcArgs.numDummyUsers).append("\n");
        toLog.append("\t(specify --usage or -u for usage instructions)").append("\n");
        logger.info(toLog.toString());

        new AccountServiceApp(jcArgs).run("server");
    }
}
