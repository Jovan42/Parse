package app.repositories;

import app.model.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractRepository implements BaseRepository<BaseEntity, String> {
  protected String FILE_PATH = "./data/";
  protected Gson gson = new Gson();
  protected Type type;
  protected Type arrayType;

  @Override
  public Optional<BaseEntity> findById(String id) throws FileNotFoundException {
    return findAll().stream().filter((user) -> user.getId().equals(id)).findFirst();
  }

  @Override
  public List<BaseEntity> findAll() throws FileNotFoundException {
    JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
    BaseEntity[] array = gson.fromJson(reader, arrayType);
    return Arrays.asList(array);
  }

  @Override
  public List<BaseEntity> findAllWhere(List<Clause> clauses) throws FileNotFoundException {
    JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
    List<HashMap<String, Object>> entities =
        gson.fromJson(reader, new TypeToken<List<HashMap<String, Object>>>() {}.getType());

    return entities.stream()
        .filter(
            (entity) -> {
              boolean toReturn = true;
              for (Clause clause : clauses) {
                if (entity.containsKey(clause.getField())
                    && !entity.get(clause.getField()).equals(clause.getValue())) {
                  toReturn = false;
                }
              }
              return toReturn;
            })
        .map(
            (element) -> {
              String elementString = gson.toJson(element);
              return (BaseEntity) gson.fromJson(elementString, type);
            })
        .collect(Collectors.toList());
  }

  @Override
  public Optional<BaseEntity> create(BaseEntity entity) throws IOException {
    List<BaseEntity> list = findAll();
    entity.setId(UUID.randomUUID().toString());
    list.add(entity);
    writeInFile(list);
    return Optional.of(entity);
  }

  @Override
  public Optional<BaseEntity> update(BaseEntity entity) throws IOException {
    List<BaseEntity> userList = findAll();
    userList.forEach(
        oldUser -> {
          if (oldUser.getId().equals(entity.getId())) {
            oldUser.edit(entity);
          }
        });

    writeInFile(userList);
    return Optional.of(entity);
  }

  @Override
  public void delete(String id) throws IOException {

    JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
    BaseEntity[] array = gson.fromJson(reader, arrayType);

    writeInFile(
        Arrays.stream(array)
            .filter((element) -> !element.getId().equals(id))
            .collect(Collectors.toList()));
  }

  protected void writeInFile(List<BaseEntity> list) throws IOException {
    Files.write(Paths.get(FILE_PATH), gson.toJson(list).getBytes());
  }
}
