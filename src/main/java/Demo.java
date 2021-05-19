import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Demo {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:/Users/aerdely.TE-SURFACE10/IdeaProjects/Review/src/test/resources/drivers/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("http://bmwusa.com");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> headTabs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".globalnav-primary__links>li")));
        ArrayList<String> allTabsTitles = new ArrayList<>();
        for (WebElement tab : headTabs) {
            String tabText = tab.getText();
            allTabsTitles.add(tabText);
        }
        System.out.println(allTabsTitles);


        ArrayList<String> expectedTabsTitles = new ArrayList<>(Arrays.asList("Model", "Build Your Own", "Shopping", "BMW Certified", "Owners"));

        for (int i = 0; i < allTabsTitles.size(); i++) {
            if (allTabsTitles.get(i).equals(expectedTabsTitles.get(i))) {
                System.out.println("Passed on " + allTabsTitles.get(i));
            } else {
                System.out.println("Failed on " + allTabsTitles.get(i));
            }

        }

        //fluent wait
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        WebElement buttonLearnMore = wait.until(new Function<WebDriver, WebElement>(){
            public WebElement apply(WebDriver driver){
                WebElement buttonLearnMore = driver.findElement(By.cssSelector("section>a[href='/future-vehicles/i4-all-electric-vehicle.html']"));
                return buttonLearnMore;
            }
        });
        buttonLearnMore.click();
        Thread.sleep(5000);






    }
}
