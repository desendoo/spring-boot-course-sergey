package site.desendo.app.ws.mobileappws.ui.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import site.desendo.app.ws.mobileappws.ui.model.request.UpdateUserDetailsRequestModel;
import site.desendo.app.ws.mobileappws.ui.model.request.UserDetailsRequestModel;
import site.desendo.app.ws.mobileappws.ui.model.response.UserRest;
import site.desendo.app.ws.userservice.UserService;

@RestController
@ComponentScan("site.desendo.app.ws.*")
// @EntityScan("site.desendo.app.ws.*")
@RequestMapping("users")
public class UserController {

    Map<String, UserRest> users;

    @Autowired(required = true)
    UserService userService;
    
    @GetMapping
    public String getUsers(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "50") int limit,
        @RequestParam(value = "sort", required = false) String sort
        ) {
        return "get users within page " + page + " and limit " + limit + " and sort " + sort;
    }
    
    @GetMapping(
        path="/",
        // API can support both XML and JSON as return media type
        produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
        }
    )
    public ResponseEntity<UserRest> getAllUser() {
        if(!users.isEmpty()) {
            // for (int loop = users.size(); loop != 0; loop--) {
            //     users.values();
            // }
            return new ResponseEntity(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(
        path="/{userId}",
        // API can support both XML and JSON as return media type
        produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
        }
    )
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        if(users.containsKey(userId)) {
            return new ResponseEntity<UserRest>(users.get(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(
        // API can support both XML and JSON as input media type
        consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
        },
        // API can support both XML and JSON as return media type
        produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
        }
    )
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        // UserRest returnValue = new UserRest();

        // returnValue.setEmail(userDetails.getEmail());
        // returnValue.setFirstName(userDetails.getFirstName());
        // returnValue.setLastName(userDetails.getLastName());

        // // Create a random number as UUID
        // String userId = UUID.randomUUID().toString();
        // returnValue.setUserId(userId);

        // // Insert UserRest data to temporary storage based on Map data type
        // if(users == null) users = new HashMap<>();
        // users.put(userId, returnValue);
        
        UserRest returnValue = userService.createUser(userDetails);
        // Insert UserRest data to temporary storage based on Map data type
        if(users == null) users = new HashMap<>();
        users.put(returnValue.getUserId(), returnValue);

        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PutMapping(
        path="/{userId}",
        // API can support both XML and JSON as input media type
        consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
        },
        // API can support both XML and JSON as return media type
        produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
        }
    )
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
        UserRest storedUserDetails = users.get(userId);
        storedUserDetails.setFirstName(userDetails.getFirstName());
        storedUserDetails.setLastName(userDetails.getLastName());

        users.put(userId, storedUserDetails);

        return storedUserDetails;
    }

    @DeleteMapping(
        path="/{id}"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        users.remove(id);

        return ResponseEntity.noContent().build();
    }

}
