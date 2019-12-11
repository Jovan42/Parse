package model;

import anotations.Entity;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@Entity
public class EntityClass extends BaseEntity {
    private String name;
    private String lastName;
    private double num;

    private static Type getListType(){
        return new TypeToken<ArrayList<EntityClass>>(){}.getType();
    }
}
