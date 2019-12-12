package app.controllers;

import app.anotations.Controller;
import app.exceptions.BadRequestException;
import app.exceptions.NotFoundException;
import app.exceptions.handlers.BadRequestExceptionHandler;
import app.exceptions.handlers.NotFoundExceptionHandler;
import spark.Spark;

@Controller
@SuppressWarnings("unused")
public class ErrorHandler implements Initialize {
  @Override
  public void init() {
    Spark.exception(NotFoundException.class, new NotFoundExceptionHandler());
    Spark.exception(BadRequestException.class, new BadRequestExceptionHandler());
  }
}
