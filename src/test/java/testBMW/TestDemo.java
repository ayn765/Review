package testBMW;

import common.BaseDemo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestDemo extends BaseDemo {


    @Test(groups = "smoke", enabled = true)
    public void testHeadTabsText() throws IOException {
        List<WebElement> headTabs = waitUntilPresenceOfAllElements(".globalnav-primary__links>li");
        ArrayList<String> allTabsTitles = getTextFromListOfElementsIntoStringList(headTabs);
        Assert.assertTrue(compareListWebElementsToExcelDoc(headTabs, "C:\\Users\\aerdely.TE-SURFACE10\\IdeaProjects\\Review\\src\\main\\resources\\testData\\BMWTestData.xlsx", "HeadTabs"), "Tabs are incorrectly displayed");
    }

    @Test(groups = "smoke")
    public void testButtonLearnMore() {
        WebElement buttonLearnMore = fluentWait("section>a[href='/future-vehicles/i4-all-electric-vehicle.html']");
        buttonLearnMore.click();
    }

    @Test(dataProvider = "LoginData", groups = "smoke")
    public void testLogin(String emailAddress, String password){
        //every time we create a test automatically one object is created for the complete test method
        //we can store that object in ExtentTest class variable
        //using this object we can use in further steps
//        ExtentTest test = extent.createTest("Login Test");
        driver.findElement(By.cssSelector(".globalnav-primary-my-bmw__portal-link")).click();
        driver.findElement(By.cssSelector("#loginEmailAddressInput")).sendKeys(emailAddress);
        driver.findElement(By.id("loginPasswordInput")).sendKeys(password);
        driver.findElement(By.id("loginBtn")).click();
        String expectedErrorMessage = "The information you entered does not match our records. Please re-enter your information, reset your password, or register for a BMW Login.";
        Assert.assertEquals(driver.findElement(By.id("errorMessage")).getText(), expectedErrorMessage, "The message is incorrect.");
//        test.fail("user do not match");
//        extent.flush();
    }


    @DataProvider(name="LoginData")
    public Object[][]getData(){
        Object [][]data = {{"robbie@someEmail.com", "efiu76@"},{"tom@someEmail.com", "oiu532&"}, {"katrin@someEmail.com", "jvxvb82&"}};
        return data;
    }
}
