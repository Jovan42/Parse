package app.controllers.impls;

import app.anotations.Controller;
import app.controllers.BaseController;
import app.services.impls.UserService;
import spark.Spark;

@Controller(baseUrl = "/users")
@SuppressWarnings("unused")
public class UserController extends BaseController<UserService> {

  public void init() {
    service = new UserService();
    super.init();
    Spark.post(
        BASE_URL + "/login",
        (req, res) -> {
          res.type("application/json");
          return "{ \"success\" :" + service.logIn(req.body()) + "}";
        });
  }
}
