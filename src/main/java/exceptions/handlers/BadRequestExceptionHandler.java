package exceptions.handlers;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class BadRequestExceptionHandler implements ExceptionHandler {
  @Override
  public void handle(Exception e, Request request, Response response) {}
}
