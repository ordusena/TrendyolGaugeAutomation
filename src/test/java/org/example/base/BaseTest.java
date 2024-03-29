package org.example.base;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
public class BaseTest {

    public static AppiumDriver<MobileElement> appiumDriver;
    FluentWait<AppiumDriver> wait;

    public Logger logger = LogManager.getLogger();
    protected boolean localAndroid = true;

    @BeforeScenario
    public void setUp() throws MalformedURLException {
        if (Constans.IS_LOCAL_ANDROID) {
            System.out.println("....Android started....");
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Constans.DEVICE_NAME);
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Constans.START_APP_ACTIVITY);
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Constans.APP_PACKAGE);
            desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Constans.NEW_COMMAND_TIMEOUT);
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);

            URL url = new URL(Constans.URL);
            appiumDriver = new AndroidDriver(url, desiredCapabilities);
            wait = new FluentWait<AppiumDriver>(appiumDriver);
            wait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(450)).ignoring(NoSuchElementException.class);
        }
        else {
            System.out.println("...IOS Test Started...");
        }
    }

    @AfterScenario
    public void tearDown() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            System.out.println("...Test finished...");
        }
    }
}