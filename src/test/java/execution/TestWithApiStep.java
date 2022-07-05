package execution;

import org.testng.annotations.Test;
import steps.ApiSteps;
import util.Utils;

import static steps.Steps.navigateToMainPage;

public class TestWithApiStep extends BaseUiTest {

    @Test
    public void registerNewUserViaApi() {
        String email = Utils.getRandomCustomerEmail();

        ApiSteps.registerNewCustomer(email);

        navigateToMainPage();
    }

}
