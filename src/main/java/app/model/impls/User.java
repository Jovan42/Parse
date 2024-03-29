package app.model.impls;

import app.anotations.Entity;
import app.anotations.NotEmpty;
import app.anotations.NotNull;
import app.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
  @NotEmpty
  @NotNull
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  private String password;
  @NotEmpty
  private String username;

  @Override
  public Map<String, List<String>> validate() {
    Map<String, List<String>> map = new HashMap<>();
    List<String> errorList = new ArrayList<>();
    if (password.length() < 8) {
      errorList.add("Password must be longer then 8 characters");
    }
    if (errorList.size() > 0) {
      map.put("password", errorList);
    }
    errorList.clear();
    if (username.length() < 8) {
      errorList.add("Password must be longer then 8 characters");
    }
    if (errorList.size() > 0) {
      map.put("username", errorList);
    }
    return map;
  }

  @Override
  public void edit(BaseEntity newData) {
    User newUser = (User) newData;
    this.firstName = newUser.getFirstName();
    this.lastName = newUser.getLastName();
    this.password = newUser.getPassword();
    this.username = newUser.getUsername();
  }
}
