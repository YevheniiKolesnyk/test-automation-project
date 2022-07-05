package pages;

import org.openqa.selenium.By;
import util.ExecutionVariables;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AutomationPracticeMainPage extends BasePage {

    private final By signIn = By.cssSelector(".login");
    private final By logoImg = By.cssSelector(".logo.img-responsive");

    public void navigateToAutomationPracticeMainPage() {
        String url = ExecutionVariables.getEnv().getLink();
        open(ExecutionVariables.getEnv().getLink());
        LOGGER.debug("Navigate to url: {}", url);
        $(logoImg).shouldBe(visible);
    }

    public Authentication clickSignInButton() {
        $(signIn).click();
        LOGGER.debug("Click to 'Sing in' button");
        return new Authentication();
    }

    public static class Authentication {
        private final By emailField = By.cssSelector("#email_create");
        private final By createAccount = By.cssSelector("#SubmitCreate");

        public CreateAnAccountPage initiateAccountCreation(String email) {
            $(emailField).shouldBe(enabled).setValue(email);
            LOGGER.debug("Enter email: {} into 'Create email address' field", email);
            $(createAccount).click();
            LOGGER.debug("Click to 'Create account' button");
            return new CreateAnAccountPage();
        }
    }

}