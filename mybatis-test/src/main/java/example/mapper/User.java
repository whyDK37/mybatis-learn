package example.mapper;

import java.io.Serializable;

public class User implements Serializable {

  public User() {
  }

  private static final long serialVersionUID = 8104961490512068314L;
  private int id;
  private String name;
  private String dept;
  private String phone;
  private String website;

  public int getId() {
    return id;
  }

  public User setId(int id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public User setName(String name) {
    this.name = name;
    return this;
  }

  public String getDept() {
    return dept;
  }

  public User setDept(String dept) {
    this.dept = dept;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public User setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getWebsite() {
    return website;
  }

  public User setWebsite(String website) {
    this.website = website;
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", dept='" + dept + '\'' +
        ", phone='" + phone + '\'' +
        ", website='" + website + '\'' +
        '}';
  }

} 