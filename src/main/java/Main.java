import java.io.File;
import java.io.IOException;
import java.util.Set;

import anotations.Entity;
import org.reflections.Reflections;

public class Main {
  public static void main(String[] args) {
//    GenericSerializer.save(new ModelClass(1L,"ime", "prezime", 0.5));
    createJsonFiles();

  }

  private static void createJsonFiles() {

    Reflections reflections = new Reflections("model");
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);

    annotated.forEach((item) -> {
      File fileToCreate = new File("./data/" + item.getCanonicalName() + ".json");
      try {
        fileToCreate.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
