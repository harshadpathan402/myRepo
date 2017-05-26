package com.hibernate;


import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeDao {

	private Session session;
	public void open() {
		session = HbUtil.getSessionFactory().openSession();
	}
	public void close() {
		if(session!=null)
			session.close();
	}
	
	public void insert(Employee employee) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(employee);
			//session.flush();
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		}
	}
	
	/*public Book find(int id) {
		Book b = (Book) session.get(Book.class, id);
		//Book b = (Book) session.load(Book.class, id);
		return b;
	}
	
	public void update(Book b) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(b);
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		}
	}
	public void delete(int id) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Book b = (Book) session.get(Book.class, id);
			session.delete(b);
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		}
	}
	
	public List<Book> getAll() {
		Criteria cr = session.createCriteria(Book.class);
		cr.addOrder(Order.desc("price"));
		return cr.list();
	}
	
	public List<Book> getBooksOfSubject(String subj) {	
		Criteria cr = session.createCriteria(Book.class);
		cr.add(Restrictions.eq("subject", subj));
		return cr.list();
	}
	

	public List<Book> getBooksOfSubjectAuthor(String subj, String auth) {
		Criteria cr = session.createCriteria(Book.class);
		cr.add(Restrictions.eq("subject", subj));
		cr.add(Restrictions.eq("author", auth));
		return cr.list();
	}*/
	
}
