package app.services;

import app.repositories.CrudRepository;

import java.lang.reflect.Type;

public class CrudService extends AbstractService {

  public CrudService(Type type, Type arrayType, String notFoundMessage) {
    super();
    this.repository = new CrudRepository(type, arrayType);
    this.type = type;
    this.notFoundMessage = notFoundMessage;
  }
}
