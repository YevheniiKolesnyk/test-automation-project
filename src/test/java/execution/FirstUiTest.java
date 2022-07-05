package execution;

import com.codeborne.selenide.testng.ScreenShooter;
import execution.BaseUiTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MyAccountPage;
import pages.objects.Customer;
import util.Browsers;
import util.Utils;

import static steps.Steps.*;

@Listeners({ScreenShooter.class})
public class FirstUiTest extends BaseUiTest {

    public FirstUiTest() {
        browser = Browsers.Type.CHROME;
    }

    @Test
    public void registerNewUser() {
        String email = Utils.getRandomCustomerEmail();
        Customer customer = new Customer().enrichWithDefaults(email);

        navigateToMainPage();
        createNewAccount(email);
        addPersonalInformation(customer);
        addPersonAddress(customer);
        registerNewCustomer();

        Assert.assertTrue(new MyAccountPage().isMyAccountPageOpened(), "My account page is not opened");
    }

}