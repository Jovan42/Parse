import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import model.Model;
import model.ModelClass;
import org.reflections.Reflections;
import serilizers.GenericSerializer;

public class Main {
  public static void main(String[] args) {
//    GenericSerializer.save(new ModelClass(1L,"ime", "prezime", 0.5));
    createJsonFiles();

  }

  private static void createJsonFiles() {
    Reflections reflections = new Reflections("model");
    Set<Class<? extends Model>> subTypes = reflections.getSubTypesOf(Model.class);

    subTypes.forEach((item) -> {
      File fileToCreate = new File("./data/" + item.getCanonicalName() + ".json");
      try {
        fileToCreate.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
