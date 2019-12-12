package app;

import app.anotations.Controller;
import app.anotations.Entity;
import app.controllers.Initialize;
import app.middlewares.AuthMiddleware;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public class Main {

  public static void main(String[] args) throws FileNotFoundException {
    createJsonFiles();
    initializeControllers();
    AuthMiddleware authMiddleware = new AuthMiddleware();
    authMiddleware.init();
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
