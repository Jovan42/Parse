package app;

import app.anotations.Controller;
import app.anotations.Entity;
import app.anotations.NotEmpty;
import app.anotations.NotNull;
import app.anotations.Valid;
import app.controllers.Initialize;
import app.exceptions.BadRequestException;
import app.middlewares.AuthMiddleware;
import app.model.impls.User;
import org.reflections.Reflections;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    createJsonFiles();
    initializeControllers();
    AuthMiddleware authMiddleware = new AuthMiddleware();
    authMiddleware.init();

    User s = new User("", "last", "pass", "user");
      try {
          method(s);
      } catch (NoSuchMethodException e) {
          e.printStackTrace();
      }
  }

  public static void method(@Valid User u) throws NoSuchMethodException {
      List<String> errors = new ArrayList<>();
      Method method =Main.class.getMethod("method", User.class);
      Annotation[][] annotations = method.getParameterAnnotations();
      final Integer[] i = {0};
      Arrays.asList(annotations).forEach((param) -> {
          Arrays.asList(param).forEach((annotation -> {
              if(annotation.annotationType().equals(Valid.class)) {
                  Parameter parameters = method.getParameters()[i[0]];
                  Arrays.asList(parameters.getType().getDeclaredFields()).forEach((field -> {
                      Arrays.asList(field.getDeclaredAnnotations()).forEach((fieldAnnotation->{
                          if(fieldAnnotation.annotationType().equals(NotNull.class)) {
                              try {
                                  String s = (String) u.getClass().getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(u);
                                  if (s == null) errors.add(field.getName() + " can not be null");
                              } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                  e.printStackTrace();
                              }
                          }
                          if(fieldAnnotation.annotationType().equals(NotEmpty.class)) {
                              try {
                                  String s = (String) u.getClass().getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(u);
                                  if (s == null || s.equalsIgnoreCase("")) errors.add(field.getName() + " can not be empty");
                              } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                  e.printStackTrace();
                              }
                          }
                      }));
                  }));
              }
          }));
          i[0]++;
      });
      errors.forEach(System.out::println);
      if(!errors.isEmpty()) {
        throw new BadRequestException(errors);
      }

  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private static void createJsonFiles() {
    File data = new File("./data");
    data.mkdir();
    Reflections reflections = new Reflections("app/model");
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);

    annotated.forEach(
        (item) -> {
          String filePath = "./data/" + item.getCanonicalName() + ".json";
          File fileToCreate = new File(filePath);
          if (!fileToCreate.exists()) {
            try {
              fileToCreate.createNewFile();
              Files.write(Paths.get(filePath), "[]".getBytes(), StandardOpenOption.APPEND);

            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }

  private static void initializeControllers() {
    Reflections reflections = new Reflections("app/controllers");
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
    annotated.forEach(
        (item) -> {
          try {
            Object o = item.getConstructors()[0].newInstance();
            ((Initialize) o).init();
          } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
          }
        });
  }
}
