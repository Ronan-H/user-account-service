package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/users")
public class UserRESTController {
    private UserDB userDB;

    public UserRESTController(UserDB userDB) {
        this.userDB = userDB;
    }

    @POST
    @Produces({ "application/json", "application/xml" })
    public Response createUser(NewUser newUser) {
        // TODO checks to make sure newUser is valid
        if (newUser != null) {
            userDB.createUser(newUser);
            return Response.ok().build();
        }
        else {
            // 405 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({ "application/json", "application/xml" })
    public Response getUser(@PathParam("id") Integer id) {
        User user = userDB.getUser(id);
        if (user != null) {
            return Response.ok(user).build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces({ "application/json", "application/xml" })
    public Response getUsers() {
        return Response.ok(userDB.getAllUsers()).build();
    }

    @PUT
    @Produces({ "application/json", "application/xml" })
    public Response updateUser(NewUser updatedUser) {
        // TODO checks to make sure newUser is valid
        if (updatedUser != null) {
            userDB.updateUser(updatedUser);
            return Response.ok().build();
        }
        else {
            // 405 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces({ "application/json", "application/xml" })
    public Response deleteUser(@PathParam("id") Integer id) {
        boolean success = userDB.deleteUser(id);
        if (success) {
            return Response.ok().build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
