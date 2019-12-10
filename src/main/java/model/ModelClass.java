package model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelClass extends Model {

    private Long id;
    private String name;
    private String lastName;
    private double num;
}
