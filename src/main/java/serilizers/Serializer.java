package serilizers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Serializer<T, I> {

    Optional<T> findById(I id) throws FileNotFoundException;
    List<T> findAll() throws FileNotFoundException;
    Optional<T>  create(T t) throws IOException;
    Optional<T>  update(T t) throws IOException;

}
