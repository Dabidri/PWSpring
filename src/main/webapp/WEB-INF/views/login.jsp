<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="estilos.jspf" %>
<title>Iniciar sesión | Productos</title>
</head>
<body class="bg-light">
	<main class="container min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
	<div class="card shadow-sm border-0 auth-card w-100">
		<div class="card-body p-4 p-md-5">
		<h1 class="h3 mb-1">Iniciar sesión</h1>
		<p class="text-secondary mb-4">Accede al administrador de productos.</p>

		<% if (request.getParameter("error") != null) { %>
			<div class="alert alert-danger">Usuario o contraseña incorrectos.</div>
		<% } %>
		<% if (request.getParameter("logout") != null) { %>
			<div class="alert alert-success">Cerraste sesión correctamente.</div>
		<% } %>

		<form action="${pageContext.request.contextPath}/login" method="post">
			<label class="form-label" for="username">Usuario</label>
			<input class="form-control" type="text" id="username" name="username" autofocus required />

			<label class="form-label mt-3" for="password">Contraseña</label>
			<input class="form-control" type="password" id="password" name="password" required />

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn btn-primary w-100 mt-4">Entrar</button>
		</form>

	</div>
	</div>

	<div class="accordion auth-card w-100 mt-3" id="datosPrueba">
		<div class="accordion-item border-0 shadow-sm">
			<h2 class="accordion-header" id="encabezadoPrueba">
				<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelPrueba" aria-expanded="false" aria-controls="panelPrueba">
					Accesos disponibles para probar los permisos
				</button>
			</h2>
			<div id="panelPrueba" class="accordion-collapse collapse" aria-labelledby="encabezadoPrueba">
				<div class="accordion-body small">
					<p class="text-secondary mb-2">Usa cualquiera de estas cuentas. La contraseña se forma agregando <code>123</code> al usuario.</p>
					<div class="d-flex flex-wrap gap-2">
						<span class="badge text-bg-light border">admin</span>
						<span class="badge text-bg-light border">creator</span>
						<span class="badge text-bg-light border">editor</span>
						<span class="badge text-bg-light border">user</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	</main>
</body>
</html>
