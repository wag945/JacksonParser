package org.afms;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.FileWriter; 

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
	Path relative = Paths.get("Employees.txt");
	System.out.println("Relative path: " + relative);
	Path absolute = relative.toAbsolutePath();
	System.out.println("Absolute path: " + absolute);

	long startTime1 = System.currentTimeMillis();

	GsonBuilder builder = new GsonBuilder(); 

	Gson gson = builder.create(); 

	BufferedReader bufferedReader = new BufferedReader(new FileReader("Employees.txt"));   
      
	for (int i = 0; i < 1000; i++) {
	    // parse json string to object
	    Employee emp = gson.fromJson(bufferedReader, Employee.class); 
	}

	long endTime1 = System.currentTimeMillis();

	System.out.println("Gson took " + (endTime1 - startTime1) + " milliseconds");

	long startTime2 = System.currentTimeMillis();

	byte[] jsonData = Files.readAllBytes(Paths.get("Employees.txt"));

	ObjectMapper objectMapper = new ObjectMapper();

	for (int i = 0; i < 1000; i++) {
	    Employee emp = objectMapper.readValue(jsonData, Employee.class);
	}
	
	long endTime2 = System.currentTimeMillis();

	System.out.println("Jackson took " + (endTime2 - startTime2) + " milliseconds");
    }

    public static Employee createEmployee() {
	Employee emp = new Employee();
	emp.setId(100);
	emp.setName("David");
	emp.setPermanent(false);
	emp.setPhoneNumbers(new long[] {123456, 987654});
	emp.setRole("Manager");

	Address add = new Address();
	add.setCity("Bangalore");
	add.setStreet("BTM !st Stage");
	add.setZipcode(560100);
	emp.setAddress(add);

	List<String> cities = new ArrayList<String>();
	cities.add("Los Angeles");
	cities.add("New York");
	emp.setCities(cities);

	Map<String,String> props = new HashMap<String,String>();
	props.put("salary","1000 Rs");
	props.put("age", "28 years");
	emp.setProperties(props);

	return emp;
    }
}
