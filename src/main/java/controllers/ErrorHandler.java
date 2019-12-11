package controllers;

import anotations.Controller;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import exceptions.handlers.BadRequestExceptionHandler;
import exceptions.handlers.NotFoundExceptionHandler;
import spark.Spark;

@Controller
public class ErrorHandler implements Initialize {
  @Override
  public void init() {
    Spark.exception(NotFoundException.class, new NotFoundExceptionHandler());
    Spark.exception(BadRequestException.class, new BadRequestExceptionHandler());
  }
}
