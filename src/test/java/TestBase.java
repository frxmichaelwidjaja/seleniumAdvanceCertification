import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class TestBase {

    /**
     * autoUpdateLocalWebDriver is a method to download and the local webdriver on the run.
     * currently is still in development. Due to this error: "Exception in thread "main"
     * org.openqa.selenium.SessionNotCreatedException: Could not start a new session. Response code 500. Message:
     * session not created: This version of ChromeDriver only supports Chrome version 114"
     * @return WebDriver
     */
    public static WebDriver autoUpdateLocalWebDriver(){
        WebDriver driver = null;
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        options.addArguments("--allowed-ips");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        return driver;
    }

    /**
     * localWebDriver is a method to start loca webdriver with the input of location of the webdriver file.
     * @param loc
     * @return
     */
    public static WebDriver localWebDriver(String loc){
        System.setProperty("webdriver.chrome.driver", loc);
        WebDriver driver = null;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        options.addArguments("--allowed-ips");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        return driver;
    }


    /**
     * remoteLambdaWebDriver is a method to create a remote webdriver to the lambdatest selenium grid.
     * @param browser please input "edge" or "chrome"
     * @param username
     * @param accessKey
     * @return
     */
    public static WebDriver remoteLambdaWebDriver(String browser, String username, String accessKey){
        String gridURL = "@hub.lambdatest.com/wd/hub";
        ChromiumOptions browserOptions = null;
        if ("edge".compareTo(browser)==0) {
            browserOptions = new EdgeOptions();
            browserOptions.setPlatformName("macOS Sierra");
            browserOptions.setBrowserVersion("87.0");
        } else {
            browserOptions = new ChromeOptions();
            browserOptions.setPlatformName("Windows 10");
            browserOptions.setBrowserVersion("86.0");
        }

        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        browserOptions.setCapability("LT:Options", ltOptions);
        URL lambdaTest = null;
        try {
            lambdaTest = new URL("https://" + username + ":" + accessKey + gridURL);
            return new RemoteWebDriver(lambdaTest, browserOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * waitUntilFind is method to scroll and wait until the webelement is being display. The max wait time is 60 seconds.
     * @param driver
     * @param xPath
     * @return
     */
    public static WebElement waitUntilFind(WebDriver driver, String xPath){
        // Number of Try after wait
        int i = 0;
        // Loop for 60
        while(i<60){
            try {
                // wait for 1 second
                Thread.sleep(1000);
                // Scroll to the object
                WebElement result = driver.findElement(By.xpath(xPath));
                String js_code = "arguments[0].scrollIntoView();";
                ((JavascriptExecutor) driver).executeScript(js_code, result);
                return (result != null&& result.isDisplayed()) ? result : null;
            } catch (InterruptedException e) {
                System.out.println("wait is interrupted");
            }catch (NoSuchElementException e) {
                //Do Nothing
                System.out.println("Debug: i:" + i);
            }
            i++;
        }
        return null;
    }

    /**
     * closeRandomPopUp is method to close a rondom pop that might block the automation.
     * @param driver
     */
    public static void closeRandomPopUp(WebDriver driver){
        String closeButtonRandomPopUpXPath = "//span[id='exit_popup_close']";
        try {
            // Scroll to the object
            WebElement result = driver.findElement(By.xpath(closeButtonRandomPopUpXPath));
            String js_code = "arguments[0].scrollIntoView(true);";
            ((JavascriptExecutor) driver).executeScript(js_code, result);
        }catch (NoSuchElementException e) {
            //Do Nothing
        }
    }

    public static void testScenario(WebDriver driver, String os){
        System.out.print("Step 1 .....");
        // Step 1. Navigate to https://www.lambdatest.com/.
        driver.get("https://www.lambdatest.com/");
        System.out.print("Done\n");
        System.out.print("Step 2 .....");
        // Step 2. Perform an explicit wait till the time all the elements in the DOM are available.
        // Accept cookies
        String allowCookieBtnXPath = "//span[text()='Allow Cookie']";
        WebElement allowCookieBtn = TestBase.waitUntilFind(driver, allowCookieBtnXPath);
        TestBase.closeRandomPopUp(driver);
        allowCookieBtn.click();
        System.out.print("Done\n");
        System.out.print("Step 3 .....");
        // Step 3. Scroll to the WebElement ‘SEE ALL INTEGRATIONS’ using the scrollIntoView() method. You are free to
        // use any of the available web locators (e.g., XPath, CssSelector, etc.)
        String linkSeeAllIntegrationsBtnXPath = "//a[text()='See All Integrations']";
        WebElement linkSeeAllIntegrationsBtn = TestBase.waitUntilFind(driver, linkSeeAllIntegrationsBtnXPath);
        TestBase.closeRandomPopUp(driver);
        System.out.print("Done\n");
        System.out.print("Step 4 ....");
        // Step 4. Click on the link and ensure that it opens in a new Tab.
        Actions actions = new Actions(driver);
        boolean checkingOS = "windows".compareTo(os)==0;
        if(checkingOS){
            linkSeeAllIntegrationsBtn.sendKeys(Keys.chord(Keys.CONTROL,Keys.RETURN));
        }
        else{
            linkSeeAllIntegrationsBtn.sendKeys(Keys.chord(Keys.COMMAND,Keys.RETURN));
        }

        System.out.print("Done\n");
        System.out.print("Step 5 ....");
        // Step 5. Save the window handles in a List (or array). Print the window handles of the opened windows
        // (now there are two windows open).
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        System.out.print("");
        driver.switchTo().window(tabs.get(1));
        System.out.print("Done\n");
        System.out.print("Step 6 ....");
        // Step 6. Verify whether the URL is the same as the expected URL (if not, throw an Assert).
        Assertions.assertEquals("https://www.lambdatest.com/integrations",driver.getCurrentUrl());
        System.out.print("Done\n");
        System.out.print("Step 7 ....");
        // Step 7. On that page, scroll to the page where the WebElement (Codeless Automation) is present.
        String h2CodelessAutomationXPath = "//h2[text()='Codeless Automation']";
        WebElement h2CodelessAutomation = TestBase.waitUntilFind(driver, h2CodelessAutomationXPath);
        TestBase.closeRandomPopUp(driver);
        Assertions.assertTrue(h2CodelessAutomation.isDisplayed());
        System.out.print("Done\n");
        System.out.print("Step 8 ....");
        // 8. Click the ‘LEARN MORE’ link for Testing Whiz. The page should open in the same window.
        String linkIntegrateTestingWhizLambdaTestXPath = "//a[text()='Integrate Testing Whiz with LambdaTest']";
        WebElement linkIntegrateTestingWhizLambdaTest = driver.findElement(By.xpath(linkIntegrateTestingWhizLambdaTestXPath));
        TestBase.closeRandomPopUp(driver);
        linkIntegrateTestingWhizLambdaTest.click();
        System.out.print("Done\n");
        System.out.print("Step 9 ....");
        // 9. Check if the title of the page is ‘Running Automation Tests Using TestingWhiz LambdaTest | LambdaTest’.
        // If not, raise an Assert.
        String expectedTitle = "Running Automation Tests Using TestingWhiz LambdaTest | LambdaTest";
        String currentTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, currentTitle);
        System.out.print("Done\n");
        System.out.print("Step 10 ....");
        // 10. Close the current window using the window handle [which we obtained in step (5)]
        driver.close();
        tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        System.out.print("Done\n");
        System.out.println("Step 11 ....");
        // 11. Print the current window count.
        System.out.println("current window count: "+tabs.size());
        System.out.print("Done\n");
        System.out.print("Step 12 ....");
        // 12. On the current window, set the URL to https://www.lambdatest.com/blog.
        driver.get("https://www.lambdatest.com/blog");
        System.out.print("Done\n");
        System.out.print("Step 13 ....");
        // 13. Click on the ‘Community’ link and verify whether the URL is https://community.lambdatest.com/.
        String linkCommunityXPath = "//a[text()='Community']";
        WebElement linkCommunity = driver.findElement(By.xpath(linkCommunityXPath));
        TestBase.closeRandomPopUp(driver);
        linkCommunity.click();
        Assertions.assertEquals("https://community.lambdatest.com/",driver.getCurrentUrl());
        System.out.print("Done\n");
        System.out.print("Step 14 ....");
        // 14. Close the current browser window.
        //driver.close();
        driver.quit();
        System.out.print("Done\n");
    }





}
