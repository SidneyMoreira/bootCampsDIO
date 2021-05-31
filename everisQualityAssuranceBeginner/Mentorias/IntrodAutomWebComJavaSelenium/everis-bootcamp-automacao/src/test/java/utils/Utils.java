package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {
	private WebDriver driver;
	// private WebDriverWait wait = new WebDriverWait(driver, 10);

	public Utils(WebDriver driver) {
		this.driver = driver;
	}

	public void preencheCampoPorId(String id_campo, String value) {

		driver.findElement(By.id(id_campo)).sendKeys(value);
	}

	public void preencheCampoPorCss(String id_campo, String value) {

		driver.findElement(By.cssSelector(id_campo)).sendKeys(value);
	}

	public void clickPorCss(String css_campo) {

		driver.findElement(By.cssSelector(css_campo)).click();
	}

	public void clickPorNome(String name_campo) {

		driver.findElement(By.name(name_campo)).click();
	}

	public void clickPorId(String id_campo) {

		driver.findElement(By.id(id_campo)).click();
	}

	/*
	 * public void waitPorId(String id_campo) {
	 * 
	 * wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id_campo))); }
	 * 
	 * public void waitPorCss(String css_campo) {
	 * 
	 * wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
	 * css_campo))); }
	 * 
	 * public void waitPorNome(String name_campo) {
	 * 
	 * wait.until(ExpectedConditions.presenceOfElementLocated(By.name(name_campo)));
	 * }
	 */
}
