package controllers;

import anotations.Controller;
import exceptions.NotFoundException;
import exceptions.handlers.NotFoundExceptionHandler;
import spark.Spark;

@Controller
public class ErrorHandler implements Initialize {
  @Override
  public void init() {
    Spark.exception(NotFoundException.class, new NotFoundExceptionHandler());
  }
}
