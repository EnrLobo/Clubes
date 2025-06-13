<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
		<title>Clubes - CBDA</title>
	</head>
	<body>
		<%@include file="modal.html"%>
		<%@include file="nav-menu.jsp"%>
					
		<div id="container" class="container-fluid">
			<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  ${message}
			</div>
		
	 	 	<div id="top" class="row">
	 			<div class="col-md-3">
			        <h3>Clubes</h3>
			    </div>
			 
			    <div class="col-md-3">
			        <a href="${pageContext.request.contextPath}/clube/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus" /></span>&nbspAdicionar Clube</a>
			    </div>
	     	</div>
	 
	     	<hr />
	     	
	     	<div id="list" class="row">
	     		<div class="table-responsive col-md-12">
			        <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
			            <thead>
			                <tr>
			                    <th>Nome</th>
			                    <th>Estado</th>
			                    <th>Competição</th>
			                    <th>Editar</th>
			                    <th>Excluir</th>
			                 </tr>
			            </thead>
			            <tbody>
			            	<c:forEach var="clube" items="${clubes}">
								<tr>
				                    <td>${clube.nome}</td>
									<td>${clube.estado}</td>
									<td>${clube.competicao.nome}</td>
				                    
				                    <td class="actions">
				                        <a class="btn btn-info btn-xs" href="${pageContext.request.contextPath}/clube/update?id=${clube.id}">
										    <span class="glyphicon glyphicon-pencil"></span>
										</a>
				                    </td>
				                    <td class="actions">
				                        <a class="btn btn-danger btn-xs modal-remove" 
											clube-id="${clube.id}" 
											clube-estado="${clube.estado}" 
											clube-nome="${clube.nome}" 
											data-toggle="modal" 
											data-target="#delete-modal"  
											href="#">
											<span class="glyphicon glyphicon-trash"></span>
										</a>
											
				                    </td>
				                </tr>
							</c:forEach>
			            </tbody>
			         </table>
			 
			     </div>
	     	</div>
	 
	     	<div id="bottom" class="row">
	     		<div class="col-md-12">
			        <ul class="pagination">
			            <li class="disabled"><a>&lt; Anterior</a></li>
			            <li class="disabled"><a>1</a></li>
			            <li><a href="#">2</a></li>
			            <li><a href="#">3</a></li>
			            <li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
			        </ul><!-- /.pagination -->
			    </div>
	     	</div>
		</div>
		
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
		    // fecha o alert após 3 segundos
		    setTimeout(function() {
		        $("#alert").slideUp(500);
		    }, 3000);
		    
		    // ao clicar no delete de algum clube, pega os dados e envia ao modal
		    $(".modal-remove").click(function () {
		        var clubeEstado = $(this).attr('clube-estado');
		        var clubeId = $(this).attr('clube-id');
		        var clubeNome = $(this).attr('clube-nome');

		        $(".modal-body #hiddenValue").text("o clube '" + clubeNome + "'");
		        $("#id").attr("value", clubeId);
		        $("#form").attr("action", "clube/delete");
		    });
		});
		</script>
	</body>
</html>
