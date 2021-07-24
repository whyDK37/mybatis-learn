package example.domain;

public class Address {

  private String city;

  public Address(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  public Address setCity(String city) {
    this.city = city;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Address{");
    sb.append("city='").append(city).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
