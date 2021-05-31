package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Utils;

public class CadastrarEnderecoPage {
	private WebDriver driver;
	private Utils util;
	private RealizarLoginPage realizarLoginPage;

	public CadastrarEnderecoPage(WebDriver driver) {
		util = new Utils(driver);
		this.driver = driver;

		realizarLoginPage = new RealizarLoginPage(driver);
		driver.manage().window().maximize();
	}

	public void cadastrarEndereco() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		realizarLoginPage.RealizarLogin();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='Addresses'] span")));
		util.clickPorCss("#center_column > div > div:nth-child(1) > ul > li:nth-child(3) > a");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='Add an address'] span")));
		util.clickPorCss("#center_column > div.clearfix.main-page-indent > a");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("address1")));

		util.preencheCampoPorId("address1", "Street Sidney, 945");
		util.preencheCampoPorId("city", "Honolulu");
		util.preencheCampoPorId("id_state", "Hawaii");
		util.preencheCampoPorId("postcode", "96815");
		util.preencheCampoPorId("id_country", "United States");
		util.preencheCampoPorId("phone", "4444444");
		util.preencheCampoPorId("phone_mobile", "11111111");
		util.preencheCampoPorId("alias", " Work 2");
		util.clickPorId("submitAddress");
		
	}

}
