package services;

import exceptions.BadRequestException;
import model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BaseService<T, I> {

  String findById(I id) throws FileNotFoundException;

  String findAll() throws FileNotFoundException;

  String create(String t) throws IOException;

  String update(String t) throws IOException;

  void validate(User user) throws BadRequestException;
}
