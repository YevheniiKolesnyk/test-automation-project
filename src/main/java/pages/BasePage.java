package pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    protected static final Logger LOGGER = LogManager.getLogger();

    private final By token = By.xpath("//*[@id = 'facebook-jssdk']/following-sibling::script[1]");

    public String getToken() {
        String attributes = $(token).getOwnText();
        return StringUtils.substringBetween(attributes, "var token = '", "';");
    }

}
