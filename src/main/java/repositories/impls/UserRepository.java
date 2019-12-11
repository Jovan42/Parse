package repositories.impls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.impls.User;
import repositories.BaseRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserRepository implements BaseRepository<User, String> {
  private static final String FILE_PATH = "./data/" + User.class.getCanonicalName() + ".json";
  private static Gson gson = new Gson();

  @Override
  public Optional<User> findById(String id) throws FileNotFoundException {
    return findAll().stream().filter((user) -> user.getId().equals(id)).findFirst();
  }

  @Override
  public List<User> findAll() throws FileNotFoundException {
    JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
    return gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
  }

  @Override
  public Optional<User> create(User user) throws IOException {
    List<User> list = findAll();
    user.setId(UUID.randomUUID().toString());
    list.add(user);
    writeInFile(list);
    return Optional.of(user);
  }

  @Override
  public Optional<User> update(User user) throws IOException {
    List<User> userList = findAll();
    userList.forEach(
        oldUser -> {
          if (oldUser.getId().equals(user.getId())) {
            oldUser.edit(user);
          }
        });

    writeInFile(userList);
    return Optional.of(user);
  }

  @Override
  public void delete(String id) throws IOException {
    writeInFile(
        findAll().stream()
            .filter((oldUser) -> !oldUser.getId().equals(id))
            .collect(Collectors.toList()));
  }

  protected static void writeInFile(List<User> list) throws IOException {
    Files.write(Paths.get(FILE_PATH), gson.toJson(list).getBytes(), StandardOpenOption.WRITE);
  }
}
