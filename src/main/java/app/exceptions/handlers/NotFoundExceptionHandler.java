package app.exceptions.handlers;

import com.google.gson.Gson;
import app.exceptions.NotFoundException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import java.util.Date;

public class NotFoundExceptionHandler implements ExceptionHandler {

  private static Gson gson = new Gson();

  @Override
  public void handle(Exception e, Request req, Response res) {
    res.status(404);
    res.type("application/json");
    NotFoundException notFoundException = (NotFoundException) e;
    String description = notFoundException.getMessage();
    res.body(
        gson.toJson(
            new ErrorMessage(404, "NOT FOUND", req.url(), new Date().getTime(), description)));
  }
}
