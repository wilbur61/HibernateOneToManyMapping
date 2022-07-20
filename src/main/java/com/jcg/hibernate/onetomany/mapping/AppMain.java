package com.jcg.hibernate.onetomany.mapping;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.List;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;



import java.util.List;


public class AppMain {

	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		
	    SessionFactory sessionFactoryObj = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();  	
		
		return sessionFactoryObj;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println(".......Hibernate One To Many Mapping Example.......\n");
		try {
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();

			Student studentObj = new Student("Java", "Geek",  "javageek@javacodegeeks.com", "0123456789");
			sessionObj.save(studentObj);
			

			MarksDetails marksObj1 = new MarksDetails("English", "100", "90",  "Pass");  
			marksObj1.setStudent(studentObj);  
			sessionObj.save(marksObj1);

			MarksDetails marksObj2 = new MarksDetails("Maths", "100", "99",  "Pass");  
			marksObj2.setStudent(studentObj);
			sessionObj.save(marksObj2);

			MarksDetails marksObj3 = new MarksDetails("Science", "100", "94",  "Pass");  
			marksObj3.setStudent(studentObj);
			sessionObj.save(marksObj3);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();

			System.out.println("\n.......Records Saved Successfully To The Database.......");
		    
			//========================
			String sql = "SELECT * FROM marks_details ";
			// Native query selecting all columns
		    List<Object[]> details = sessionObj.createNativeQuery("SELECT * FROM marks_details").list();
		         for (Object[] objects : details) {
		            Integer student_id=(Integer)objects[0];
		            Integer test_id=(Integer)objects[1];
		            String subject=(String)objects[2];
		            System.out.println("student_id="+student_id+" test_id="+test_id+" subject="+subject);
		         }	
			
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
	
}