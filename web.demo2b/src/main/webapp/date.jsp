<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="javax.swing.text.DateFormatter" %>

<%
LocalDate now = LocalDate.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyy");
String currentDate = formatter.format(now);
%>

<%!
public int computeSum(int num1, int num2) {
	return num1 + num2;
}
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Current Date</title>
		<style type="text/css">
			body {
				background-color: gray;
				color:white;
				marigin: 50px;
			}
		</style>
	</head>
	<body>
		<h1>Java Server Page</h1>
		<hr />
		<b>Current Date: <%= currentDate %></b>
		<br />
		Sum: <%= computeSum(10,5) %>
		
		<br />
		<br />
		<%
		for (int index=0; index<100; index++) {
		%>
		Input <%= index +1 %> <input type="text" name="textbox<%=index+1 %>"><br />
		<%
		}
		%>
		<%@ include file="footer.jsp" %>
	</body>
</html>