<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CyberSecSys - Gestão de Cibersegurança</title>

<!-- Bootstrap 5 -->
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
    rel="stylesheet" />
<link
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    rel="stylesheet" />
<link
    href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
    rel="stylesheet">

<style>
:root {
    --primary-color: #4c5cb0;
    --primary-dark: #3f4f9d;
    --secondary-color: #5a3b85;
    --accent-color: #c16bd6;
    --bg-gradient: linear-gradient(135deg, #4c5cb0 0%, #5a3b85 100%);
    --card-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
    --card-shadow-hover: 0 20px 40px rgba(0, 0, 0, 0.3);
    --text-primary: #1a202c;
    --text-secondary: #2d3748;
    --border-color: #cbd5e0;
    --bg-color: #e2e8f0;
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Inter', sans-serif;
    background: linear-gradient(135deg, #e2e8f0 0%, #a3b8d1 100%);
    min-height: 100vh;
    color: var(--text-primary);
}

.hero-section {
    background: var(--bg-gradient);
    padding: 4rem 0;
    margin-bottom: 3rem;
    position: relative;
    overflow: hidden;
}

.hero-section::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background:
        url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grid" width="10" height="10" patternUnits="userSpaceOnUse"><path d="M 10 0 L 0 0 0 10" fill="none" stroke="rgba(0,0,0,0.2)" stroke-width="0.5"/></pattern></defs><rect width="100" height="100" fill="url(%23grid)" /></svg>');
    opacity: 0.3;
}

.hero-content {
    position: relative;
    z-index: 1;
}

.main-title {
    font-size: 3.5rem;
    font-weight: 700;
    color: white;
    text-align: center;
    margin-bottom: 1rem;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
    animation: fadeInUp 1s ease-out;
}

.hero-subtitle {
    font-size: 1.2rem;
    color: rgba(255, 255, 255, 0.9);
    text-align: center;
    font-weight: 300;
    animation: fadeInUp 1s ease-out 0.3s both;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.container {
    max-width: 1100px;
}

.section-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
    gap: 2rem;
    margin-bottom: 2rem;
}

.card {
    background: white;
    border: none;
    border-radius: 20px;
    padding: 2rem;
    box-shadow: var(--card-shadow);
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    position: relative;
    overflow: hidden;
    animation: fadeInUp 0.6s ease-out;
    animation-fill-mode: both;
}

.card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 4px;
    background: var(--bg-gradient);
    transform: scaleX(0);
    transform-origin: left;
    transition: transform 0.3s ease;
}

.card:hover {
    transform: translateY(-5px);
    box-shadow: var(--card-shadow-hover);
}

.card:hover::before {
    transform: scaleX(1);
}

.card-icon {
    width: 60px;
    height: 60px;
    background: var(--bg-gradient);
    border-radius: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 1.5rem;
    color: white;
    font-size: 1.5rem;
}

.card-title {
    color: var(--text-primary);
    font-weight: 600;
    font-size: 1.1rem;
    margin-bottom: 1.5rem;
    line-height: 1.4;
}

.btn-action {
    background: var(--bg-gradient);
    color: white;
    border: none;
    border-radius: 50px;
    padding: 0.75rem 1.5rem;
    font-weight: 500;
    font-size: 0.85rem;
    transition: all 0.3s ease;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    position: relative;
    overflow: hidden;
    flex: 1;
    min-width: 120px;
    text-align: center;
}

.btn-action::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2),
        transparent);
    transition: left 0.5s;
}

.btn-action:hover::before {
    left: 100%;
}

.btn-action:hover {
    transform: scale(1.05);
    box-shadow: 0 10px 20px rgba(76, 92, 176, 0.4);
}

/* Estilo para botões PDF - VERMELHO */
.btn-pdf {
    background: linear-gradient(135deg, #dc3545 0%, #c82333 100%);
    color: white;
    border: none;
    border-radius: 50px;
    padding: 0.75rem 1.5rem;
    font-weight: 500;
    font-size: 0.85rem;
    transition: all 0.3s ease;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    flex: 1;
    min-width: 100px;
    text-align: center;
}

.btn-pdf:hover {
    transform: scale(1.05);
    box-shadow: 0 10px 20px rgba(220, 53, 69, 0.4);
    color: white;
    background: linear-gradient(135deg, #c82333 0%, #dc3545 100%);
}

/* Estilo para botões CSV - BRANCO */
.btn-csv {
    background: white;
    color: #2d3748;
    border: 2px solid #e2e8f0;
    border-radius: 50px;
    padding: 0.75rem 1.5rem;
    font-weight: 500;
    font-size: 0.85rem;
    transition: all 0.3s ease;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    flex: 1;
    min-width: 100px;
    text-align: center;
}

.btn-csv:hover {
    transform: scale(1.05);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
    color: #2d3748;
    background: #f8f9fa;
    border-color: #cbd5e0;
}

/* Estilo para botão Chatbot - VERDE */
.btn-chatbot {
    background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
    color: white;
    border: none;
    border-radius: 50px;
    padding: 0.75rem 1.5rem;
    font-weight: 500;
    font-size: 0.85rem;
    transition: all 0.3s ease;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    width: 100%;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

.btn-chatbot:hover {
    transform: scale(1.05);
    box-shadow: 0 10px 20px rgba(40, 167, 69, 0.4);
    color: white;
    background: linear-gradient(135deg, #20c997 0%, #28a745 100%);
}

/* Estilo para botão Training Game - LARANJA */
.btn-training {
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
    color: white;
    border: none;
    border-radius: 50px;
    padding: 0.75rem 1.5rem;
    font-weight: 500;
    font-size: 0.85rem;
    transition: all 0.3s ease;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    width: 100%;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

.btn-training:hover {
    transform: scale(1.05);
    box-shadow: 0 10px 20px rgba(255, 107, 107, 0.4);
    color: white;
    background: linear-gradient(135deg, #ee5a24 0%, #ff6b6b 100%);
}

.button-group {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 0.5rem;
    width: 100%;
}

.button-group form {
    flex: 1;
    display: flex;
}

.button-group .btn {
    width: 100%;
    white-space: nowrap;
}

.search-card {
    background: linear-gradient(135deg, #3a4990 0%, #4a2d75 100%);
    color: white;
    grid-column: 1 / -1;
    margin-top: 2rem;
}

.search-card .card-title {
    color: white;
}

.search-input {
    background: rgba(255, 255, 255, 0.9);
    border: none;
    border-radius: 15px;
    padding: 1rem 1.5rem;
    font-size: 1rem;
    margin-bottom: 1.5rem;
    transition: all 0.3s ease;
    width: 100%;
}

.search-input:focus {
    background: white;
    box-shadow: 0 0 0 3px rgba(255, 255, 255, 0.3);
    outline: none;
}

.search-input::placeholder {
    color: #666;
}

.btn-search {
    background: rgba(255, 255, 255, 0.2);
    color: white;
    border: 2px solid rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(10px);
    border-radius: 50px;
    padding: 0.75rem 2rem;
    font-weight: 500;
    transition: all 0.3s ease;
}

.btn-search:hover {
    background: white;
    color: var(--primary-color);
    border-color: white;
}

.stats-section {
    background: white;
    border-radius: 20px;
    padding: 2rem;
    margin: 3rem 0;
    box-shadow: var(--card-shadow);
}

.stat-item {
    text-align: center;
    padding: 1rem;
}

.stat-number {
    font-size: 2.5rem;
    font-weight: 700;
    color: var(--primary-color);
    display: block;
}

.stat-label {
    color: var(--text-secondary);
    font-size: 0.9rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-top: 0.5rem;
}

.footer-section {
    background: #1a202c;
    color: white;
    text-align: center;
    padding: 2rem 0;
    margin-top: 4rem;
}

/* Animation delays for staggered effect */
.card:nth-child(1) { animation-delay: 0.1s; }
.card:nth-child(2) { animation-delay: 0.2s; }
.card:nth-child(3) { animation-delay: 0.3s; }
.card:nth-child(4) { animation-delay: 0.4s; }
.card:nth-child(5) { animation-delay: 0.5s; }
.card:nth-child(6) { animation-delay: 0.6s; }
.card:nth-child(7) { animation-delay: 0.7s; }
.card:nth-child(8) { animation-delay: 0.8s; }
.card:nth-child(9) { animation-delay: 0.9s; }
.card:nth-child(10) { animation-delay: 1.0s; }
.card:nth-child(11) { animation-delay: 1.1s; }
.card:nth-child(12) { animation-delay: 1.2s; }
.card:nth-child(13) { animation-delay: 1.3s; }

/* Responsive design */
@media ( max-width : 768px) {
    .main-title {
        font-size: 2.5rem;
    }
    .section-grid {
        grid-template-columns: 1fr;
        gap: 1.5rem;
    }
    .card {
        padding: 1.5rem;
    }
    .hero-section {
        padding: 3rem 0;
    }
    .button-group {
        flex-direction: column;
        gap: 0.5rem;
    }
    .button-group form {
        width: 100%;
    }
    .btn-action, .btn-pdf, .btn-csv, .btn-chatbot, .btn-training {
        width: 100%;
        padding: 0.75rem 1rem;
    }
}

/* Loading animation */
@keyframes pulse {
    0%, 100% { opacity: 1; }
    50% { opacity: 0.7; }
}

.loading {
    animation: pulse 2s infinite;
}

/* Estilo para botões desabilitados */
.btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}
</style>
</head>
<body oncontextmenu="return false">
    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container hero-content">
            <h1 class="main-title">CyberSecSys Version 1.0</h1>
            <p class="hero-subtitle">Cybersecurity Management in Information
                Systems</p>
        </div>
    </div>

    <div class="container">
        <!-- Stats Section -->
        <div class="stats-section">
            <div class="row">
                <div class="col-md-3 stat-item">
                    <span class="stat-number">10</span>
                    <div class="stat-label">Query Forms</div>
                </div>
                <div class="col-md-3 stat-item">
                    <span class="stat-number">7</span>
                    <div class="stat-label">PDF Reports</div>
                </div>
                <div class="col-md-3 stat-item">
                    <span class="stat-number">7</span>
                    <div class="stat-label">CSV Reports</div>
                </div>
                <div class="col-md-3 stat-item">
                    <span class="stat-number">2</span>
                    <div class="stat-label">AI Tools</div>
                </div>
            </div>
        </div>

        <!-- Main Content Grid -->
        <div class="section-grid">
            <%
            // Array de formulários principais
            String[][] forms = {
                {"Form_01", "Cybersecurity Approach", "fas fa-clipboard-check", "approach", "approach"},
                {"Form_02", "Cybersecurity Performance", "fas fa-shield-alt", "performance", "performance"},
                {"Form_03", "Cybersecurity Component", "fas fa-tools", "component", "component"},
                {"Form_04", "Types of Systems in Cybersecurity", "fas fa-microchip", "systems", "systems"},
                {"Form_05", "Types of OS API System Functions", "fas fa-code", "osapi", "osapi"},
                {"Form_06", "Defensive Technique", "fas fa-lock", "defensive", "defensive"},
                {"Form_07", "Attack Enterprise Mitigation", "fas fa-globe", "attack", "mitigation"}
            };

            for (int i = 0; i < forms.length; i++) {
            %>
            <div class="card">
                <div class="card-icon">
                    <i class="<%=forms[i][2]%>"></i>
                </div>
                <h5 class="card-title"><%=forms[i][1]%></h5>
                <div class="button-group">
                    <!-- Botão Explore -->
                    <form
                        action="<%=request.getContextPath() + "/forms/" + forms[i][3]%>"
                        method="get">
                        <button type="submit" class="btn btn-action">
                            Explore <i class="fas fa-arrow-right ms-2"></i>
                        </button>
                    </form>
                    <!-- Botão PDF -->
                    <form action="<%=request.getContextPath() + "/forms/report"%>"
                        method="get">
                        <input type="hidden" name="report" value="<%=forms[i][4]%>">
                        <button type="submit" class="btn btn-pdf">
                            PDF <i class="fas fa-file-pdf ms-1"></i>
                        </button>
                    </form>
                    <!-- Botão CSV -->
                    <form action="<%=request.getContextPath()%>/forms/reportcsv"
                        method="get">
                        <input type="hidden" name="report" value="<%=forms[i][4]%>">
                        <input type="hidden" name="format" value="csv">
                        <button type="submit" class="btn btn-csv">
                            CSV <i class="fas fa-file-csv ms-1"></i>
                        </button>
                    </form>
                </div>
            </div>
            <%
            }
            %>
            
            <!-- Card do Training Game -->
            <div class="card">
                <div class="card-icon" style="background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);">
                    <i class="fas fa-gamepad"></i>
                </div>
                <h5 class="card-title">Cybersecurity Training Game</h5>
                <p class="text-muted mb-3" style="font-size: 0.9rem;">
                    Interactive training simulation with real-world cyber attack scenarios
                </p>
                <div class="button-group">
                    <form action="<%=request.getContextPath()%>/training" method="get">
                        <button type="submit" class="btn btn-training">
                            Start Training <i class="fas fa-arrow-right ms-2"></i>
                        </button>
                    </form>
                </div>
            </div>
            
            <!-- Card do Chatbot AI -->
            <div class="card">
                <div class="card-icon" style="background: linear-gradient(135deg, #28a745 0%, #20c997 100%);">
                    <i class="fas fa-robot"></i>
                </div>
                <h5 class="card-title">AI Cybersecurity Chatbot</h5>
                <p class="text-muted mb-3" style="font-size: 0.9rem;">
                    Get intelligent answers about cybersecurity concepts and best practices
                </p>
                <div class="button-group">
                    <form action="<%=request.getContextPath()%>/forms/chatbot" method="get">
                        <button type="submit" class="btn btn-chatbot">
                            Open Chat <i class="fas fa-comments ms-2"></i>
                        </button>
                    </form>
                </div>
            </div>
            
            <!-- Search Card for OS API Functions -->
            <div class="card search-card">
                <div class="card-icon" style="background: rgba(255, 255, 255, 0.2);">
                    <i class="fas fa-search"></i>
                </div>
                <h5 class="card-title">OS API System Functions Search</h5>
                <form action="<%= request.getContextPath() %>/forms/searchFunctions" method="get">
                    <input type="text" class="form-control search-input"
                        name="searchTerm" placeholder="Enter function name (e.g., Allocate Memory, Create File)..."
                        required>
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-search">
                            <i class="fas fa-search me-2"></i> Search Functions
                        </button>
                    </div>
                </form>
            </div>
            
            <!-- Search Card for Defensive Techniques -->
            <div class="card search-card">
                <div class="card-icon" style="background: rgba(255, 255, 255, 0.2);">
                    <i class="fas fa-shield-alt"></i>
                </div>
                <h5 class="card-title">Defensive Technique Search</h5>
                <form action="<%= request.getContextPath() %>/forms/searchTechnique" method="get">
                    <input type="text" class="form-control search-input"
                        name="searchTerm" placeholder="Enter defensive technique name (e.g., Access Mediation, File Analysis)..."
                        required>
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-search">
                            <i class="fas fa-search me-2"></i> Search Techniques
                        </button>
                    </div>
                </form>
            </div>
            
            <!-- Search Card for Attack Mitigation -->
            <div class="card search-card">
                <div class="card-icon" style="background: rgba(255, 255, 255, 0.2);">
                    <i class="fas fa-user-shield"></i>
                </div>
                <h5 class="card-title">Attack Mitigation Search</h5>
                <form action="<%= request.getContextPath() %>/forms/searchMitigation" method="get">
                    <input type="text" class="form-control search-input"
                        name="searchTerm" placeholder="Enter attack mitigation technique (e.g., Account Use Policies, Execution Prevention)..."
                        required>
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-search">
                            <i class="fas fa-search me-2"></i> Search Mitigation
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Footer Section -->
    <div class="footer-section">
        <div class="container">
            <p style="margin: 5px 0; font-weight: bold;">© 2025
                Cybersecurity Management in Information Systems</p>
            <p style="margin: 5px 0;">Federal University of the State of
                Rio de Janeiro (UNIRIO)</p>
            <p style="margin: 5px 0;">Graduate Program in Computer Science
                (PPGI)</p>
            <p style="margin: 5px 0; font-style: italic;">PhD Student:
                Roberto Monteiro Dias</p>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    // Add smooth scrolling and enhanced interactions
    document.addEventListener('DOMContentLoaded', function() {
      // Animate cards on scroll
      const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
      };

      const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
          }
        });
      }, observerOptions);

      // Observe all cards
      document.querySelectorAll('.card').forEach(card => {
        observer.observe(card);
      });

      // Sistema simples de loading que não interfere na submissão do formulário
      document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function(e) {
          const button = this.querySelector('button[type="submit"]');
          const originalHTML = button.innerHTML;
          
          // Apenas muda visualmente por um curto período
          if (button.classList.contains('btn-action')) {
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Loading...';
          } else if (button.classList.contains('btn-pdf')) {
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>PDF...';
          } else if (button.classList.contains('btn-csv')) {
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>CSV...';
          } else if (button.classList.contains('btn-search')) {
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Searching...';
          } else if (button.classList.contains('btn-chatbot')) {
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Opening...';
          } else if (button.classList.contains('btn-training')) {
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Opening...';
          }
          
          // Desabilita o botão temporariamente para evitar múltiplos cliques
          button.disabled = true;
          
          // Restaura após 3 segundos (caso a página não tenha recarregado)
          setTimeout(function() {
            button.innerHTML = originalHTML;
            button.disabled = false;
          }, 4000);
        });
      });

      // Enhanced input focus effects
      document.querySelectorAll('.search-input').forEach(input => {
        input.addEventListener('focus', function() {
          this.parentElement.style.transform = 'scale(1.02)';
        });
        
        input.addEventListener('blur', function() {
          this.parentElement.style.transform = 'scale(1)';
        });
      });
    });
    </script>
</body>
</html>