package site.desendo.app.ws.userservice.implementation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.desendo.app.ws.mobileappws.ui.model.request.UserDetailsRequestModel;
import site.desendo.app.ws.mobileappws.ui.model.response.UserRest;
import site.desendo.app.ws.shared.Utils;
import site.desendo.app.ws.userservice.UserService;

@Service
public class UserServiceImplementation implements UserService {
    Map<String, UserRest> users;
    Utils utils;

    public UserServiceImplementation() {}

    @Autowired
    public UserServiceImplementation(Utils utils) {
        this.utils = utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
    
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
    
        // Create a random number as UUID with Constructor Based Dependency Injection
        String userId = utils.generateUserId();
        returnValue.setUserId(userId);
    
        // Insert UserRest data to temporary storage based on Map data type
        if(users == null) users = new HashMap<>();
        users.put(userId, returnValue);
    
        return returnValue;
    }
}
