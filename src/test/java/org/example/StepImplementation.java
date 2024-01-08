package org.example;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.example.base.BaseTest;
import org.example.base.Constans;
import org.example.methods.Methods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
public class StepImplementation extends BaseTest {

    Methods methods = new Methods();
    Logger logger = LogManager.getLogger(StepImplementation.class);


    @Step("<Key> saniye kadar beklenir")
    public void waitWitSeconds(int Key) throws InterruptedException{
        Thread.sleep(Key* 1000L);
    }

   @Step("Cinsiyet secilir")
    public void genderButton() {

             methods.waitForElementVisible("id",Constans.GENDER,20);
            // Cinsiyet seçimi yap
             methods.click(Constans.GENDER, "id");

             logger.info("Cinsiyet secildi");
    }

    @Step("Trendyol sayfasi acilir")
    public void loginPermission() {
        // Öğenin tıklanabilir veya görünür olmasını bekleyin
        methods.waitForElementVisible("id",Constans.APP_BAR_LAYOUT,5);
        methods.click(Constans.APP_BAR_LAYOUT, "id");

        // Başarı mesajını günlüğe kaydet
        logger.info("APP_BAR_LAYOUT butonu gorunur durumda");
    }

    @Step("PopUp kapatilir")
    public void closePopup() {
        try {
            methods.waitForElementVisible("id",Constans.TAB_CLOSE,5);
            methods.click(Constans.TAB_CLOSE, "id");

            logger.info("PopUp başarıyla kapatıldı");
        } catch (Exception e) {
            logger.error("PopUp kapatma sirasinda hata olustu", e);
        }
    }

    @Step("Hesaba tiklanir")
    public void clickLogIn(){
        methods.click(Constans.ACCOUNT,"id");
    }

    @Step("Kullanici adi <name> yazilir")
    public void LogInAccount(String name) {
        methods.waitForElementVisible("id",Constans.TEXT_EMAIL,5);
        methods.click(Constans.TEXT_EMAIL,"id");
        methods.sendKeys(Constans.TEXT_EMAIL, name, "id");
    }

    @Step("Sifre <password> yazilir")
    public void passwordText(String password){
        methods.click(Constans.TEXT_PASSWORD,"id");
        methods.sendKeys(Constans.TEXT_PASSWORD, password, "id");
        //Klavyeyi kapat
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent(AndroidKey.BACK));
    }

    @Step("Giris yap butonuna tiklanir")
    public void LogInButton(){
        methods.click(Constans.LOGIN_BUTTON, "id");
    }

    @Step("Pop-up'lari kapat")
    public void closePopups() {
        try {
            int xCoordinate = 600;
            int yCoordinate = 300;

            // Ekranın belirli bir koordinatına dokunma işlemi
            new TouchAction<>(appiumDriver)
                    .press(PointOption.point(xCoordinate, yCoordinate))
                    .release()
                    .perform();

            // Günlüğe kapatma başarılı mesajını kaydet
            logger.info("Popup başarıyla kapatıldı");
        } catch (Exception e) {
            // Hata durumunda günlüğe hata mesajını kaydet
            logger.error("Popup kapatma sırasında hata oluştu", e);
        }
    }

    @Step("Anasayfa'ya gidilir")
            public void clickToHomeButton() {
        methods.waitForElementVisible("id",Constans.TAB_HOME,5);
        methods.click(Constans.TAB_HOME, "id");
    }

    @Step("Arama butonuna tiklanir")
    public void clickToSearchButton(){
        methods.click(Constans.SEARCH_TAB, "id");
        methods.sendKeys(Constans.SEARCH_TAB, "bordo çizme", "id");
    }

    @Step("Bordo cizme aratilir")
    public void searchForBurgundyBoot(){
        methods.click(Constans.BURGUNDY_BOOT, "xpath");
    }

    @Step("Rastgele bir ürün seçilir")
    public void selectRandomProduct() {
        methods.clickRandomProduct();
    }

    @Step("Sepete urun eklenir")
    public void addProductToTheCart() {
        methods.waitForElementVisible("id",Constans.ADD_PRODUCT,5);
        methods.click(Constans.ADD_PRODUCT, "id");
    }

    @Step("Rastgele bir beden seçilir")
    public void selectRandomProductSize() {
        methods.clickRandomProductSize();
    }

    @Step("Rastgele secilen beden sepete eklenir")
    public void addProductToTheCart2(){
        methods.click(Constans.ADD_PRODUCT2, "id");
    }

    @Step("Sepete gidilir")
    public void goToTheCart() {
        methods.click(Constans.PRODUCT_DETAILS, "id");
    }

    @Step("Urun silinir")
    public void removeProductFromTheCart() {
        methods.waitForElementVisible("id",Constans.DELETE_BUTTON,10);
        methods.click(Constans.DELETE_BUTTON, "id");
        methods.click(Constans.REMOVE_BUTTON,"id");
    }

    @Step("Sepetin bos oldugu kontrol edilir")
    public void verifyBasket () {
        methods.waitForElementVisible("id",Constans.EMPTY_TEXT,5);
        methods.verifyTheTextOnThePage(Constans.EMPTY_TEXT, "id");

    }

    @Step("Hesabima gidilir")
    public void clickToMyAccountButton(){
        methods.click(Constans.ACCOUNT_TAB,"id");
    }

    @Step("Hesap ayarlarina gidilir")
    public void clickToLayoutAccountButton() {
        methods.waitForElementVisible("id",Constans.LAYOUT_ACCOUNT_TAB,5);
        methods.click(Constans.LAYOUT_ACCOUNT_TAB,"id");
    }

    @Step("Hesaptan cikis yapilir")
    public void clickToExitButton() {
        methods.waitForElementVisible("xpath",Constans.EXIT_TAB_XPATH2,5);
        methods.click(Constans.EXIT_TAB_XPATH2,"xpath");
        methods.waitForElementVisible("id",Constans.LOGOUT_BUTTON,5);
        methods.click(Constans.LOGOUT_BUTTON,"id");
    }

    @Step({"<times> kere yukarı dogru kaydir - AND", "Swipe up <times> times"}) //ecs
    public void swipeUpMultipleTimes(int times) {
        methods.swipeUpMultipleTimes(times);
    }
}
