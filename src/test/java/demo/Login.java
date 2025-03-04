package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {
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
    WebDriverWait wait;
    String projectPath = System.getProperty("user.dir");

//    Lay xpath
    By inputUsername = By.xpath("//input[@name='user_name']");
    By inputPassword = By.xpath("//input[@name='password']");
    By btnLogin = By.xpath("//button[@id='kt_sign_in_submit']");
    By avt = By.xpath("//div[@id='kt_header_user_menu_toggle']");
    By txtSignOut = By.xpath("//a[contains(text(),'Sign Out')]");
    By btnComfirm = By.xpath("//button[@class='btn-sm mx-2 btn btn-primary']");
//Nhap gia tri
    String valueUsername = "phuongtt-chilinh";
    String valuePassword = "Golf@12345";

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", projectPath + "/src/browserDriver/chromedriver.exe");
        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, 10);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Sử dụng Duration.ofSeconds

        driver.manage().window().maximize();
    }

    @Test
    public void TC01_Login_successfully(){
        driver.get("https://vngolf-portal.vnpaytest.vn/auth");
        sleepInSecond(2);
        driver.findElement(inputUsername).sendKeys(valueUsername);
        sleepInSecond(1);
        driver.findElement(inputPassword).sendKeys(valuePassword);
        sleepInSecond(1);
        driver.findElement(btnLogin).click();
        sleepInSecond(3);
        driver.findElement(avt).click();
        sleepInSecond(1);
        driver.findElement(txtSignOut).click();
        sleepInSecond(1);
        driver.findElement(btnComfirm).click();

        // Đợi toast hiển thị và kiểm tra nội dung
        WebElement toastElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'text-white toast-body') and text()='Sign out successfuly!!!']"))
        );

        // Kiểm tra toast hiển thị đúng
        if (toastElement.isDisplayed()) {
            System.out.println("Toast 'Sign out successfuly!!!' hiển thị thành công.");
        } else {
            System.out.println("Toast 'Sign out successfuly!!!' không được hiển thị.");
        };

        sleepInSecond(3);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
