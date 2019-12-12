package app.controllers;

import app.exceptions.BadRequestException;
import app.exceptions.NotFoundException;
import app.repositories.Clause;
import app.services.BaseService;
import spark.Request;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"})
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
          Set<String> params = req.queryParams();
          if (params == null || params.size() == 0) {
            return service.findAll();
          } else {
            return service.findAllWhere(queryParamsToList(req));
          }
        });
  }

  private List<Clause> queryParamsToList(Request req) {
    List list = new ArrayList();
    req.queryParams()
        .forEach(
            (paramKey) -> {
              list.add(new Clause(paramKey, req.queryMap(paramKey).value()));
            });
    return list;
  }

  public void getById() {
    Spark.get(
        BASE_URL + "/:id",
        (req, res) -> {
          res.type("application/json");
          try {
            return service.findById(req.params(":id"));
          } catch (Throwable throwable) {
            catchExceptions(throwable);
          }
          return "";
        });
  }

  public void post() {
    Spark.post(
        BASE_URL,
        (req, res) -> {
          res.type("application/json");
          try {
            return service.findById(req.params(":id"));
          } catch (Throwable throwable) {
            catchExceptions(throwable);
          }
          return "";
        });
  }

  public void put() {
    Spark.put(
        BASE_URL,
        (req, res) -> {
          res.type("application/json");
          try {
            return service.findById(req.params(":id"));
          } catch (Throwable throwable) {
            catchExceptions(throwable);
          }
          return "";
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

  private void catchExceptions(Throwable throwable) {
    if (throwable instanceof BadRequestException) {
      throw (BadRequestException) throwable;
    }
    if (throwable instanceof NotFoundException) {
      throw (NotFoundException) throwable;
    }
    throwable.printStackTrace();
  }
}
