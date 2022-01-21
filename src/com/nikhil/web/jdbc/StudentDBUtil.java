package com.nikhil.web.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	// try{
	private DataSource dataSource;

	public StudentDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		Connection myConnection = null;
		Statement mtst = null;
		ResultSet myrs = null;
		try {
			myConnection = dataSource.getConnection();
			String query = "select * from student order by last_name";
			mtst = myConnection.createStatement();
			myrs = mtst.executeQuery(query);
			while (myrs.next()) {
				students.add(new Student(myrs.getInt("id"), myrs.getString("first_name"), myrs.getString("last_name"),
						myrs.getString("email")));
			}
		} finally {
			if (myrs != null) {
				myrs.close();
			}
			if (mtst != null) {
				mtst.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}
		return students;
	}

	public void addStudent(Student theStudent) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			String sql = "insert into student (first_name,last_name,email) values(?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.execute();
		} finally {
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
	}

	public Student getStudent(String studentId) throws SQLException {
		Student student = null;
		Connection myConn = null;
		PreparedStatement mystmt = null;
		ResultSet myrs = null;
		int thestudentId;
		try {
			thestudentId = Integer.parseInt(studentId);
			myConn = dataSource.getConnection();
			String sql = "select * from student where id=?";
			mystmt = myConn.prepareStatement(sql);
			mystmt.setInt(1, thestudentId);
			myrs = mystmt.executeQuery();
			if (myrs.next()) {
				String firstName = myrs.getString("first_name");
				String lastName = myrs.getString("last_name");
				String email = myrs.getString("email");
				student = new Student(thestudentId, firstName, lastName, email);
			} else {
				throw new Exception("Could not find student id" + studentId);
			}
			return student;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (mystmt != null) {
				mystmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
		return null;
	}

	void updateStudent(Student theStudent) throws SQLException {
		Connection co = null;
		PreparedStatement prst = null;
		try {

			// ResultSet rs=null;
			co = dataSource.getConnection();
			String sql = "update student set first_name=?,last_name=?,email=? where id=?";
			prst = co.prepareStatement(sql);
			prst.setString(1, theStudent.getFirstName());
			prst.setString(2, theStudent.getLastName());
			prst.setString(3, theStudent.getEmail());
			prst.setInt(4, theStudent.getId());
			prst.execute();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (co != null) {
				co.close();
			}
			if (prst != null) {
				prst.close();
			}
		}
	}

	void deleteStudent(int id) throws SQLException {
		Connection co = null;
		PreparedStatement prst = null;
		try {
			co = dataSource.getConnection();
			String sql = "delete from student where id=?";
			prst = co.prepareStatement(sql);
			prst.setInt(1, id);
			prst.execute();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (co != null) {
				co.close();
			}
			try {
				if (prst != null) {
					prst.close();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}