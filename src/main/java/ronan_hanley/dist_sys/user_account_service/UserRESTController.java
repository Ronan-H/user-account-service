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
        if (newUser != null && !userDB.userExists(newUser.getUserDetails().getUserId())) {
            userDB.createUser(newUser);
            return Response.ok().build();
        }
        else {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({ "application/json", "application/xml" })
    public Response getUser(@PathParam("id") Integer id) {
        if (userDB.userExists(id)) {
            return Response.ok(userDB.getUser(id)).build();
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
        if (updatedUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (userDB.userExists(updatedUser.getUserDetails().getUserId())) {
            userDB.updateUser(updatedUser);
            return Response.ok().build();
        }
        else {
            // 404 user not fouind
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces({ "application/json", "application/xml" })
    public Response deleteUser(@PathParam("id") Integer id) {
        if (userDB.userExists(id)) {
            userDB.deleteUser(id);
            return Response.ok().build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
