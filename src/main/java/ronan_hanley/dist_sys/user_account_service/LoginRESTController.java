package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/login")
public class LoginRESTController {
    @POST
    @Produces({ "application/json", "application/xml" })
    public Response login(NewUser loginUser) {
        // TODO checks to make sure newUser is valid
        if (loginUser != null) {
            // TODO authenticate user
            return Response.ok().build();
        }
        else {
            // 405 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
