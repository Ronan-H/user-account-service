package ronan_hanley.dist_sys.user_account_service;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import ronan_hanley.dist_sys.user_account_service.service.GRPCHealthCheck;
import ronan_hanley.dist_sys.user_account_service.service.LoginRESTController;
import ronan_hanley.dist_sys.user_account_service.service.MappedUserManager;
import ronan_hanley.dist_sys.user_account_service.service.UserRESTController;

public class AccountServiceApp extends Application<Configuration> {

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
