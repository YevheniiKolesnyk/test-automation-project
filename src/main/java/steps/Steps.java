package steps;

import io.qameta.allure.Step;
import pages.AutomationPracticeMainPage;
import pages.CreateAnAccountPage;
import pages.objects.Customer;

public class Steps {

    @Step("Navigate to main page")
    public static void navigateToMainPage() {
        new AutomationPracticeMainPage()
                .navigateToAutomationPracticeMainPage();
    }

    @Step("Create new Account")
    public static void createNewAccount(String email) {
        new AutomationPracticeMainPage()
                .clickSignInButton()
                .initiateAccountCreation(email);
    }

    @Step("Fill your personal information")
    public static void addPersonalInformation(Customer customer) {
        new CreateAnAccountPage()
                .setTitle(customer.getTitle())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setEmail(customer.getEmail())
                .setPassword(customer.getPassword())
                .addDateOfBirth(customer.getBirthDate());
    }

    @Step("Add 'Your address")
    public static void addPersonAddress(Customer customer) {
        new CreateAnAccountPage()
                .setCompany(customer.getCompany())
                .setAddress(customer.getAddress())
                .setCity(customer.getCity())
                .selectState(customer.getState())
                .setZip(customer.getZipCode())
                .setMobilePhone(customer.getPhone());
    }

    @Step("Click on register new customer")
    public static void registerNewCustomer() {
        new CreateAnAccountPage().clickOnRegister();
    }

}