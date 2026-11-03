<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="estilos.jspf" %>
<title>Acceso denegado</title>
</head>
<body class="bg-light">
	<div class="container min-vh-100 d-flex align-items-center justify-content-center py-4">
	<div class="card shadow-sm border-0 text-center" style="max-width: 520px">
	<div class="card-body p-4 p-md-5">
		<p class="error-code fw-bold text-danger mb-2">403</p>
		<h1 class="h3">Acceso denegado</h1>
		<p>
			Lo sentimos <strong><sec:authentication property="name" /></strong>,
			tu rol no tiene permiso para entrar a esta página.
		</p>
		<p>Tus roles: <sec:authentication property="authorities" /></p>
		<a class="btn btn-primary me-2" href="${pageContext.request.contextPath}/">Volver al inicio</a>
		<a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
	</div>
	</div>
	</div>
</body>
</html>
