package serilizers;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GenericSerializer {
  private static Gson gson = new Gson();

  public static void save(Object o) {
    String filePath = "./data/" + o.getClass().getCanonicalName() + ".json";
    try {

      Files.write(
          Paths.get(filePath),
          gson.toJson(o).getBytes(),
          StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
