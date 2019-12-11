package services;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import model.User;
import repositories.UserRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserService implements BaseService<User, String> {

  UserRepository userRepository = new UserRepository();

  @Override
  public User findById(String id) throws FileNotFoundException {
    return userRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public List<User> findAll() throws FileNotFoundException {
    return userRepository.findAll();
  }

  @Override
  public User create(User user) throws IOException {
    validate(user);
    return userRepository.create(user).orElseThrow(NotFoundException::new);
  }

  @Override
  public User update(User user) throws IOException {
    validate(user);
    return userRepository.update(user).orElseThrow(NotFoundException::new);
  }

  @Override
  public void validate(User user) {
    Map<String, List<String>> validate = user.validate();
    if (validate.size() > 0) {
      throw new BadRequestException();
    }
  }
}
