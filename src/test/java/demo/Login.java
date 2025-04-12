package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import org.testng.Assert;
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

    By alertText = By.xpath("//div[@class='alert-text font-weight-bold']");

//Nhap gia tri
    String valueUsernameCorrect = "phuongtt-chilinh";
    String valuePasswordCorrect= "Golf@12345";

    String valueUsernameWrong = "phuongtt";
    String valuePasswordWrong = "Golf@12345";

    String expectedText = "The login detail is incorrect";

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", projectPath + "/src/browserDriver/chromedriver.exe");
        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, 10);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Sử dụng Duration.ofSeconds

        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void TC01_Login_successfully(){
        driver.get("https://vngolf-portal.vnpaytest.vn/auth");
        sleepInSecond(2);

        driver.findElement(inputUsername).sendKeys(valueUsernameCorrect);
        sleepInSecond(1);
        driver.findElement(inputPassword).sendKeys(valuePasswordCorrect);
        sleepInSecond(1);

//      verify enable btnLogin
        boolean statusBtnLoginEnable = driver.findElement(btnLogin).isEnabled();
        if (statusBtnLoginEnable == true){
            System.out.println("Button Login is enable");
        }
        else {
            System.out.println("Login failed because the Login button is disabled");
        }
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

    @Test(priority = 2)
    public void TC02_Login_Fail_InputUsernameWrong() {
        driver.get("https://vngolf-portal.vnpaytest.vn/auth");
        sleepInSecond(2);

        driver.findElement(inputUsername).clear();
        driver.findElement(inputUsername).sendKeys(valueUsernameWrong);
        driver.findElement(inputPassword).clear();
        driver.findElement(inputPassword).sendKeys(valuePasswordCorrect);

        boolean statusBtnLoginEnable = driver.findElement(btnLogin).isEnabled();
        if (statusBtnLoginEnable == true){
            System.out.println("Button Login is enable");
        }
        else {
            System.out.println("Login failed because the Login button is disabled");
        }
        driver.findElement(btnLogin).click();
        sleepInSecond(3);

        String bodyText = driver.findElement(alertText).getText();
        System.out.println(bodyText);
        Assert.assertEquals(expectedText, bodyText);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
