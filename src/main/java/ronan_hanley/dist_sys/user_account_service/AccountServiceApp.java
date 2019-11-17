package ronan_hanley.dist_sys.user_account_service;

import com.beust.jcommander.JCommander;
import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
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
        env.healthChecks().register("GRPCHealthCheck", new GRPCHealthCheck(jcArgs.host, jcArgs.port));

        HealthCheck.Result result = env.healthChecks().runHealthCheck("GRPCHealthCheck");
        if (result.isHealthy()) {
            logger.info("GRPCHealthCheck: OK");
        }
        else {
            logger.severe(String.format("GRPCHealthCheck: FAIL with message \"%s\"", result.getMessage()));
            logger.severe("Please review your gRPC password service setup and try again. Exiting...");
            System.exit(1);
        }


        PasswordServiceClient passwordServiceClient = new PasswordServiceClient(jcArgs.host, jcArgs.port);
        UserManager userManager = new MappedUserManager(passwordServiceClient);
        env.jersey().register(new UserRESTController(userManager));
        env.jersey().register(new LoginRESTController(userManager));
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
        toLog.append("\tHost = " + jcArgs.host).append("\n");
        toLog.append("\tPort = " + jcArgs.port).append("\n");
        toLog.append("\t(specify --usage or -u for usage instructions)").append("\n");
        logger.info(toLog.toString());

        new AccountServiceApp(jcArgs).run("server");
    }
}
