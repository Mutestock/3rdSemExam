package rest;

import dto.DayPlanDTO;
import dto.ItemDTO;
import dto.PersonDTO;
import entities.Item;
import entities.User;
import facades.MultiFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

@OpenAPIDefinition(
        info = @Info(
                title = "teamone-ca3",
                version = "0.1",
                description = "Backend of the CA3 project"
        ),
        tags = {
            @Tag(name = "Star Wars resource", description = "API related to the Star Wars fetch from distant API's")
        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/3rdSemExam"
            ),
            @Server(
                    description = "Server API",
                    url = "https://www.mutezone.site/3rdSemExam"
            )

        }
)
@Path("item")
public class ItemResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MultiFacade<Item> DAYPLAN_FACADE = new MultiFacade(Item.class, EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@RolesAllowed({"user", "admin"})
    @Operation(summary = "Basic API Response: Item",
            tags = {"Item Resource"},
            responses = {
                @ApiResponse(responseCode = "200", description = "The user successfully connected"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request")})
    public String connectionCheck() {
        return "{\"msg\":\"Connection to the 'Item' api section is online\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    // @RolesAllowed({"user", "admin"})
    @Operation(summary = "Fetches data from distant Star Wars API's by id",
            tags = {"Item Resource"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested resources was returned"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public ItemDTO getItem(@PathParam("id") int id) throws IOException, InterruptedException, ExecutionException {
        return new ItemDTO(((Item) DAYPLAN_FACADE.find(id)));
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches all Item components from hardware data",
            tags = {"Item Resource"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Item resources were returned"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public List<ItemDTO> getAllItems() {
        return (List<ItemDTO>) DAYPLAN_FACADE.findAll()
                .stream()
                .map(item -> new ItemDTO((Item) item))
                .collect(Collectors.toList());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    @Operation(summary = "Creates a Item and persists it to the database",
            tags = {"Item Resource"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "200", description = "The person was created and persisted"),
                @ApiResponse(responseCode = "400", description = "No users was created or persisted")})
    public void createItem(Item entity) {
        DAYPLAN_FACADE.create(entity);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    // @RolesAllowed("admin")
    @Operation(summary = "Item Deletion", tags = {"Item Resource"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Item Deleted"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided to delete")
            })
    public void deleteRest(@PathParam("id") Long id) {
        DAYPLAN_FACADE.remove(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    @Operation(summary = "Item Editing", tags = {"Item Resource"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Item Edited"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body to edit")
            })
    public void editRest(Item entity) {
        DAYPLAN_FACADE.edit(entity);
    }
}
