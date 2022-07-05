package util;

import com.codeborne.selenide.WebDriverRunner;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String withArguments(String string, Object... arguments) {
        int index = 0;
        for (Object arg : arguments) {
            string = string.replace("{" + index++ + "}", arg.toString());
        }
        return string;
    }

    public static String getRandomCustomerEmail() {
        return "test.mail+" + getRandomInt() + "@test.com";
    }

    @SneakyThrows
    public static int getRandomInt() {
        Random rand = SecureRandom.getInstanceStrong();
        return rand.nextInt(100000);
    }

    public static void resizeBrowser(int width, int height) {
        Dimension d = new Dimension(width, height);
        WebDriverRunner.getWebDriver().manage().window().setSize(d);
        LogManager.getLogger().debug("Browser size has been set to: {}x{}", width, height);
    }

    public static void waitForTextToBeDisplayed(String text, Duration duration) {
        String xPath = "//*[contains(text(), '{0}')]";
        By element = By.xpath(withArguments(xPath, text));
        $(element).shouldBe(visible, duration);
    }

}