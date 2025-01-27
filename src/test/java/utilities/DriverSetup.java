package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverSetup {

    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("local")
                .setUdid("R58M43K2YCP")
                .setAppPackage("com.banggood.client")
                .setAppActivity("com.banggood.client.module.home.MainActivity");

        URL remoteURL = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(remoteURL, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test(priority = 1, description = "Banner & Searchbar Test")
    public void testSearchBar() throws InterruptedException {
        try {
            // Locate and close the banner if present
            WebElement bannerClose = driver.findElement(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.banggood.client:id/iv_close\"]"));
            if (bannerClose.isDisplayed()) {
                bannerClose.click();
            } else {
                System.out.println("Banner Not Found");
            }
        } catch (Exception e) {
            System.out.println("Error handling banner: " + e.getMessage());
        }

        // Add a short wait to allow for UI updates
        Thread.sleep(3000);

        try {
            // Click on the search icon
            WebElement searchIcon = driver.findElement(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.banggood.client:id/iv_search_icon\"]"));
            searchIcon.click();

            // Wait for the search bar to appear
            Thread.sleep(2000);

            // Locate the search bar and enter the search query
            WebElement searchBar = driver.findElement(AppiumBy.id("com.banggood.client:id/edt_search"));
            searchBar.sendKeys("Smart Watch");

            // Use the Android UI Automator to locate and click the suggestion
            WebElement suggestion = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"smart watches for men\")"));
            suggestion.click();

            //Click the first watch
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement firstWatch = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.banggood.client:id/tv_product_name\" and @text=\"  \n" +
                    "LOKMAT OCEAN MAX 1.96 inch HD Screen bluetooth Call Heart Rate Blood Pressure SpO2 Monitor Sleep Monitoring Multi-sport Modes Music Playback 5ATM Waterproof Smart Watch\"]")));
            firstWatch.click();
            Assert.assertNotEquals(firstWatch,"Search results are not displayed or no relevant products found.");

            // validate product price
            WebElement priceValidate = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.banggood.client:id/tv_new_user_bonus_label_price\"]"));
            String price = priceValidate.getText();
            Assert.assertTrue(price.contains("US$43.99"),"Does not contain this Price!");
            System.out.println(price);

            Thread.sleep(15000);
        } catch (Exception e) {
            System.out.println("Error in search functionality: " + e.getMessage());
        }
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}

// Press the Android search key
//            ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.SEARCH));