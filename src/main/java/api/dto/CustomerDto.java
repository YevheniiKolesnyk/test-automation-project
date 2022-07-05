package api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import util.PropertyLoader;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
public class CustomerDto {

    private Integer id_gender;
    private String customer_firstname;
    private String customer_lastname;
    private String email;
    private String passwd;
    private Integer days;
    private Integer months;
    private Integer years;
    private String firstname;
    private String lastname;
    private String company;
    private String address1;
    private String address2;
    private String city;
    private Integer id_state;
    private String postcode;
    private Integer id_country;
    private String other;
    private String phone;
    private String phone_mobile;
    private String alias;
    private String dni;
    private Integer email_create;
    private Integer is_new_customer;
    private String back;
    private String submitAccount;

    public static CustomerDto buildCustomer(String email) {
        return builder()
                .id_gender(1)
                .customer_firstname("Tony")
                .customer_lastname("Stark")
                .email(email)
                .passwd(PropertyLoader.loadProperty("/credentials.properties", "user-password"))
                .days(29)
                .months(5)
                .days(1970)
                .firstname("Tony")
                .lastname("Stark")
                .company("Stark Industries")
                .address1("200 Park Avenue")
                .address2("")
                .city("New York City")
                .id_state(32)
                .postcode("10012")
                .id_country(21)
                .other("")
                .phone("")
                .phone_mobile("212-970-4133")
                .alias("My address")
                .dni("")
                .email_create(1)
                .is_new_customer(1)
                .back("my-account")
                .submitAccount("")
                .build();
    }

}