package controllers;

import anotations.Controller;
import services.UserService;

@Controller
public class UserController extends BaseController<UserService> {

  public void init() {
    service = new UserService();
    BASE_URL = "/users";
    super.init();
  }
}
