package org.example.methods;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.example.base.BaseTest;
import org.example.base.Constans;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Methods extends BaseTest {

    AppiumDriver driver;
    FluentWait<AppiumDriver<MobileElement>> wait;
    Actions actions;
    Logger logger = LogManager.getLogger(Methods.class);


    public Methods() {
        driver = appiumDriver;
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);

        actions = new Actions(driver);
    }

    public MobileElement findElement(By by) {
        WebDriverWait wdw = new WebDriverWait(appiumDriver, 5);
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<MobileElement> findElements(By by) {
        return wait.until(new ExpectedCondition<List<MobileElement>>() {
            @Nullable
            @Override
            public List<MobileElement> apply(@Nullable WebDriver driver) {
                List<MobileElement> elements = driver.findElements(by);
                return elements.size() > 0 ? elements : null;
            }
        });
    }

    public MobileElement findElementBykey(String key, String selector) {
        if (selector.equals("xpath")) {
            return findElement(By.xpath(key));
        } else {
            return findElement(By.id(key));
        }
    }

    public void click(String key, String selector) {
        findElementBykey(key, selector).click();
        logger.info("Clicked on the " + key + " button");
    }

    public void sendKeys(String key, String text, String selector) {
        logger.info("Entered '" + text + "' into the " + key + " field");
        findElementBykey(key, selector).sendKeys(text);
    }

    public void waitForElementToVisible2(String key, String selector) {
            By by = getBy(key, selector);
       // wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        logger.info("Waiting for the visibility of the " + key + " element.");
    }

    public void waitForElementVisible(String locatorType, String locatorValue, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        By locator;

        switch (locatorType.toLowerCase()) {
            case "id":
                locator = By.id(locatorValue);
                break;
            case "xpath":
                locator = By.xpath(locatorValue);
                break;
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private By getBy(String key, String selector) {
        if (selector.equals("xpath")) {
            return By.xpath(key);
        } else {
            return By.id(key);
        }
    }

    public List<WebElement> findElementsByKey(String key, String selector) {
        By by;

        switch (selector.toLowerCase()) {
            case "id":
                by = By.id(key);
                break;
            case "xpath":
                by = By.xpath(key);
                break;
            // Diğer seçenekler de eklenebilir
            default:
                throw new IllegalArgumentException("Geçersiz selector: " + selector);
        }

        return driver.findElements(by);
    }

   /* public void clickRandomProduct() {
        // Ürün listesini bul
        List<WebElement> productList = findElementsByKey(Constans.PRODUCT_LIST_ID, "id");

        if (productList.size() > 0) {
            // Rastgele bir ürünü seç
            Random random = new Random();
            int randomIndex = random.nextInt(productList.size());
            WebElement randomProduct = productList.get(randomIndex);
            // Ürün içindeki resmi tıkla
            WebElement productImage = randomProduct.findElement(By.id(Constans.PRODUCT_ID));
            productImage.click();
            logger.info("Rastgele bir ürün seçildi");
        } else {
            logger.error("Urun yok");
        }
    }
    */

    public void clickRandomProduct() {
        // Ürün listesini bul
        List<WebElement> productList = findElementsByKey(Constans.PRODUCT_LIST_ID, "id");

        if (productList.size() > 0) {
            // Rastgele bir ürünü seç
            Collections.shuffle(productList); // Liste öğelerini karıştır
            WebElement randomProduct = productList.get(0); // İlk öğeyi seç (karıştırıldığı için rastgele olacak)

            // Ürün içindeki resmi tıkla
            WebElement productImage = randomProduct.findElement(By.id(Constans.PRODUCT_ID));
            productImage.click();

            logger.info("A random product has been selected!");
        } else {
            logger.error("No products available");
        }
    }

    public void clickRandomProductSize() {
        // Ürün listesini bul
        WebElement productSizeElement = findElementBykey(Constans.PRODUCT_SIZE_ID, "id");
        // productSizeElement null kontrolü ekleyin
        if (productSizeElement != null) {
            // Rastgele bir ürünü seç
            List<WebElement> productList = productSizeElement.findElements(By.xpath(Constans.SIZE_XPATH));
            if (productList.size() > 0) {
                // Rastgele bir ürünü seç
                Random random = new Random();
                int randomIndex = random.nextInt(productList.size());
                WebElement randomProduct = productList.get(randomIndex);
                // Ürün içindeki resmi tıkla
                randomProduct.click();
                logger.info("A random size has been selected!");
            } else {
                logger.error("No sizes available!");
            }
        } else {
            logger.error("Product size list element not found!");
        }
    }

    public void verifyTheTextOnThePage(String id, String selector) {
        By by;
        switch (selector.toLowerCase()) {
            case "id":
                by = By.id(id);
                break;
            case "xpath":
                by = By.xpath(id);
                break;
            // Diğer seçenekler de eklenebilir
            default:
                throw new IllegalArgumentException("Geçersiz selector: " + selector);
        }
        MobileElement element = (MobileElement) appiumDriver.findElement(by);
        logger.info("Confirmed that the current element contains '" + element.getText() + "'");
    }

    public void scrollDown() {
        Dimension size = appiumDriver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8); // Başlangıç yüksekliği
        int endY = (int) (size.height * 0.2); // Bitiş yüksekliği

        new TouchAction<>(appiumDriver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public void scrollDown2() {
        try {
            Dimension size = appiumDriver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.8); // Başlangıç yüksekliği
            int endY = (int) (size.height * 0.2); // Bitiş yüksekliği
            new TouchAction<>(appiumDriver)
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        } catch (Exception e) {
            logger.error("ScrollDownPage2 sırasında hata oluştu", e);
        }
    }

    public void scrollToElement(String selector, String key) {
        By specificElementLocator = getBy(key, selector);
        try {
            MobileElement element = (MobileElement) appiumDriver.findElement(specificElementLocator);
            WebDriverWait wait = new WebDriverWait(appiumDriver, 20);
            wait.until(ExpectedConditions.visibilityOf(element));
            appiumDriver.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to the specific element with locator: " + specificElementLocator);
        } catch (Exception e) {
            logger.error("Error while scrolling to the element with locator: " + specificElementLocator, e);
        }
        // Replace the following line with the correct By instance
        By elementLocator = getBy(key, selector);
        WebDriverWait wait = new WebDriverWait(driver, 10); // 10 saniyeye kadar bekleyin
        wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
    }

    public void swipeUpAccordingToPhoneSizeECS() {  //ECS
        Dimension size = appiumDriver.manage().window().getSize();
        int startx = size.width / 2;
        int endy = (int) (size.height * 0.20);
        int starty = (int) (size.height * 0.80);
        new TouchAction<>((AndroidDriver) appiumDriver)
                .press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(startx, endy))
                .release()
                .perform();
    }

   /* public void swipeUpMultipleTimes(int times) {
        for (int i = 0; i < times; i++) {
            swipeUpAccordingToPhoneSizeECS();
            // Diğer işlemleri ekle (isteğe bağlı)
            System.out.println("-----------------------------------------------------------------");
            System.out.println(i + 1 + ". SWİPE UP EDİLDİ");
            System.out.println("-----------------------------------------------------------------");
        }
    }

    */
   public void swipeUpMultipleTimes(int times) {
       for (int i = 0; i < times; i++) {
           swipeUpAccordingToPhoneSizeECS();

           // Diğer işlemleri ekle (isteğe bağlı)

           logger.info((i + 1) + ". Swipe up completed");
       }
   }
}



