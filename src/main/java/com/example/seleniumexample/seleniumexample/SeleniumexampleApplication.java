package com.example.seleniumexample.seleniumexample;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SeleniumexampleApplication {

    public static void main(String[] args) throws AWTException, InterruptedException, MalformedURLException {

        SpringApplication.run(SeleniumexampleApplication.class, args);

        System.out.println("Execution Started !!!");
        System.out.println("BrowserStack Configuration Started");

        String AUTOMATE_USERNAME = "siddhesh29";
        String AUTOMATE_ACCESS_KEY = "yvgiuyRhsJEL1AUzeEbX";
        String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
        String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
        System.out.println("###### buildName is ########### " + buildName);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os_version", "7");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "87.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("name", "BStack-[Java] Sample Test"); // test name
       // caps.setCapability("build", "BStack Build Number 1"); // CI/CD job or build name
        caps.setCapability("build", buildName);
        caps.setCapability("name", buildName); // CI/CD job name using BROWSERSTACK_BUILD_NAME env variable

        System.out.println("BrowserStack Configuration Completed");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);

        System.setProperty("webdriver.chrome.driver","D:\\chromedriver_win32\\chromedriver.exe");
        //WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("http://www.flipkart.com/");
        System.out.println("Opening webbrowser");

        //With Login
       /*
        driver.findElement(By.className("_2IX_2-")).sendKeys("9821997709");
        driver.findElement(By.className("_3mctLh")).sendKeys("jaigajanan");
        driver.findElement(By.className("_2HKlqd")).click(); */

        //Without Login
        driver.findElement(By.className("_2doB4z")).click();
        driver.findElement(By.className("_3704LK")).sendKeys("iPhone 11");
        driver.findElement(By.className("L0Z3Pu")).click();
       
        System.out.println("Search iPhone11");
        Thread.sleep(10000);
        Select priceSelect = new Select(driver.findElement(By.className("_2YxCDZ")));
        priceSelect.selectByVisibleText("₹30000");

       // driver.findElement(By.className("_24_Dny")).click();
        Thread.sleep(3000);

        System.out.println("Before Apple filter JG");

        List<WebElement> checkList = driver.findElements(By.className("_3879cV"));

        for(int i=0;i<checkList.size();i++)
        {
            if(checkList.get(i).getText().equals("APPLE"))
            {
                checkList.get(i).click();
                Thread.sleep(5000);
            }
            if(checkList.get(i).getText().equals(""))
            {
                checkList.get(i).click();
                System.out.println("Inside Flipkart Assured !!!!!");
                Thread.sleep(5000);
            }
        }

        System.out.println("Filtering Completed!");

        
        List<WebElement> productList = driver.findElements(By.className("_4rR01T"));
        List<WebElement> productLinkList = driver.findElements(By.className("_1fQZEK"));
        List<WebElement> priceList = driver.findElements(By.className("_30jeq3"));
        List<String> productDetailList = new ArrayList<>();

        System.out.println("Printing Results on Console");

        for(int i=0;i<productList.size();i++) {
            productDetailList.add("Product Name: " + productList.get(i).getText() + " #### Product Link: "+ productLinkList.get(i).getAttribute("href") + " #### Price: " + priceList.get(i).getText());
            System.out.println("Product Name: " + productList.get(i).getText() + " #### Product Link: "+ productLinkList.get(i).getAttribute("href") + " #### Price: " + priceList.get(i).getText());

            System.out.println(productDetailList);
        }

       //driver.findElement(By.className("_3879cV")).click();


        driver.close();
        if(productDetailList.size() > 0)
        {
            markTestStatus("passed","Product List Successfully Populated",driver);
        }
        else
        {
            markTestStatus("failed","Unable to fetch the Product List",driver);
        }

        driver.quit();
        System.out.println("Execution ended !!!");
    }
    public static void markTestStatus(String status, String reason, WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+status+"\", \"reason\": \""+reason+"\"}}");
    }
    

}
