package tests;

import com.codeborne.selenide.Configuration;
import drivers.BrowserstackMobileDriver;
import drivers.EmulatorDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.Attach.getSessionId;
import static helpers.DeviceSelection.getDeviceDriver;

public class TestBase {
    private static final String deviceHost = System.getProperty("deviceHost");

    @BeforeAll
    public static void setup() {
        addListener("AllureSelenide", new AllureSelenide());
        Configuration.browser = getDeviceDriver(deviceHost);
        //Configuration.browser = BrowserstackMobileDriver.class.getName();
        //Configuration.browser = EmulatorDriver.class.getName();
        //Configuration.startMaximized = false; //NB, для мобильных приложений это не нужно
        Configuration.browserSize = null;
    }

    @BeforeEach
    public void startDriver() {
        open();
    }

    @AfterEach
    public void afterEach() {
        String sessionId = getSessionId();
//добавляем вложения:
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        closeWebDriver();
        Attach.video(sessionId);
    }
}
