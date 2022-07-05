package pages;

import org.openqa.selenium.By;
import pages.objects.Customer;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;

public class CreateAnAccountPage extends BasePage {

    private final By titleMr = By.cssSelector("#id_gender1");
    private final By titleMrs = By.cssSelector("#id_gender2");
    private final By firstName = By.cssSelector("#customer_firstname");
    private final By lastName = By.cssSelector("#customer_lastname");
    private final By email = By.cssSelector("#email");
    private final By password = By.cssSelector("#passwd");
    private final By dateOfBirthDay = By.cssSelector("#days");
    private final By dateOfBirthMonth = By.cssSelector("#months");
    private final By dateOfBirthYear = By.cssSelector("#years");

    private final By company = By.cssSelector("#company");
    private final By address = By.cssSelector("#address1");
    private final By city = By.cssSelector("#city");
    private final By state = By.cssSelector("#id_state");
    private final By zip = By.cssSelector("#postcode");
    private final By mobilePhone = By.cssSelector("#phone_mobile");
    private final By registerButton = By.cssSelector("#submitAccount");

    public CreateAnAccountPage setTitle(Customer.Title title) {
        switch (title) {
            case MR:
                if (!Objects.equals($(titleMr).getAttribute("checked"), "checked")) {
                    $(titleMr).click();
                    LOGGER.debug("Select 'Mr.' title");
                } else {
                    LOGGER.debug("Mr. title is already selected");
                }
                break;
            case MRS:
                if (!Objects.equals($(titleMrs).getAttribute("checked"), "checked")) {
                    $(titleMrs).click();
                    LOGGER.debug("Select 'Mrs.' title");
                } else {
                    LOGGER.debug("Mrs. title is already selected");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected title: " + title.getTitle());
        }
        return this;
    }

    public CreateAnAccountPage setFirstName(String firstNameValue) {
        $(firstName).setValue(firstNameValue);
        LOGGER.debug("Enter value: {} into 'First name' field", firstNameValue);
        return this;
    }

    public CreateAnAccountPage setLastName(String lastNameValue) {
        $(lastName).setValue(lastNameValue);
        LOGGER.debug("Enter value: {} into 'Last name' field", lastNameValue);
        return this;
    }

    public CreateAnAccountPage setEmail(String emailValue) {
        if (!Objects.equals($(email).getAttribute("value"), emailValue)) {
            $(email).setValue(emailValue);
            LOGGER.debug("Enter value: {} into 'Email' field", emailValue);
        } else {
            LOGGER.debug("Email is already added");
        }
        return this;
    }

    public CreateAnAccountPage setPassword(String passwordValue) {
        $(password).setValue(passwordValue);
        LOGGER.debug("Set password");
        return this;
    }

    public CreateAnAccountPage addDateOfBirth(LocalDate date) {
        int day = date.getDayOfMonth();
        $(dateOfBirthDay).selectOption(day);
        LOGGER.debug("Select day: {}", day);

        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        $(dateOfBirthMonth).selectOptionContainingText(month);
        LOGGER.debug("Select month: {}", month);

        int year = date.getYear();
        $(dateOfBirthYear).selectOptionContainingText(String.valueOf(year));
        LOGGER.debug("Select year: {}", year);

        return this;
    }

    public CreateAnAccountPage setCompany(String companyValue) {
        $(company).setValue(companyValue);
        LOGGER.debug("Enter value: {} into 'Company' field", companyValue);
        return this;
    }

    public CreateAnAccountPage setAddress(String addressValue) {
        $(address).setValue(addressValue);
        LOGGER.debug("Enter value: {} into 'Address' field", addressValue);
        return this;
    }

    public CreateAnAccountPage setCity(String cityValue) {
        $(city).setValue(cityValue);
        LOGGER.debug("Enter value: {} into 'City' field", cityValue);
        return this;
    }

    public CreateAnAccountPage selectState(String stateValue) {
        $(state).selectOption(stateValue);
        LOGGER.debug("Enter value: {} into 'State' field", stateValue);
        return this;
    }

    public CreateAnAccountPage setZip(String zipValue) {
        $(zip).setValue(zipValue);
        LOGGER.debug("Enter value: {} into 'Zip/Postal Code' field", zipValue);
        return this;
    }

    public CreateAnAccountPage setMobilePhone(String mobilePhoneValue) {
        $(mobilePhone).setValue(mobilePhoneValue);
        LOGGER.debug("Enter value: {} into 'Mobile phone' field", mobilePhoneValue);
        return this;
    }

    public CreateAnAccountPage clickOnRegister() {
        $(registerButton).click();
        LOGGER.debug("Click on register button");
        return this;
    }

}