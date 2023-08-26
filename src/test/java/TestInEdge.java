import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class TestInEdge {
    @Test
    void testRunWithEdge() {
        WebDriver driver = TestBase.remoteLambdaWebDriver("edge", "michaelmunalively", "erAS4nNUyBzkzKVIwrBemzXXf9SdN5VElMLEfLhHWsY1SggULU");
        TestBase.testScenario(driver, "macos");
    }
}
