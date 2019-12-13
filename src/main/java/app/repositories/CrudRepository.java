package app.repositories;

import java.lang.reflect.Type;

public class CrudRepository extends AbstractRepository {

  public CrudRepository(Type type, Type arrayType) {
    FILE_PATH = FILE_PATH + type.getTypeName() + ".json";
    this.type = type;
    this.arrayType = arrayType;
  }
}
