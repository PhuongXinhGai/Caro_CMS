import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest01 {
    public void sleepInSecond(long timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // khai bao driver
    WebDriver driver;

    //Lay xpath
    By txtUsername = By.xpath("//input[@id='user-name']");
    By txtPassword = By.xpath("//input[@id='password']");
    By btnContinue = By.xpath("//input[@id='login-button']");

    //gia tri nhap
    String userName = "standard_user";
    String password = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void TC01_testLoginSuccess() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(txtUsername).sendKeys(userName);
        sleepInSecond(1);
        driver.findElement(txtPassword).sendKeys(password);
        sleepInSecond(1);
        driver.findElement(btnContinue).click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
