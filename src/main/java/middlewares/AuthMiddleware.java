package middlewares;

import controllers.Initialize;
import spark.Spark;

public class AuthMiddleware implements Initialize {

  @Override
  public void init() {
    Spark.before(
        "/users/*",
        (req, res) -> {
          String user = req.session().attribute("user");
          if (user == null) {
            req.session().attribute("user", "user");
          } else System.out.println(user);
        });
  }
}
