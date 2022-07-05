package pages;

import org.openqa.selenium.NoSuchElementException;
import util.Utils;

import java.time.Duration;

public class MyAccountPage extends BasePage {

    public boolean isMyAccountPageOpened() {
        String text = "Welcome to your account. Here you can manage all of your personal information and orders.";
        try {
            Utils.waitForTextToBeDisplayed(text, Duration.ofSeconds(20));
            LOGGER.debug("'{}' text is displayed", text);
            return true;
        } catch (NoSuchElementException | AssertionError e) {
            return false;
        }
    }

}