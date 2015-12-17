package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NavigatorTest {

	private static IOSDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, 40); // time limit for waiting - 40 s
	
	public static ArrayList<String> getConfig(String propertiesFile) {
		ArrayList<String> configs = new ArrayList<String>();
		Properties prop = new Properties();
		InputStream inputStream = NavigatorTest.class.getClassLoader().getResourceAsStream(propertiesFile);
		
		try {
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propertiesFile + "' not found in the classpath!");
			}
			
			configs.add(prop.getProperty("platformName"));
			configs.add(prop.getProperty("deviceName"));
			configs.add(prop.getProperty("name"));
			configs.add(prop.getProperty("bundleID"));
			configs.add(prop.getProperty("autoAcceptAlerts"));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		return configs;
	}

	@BeforeClass
	public static void setUp() throws MalformedURLException, InterruptedException {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/Navigator");
		File app = new File(appDir, "Navigator.ba.app");
		
		ArrayList<String> configs = getConfig("config.properties");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); 
		capabilities.setCapability("platformName", configs.get(0));
		capabilities.setCapability("deviceName", configs.get(1));
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("bundleID", configs.get(3));
		capabilities.setCapability("name", configs.get(2));
		capabilities.setCapability("autoAcceptAlerts", configs.get(4));

		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test
	public void step1_enterLocation() throws Exception {
		Thread.sleep(3000); //wait for alert to close
		WebElement textField = wait.until(ExpectedConditions.elementToBeClickable(By.className("UIASearchBar")));
		textField.click();
		textField.sendKeys("atlantbh");
		
		WebElement atlantTable = wait.until(ExpectedConditions.elementToBeClickable(By.className("UIATableCell")));
		atlantTable.click();
	}

	@Test
	public void step2_placeTitleShowed() throws Exception {
		WebElement placeTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//UIAStaticText[1]")));
		
		System.out.println(placeTitle.getLocation());
		System.out.println(placeTitle.getSize());
		
		assertTrue("Place title is not displayed.", placeTitle.isDisplayed());	
		assertEquals("Place title is not proper.", "ATLANTBH", placeTitle.getText());
	}

	@Test 
	public void step3_imagesGallery() throws Exception {
		TouchAction action = new TouchAction(driver);
		action.longPress(110,150).perform();
			
		WebElement doneButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("UIAButton")));
		assertTrue("Done button is not displayed.", doneButton.isDisplayed());
		
		doneButton.click();
	}

	@Test
	public void step4_placeNameShowed() throws Exception {
		WebElement placeName = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Atlantbh")));

		assertTrue("Place name is not displayed.", placeName.isDisplayed());	
		assertEquals("Place name is not proper.", "Atlantbh", placeName.getText());
	}

	@Test
	public void step5_placeAddressContainerShowed() throws Exception {
		WebElement placeAddress = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Milana Preloga 12A, Sarajevo")));
		WebElement placePhone = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("033 716 550")));
		WebElement placeEmail = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("contact@atlantbh.com")));

		assertTrue("Place address is not displayed.", placeAddress.isDisplayed());	
		assertTrue("Place phone is not displayed.", placePhone.isDisplayed());		
		assertTrue("Place e-mail is not displayed.", placeEmail.isDisplayed());
	}

	@Test
	public void step6_placeWorkingHoursShowed() throws Exception {
		WebElement placeHours = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("09:00-17:00h")));

		assertTrue("Place working hours are not displayed.", placeHours.isDisplayed());
	}

	@Test
	public void step7_placeDescriptionShowed() throws Exception {
		WebElement placeDescription = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Opis objekta")));	

		assertTrue("Place description is not displayed.", placeDescription.isDisplayed());
	}


	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}

}