package execution;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.testng.annotations.*;
import util.Browsers;
import util.ExecutionVariables;

import java.util.Objects;
import java.util.logging.Level;

public class BaseUiTest {

    protected Browsers.Type browser = Browsers.Type.CHROME;

    @BeforeClass
    public void setAllureSelenide() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .enableLogs(LogType.BROWSER, Level.INFO));
    }

    @Parameters({"browserName"})
    @BeforeMethod
    public void setup(@Optional String browserName) {
        addConfigurations();
        Browsers.setBrowserType(Objects.requireNonNullElse(browserName, browser.getName()));
        if (ExecutionVariables.isRemoteExecution()) {
            Browsers.setRemoteSelenoidLink();
        }
        Browsers.setCapabilities();
        ExecutionVariables.setEnv();
    }

    private void addConfigurations() {
        Configuration.webdriverLogsEnabled = true;
        Configuration.pageLoadTimeout = 50000;
        Configuration.timeout = 10000;
    }

    @AfterMethod
    protected void end() {
        Selenide.closeWebDriver();
    }

}