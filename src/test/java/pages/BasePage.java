package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import utilities.DriverSetup;

import java.io.ByteArrayInputStream;

public class BasePage extends DriverSetup {

    // Find and return a WebElement using the given locator
    public WebElement getElement(By locator) {
        try {
            return driver.findElement(locator); // Use the driver instance to find the element
        } catch (Exception e) {
            System.out.println("Element not found: " + locator);
            e.printStackTrace(); // Print stack trace for debugging
            return null;
        }
    }

    // Click on an element
    public void clickElement(By locator) {
        WebElement element = getElement(locator);
        if (element != null) {
            element.click();
        } else {
            System.out.println("Unable to click. Element not found: " + locator);
        }
    }

    // Send keys to an element
    public void writeOnElement(By locator, String text) {
        WebElement element = getElement(locator);
        if (element != null) {
            element.sendKeys(text);
        } else {
            System.out.println("Unable to send keys. Element not found: " + locator);
        }
    }

    // Get the current URL of the page
    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    // Get the title of the page
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Load a web page by URL
    public void loadWebPage(String url) {
        driver.get(url);
    }

    // Check if an element is displayed
    public boolean isElementDisplayed(By locator) {
        WebElement element = getElement(locator);
        return element != null && element.isDisplayed();
    }

    //clear Field
    public void clearField(By locator){
        WebElement element = getElement(locator);
        if (element != null){
            element.clear();
        }else {
            System.out.println("Element not found "+ locator);
        }
    }

    // Screenshots Method
    public void addScreenShot() {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

}
