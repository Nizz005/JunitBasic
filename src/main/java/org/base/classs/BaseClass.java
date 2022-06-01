package org.base.classs;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Desktop.Action;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static WebDriver driver;// must import line
	
	public static WebDriver launchBrowser(String name) { //to lunch the browser
		
		switch(name) {
		
		case "chrome":
			
			WebDriverManager.chromedriver().setup();			
			driver = new ChromeDriver();
			break;
			
		case "firefox":
			
			WebDriverManager.firefoxdriver().setup();			
			driver = new FirefoxDriver();
			break;
		
		case "edge":
			WebDriverManager.edgedriver().setup();			
			driver = new EdgeDriver();			
			break;
		
		default:
			System.err.println("Invalid browser");
			
			throw new WebDriverException();	
		}
		
		return driver;
	}
	
	public static void launchUrl(String url) {
		driver.get(url);	
		driver.manage().window().maximize();
	}

	public static void implicitWait(long sec) {
		
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}
	
	public static void explicitWait(long sec) {
		
		WebDriverWait w = new WebDriverWait(driver, sec);		
	}
	
	
	public static WebElement findElement(String loctor, String locvalue) {
		
		WebElement e = null;
		
		if (loctor.equalsIgnoreCase("id")) {		
			e = driver.findElement(By.id(locvalue));			
		}
		
		if (loctor.equalsIgnoreCase("name")) {			
			e = driver.findElement(By.name(locvalue));
		}
		
		if (loctor.equalsIgnoreCase("className")) {
			e = driver.findElement(By.className(locvalue));				
		}
		if (loctor.equalsIgnoreCase("xpath")) {
			e = driver.findElement(By.xpath(locvalue));		
		}
		return e;
	}	
	
	public static void sendkeys(WebElement e, String value) {
		e.sendKeys(value);
	}
	
	public static void sendkeysEnter(WebElement e, String value) {
		e.sendKeys(value, Keys.ENTER);
	}
	
	public static void btnClick(WebElement e) {
		e.click();
	}
	
	public static void clearTxt(WebElement e) {
		e.clear();
	}
	
	public static void quitBrowser() {
		driver.quit();
	}
	
	public static void closeBrowser() {
		driver.close();
	}
	
	public static void alertAccept() {
	  Alert al = driver.switchTo().alert();	  
	  al.accept();
	}
	
	public static void alertDismiss() {
		Alert al = driver.switchTo().alert();
		al.dismiss();
	}
	
	public static void selectByIndex(WebElement e, int value) {
		Select s = new Select(e);
		s.selectByIndex(value);
	}
	
	public static void selectByVisibleText(WebElement e, String value) {		
		Select s = new Select(e);
		s.selectByVisibleText(value);
	}
	
	public static void selectByValue(WebElement e, String value) {
		Select s = new Select(e);
		s.selectByValue(value);
	}
	
	public static void deselectByIndex(WebElement e, int value) {
		Select s = new Select(e);
		s.deselectByIndex(value);
	}
	
	public static void deselectAll(WebElement e) {
		Select s = new Select(e);
		s.deselectAll();
	}
	
	public static String getFirstSelectedOption(WebElement e) { //doubt asking to change the as string
		Select s = new Select(e);
		WebElement selectOp = s.getFirstSelectedOption();
		return selectOp.getText();		
	}
	
	public static List<String> getAllSelectedOptions(WebElement e, String text) { //doubt asking to change the as string (important)
		Select s = new Select(e);
		List<WebElement> allSelectedOpp = s.getAllSelectedOptions();
		int size = allSelectedOpp.size();		
		List<String> st=new ArrayList<>();
		for (int i = 0; i < allSelectedOpp.size(); i++) {
			String text1 = allSelectedOpp.get(i).getText();	
			st.add(text1);
		}
		return st;
	}
	
	public static void frameByIndex(int value) {
		driver.switchTo().frame(value);
	}
	
	public static void frameByXpath(WebElement e) { // for return WebElement is mandatory	
		driver.switchTo().frame(e);
	}
	
	public static void getOutFromFrame() {
		driver.switchTo().defaultContent();
	}
	
	public static void jsClick(WebElement e) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", e);
	}
	
	public static void jsScrollIntoViewDown(WebElement e) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeAsyncScript("arguments[0].scrollIntoView(true)", e);
	}
	
	public static void jsScrollIntoViewUp(WebElement e) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeAsyncScript("arguments[0].scrollIntoView(false)", e);
	}
	
	public static void jsSetAttribute(WebElement e, String val) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeAsyncScript("arguments[0].setAttribute('value', 'val')", e);
	}
	
	public static void navigateBack() {
		driver.navigate().back();
	}
	
	public static void navigateForward() {
		driver.navigate().forward();
	}
	
	public static void navigateRefresh() {
		driver.navigate().refresh();
	}
	
	public static void navigateToUrl(String url) {
		driver.navigate().to(url);
	}
	
	public static void swithcToWindow(int value) {
		Set<String> allID = driver.getWindowHandles();
		int count= 1;	
		for (String eachid : allID) {
			
			if (count == value) {
			driver.switchTo().window(eachid);				
			}			
			count++;
		}		
	}
	
	public static void fileOneNew(String path) {
		File f = new File(path);
		boolean mkdir = f.mkdir();	
	}
	
	public static void fileMultiNew(String path) {
		File f = new File(path);
		boolean mkdirs = f.mkdirs();
		
	}
	
	public static void createNewFile(String path) throws IOException {
		File f = new File(path);
		boolean createNewFile = f.createNewFile();
	}
	
	public static boolean fileCanRead(String path) {
		File f = new File(path);
		boolean read = f.canRead();	
		return read;
	}

	public static boolean fileCanWrite(String path) {
		File f = new File(path);
		boolean write = f.canWrite();	
		return write;
	}
	
	public static void fileWrite(String path, String txt) throws IOException {
		File wr = new File(path);
		FileUtils.write(wr, txt, true);
	}
	
	public static void fileCopy(String path, String path1) throws IOException {
		File from = new File(path);
		File to = new File(path1);
		FileUtils.copyFile(from, to);
	}
	
	public static List<String> fileRead(String path) throws IOException { // doubt
		File f = new File(path);
		List<String> read = FileUtils.readLines(f);		
		for (String x : read) {
			System.out.println(x);
		}
		return read;
	}
	
	public static File takeScreenshot(String path) throws IOException {
		TakesScreenshot tk = (TakesScreenshot)driver;		
		File from = tk.getScreenshotAs(OutputType.FILE);
		File to = new File(path);
		FileUtils.copyFile(from, to);
		return to;		
	}
	
	public static String getCurrentURL() {
		String url = driver.getCurrentUrl();
		return url;
	}
	
	public static String getAttribute(WebElement e) {
		return e.getAttribute("value");
	}
	
	public static String getAttributeInnerValue(WebElement e) {
		return e.getAttribute("innerText");
	}
	
	public static String getTitle() {
		return driver.getTitle();
	}
	
	public static void moveToElement(WebElement target) {
		Actions a = new Actions(driver);
		a.moveToElement(target).perform();
	}
	
	public static void dragAndDrop(WebElement from, WebElement to) {
		Actions a = new Actions(driver);
		a.dragAndDrop(from, to);
	}
	
	public static void doubleClick(WebElement target) {
		Actions a = new Actions(driver);
		a.doubleClick(target).perform();
	}
	
	public static void robotClassCopy() throws AWTException {
		Robot r = new Robot();		
		r.keyPress(KeyEvent.VK_CONTROL);		
		r.keyPress(KeyEvent.VK_C);
		r.keyRelease(KeyEvent.VK_CONTROL);		
		r.keyRelease(KeyEvent.VK_C);
	}
	
	public static void robotClassPaste() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);	
	}
	
	public static String excelData(String path,String sheetname, int rowno, int cellno) throws IOException {
		File loc = new File(System.getProperty("user.dir")+ path);
		FileInputStream st = new FileInputStream(loc);
		Workbook W = new XSSFWorkbook(st);
		Sheet sheet = W.getSheet(sheetname);
		Row row = sheet.getRow(rowno);
		Cell cell = row.getCell(cellno);
		
		int type = cell.getCellType();
		String value=null;
		if (type==1) {
			value = cell.getStringCellValue();	
		}
		
		else {
			if (DateUtil.isCellDateFormatted(cell)) {
				value = new SimpleDateFormat("dd-MMM-yyyy").format(cell.getDateCellValue());				
			}
			
			else {
				value = String.valueOf((long)cell.getNumericCellValue());
			}
		}
		return value;
	}
	
}
		 
		
		
		
		
	