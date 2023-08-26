import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class TestInChrome {
    @Test
    void testRunWithChrome() {
        WebDriver driver = TestBase.remoteLambdaWebDriver("chrome", "michaelmunalively", "erAS4nNUyBzkzKVIwrBemzXXf9SdN5VElMLEfLhHWsY1SggULU");
        TestBase.testScenario(driver, "windows");
    }
}
