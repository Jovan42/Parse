package app.services;

import app.exceptions.BadRequestException;
import app.model.BaseEntity;
import app.repositories.Clause;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BaseService<T, I> {

  T findById(I id) throws Throwable;

  T findAll() throws FileNotFoundException;

  T findAllWhere(List<Clause> clauses) throws FileNotFoundException;

  T create(T t) throws Throwable;

  T update(T t) throws Throwable;

  void delete(I id) throws IOException;

  void validate(BaseEntity t) throws BadRequestException;
}
