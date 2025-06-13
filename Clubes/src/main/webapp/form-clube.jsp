<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulário de Clube</title>
</head>
<body>

<h2>${action == 'insert' ? 'Cadastro de Clube' : 'Editar Clube'}</h2>

<form method="post" action="${pageContext.request.contextPath}/clube/${action}">

    <c:if test="${action == 'update'}">
        <!-- Para atualização envia o id do clube -->
        <input type="hidden" name="id" value="${clube.id}" />
    </c:if>

    <label>Nome:</label>
    <input type="text" name="nome" value="${clube != null ? clube.nome : ''}" required />

    <label>Estado:</label>
    <input type="text" name="estado" value="${clube != null ? clube.estado : ''}" required />

    <label>Competição:</label>
    <select name="competicaoId" required>
        <option value="">-- Selecione --</option>
        <c:forEach var="comp" items="${competicoes}">
            <option value="${comp.id}" 
                <c:if test="${clube != null && clube.competicao.id == comp.id}">selected</c:if>>
                ${comp.nome}
            </option>
        </c:forEach>
    </select>

    <button type="submit">${action == 'insert' ? 'Salvar' : 'Atualizar'}</button>
</form>

</body>
</html>
