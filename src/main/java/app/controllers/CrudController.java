package app.controllers;

import app.anotations.Controller;
import app.anotations.NotFound;
import app.services.CrudService;

@SuppressWarnings("rawtypes")
public class CrudController extends BaseController {
  @Override
  public void init() {
    String notFoundMessage = "";
    Class type = getClass().getAnnotation(Controller.class).type();
    Class arrayType = getClass().getAnnotation(Controller.class).arrayType();
    NotFound notFound = getClass().getAnnotation(NotFound.class);
    if (notFound != null) {
      notFoundMessage = notFound.message();
    }
    if (type != Object.class && arrayType != Object.class) {
      service = new CrudService(type, arrayType, notFoundMessage);
    }
    super.init();
  }
}
