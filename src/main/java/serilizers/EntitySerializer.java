package serilizers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.EntityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class EntitySerializer implements Serializer<EntityClass, String> {
    private static final String FILE_PATH = "./data/" + EntityClass.class.getCanonicalName() + ".json";
    private static Gson gson = new Gson();

    @Override
    public Optional<EntityClass> findById(String id) throws FileNotFoundException {
        return findAll().stream().filter((entityClass) -> entityClass.getId().equals(id)).findFirst();
    }

    @Override
    public List<EntityClass> findAll() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
        return gson.fromJson(reader, new TypeToken<List<EntityClass>>(){}.getType());
    }

    @Override
    public Optional<EntityClass> create(EntityClass entityClass) throws IOException {
        List<EntityClass> list = findAll();
        entityClass.setId(UUID.randomUUID().toString());
        list.add(entityClass);
        writeInFile(list);
        return Optional.of(entityClass);
    }

    @Override
    public Optional<EntityClass> update(EntityClass entityClass) throws IOException {
        final EntityClass toReturn = new EntityClass();
        List<EntityClass> list = findAll().stream().filter(oldEntityClass -> entityClass.getId().equals(entityClass.getId())).map(oldEntityClass -> {
            toReturn.setLastName(entityClass.getLastName());
            oldEntityClass.setName(entityClass.getName());
            oldEntityClass.setNum(entityClass.getNum());
            return toReturn = oldEntityClass;
        }).collect(Collectors.toList());
        writeInFile(list);
        return Optional.empty();
    }

    protected static void writeInFile(List<EntityClass> list) throws IOException {
        Files.write(Paths.get(FILE_PATH), gson.toJson(list).getBytes(), StandardOpenOption.WRITE);
   }

}
