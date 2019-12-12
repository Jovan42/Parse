package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity implements Editable {
  protected String id;

  public abstract Map<String, List<String>> validate();
}
