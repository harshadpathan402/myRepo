package com.hibernate;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	EmployeeDao dao = new EmployeeDao();
    	try {
    		System.out.println("Hello");
    		dao.open();
			Employee employee = new Employee();
			employee.setFirstName("harshad");
			employee.setLastName("Pathan");
			dao.insert(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
