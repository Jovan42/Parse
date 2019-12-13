package app.repositories.impls;

import app.model.impls.User;
import app.repositories.AbstractRepository;

import java.io.FileNotFoundException;
import java.util.Optional;

public class UserRepository extends AbstractRepository {

  public UserRepository() {
    FILE_PATH = FILE_PATH + User.class.getCanonicalName() + ".json";
    type = User.class;
  }

  public Optional<User> findByUsername(String username) throws FileNotFoundException {
    return findAll().stream()
        .map((user) -> (User) user)
        .filter((user) -> (user).getUsername().equals(username))
        .findFirst();
  }
}
