package controllers.impls;

import anotations.Controller;
import controllers.BaseController;
import services.impls.UserService;

@Controller
public class UserController extends BaseController<UserService> {

  public void init() {
    service = new UserService();
    BASE_URL = "/users";
    super.init();
  }
}
