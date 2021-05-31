package tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import pages.CadastrarEnderecoPage;

public class CadastrarEnderecoPageTest {
	private ATUTestRecorder gravar;
	private WebDriver driver;
	private CadastrarEnderecoPage cadastrarEnderecoPage;

	/*
	 * @Before public void setUp() throws ATUTestRecorderException{
	 * 
	 * }
	 */
	@Before
	public void inicializa() throws ATUTestRecorderException{
		
		//gravar
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		gravar = new ATUTestRecorder("src/test/", "Desafio_Everis"+dateFormat.format(date),false);
		gravar.start();
		
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		cadastrarEnderecoPage = new CadastrarEnderecoPage(driver);

	}

	@Test
	public void CadastrarEnderecoPage() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		cadastrarEnderecoPage.cadastrarEndereco();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='Add an address'] span")));
		WebElement validaTexto = driver.findElement(By.cssSelector("a[title='Add an address'] span"));
		Assert.assertEquals("Add a new address", validaTexto.getText());
	}

	@After
	public void afterCenario() throws ATUTestRecorderException{
		driver.quit();
		gravar.stop();
	}
}
