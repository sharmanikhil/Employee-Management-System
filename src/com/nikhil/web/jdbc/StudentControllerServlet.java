package com.nikhil.web.jdbc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDBUtil studentDBUtil;
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			if (theCommand == null) {
				theCommand = "LIST";
			}
			switch (theCommand) {
			case "LIST":
				listStudent(request, response);
				break;
			case "ADD":
				addStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			default:
				listStudent(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("studentId"));
			studentDBUtil.deleteStudent(id);
			listStudent(request, response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student theStudent = new Student(id, firstName, lastName, email);
		studentDBUtil.updateStudent(theStudent);
		listStudent(request, response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getParameter("studentId");
		Student student = studentDBUtil.getStudent(studentId);
		request.setAttribute("THE_STUDENT", student);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstName = request.getParameter("firstName"), lastName = request.getParameter("lastName"),
				email = request.getParameter("email");
		Student theStudent = new Student(firstName, lastName, email);
		studentDBUtil.addStudent(theStudent);
		listStudent(request, response);
	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = studentDBUtil.getStudents();
		request.setAttribute("STUDENT_LIST", students);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		dispatcher.forward(request, response);
	}

}
