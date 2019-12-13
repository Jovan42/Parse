package app.controllers;

import app.services.CrudService;

@SuppressWarnings("rawtypes")
public class CrudController extends BaseController {

  @Override
  public void init() {
    super.init();
    if (type != Object.class && arrayType != Object.class) {
      service = new CrudService(type, arrayType, notFoundMessage);
    }
  }
}
