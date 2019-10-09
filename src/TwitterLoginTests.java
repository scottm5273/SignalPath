
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

public class TwitterLoginTests {
	//CSS selectors
	private String usernameCss = "#page-container > div > div.signin-wrapper > form > fieldset > div:nth-child(2) > input";
	private String passwordCss = "#page-container > div > div.signin-wrapper > form > fieldset > div:nth-child(3) > input";
	private String loginButtonCss = "#page-container > div > div.signin-wrapper > form > div.clearfix > button";
	private String homeTextCss = "#react-root > div > div > div > main > div > div > div > div > div > div.css-1dbjc4n.r-aqfbo4.r-14lw9ot.r-my5ep6.r-rull8r.r-qklmqi.r-gtdqiz.r-ipm5af.r-1g40b8q > div.css-1dbjc4n.r-1loqt21.r-136ojw6 > div > div > div > div > div.css-1dbjc4n.r-16y2uox.r-1wbh5a2.r-1pi2tsx.r-1777fci > div > h2 > span";
	private String errorMessageCss = "#message-drawer";
	
	private WebDriver driver;
	private String baseUrl;
	
	
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		baseUrl = "https://twitter.com/login";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void validUserAndPassword() {
		driver.get(baseUrl);
		login("sootedpair", "r3dsk1ns"); //replace w/ your Twitter uname and pwd
		assertEquals(driver.findElement(By.cssSelector(homeTextCss)).getText(), "Home");
	}
	
	@Test
	public void noUserAndNoPassword() {
		driver.get(baseUrl);
		login("", "");
		assertTrue(driver.getCurrentUrl().contains("login/error"));
		assertTrue(isErrorDisplayed());
	}

	@Test
	public void invalidUserAndPassword() {
		driver.get(baseUrl);
		login("aizvigly", "aizvigly");
		assertTrue(driver.getCurrentUrl().contains("login/error"));
		assertTrue(isErrorDisplayed());
	}
	
	@Test
	public void validUserAndInvalidPassword() {
		driver.get(baseUrl);
		login("sootedpair", "correct horse battery staple");
		assertTrue(driver.getCurrentUrl().contains("login/error"));
		assertTrue(isErrorDisplayed());
	}
	
	@Test
	public void validUserAndNoPassword() {
		driver.get(baseUrl);
		login("sootedpair", "");
		assertTrue(driver.getCurrentUrl().contains("login/error"));
		assertTrue(isErrorDisplayed());
	}

	private void login(String username, String password) {
		driver.findElement(By.cssSelector(usernameCss)).sendKeys(username);
		driver.findElement(By.cssSelector(passwordCss)).sendKeys(password);
		driver.findElement(By.cssSelector(loginButtonCss)).click();
	}
	
	private boolean isErrorDisplayed() {
		return driver.findElement(By.cssSelector(errorMessageCss)).isDisplayed();
	}
	
	@After
	public void tearDown() 
	{
		driver.close();
	}
}
