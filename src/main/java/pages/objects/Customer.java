package pages.objects;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import util.PropertyLoader;

import java.time.LocalDate;

@Data
public class Customer {

    private Title title;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String company;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;

    public Customer enrichWithDefaults(String email) {
        Customer defaults = new Customer();

        defaults.setTitle(Title.MR);
        defaults.setFirstName("Tony");
        defaults.setLastName("Stark");
        defaults.setEmail(email);
        defaults.setPassword(PropertyLoader.loadProperty("/credentials.properties", "user-password"));
        defaults.setBirthDate(LocalDate.of(1970, 5, 29));
        defaults.setCompany("Stark Industries");
        defaults.setAddress("200 Park Avenue");
        defaults.setCity("New York City");
        defaults.setState("New York");
        defaults.setZipCode("10002");
        defaults.setCountry("United States");
        defaults.setPhone("212-970-4133");

        return defaults;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Title {

        MR("Mr."),
        MRS("Mrs.");

        private final String title;

    }

}