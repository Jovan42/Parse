package serilizers;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.BaseEntity;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericSerializer implements Serializer {
    private static Gson gson = new Gson();



//    public static <T> void add(T o, Type type) throws IOException {
//        String filePath = "./data/" + type.getTypeName() + ".json";
//        List<T> list = readFromFile(type);
//        list.add(o);
//        writeInFile(list, type);
//    }
//
//    public Optional<T> findById(Long id, Type type) throws IOException {
//        List<T> ts = readFromFile(type);
//        return ts.stream().filter((item) -> item.getId().equals(id)).findFirst();
//    }
//
//    public List<T> getAll(Type type) throws IOException {
//        return readFromFile(type);
//    }
//
//    protected List<T> readFromFile(Type type) throws IOException {
//        String filePath = "./data/" + T + ".json";
//        JsonReader reader = new JsonReader(new FileReader(filePath));
//        List<T> o = gson.fromJson(reader, type);
//        return o.stream().map((item) -> ((T) item)).collect(Collectors.toList());
//    }
//
//    protected static <T> void writeInFile(List<T> list, Type type) throws IOException {
//        String filePath = "./data/" + type.getTypeName() + ".json";
//
//        Files.write(Paths.get(filePath), gson.toJson(list).getBytes(), StandardOpenOption.WRITE);
//    }
}
