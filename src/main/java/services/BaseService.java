package services;

import exceptions.BadRequestException;
import model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BaseService<T, I> {

  T findById(I id) throws FileNotFoundException;

  T findAll() throws FileNotFoundException;

  T create(T t) throws IOException;

  T update(T t) throws IOException;

  void delete(I id) throws IOException;

  void validate(User user) throws BadRequestException;
}
