import anotations.Controller;
import anotations.Entity;
import controllers.Initialize;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    createJsonFiles();
    initializeControllers();

    //    UserService userService = new UserService();
    //    try {
    //      User user = userService.create(new User("Jovan", "Manojlovic", "123456789",
    // "jovan123"));
    //
    //      user.setUsername("jovan123");
    //      userService.update(user);
    //
    //    } catch (IOException | BadRequestException e) {
    //      e.printStackTrace();
    //    }
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private static void createJsonFiles() {
    File data = new File("./data");
    data.mkdir();
    Reflections reflections = new Reflections("model");
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
    Reflections reflections = new Reflections("controllers");
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
