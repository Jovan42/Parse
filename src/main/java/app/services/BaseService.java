package app.services;

import app.exceptions.BadRequestException;
import app.model.BaseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BaseService<T, I> {

  T findById(I id) throws Throwable;

  T findAll() throws FileNotFoundException;

  T create(T t) throws Throwable;

  T update(T t) throws Throwable;

  void delete(I id) throws IOException;

  void validate(BaseEntity t) throws BadRequestException;
}
