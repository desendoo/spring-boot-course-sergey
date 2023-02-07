package site.desendo.app.ws.userservice;

import site.desendo.app.ws.mobileappws.ui.model.request.UserDetailsRequestModel;
import site.desendo.app.ws.mobileappws.ui.model.response.UserRest;

public interface UserService {
    UserRest createUser(UserDetailsRequestModel userDetails);
}
