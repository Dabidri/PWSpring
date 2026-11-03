<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="estilos.jspf" %>
<title>Cerrar sesión | Productos</title>
</head>
<body class="bg-light">
	<main class="container min-vh-100 d-flex align-items-center justify-content-center py-4">
	<div class="card shadow-sm border-0 auth-card w-100">
	<div class="card-body p-4 p-md-5">
		<h1 class="h3 mb-3">Cerrar sesión</h1>
		<p>¿Seguro que quieres salir?</p>

		<form action="${pageContext.request.contextPath}/logout" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn btn-danger">Sí, cerrar sesión</button>
			<a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/">Cancelar</a>
		</form>
	</div>
	</div>
	</main>
</body>
</html>
