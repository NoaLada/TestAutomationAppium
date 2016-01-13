package org.atlantbh.test;

import java.net.MalformedURLException;
import java.net.URL;

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

import java.util.ArrayList;
import java.util.Properties;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ProjectCinemaTest {

	private static IOSDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, 40); // time limit for waiting - 40 s
	
	public static ArrayList<String> getConfig(String propertiesFile) {
		ArrayList<String> configs = new ArrayList<String>();
		Properties prop = new Properties();
		InputStream inputStream = ProjectCinemaTest.class.getClassLoader().getResourceAsStream(propertiesFile);
		
		try {
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertiesFile + "' not found in the classpath");
			}
			
			configs.add(prop.getProperty("platformName"));
			configs.add(prop.getProperty("deviceName"));
			configs.add(prop.getProperty("name"));
			configs.add(prop.getProperty("autoAcceptAlerts"));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		return configs;
	}

	@BeforeClass
	public static void setUp() throws MalformedURLException, InterruptedException {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/ProjectCinema");
		File app = new File(appDir, "Project Cinema.app");
		
		ArrayList<String> configs = getConfig("config.properties");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); 
		capabilities.setCapability("platformName", configs.get(0));
		capabilities.setCapability("deviceName", configs.get(1));
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("name", configs.get(2));
		capabilities.setCapability("autoAcceptAlerts", configs.get(3));

		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test
	public void step1_searchMovie() throws Exception {
		Thread.sleep(3000); // wait for alert to close
		WebElement searchField = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIASearchBar[1]"));
		searchField.click();
		
		searchField.sendKeys("Minions");
		
		Thread.sleep(3000);
		
		TouchAction action = new TouchAction(driver);
		action.longPress(50,90).perform();
	}

	@Test
	public void step2_movieTitleShowed() throws Exception {
		WebElement movieTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//UIAStaticText[1]")));
		
		assertTrue("Movie title is not displayed.", movieTitle.isDisplayed());	
		assertEquals("Movie title is not proper.", "Minions", movieTitle.getText());
	}

	@Test
	public void step3_movieNameShowed() throws Exception {
		WebElement movieName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//UIAStaticText[2]")));

		assertTrue("Movie name is not displayed.", movieName.isDisplayed());
		assertEquals("Movie name is not proper.", "Minions (2015)", movieName.getText());
	}

	@Test
	public void step4_timeLengthShowed() throws Exception {
		WebElement timeMovie = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//UIAStaticText[3]")));

		assertTrue("Time lenght of movie is not displayed.", timeMovie.isDisplayed());	
	}

	@Test
	public void step5_heartButton() throws Exception {
		WebElement heartButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("FavoritesOutlineBarIcon")));
		assertTrue("Heart button is not displayed.", heartButton.isDisplayed());
		
		heartButton.click();
		heartButton.click();
	}

	@Test
	public void step6_movieDescriptionShowed() throws Exception {
		WebElement movieDescription = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//UIAStaticText[4]")));	

		assertTrue("Movie description is not displayed.", movieDescription.isDisplayed());
	}
	
	@Test
	public void step7_videoSection() throws Exception {
		WebElement videoButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("Videos")));
		assertTrue("Video button is not displayed.", videoButton.isDisplayed());
		
		videoButton.click();
		
		WebElement backButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Back")));
		assertTrue("Back button is not displayed.", backButton.isDisplayed());
		
		backButton.click();
	}
	
	@Test
	public void step8_movieCastShowed() throws Exception {
		WebElement movieCast = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Cast")));	

		assertTrue("Movie cast is not displayed.", movieCast.isDisplayed());
	}
	
	@Test
	public void step9_movieReviewShowed() throws Exception {
		WebElement movieReview = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("UIACollectionView")));	

		assertTrue("Movie review is not displayed.", movieReview.isDisplayed());
	}


	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}
}