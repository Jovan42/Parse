import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import anotations.Entity;
import com.google.gson.Gson;

import org.reflections.Reflections;
import serilizers.EntitySerializer;

public class Main {
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        createJsonFiles();
        EntitySerializer entitySerializer = new EntitySerializer();
        try {
            System.out.println(entitySerializer.get().get().size());;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //        try {
        //            String filePath = "./data/" + EntityClass.class.getCanonicalName() + ".json";
        //            Type listType = new TypeToken<List<EntityClass>>() {}.getType();
        //            JsonReader reader = new JsonReader(new FileReader(filePath));
        //            List<EntityClass> a=  gson.fromJson(reader, listType);

        //            List<EntityClass> all =
        // GenericSerializer.getAll(Types.typesMap.get(Types.ENTITY_CLASS_LIST));
        //            System.out.println(all.get(0));
        //            Optional<EntityClass> byId = GenericSerializer.findById(1L,
        // Types.typesMap.get(Types.ENTITY_CLASS));
        //            System.out.println(byId);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
    }

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
                            Files.write(
                                    Paths.get(filePath),
                                    "[]".getBytes(),
                                    StandardOpenOption.APPEND);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
