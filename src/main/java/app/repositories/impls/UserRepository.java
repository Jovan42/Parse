package app.repositories.impls;

import app.model.impls.User;
import app.repositories.CrudRepository;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Optional;

public class UserRepository extends CrudRepository {

  public UserRepository(Type type, Type arrayType) {
    super(type, arrayType);
  }

  public Optional<User> findByUsername(String username) throws FileNotFoundException {
    return findAll().stream()
        .map((user) -> (User) user)
        .filter((user) -> (user).getUsername().equals(username))
        .findFirst();
  }
}
