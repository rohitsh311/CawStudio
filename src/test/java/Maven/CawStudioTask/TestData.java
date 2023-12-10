package Maven.CawStudioTask;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestData {

	public static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		Thread.sleep(2000);
		
		WebElement tabledat_elem=driver.findElement(By.xpath("//*[text()=\"Table Data\"]"));
		tabledat_elem.click();
		Thread.sleep(2000);
		
		WebElement json_text = driver.findElement(By.id("jsondata"));
		json_text.clear();
		Thread.sleep(2000);
		
		String json_data = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\":\r\n" + 
				"\"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\":\r\n" + 
				"\"Jennifer\", \"age\" : 42, \"gender\": \"female\"}] ";
		json_text.sendKeys(json_data);
		Thread.sleep(2000);
		
		WebElement refreshtable = driver.findElement(By.id("refreshtable"));
		refreshtable.click();
		
		JSONArray obj = readJson();
		System.out.println("obj: " + obj);
		fetch_table_data(obj);
		
		Thread.sleep(2000);
		driver.close();
		
		
	}
	 
	private static void fetch_table_data(JSONArray jsonarray) {
		List<WebElement> allnames = driver.findElements(By.xpath("//table/tr/td[1]"));
		List<WebElement> allage = driver.findElements(By.xpath("//table/tr/td[2]"));
		List<WebElement> allgender = driver.findElements(By.xpath("//table/tr/td[3]"));
		for(int i=0;i<jsonarray.length();i++) {
			
			JSONObject person =(JSONObject)jsonarray.get(i);
			
			String name_value2 =(String)person.get("name");
			String name_value1 = allage.get(i).getText();
			assertEquals(name_value1,name_value2);
			
			String age_value2 =(String)person.get("age");
			String age_value1 = allgender.get(i).getText();
			assertEquals(age_value1,age_value2);
			
			String gender_value2 =(String)person.get("gender");
			String gender_value1 = allnames.get(i).getText();
			assertEquals(gender_value1,gender_value2);
			
			
		}
			
			
		}
	
	
	private static void assertEquals(String name_value1, String name_value2) {
		// TODO Auto-generated method stub
		
	}

	public static JSONArray readJson() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray a =(JSONArray)parser.parse(new FileReader("F:\\Rohit\\Eclipse workspace\\CAW_Studios_Project\\src\\test\\reosurces\\Data.json"));
		String jsonstr = a.toString();
		System.out.println(jsonstr);
		return a;
		
	}
}
