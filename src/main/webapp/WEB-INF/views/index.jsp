<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="estilos.jspf" %>
<title>Productos</title>
</head>
<body>
	<nav class="navbar navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">Productos</a>
			<div class="d-flex align-items-center gap-3 text-white">
				<span class="small">Hola, <strong><sec:authentication property="name" /></strong></span>
				<a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">Salir</a>
			</div>
		</div>
	</nav>

	<main class="container page-shell py-4">
		<div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-4">
			<div>
				<h1 class="h3 mb-1">Lista de productos</h1>
				<p class="text-secondary mb-0">Consulta y administra los productos registrados.</p>
			</div>
			<sec:authorize access="hasAnyAuthority('ADMIN', 'CREATOR')">
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/new">Nuevo producto</a>
			</sec:authorize>
		</div>

		<div class="mb-3">
			<span class="text-secondary me-2">Tus roles:</span>
			<sec:authentication property="authorities" var="roles" />
			<c:forEach var="rol" items="${roles}">
				<span class="badge text-bg-secondary me-1">${rol.authority}</span>
			</c:forEach>
		</div>

		<div class="card shadow-sm border-0">
			<div class="card-body p-0">
				<div class="table-responsive">
					<table class="table table-hover align-middle mb-0">
						<thead class="table-light"><tr><th>ID</th><th>Nombre</th><th>Precio</th><th class="text-end">Acciones</th></tr></thead>
						<tbody>
							<c:forEach var="producto" items="${productos}">
								<tr>
									<td>${producto.id}</td><td>${producto.nombre}</td><td>$ ${producto.precio}</td>
									<td class="text-end text-nowrap">
										<sec:authorize access="hasAnyAuthority('ADMIN', 'EDITOR')"><a class="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/edit/${producto.id}">Editar</a></sec:authorize>
										<sec:authorize access="hasAuthority('ADMIN')"><a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/delete/${producto.id}">Eliminar</a></sec:authorize>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty productos}"><tr><td colspan="4" class="text-center text-secondary py-4">No hay productos todavía.</td></tr></c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<p class="small text-secondary mt-3">Los permisos de edición y eliminación dependen de tu rol.</p>
	</main>
</body>
</html>
