package first_commit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class GetData {

    private WebDriver driver;
    private WebElement element;
    private PrintWriter printWriter;
    private WebDriverWait wait;

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver",".\\BrowserDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--ignore-certificate-error","--silent");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }
    @Test
    public void getData() throws FileNotFoundException, UnsupportedEncodingException {
        driver.get("http://diemthi.hcm.edu.vn/");
        wait = new WebDriverWait(driver,15);
        printWriter = new PrintWriter("D:\\Data\\data2.txt","UTF_8");
        int i = 2000519; //2074719
        while(true) {
            driver.findElement(By.xpath("//input[@id='SoBaoDanh']")).clear();
            driver.findElement(By.xpath("//input[@id='SoBaoDanh']")).sendKeys("0" + i);
            driver.findElement(By.xpath("//div[@class='jumbotron']//input[@type='submit']")).click();
            if(isElementDisplay()) {
                sleepInSecond(1);
                driver.navigate().back();
                i++;
                continue;
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[2]")));
            sleepInSecond(1);
            String data = driver.findElement(By.xpath("//tbody/tr[2]")).getText();
            printWriter.append(data).append("/n");
            driver.navigate().back();
            i++;
            if (i == 2074719) {
                break;
            }
        }
        printWriter.close();
    }

    public void sleepInSecond(int time) {
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isElementDisplay(){
        try {
            element = driver.findElement(By.xpath("//label[contains(text(),'Không tìm thấy số báo danh này !')]"));
            return element.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

}
