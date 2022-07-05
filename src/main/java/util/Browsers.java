package util;

import com.codeborne.selenide.Configuration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.MutableCapabilities;

public class Browsers {

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        CHROME("chrome", "100.0"),
        CHROME_IPHONE_X("chrome-mobile", "100.0"),
        CHROME_IPAD("chrome-tablet", "100.0"),
        FIREFOX("firefox", "98.0");

        private final String name;
        private final String defaultVersion;

        public static Type getByName(String name) {
            Type[] types = values();
            for (Type type : types) {
                if (type.name.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Browser type: " + name + " is not found");
        }
    }

    /**
     * to enable video recording add:
     * capabilities.setCapability("enableVideo", true);
     */
    public static void setCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("enableVNC", true);
        Configuration.browserCapabilities = capabilities;
    }

    public static void setRemoteSelenoidLink() {
        Configuration.remote = PropertyLoader.loadProperty("/credentials.properties", "remote-selenoid-link");
    }

    public static void setBrowserType(String browserName) {
        Browsers.Type browser = Browsers.Type.getByName(browserName);
        switch (browser) {
            case CHROME:
                Configuration.browser = Browsers.Type.CHROME.getName();
                Configuration.browserVersion = Browsers.Type.CHROME.getDefaultVersion();
                Configuration.browserSize = "1448x2000";
                break;
            case CHROME_IPHONE_X:
                Configuration.browser = Browsers.Type.CHROME.getName();
                Configuration.browserVersion = Browsers.Type.CHROME.getDefaultVersion();
                System.setProperty("chromeoptions.mobileEmulation", "deviceName=iPhone X");
                Configuration.browserSize = "400x1000";
                break;
            case CHROME_IPAD:
                Configuration.browser = Browsers.Type.CHROME.getName();
                Configuration.browserVersion = Browsers.Type.CHROME.getDefaultVersion();
                System.setProperty("chromeoptions.mobileEmulation", "deviceName=iPad");
                Configuration.browserSize = "768x1024";
                break;
            case FIREFOX:
                Configuration.browser = Browsers.Type.FIREFOX.getName();
                Configuration.browserVersion = Browsers.Type.FIREFOX.getDefaultVersion();
                Configuration.browserSize = "1440x1600";
                break;
            default:
                throw new IllegalStateException("Unexpected browser: " + browser.getName());
        }
    }

}