package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import utilities.DataReader;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BaseDemo {

    public WebDriver driver;
    DataReader dataReader;




    @BeforeTest(alwaysRun = true)
    public void config(){

    }


    @Parameters({"url"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(String url) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/aerdely.TE-SURFACE10/IdeaProjects/Review/src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }


    //Helper Methods:
    public boolean compareListWebElementsToExcelDoc(List<WebElement> elements, String excelDocPath, String sheetName) throws IOException {

        dataReader = new DataReader();
        String[] excelData = dataReader.fileReaderStringArrayXSSF(excelDocPath, sheetName);
//        waitUntilWebElementListVisible(elements);
        boolean flag = false;
        int count = 0;

        for (int i = 0; i < elements.size(); i++) {
            String elementsData = elements.get(i).getText();
            if (elementsData.equals(excelData[i])) {
                flag = true;
                System.out.println("PASSED ON: " + elementsData);
            } else {
                System.out.println("FAILED ON: " + elementsData);
                count++;
            }
        }
        if (count > 0) {
            flag = false;
        }
        return flag;

    }


    //Wait helper methods:
    public List<WebElement> waitUntilPresenceOfAllElements(String cssSelector) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        return elements;
    }

    public WebElement fluentWait(String cssSelector){
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        WebElement elementToWait = fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement elementToWait = driver.findElement(By.cssSelector(cssSelector));
                return elementToWait;
            }
        });
        return elementToWait;
    }

    //other methods:
    public ArrayList<String> getTextFromListOfElementsIntoStringList(List<WebElement> elements){
        ArrayList<String> allElementsText = new ArrayList<>();
        for (WebElement singleElement : elements) {
            String tabText = singleElement.getText();
            allElementsText.add(tabText);
        }
        return allElementsText;
    }
    public static void captureScreenshot(WebDriver driver, String testName) {
        Date date = new Date();
        String fileName = testName + " - " + date.toString().replace(" ", "_").replace(":", "-") + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "/lib/Screenshots/" + fileName));
            System.out.println("SCREENSHOT TAKEN");
        } catch (Exception e) {
            System.out.println("ERROR TAKING SCREENSHOT: " + e.getMessage());
        }
    }

    public static String convertToString(String st) {
        String splitString = "";
        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
        return splitString;
    }

}
