<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Competição</title>
</head>
<body>
    <h2>${action == 'insert' ? 'Nova Competição' : 'Editar Competição'}</h2>

    <form action="${pageContext.request.contextPath}/comp/${action}" method="post">
        <c:if test="${action == 'update'}">
            <input type="hidden" name="compId" value="${competicao.id}" />
        </c:if>

        <label for="nome">Nome da Competição:</label><br>
        <input type="text" id="nome" name="nome" value="${competicao.nome}" required /><br><br>

        <label for="clubeRealizacao">Clube Realizador:</label><br>
        <input type="text" id="clubeRealizacao" name="clubeRealizacao" value="${competicao.nomeClube}" required /><br><br>

        <label for="cidadeRealizacao">Cidade de Realização:</label><br>
        <input type="text" id="cidadeRealizacao" name="cidadeRealizacao" value="${competicao.cidade}" required /><br><br>

        <input type="submit" value="${action == 'insert' ? 'Cadastrar' : 'Atualizar'}" />
        <a href="${pageContext.request.contextPath}/comps">Cancelar</a>
    </form>
</body>
</html>
