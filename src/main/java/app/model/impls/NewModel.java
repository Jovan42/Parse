package app.model.impls;

import app.anotations.Entity;
import app.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewModel extends BaseEntity {
    private String field1;
    private String field2;

    @Override
    public Map<String, List<String>> validate() {
        return new HashMap<>();
    }

    @Override
    public void edit(BaseEntity newData) {
        NewModel newModel = (NewModel) newData;
        this.field1 = newModel.getField1();
        this.field2 = newModel.getField2();
    }
}

