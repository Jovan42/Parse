package model.impls;

import anotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.BaseEntity;
import model.Editable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Editable<User> {
  private String firstName;
  private String lastName;
  private String password;
  private String username;

  @Override
  public User edit(User newData) {
    this.firstName = newData.getFirstName();
    this.lastName = newData.getLastName();
    this.password = newData.getPassword();
    this.username = newData.getUsername();
    return this;
  }

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
}
