package controllers;

import services.BaseService;
import spark.Spark;

public abstract class BaseController<S extends BaseService> implements Initialize {
  protected String BASE_URL;
  protected S service;

  @Override
  public void init() {
    get();
    getById();
    post();
    put();
    delete();
  }

  public void get() {
    Spark.get(
        BASE_URL,
        (req, res) -> {
          res.type("application/json");
          return service.findAll();
        });
  }

  public void getById() {
    Spark.get(
        BASE_URL + "/:id",
        (req, res) -> {
          res.type("application/json");
          return service.findById(req.params(":id"));
        });
  }

  public void post() {
    Spark.post(
        BASE_URL,
        (req, res) -> {
          res.type("application/json");
          return service.create(req.body());
        });
  }

  public void put() {
    Spark.put(
        BASE_URL,
        (req, res) -> {
          res.type("application/json");
          return service.update(req.body());
        });
  }

  public void delete() {
    Spark.delete(
        BASE_URL + "/:id",
        (req, res) -> {
          res.type("application/json");
          service.delete(req.params(":id"));
          return "";
        });
  }
}
