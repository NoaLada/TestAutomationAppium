package test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NavigatorTest {

	private static AndroidDriver driver;
	
	@BeforeClass
	public static void setUp() throws MalformedURLException, InterruptedException{
		
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/Navigator");
		File app = new File(appDir, "navigator.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); //Name of mobile web browser to automate. Should be an empty string if automating an app instead.
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appActivity", "com.atlantbh.navigator.HomeActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		Thread.sleep(4000);
		
	}
	
	@Test
	public void step1_enterLocation() throws Exception {
		WebElement textField = driver.findElement(By.id("com.atlantbh.navigator.debug:id/search_input_custom"));
		textField.sendKeys("atlantbh");
		textField.click();
		
		Thread.sleep(4000);
		
		TouchAction action = new TouchAction(driver);
		action.longPress(10,166).perform(); 
		
	}
	
	@Test
	public void step2_placeTitleShowed() throws Exception {
		WebElement placeTitle = driver.findElement(By.id("com.atlantbh.navigator.debug:id/title"));
		
		assertTrue("Place title is not displayed.", placeTitle.isDisplayed());	
		assertEquals("Place title is not proper.", "ATLANTBH", placeTitle.getText());
			
	}
	
	@Test
	public void step3_placeImageShowed() throws Exception {
		WebElement placeImage = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_image"));
		
		assertTrue("Place image is not displayed.", placeImage.isDisplayed());
		
	}
	
	@Test
	public void step4_imagesGallery() throws Exception {
		WebElement placeImage = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_image"));
		TouchAction action = new TouchAction(driver);
		
		action.longPress(placeImage).perform(); 
		Thread.sleep(2000);
		
		WebElement arrowRight = driver.findElement(By.id("com.atlantbh.navigator.debug:id/arrow_right"));
	    assertTrue("Arrow right is not displayed.", arrowRight.isDisplayed());	
		arrowRight.click();
		arrowRight.click();
		
		WebElement arrowLeft = driver.findElement(By.id("com.atlantbh.navigator.debug:id/arrow_left"));
		assertTrue("Arrow left is not displayed.", arrowLeft.isDisplayed());	
		arrowLeft.click();
		
		driver.navigate().back();
		
	}
	
	@Test
	public void step5_placeNameShowed() throws Exception {
		WebElement placeName = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_name"));
		
		assertTrue("Place name is not displayed.", placeName.isDisplayed());	
		assertEquals("Place name is not proper.", "Atlantbh", placeName.getText());
		
	}
	
	@Test
	public void step6_placeAddressContainerShowed() throws Exception {
		WebElement placeAddress = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_address"));
		WebElement placePhone = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_phone"));
		WebElement placeEmail = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_email"));
		
		assertTrue("Place address is not displayed.", placeAddress.isDisplayed());	
		assertTrue("Place phone is not displayed.", placePhone.isDisplayed());		
		assertTrue("Place e-mail is not displayed.", placeEmail.isDisplayed());

	}
	
	@Test
	public void step7_placeWorkingHoursShowed() throws Exception {
		WebElement placeHours = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_working_hours"));
		
		assertTrue("Place working hours are not displayed.", placeHours.isDisplayed());
		
	}
	
	@Test
	public void step8_placeDescriptionShowed() throws Exception {
		WebElement placeDescription = driver.findElement(By.id("com.atlantbh.navigator.debug:id/profile_description"));	
		
		assertTrue("Place description is not displayed.", placeDescription.isDisplayed());

	}
	
	
	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}
	
}