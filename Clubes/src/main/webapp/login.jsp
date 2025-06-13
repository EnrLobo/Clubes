<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>

<style>
  body {
    margin: 0;
    padding: 0;
    background: linear-gradient(to bottom right, #1e3c72, #2a5298);
    font-family: 'Segoe UI', sans-serif;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }

  .navbar {
    background-color: #0d1b2a !important;
  }

  .navbar-brand {
    color: #ffffff !important;
    font-weight: 600;
    font-size: 1.5rem;
  }

  main {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 30px;
  }

  .card {
    background: #ffffff;
    border-radius: 16px;
    padding: 30px;
    width: 100%;
    max-width: 400px;
    box-shadow: 0 12px 30px rgba(0, 0, 0, 0.2);
    border: none;
  }

  h2 {
    margin-bottom: 25px;
    color: #1e3c72;
    font-weight: 600;
    text-align: center;
  }

  .form-label {
    font-weight: 500;
    margin-bottom: 5px;
    color: #333;
  }

  .form-control {
    border-radius: 10px;
    padding: 10px 12px;
    border: 1px solid #ccc;
    transition: all 0.3s ease-in-out;
  }

  .form-control:focus {
    border-color: #2a5298;
    box-shadow: 0 0 5px rgba(42, 82, 152, 0.5);
    outline: none;
  }

  .btn-dark {
    background-color: #2a5298;
    border: none;
    border-radius: 10px;
    padding: 12px;
    font-size: 1rem;
    font-weight: 500;
    transition: background-color 0.3s ease;
  }

  .btn-dark:hover {
    background-color: #1e3c72;
  }

  .text-danger {
    margin-top: 10px;
    font-size: 0.875rem;
    text-align: center;
    color: #c0392b !important;
  }

  footer {
    background-color: #0d1b2a;
    color: #ffffff;
    text-align: center;
    padding: 15px 0;
  }
</style>



  </head>
  <body>
    <div class="d-flex flex-column min-vh-100 bg-light">
      <!-- Navbar -->
      <nav class="navbar navbar-dark bg-dark">
        <div class="container">
          <span class="navbar-brand mb-0 h1">Cadastro de Clubes CBDA</span>
        </div>
      </nav>

      <main class="flex-grow-1 d-flex justify-content-center align-items-center">
        <div class="card shadow-sm p-4" style="width: 100%; max-width: 400px;">
          <h2 class="text-center mb-4">Login</h2>
          <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="mb-3">
              <label for="user_login_id" class="form-label">Login (e-mail)</label>
              <input type="text" class="form-control" id="user_login_id" name="user_login" required />
            </div>
            
            <div class="mb-3">
              <label for="user_pw_id" class="form-label">Senha</label>
              <input type="password" class="form-control" id="user_pw_id" name="user_pw" required />
            </div>
            
            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark btn-lg">Logar</button>
            </div>
            
            <c:if test="${param.erro == 'true'}">
			    <span class="text-danger small">Usuário ou senha inválidos.</span>
			</c:if>
          </form>
        </div>
      </main>

      <footer class="bg-dark text-white text-center py-3 mt-auto">
        <div class="container">
          <jsp:useBean id="date" class="java.util.Date" />
		  <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
          <p class="mb-0">©<c:out value="${currentYear}" /> CBDA. Todos os direitos reservados.</p>
        </div>
      </footer>
    </div>
  </body>
</html>