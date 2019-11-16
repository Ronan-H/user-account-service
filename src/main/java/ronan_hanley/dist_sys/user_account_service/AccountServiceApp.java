package ronan_hanley.dist_sys.user_account_service;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountServiceApp extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceApp.class);

    @Override
    public void run(Configuration config, Environment env) {
        env.healthChecks().register("GRPCHealthCheck", new GRPCHealthCheck("localhost", 50051));
        env.jersey().register(new UserRESTController(MappedUserManager.getInstance()));
        env.jersey().register(new LoginRESTController(MappedUserManager.getInstance()));
    }


    public static void main(String[] args) throws Exception {
        //new AccountServiceApp().run(args);
        new AccountServiceApp().run("server");
    }
}
