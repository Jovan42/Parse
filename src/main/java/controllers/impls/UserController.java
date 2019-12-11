package controllers.impls;

import anotations.Controller;
import controllers.BaseController;
import services.impls.UserService;
import spark.Spark;

@Controller
public class UserController extends BaseController<UserService> {

  public void init() {
    service = new UserService();
    BASE_URL = "/users";
    super.init();
    Spark.post(
        BASE_URL + "/login",
        (req, res) -> {
          res.type("application/json");
          return "{ \"success\" :" + service.logIn(req.body()) + "}";
        });
  }
}
