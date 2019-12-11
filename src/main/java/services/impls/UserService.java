package services.impls;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import model.dto.LoginDto;
import model.impls.User;
import repositories.impls.UserRepository;
import services.BaseService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserService implements BaseService<String, String> {

  private UserRepository userRepository = new UserRepository();
  private Gson gson = new Gson();

  @Override
  public String findById(String id) throws FileNotFoundException {
    return gson.toJson(
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", id)));
  }

  @Override
  public String findAll() throws FileNotFoundException {
    return gson.toJson(userRepository.findAll());
  }

  @Override
  public String create(String sUser) throws IOException {
    User user = gson.fromJson(sUser, User.class);
    validate(user);
    String hash =
        BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, user.getPassword().toCharArray());
    user.setPassword(hash);
    return gson.toJson(
        userRepository.create(user).orElseThrow(() -> new NotFoundException("User", user.getId())));
  }

  @Override
  public String update(String sUser) throws IOException {
    User user = gson.fromJson(sUser, User.class);
    validate(user);
    String hash =
        BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, user.getPassword().toCharArray());
    user.setPassword(hash);
    return gson.toJson(
        userRepository.update(user).orElseThrow(() -> new NotFoundException("User", user.getId())));
  }

  public String findByUsername(String username) throws FileNotFoundException {
    return gson.toJson(
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User", username)));
  }

  public boolean logIn(String loginDtoString) throws FileNotFoundException {
    LoginDto loginDto = gson.fromJson(loginDtoString, LoginDto.class);
    User user =
        userRepository
            .findByUsername(loginDto.getUsername())
            .orElseThrow(() -> new NotFoundException("User", loginDto.getUsername()));
    return BCrypt.verifyer().verify(loginDto.getPassword().getBytes(), user.getPassword().getBytes()).verified;
  }

  @Override
  public void delete(String id) throws IOException {
    userRepository.delete(id);
  }

  @Override
  public void validate(User user) {
    Map<String, List<String>> validate = user.validate();
    if (validate.size() > 0) {
      throw new BadRequestException();
    }
  }
}
