package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.DataReader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseDemo {

    WebDriver driver;
    DataReader dataReader;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:/Users/aerdely.TE-SURFACE10/IdeaProjects/Review/src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://bmwusa.com");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(){
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
}
