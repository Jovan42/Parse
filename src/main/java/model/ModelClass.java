package model;

import anotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class ModelClass {

    private Long id;
    private String name;
    private String lastName;
    private double num;
}
