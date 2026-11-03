<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="estilos.jspf" %>
<title>Editar producto</title>
</head>
<body>
	<main class="container page-shell py-4">
	<div class="card shadow-sm border-0 form-card mx-auto">
		<div class="card-body p-4">
		<h1 class="h3 mb-4">Editar producto</h1>
		<form action="${pageContext.request.contextPath}/update/${producto.id}" method="post">
			<label class="form-label" for="nombre">Nombre</label>
			<input class="form-control" type="text" id="nombre" name="nombre" value="${producto.nombre}" required />

			<label class="form-label mt-3" for="precio">Precio</label>
			<input class="form-control" type="number" id="precio" name="precio" step="0.01" value="${producto.precio}" required />

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn btn-primary mt-4">Guardar cambios</button>
		</form>
		<a class="btn btn-link px-0 mt-2" href="${pageContext.request.contextPath}/">Volver</a>
	</div>
	</div>
	</main>
</body>
</html>
