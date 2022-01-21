<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Student Tracker Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>RGPV University</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<input type="button" value="Add Student" 
			onclick="window.location.href='add-student-form.jsp';return false;" class="add-student-button" />
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<br/>
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					<tr>
						<c:url var="tempLink" value="StudentControllerServlet">
							<c:param name="command" value="LOAD"></c:param>
							<c:param name="studentId" value="${tempStudent.id}"></c:param>
						</c:url>
						<c:url var="tempDelete" value="StudentControllerServlet">
							<c:param name="command" value="DELETE"></c:param>
							<c:param name="studentId" value="${tempStudent.id}"></c:param>
						</c:url>
						<td>${tempStudent.firstName }</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td><a href="${tempLink}">Update</a>
							|
							<a href="${tempDelete}" onclick="if (!(confirm('Are you sure you want to delete?'))) return false">Delete</a>
					</tr>
				</c:forEach>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>