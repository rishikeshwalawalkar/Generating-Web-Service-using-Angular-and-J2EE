package com.marlabs.classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class CreateJSON {

	public void createJSON() {
		Session session = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		StringBuilder JsonObjs = new StringBuilder();
		String hql = "FROM Book";
		Query query = session.createQuery(hql);
		ObjectMapper mapper = new ObjectMapper();
		List<Book> results = query.list();

		File file = new File("WebContent/test.json");

		for (Book b : results) {
			JSONObject obj = new JSONObject();
			obj.put("id", b.getId());
			obj.put("title", b.getTitle());
			obj.put("author", b.getAuthor());
			obj.put("price", b.getPrice());
			System.out.println(obj.toString());
			try {
				mapper.writeValue(file, obj);
				System.out.println("Created!");
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
