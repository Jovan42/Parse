package app.repositories.impls;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import app.model.BaseEntity;
import app.model.impls.User;
import app.repositories.AbstractRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository extends AbstractRepository {

  public UserRepository() {
    FILE_PATH = FILE_PATH + User.class.getCanonicalName() + ".json";
  }

  @Override
  public List<BaseEntity> findAll() throws FileNotFoundException {
    JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
    Object o = gson.fromJson(reader, new TypeToken<List<HashMap>>() {
    }.getType());

    return new ArrayList<>();
  }

  public Optional<User> findByUsername(String username) throws FileNotFoundException {
    return findAll().stream()
        .map((user) -> (User) user)
        .filter((user) -> (user).getUsername().equals(username))
        .findFirst();
  }

  @Override
  public void delete(String id) throws IOException {
    writeInFile(
        findAll().stream()
            .map((user) -> (User) user)
            .filter((oldUser) -> !oldUser.getId().equals(id))
            .collect(Collectors.toList()));
  }
}
