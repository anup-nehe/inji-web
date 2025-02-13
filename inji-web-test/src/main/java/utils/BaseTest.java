package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    private static WebDriver driver;
    private static JavascriptExecutor jse;
    private static final String url = "https://injiweb.qa-inji1.mosip.net/";

    private final String accessKey = getKeyValueFromYaml("/browserstack.yml", "accessKey");
    private final String userName = getKeyValueFromYaml("/browserstack.yml", "userName");
    private final String BROWSERSTACK_URL = "https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

    public BaseTest() {
        if (driver == null) {
            try {
                initializeDriver();
            } catch (MalformedURLException e) {
                throw new RuntimeException("Failed to initialize WebDriver", e);
            }
        }
    }

    private void initializeDriver() throws MalformedURLException {
        System.out.println("Initializing WebDriver...");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");

        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", "10");
        capabilities.setCapability("bstack:options", browserstackOptions);

        driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), capabilities);
        jse = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.get(url);
        System.out.println("WebDriver Initialized Successfully!");
    }

    @Before
    public void beforeAll() throws MalformedURLException {
        if (driver == null) {
            initializeDriver();
        }
    }

    @After
    public void afterAll() {
        if (driver != null) {
            System.out.println("Closing WebDriver...");
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public JavascriptExecutor getJse() {
        return jse;
    }

    public static String getKeyValueFromYaml(String filePath, String key) {
        try (FileReader reader = new FileReader(System.getProperty("user.dir") + filePath)) {
            Yaml yaml = new Yaml();
            Object data = yaml.load(reader);

            if (data instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> map = (Map<String, String>) data;
                return map.getOrDefault(key, "");
            } else {
                throw new RuntimeException("Invalid YAML format, expected a map");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("YAML file not found: " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading YAML file: " + filePath, e);
        }
    }
}
