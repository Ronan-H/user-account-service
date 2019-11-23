package ronan_hanley.dist_sys.user_account_service.service;

import io.grpc.StatusRuntimeException;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/users")
@Produces({ "application/json", "application/xml" })
@Consumes({ "application/json", "application/xml" })
public class UserRESTController {
    private UserManager userManager;

    public UserRESTController(UserManager userManager) {
        this.userManager = userManager;
    }

    @POST
    public Response createUser(NewUser newUser) throws URISyntaxException {
        if (newUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Bad request, new user can't be null").build();
        }

        if (userManager.userExists(newUser.getUserDetails().getUserId())) {
            // 409 conflict
            return Response.status(Response.Status.CONFLICT)
                           .entity("Conflict, user with that id already exists").build();
        }

        if (userManager.isOperationInProgress(newUser.getUserDetails().getUserId())) {
            // 409 conflict
            return Response.status(Response.Status.CONFLICT)
                    .entity("Conflict, someone is already trying to create that user right now. Try again later.").build();
        }

        // user isn't null and doesn't already exist, create user asynchronously
        try {
            userManager.createUserAsync(newUser);
        } catch (StatusRuntimeException e) {
            // gRPC failed
            // 503 service unavailable
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                           .entity("Failed to create user, password hashing service was not available. Try again later.").build();
        }

        return Response.accepted(new URI("/users/status/" + newUser.getUserDetails().getUserId())).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        if (!userManager.userExists(id)) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("User not found with that id").build();
        }

        // user exists, respond with user details
        return Response.ok(userManager.getUser(id)).build();
    }

    @PUT
    public Response updateUser(NewUser updatedUser) throws URISyntaxException {
        if (updatedUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad request, updated user can't be null").build();
        }

        if (!userManager.userExists(updatedUser.getUserDetails().getUserId())) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with that id").build();
        }

        if (userManager.isOperationInProgress(updatedUser.getUserDetails().getUserId())) {
            // 409 conflict
            return Response.status(Response.Status.CONFLICT)
                    .entity("Conflict, someone is already trying to update that user right now. Try again later.").build();
        }

        // user exists, update user
        try {
            userManager.updateUserAsync(updatedUser);
        } catch (StatusRuntimeException e) {
            // gRPC failed
            // 503 service unavailable
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Failed to update user, password hashing service was not available. Try again later.").build();
        }

        return Response.accepted(new URI("/users/status/" + updatedUser.getUserDetails().getUserId())).build();
    }

    @GET
    @Path("/status/{id}")
    public Response getUserStatus(@PathParam("id") Integer id) {
        if (!userManager.isOperationResultAvailable(id)) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No create/update operation found for a user with that id").build();
        }

        // user exists, respond with create/update operation result
        return userManager.getOperationResult(id).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        if (!userManager.userExists(id)) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with that id").build();
        }

        // user exists, delete user
        userManager.deleteUser(id);
        return Response.ok().entity("User deleted successfully").build();
    }

    @GET
    public Response getUsers() {
        // nothing to validate here, just respond with a list of all users
        List<User> users = userManager.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }
}
