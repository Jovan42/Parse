package app.services.impls;

import app.exceptions.NotFoundException;
import app.model.BaseEntity;
import app.model.dto.LoginDto;
import app.model.impls.User;
import app.repositories.impls.UserRepository;
import app.services.CrudService;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class UserService extends CrudService {

  public UserService(Type type, Type arrayType, String notFoundMessage) {
    super(type, arrayType, notFoundMessage);
  }

  @Override
  public String create(String sUser) throws Throwable {
    BaseEntity entity = gson.fromJson(sUser, type);
    User user = (User) entity;
    validate(user);
    String hash =
        BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, user.getPassword().toCharArray());
    user.setPassword(hash);
    return gson.toJson(
        repository.create(user).orElseThrow(() -> new NotFoundException(notFoundMessage)));
  }

  @Override
  public String update(String sUser) throws Throwable {
    BaseEntity entity = gson.fromJson(sUser, type);
    User user = (User) entity;
    validate(user);
    String hash =
        BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, user.getPassword().toCharArray());
    user.setPassword(hash);
    return gson.toJson(
        repository.update(user).orElseThrow(() -> new NotFoundException(notFoundMessage)));
  }

  public boolean logIn(String loginDtoString) throws FileNotFoundException {
    UserRepository userRepository = (UserRepository) repository;

    LoginDto loginDto = gson.fromJson(loginDtoString, LoginDto.class);
    User user =
        userRepository
            .findByUsername(loginDto.getUsername())
            .orElseThrow(() -> new NotFoundException(notFoundMessage));
    return BCrypt.verifyer()
        .verify(loginDto.getPassword().getBytes(), user.getPassword().getBytes())
        .verified;
  }
}
