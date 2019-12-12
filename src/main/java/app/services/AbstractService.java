package app.services;

import com.google.gson.Gson;
import app.exceptions.BadRequestException;
import app.exceptions.NotFoundException;
import app.model.BaseEntity;
import app.repositories.BaseRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AbstractService implements BaseService<String, String> {

  protected BaseRepository repository;
  protected Gson gson = new Gson();
  protected Type type;
  protected String notFoundMessage = "Entity that you looking for does not exist.";

  @Override
  public String findById(String id) throws Throwable {
    return gson.toJson(
        repository.findById(id).orElseThrow(() -> new NotFoundException(notFoundMessage)));
  }

  @Override
  public String findAll() throws FileNotFoundException {
    return gson.toJson(repository.findAll());
  }

  @Override
  public String create(String sUser) throws Throwable {
    BaseEntity entity = gson.fromJson(sUser, type);
    validate(entity);
    return gson.toJson(
        repository.create(entity).orElseThrow(() -> new NotFoundException(notFoundMessage)));
  }

  @Override
  public String update(String sUser) throws Throwable {
    BaseEntity entity = gson.fromJson(sUser, type);
    validate(entity);
    return gson.toJson(
        repository.update(entity).orElseThrow(() -> new NotFoundException(notFoundMessage)));
  }

  @Override
  public void delete(String id) throws IOException {
    repository.delete(id);
  }

  @Override
  public void validate(BaseEntity entity) {
    Map<String, List<String>> validate = entity.validate();
    if (validate.size() > 0) {
      throw new BadRequestException();
    }
  }
}
