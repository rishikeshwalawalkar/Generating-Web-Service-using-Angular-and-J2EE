package com.marlabs.classes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class OperationServlet
 */
@WebServlet("/OperationServlet")
public class OperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OperationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Session session = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		String action = request.getParameter("action");

		if (action.equals("add")) {
			Book newBook = new Book(request.getParameter("bookTitle"), request.getParameter("bookAuthor"),
					Double.parseDouble(request.getParameter("bookPrice")));
			session.save(newBook);
			System.out.println(newBook.toString());
			response.sendRedirect("index.html");
		} else if (action.equals("delete")) {
			Query query = session.createQuery("delete Book where id = :bookId");
			query.setParameter("bookId", Integer.parseInt(request.getParameter("deleteId")));

			int result = query.executeUpdate();

			if (result > 0) {
				System.out.println("Expensive products was removed");
			}

		} else if (action.equals("update")) {
			String hql = "UPDATE Book set price = :bookPrice, author = :bookAuthor, title = :bookTitle WHERE id = :bookId";
			Query query = session.createQuery(hql);
			query.setParameter("bookPrice", Double.parseDouble(request.getParameter("newPrice")));
			query.setParameter("bookId", request.getParameter("bookId"));
			query.setParameter("bookAuthor", request.getParameter("bookAuthor"));
			query.setParameter("bookTitle", request.getParameter("bookTitle"));
			int result = query.executeUpdate();

		} else if (action.equals("show")) {
			JSONArray JsonObjs = new JSONArray();
			String hql = "FROM Book";
			Query query = session.createQuery(hql);
			List<Book> results = query.list();
			for (Book b : results) {
				JSONObject obj = new JSONObject();
				obj.put("id", b.getId());
				obj.put("title", b.getTitle());
				obj.put("author", b.getAuthor());
				obj.put("price", b.getPrice());
				System.out.println(obj.toJSONString());
				
				JsonObjs.add(obj);
			}
		response.getWriter().write(JsonObjs.toString());
		}
		t.commit();
		session.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
