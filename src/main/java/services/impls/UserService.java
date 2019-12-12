package services.impls;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import exceptions.NotFoundException;
import model.BaseEntity;
import model.dto.LoginDto;
import model.impls.User;
import repositories.impls.UserRepository;
import services.AbstractService;

import java.io.FileNotFoundException;

public class UserService extends AbstractService {

  public UserService() {
    super();
    repository = new UserRepository();
    type = User.class;
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
        repository.create(user).orElseThrow(() -> new NotFoundException("User", user.getId())));
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
        repository.update(user).orElseThrow(() -> new NotFoundException("User", user.getId())));
  }

  public String findByUsername(String username) throws FileNotFoundException {
    UserRepository userRepository = (UserRepository) repository;
    return gson.toJson(
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User", username)));
  }

  public boolean logIn(String loginDtoString) throws FileNotFoundException {
    UserRepository userRepository = (UserRepository) repository;

    LoginDto loginDto = gson.fromJson(loginDtoString, LoginDto.class);
    User user =
        userRepository
            .findByUsername(loginDto.getUsername())
            .orElseThrow(() -> new NotFoundException("User", loginDto.getUsername()));
    return BCrypt.verifyer()
        .verify(loginDto.getPassword().getBytes(), user.getPassword().getBytes())
        .verified;
  }
}
