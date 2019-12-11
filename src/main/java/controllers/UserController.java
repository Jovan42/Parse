package controllers;

import services.UserService;

import static spark.Spark.*;

public class UserController {

  private static UserService userService = new UserService();

  public static void init() {
    get(
        "/users",
        (req, res) -> {
          res.type("application/json");
          return userService.findAll();
        });
    get(
        "/users/:id",
        (req, res) -> {
          res.type("application/json");
          return userService.findById(req.params(":id"));
        });

    post(
        "/users",
        (req, res) -> {
          res.type("application/json");

          return userService.create(req.body());
        });
    put(
            "/users",
            (req, res) -> {
              res.type("application/json");

              return userService.update(req.body());
            });
  }
}
