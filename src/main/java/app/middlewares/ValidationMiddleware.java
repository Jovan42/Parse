package app.middlewares;

import app.controllers.Initialize;
import spark.Spark;

public class ValidationMiddleware implements Initialize {
    @Override
    public void init() {
        Spark.before(
                "/*",
                (req, res) -> {
                    String user = req.session().attribute("user");
                    if (user == null) {
                        req.session().attribute("user", "user");
                    } else System.out.println(user);
                });
    }
}
