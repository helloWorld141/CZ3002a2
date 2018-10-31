<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="UTF-8">
<title>Basic Struts 2 Application - Welcome</title>
</head>
<body>
	<h2>Welcome to Strut2</h2>
	<s:form action="login" method="post">
		<s:textfield label="Username" key="username" />
		<s:password label="Password" key="password" />
		<s:submit value="Log in" />
	</s:form>
</body>
</html>