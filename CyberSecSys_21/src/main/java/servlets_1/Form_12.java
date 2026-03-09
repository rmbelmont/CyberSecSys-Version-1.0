package servlets_1;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/forms/searchTechnique"})
public class Form_12 extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      res.setContentType("text/html");
      List<String> lista = new ArrayList();
      String termoBusca = req.getParameter("searchTerm");
      Object resultadoBusca = new ArrayList();

      try {
         CyberSecView view = (CyberSecView)req.getServletContext().getAttribute("cyberSecView");
         if (view != null) {
            lista.addAll(view.getDefensiveTechnique7());
            Collections.sort(lista);
         } else {
            lista.add("Erro: CyberSecView não foi carregada no contexto da aplicação.");
         }
      } catch (Exception var10) {
         lista.add("Erro ao carregar dados da ontologia: " + var10.getMessage());
      }

      if (termoBusca != null && !termoBusca.trim().isEmpty()) {
         resultadoBusca = this.buscarDefensiveTechniques(lista, termoBusca.trim());
      }

      PrintWriter printWriter = res.getWriter();
      printWriter.print("<!DOCTYPE html>");
      printWriter.print("<html lang='en'>");
      printWriter.print("<head>");
      printWriter.print("  <meta charset='UTF-8'>");
      printWriter.print("  <meta name='viewport' content='width=device-width, initial-scale=1'>");
      printWriter.print("  <title>Search - Defensive Techniques</title>");
      printWriter.print("  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet' />");
      printWriter.print("  <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet' />");
      printWriter.print("  <link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap' rel='stylesheet'>");
      printWriter.print("  <style>");
      printWriter.print("    :root {");
      printWriter.print("      --primary-color: #4c5cb0;");
      printWriter.print("      --primary-dark: #3f4f9d;");
      printWriter.print("      --secondary-color: #5a3b85;");
      printWriter.print("      --accent-color: #c16bd6;");
      printWriter.print("      --bg-gradient: linear-gradient(135deg, #4c5cb0 0%, #5a3b85 100%);");
      printWriter.print("      --card-shadow: 0 10px 25px rgba(0,0,0,0.2);");
      printWriter.print("      --text-primary: #1a202c;");
      printWriter.print("      --text-secondary: #2d3748;");
      printWriter.print("      --border-color: #cbd5e0;");
      printWriter.print("      --bg-color: #e2e8f0;");
      printWriter.print("    }");
      printWriter.print("    body {");
      printWriter.print("      font-family: 'Inter', sans-serif;");
      printWriter.print("      background: linear-gradient(135deg, #e2e8f0 0%, #a3b8d1 100%);");
      printWriter.print("      min-height: 100vh;");
      printWriter.print("      color: var(--text-primary);");
      printWriter.print("    }");
      printWriter.print("    .hero-section {");
      printWriter.print("      background: var(--bg-gradient);");
      printWriter.print("      padding: 2rem 0;");
      printWriter.print("      margin-bottom: 2rem;");
      printWriter.print("    }");
      printWriter.print("    .main-title {");
      printWriter.print("      font-size: 2.2rem;");
      printWriter.print("      font-weight: 700;");
      printWriter.print("      color: white;");
      printWriter.print("      text-align: center;");
      printWriter.print("    }");
      printWriter.print("    .search-section {");
      printWriter.print("      background: white;");
      printWriter.print("      border-radius: 15px;");
      printWriter.print("      padding: 2rem;");
      printWriter.print("      box-shadow: var(--card-shadow);");
      printWriter.print("      margin-bottom: 2rem;");
      printWriter.print("    }");
      printWriter.print("    .search-form {");
      printWriter.print("      max-width: 600px;");
      printWriter.print("      margin: 0 auto;");
      printWriter.print("    }");
      printWriter.print("    .search-input {");
      printWriter.print("      border-radius: 50px;");
      printWriter.print("      padding: 0.75rem 1.5rem;");
      printWriter.print("      border: 2px solid var(--border-color);");
      printWriter.print("      font-size: 1rem;");
      printWriter.print("    }");
      printWriter.print("    .search-input:focus {");
      printWriter.print("      border-color: var(--primary-color);");
      printWriter.print("      box-shadow: 0 0 0 0.2rem rgba(76, 92, 176, 0.25);");
      printWriter.print("    }");
      printWriter.print("    .search-btn {");
      printWriter.print("      border-radius: 50px;");
      printWriter.print("      padding: 0.75rem 2rem;");
      printWriter.print("      background: var(--bg-gradient);");
      printWriter.print("      border: none;");
      printWriter.print("      font-weight: 500;");
      printWriter.print("      transition: all 0.3s ease;");
      printWriter.print("    }");
      printWriter.print("    .search-btn:hover {");
      printWriter.print("      transform: translateY(-2px);");
      printWriter.print("      box-shadow: 0 5px 15px rgba(76, 92, 176, 0.4);");
      printWriter.print("    }");
      printWriter.print("    .results-section {");
      printWriter.print("      background: white;");
      printWriter.print("      border-radius: 15px;");
      printWriter.print("      padding: 2rem;");
      printWriter.print("      box-shadow: var(--card-shadow);");
      printWriter.print("    }");
      printWriter.print("    .result-item {");
      printWriter.print("      background: #f8f9fa;");
      printWriter.print("      border-radius: 10px;");
      printWriter.print("      padding: 1rem 1.5rem;");
      printWriter.print("      margin-bottom: 1rem;");
      printWriter.print("      border-left: 4px solid var(--accent-color);");
      printWriter.print("      transition: all 0.3s ease;");
      printWriter.print("    }");
      printWriter.print("    .result-item:hover {");
      printWriter.print("      background: #e9ecef;");
      printWriter.print("      transform: translateX(5px);");
      printWriter.print("    }");
      printWriter.print("    .no-results {");
      printWriter.print("      text-align: center;");
      printWriter.print("      color: #6c757d;");
      printWriter.print("      padding: 2rem;");
      printWriter.print("    }");
      printWriter.print("    .search-info {");
      printWriter.print("      background: rgba(76, 92, 176, 0.1);");
      printWriter.print("      border-radius: 10px;");
      printWriter.print("      padding: 1rem;");
      printWriter.print("      margin-bottom: 1.5rem;");
      printWriter.print("      font-size: 0.9rem;");
      printWriter.print("    }");
      printWriter.print("    .total-count {");
      printWriter.print("      background: var(--primary-color);");
      printWriter.print("      color: white;");
      printWriter.print("      border-radius: 20px;");
      printWriter.print("      padding: 0.25rem 0.75rem;");
      printWriter.print("      font-size: 0.8rem;");
      printWriter.print("      font-weight: 600;");
      printWriter.print("    }");
      printWriter.print("    .highlight {");
      printWriter.print("      background-color: #fff3cd;");
      printWriter.print("      padding: 0.1rem 0.3rem;");
      printWriter.print("      border-radius: 3px;");
      printWriter.print("      font-weight: 600;");
      printWriter.print("    }");
      printWriter.print("  </style>");
      printWriter.print("</head>");
      printWriter.print("<body>");
      printWriter.print("  <div class='hero-section'>");
      printWriter.print("    <div class='container'>");
      printWriter.print("      <h1 class='main-title'>");
      printWriter.print("        <i class='fas fa-shield-alt me-3'></i>");
      printWriter.print("        Defensive Techniques Search");
      printWriter.print("      </h1>");
      printWriter.print("    </div>");
      printWriter.print("  </div>");
      printWriter.print("  <div class='container'>");
      printWriter.print("    <div class='search-section'>");
      printWriter.print("      <form class='search-form' method='GET' action='forms/searchTechnique'>");
      printWriter.print("        <div class='input-group mb-3'>");
      printWriter.print("          <input type='text' class='form-control search-input' name='searchTerm' ");
      printWriter.print("                 placeholder='Search for Defensive Techniques (e.g., Access Policy, File Analysis, Network Isolation)...' ");
      if (termoBusca != null) {
         printWriter.print("                 value='" + this.escapeHtml(termoBusca) + "'");
      }

      printWriter.print("                 required>");
      printWriter.print("          <button class='btn btn-primary search-btn' type='submit'>");
      printWriter.print("            <i class='fas fa-search me-2'></i>Search");
      printWriter.print("          </button>");
      printWriter.print("        </div>");
      printWriter.print("        <div class='search-info'>");
      printWriter.print("          <i class='fas fa-info-circle me-2'></i>");
      printWriter.print("          Search is case-insensitive and ignores spaces. Total defensive techniques available: ");
      printWriter.print("          <span class='total-count'>" + lista.size() + "</span>");
      printWriter.print("        </div>");
      printWriter.print("      </form>");
      printWriter.print("    </div>");
      if (termoBusca != null && !termoBusca.trim().isEmpty()) {
         printWriter.print("    <div class='results-section'>");
         printWriter.print("      <h3 class='mb-4'>");
         printWriter.print("        <i class='fas fa-list me-2'></i>");
         printWriter.print("        Search Results for: \"<span class='text-primary'>" + this.escapeHtml(termoBusca) + "</span>\"");
         printWriter.print("      </h3>");
         if (((List)resultadoBusca).isEmpty()) {
            printWriter.print("      <div class='no-results'>");
            printWriter.print("        <i class='fas fa-search fa-3x mb-3 text-muted'></i>");
            printWriter.print("        <h5>No results found</h5>");
            printWriter.print("        <p class='text-muted'>Try different keywords or browse all defensive techniques below.</p>");
            printWriter.print("      </div>");
         } else {
            printWriter.print("      <div class='alert alert-success mb-4'>");
            printWriter.print("        <i class='fas fa-check-circle me-2'></i>");
            printWriter.print("        Found " + ((List)resultadoBusca).size() + " matching technique(s)");
            printWriter.print("      </div>");
            Iterator var8 = ((List)resultadoBusca).iterator();

            while(var8.hasNext()) {
               String resultadoItem = (String)var8.next();
               String highlightedItem = this.highlightText(resultadoItem, termoBusca);
               printWriter.print("      <div class='result-item'>");
               printWriter.print("        <i class='fas fa-shield-alt me-2 text-primary'></i>");
               printWriter.print("        " + highlightedItem);
               printWriter.print("      </div>");
            }
         }

         printWriter.print("    </div>");
      }

      printWriter.print("    <div class='results-section'>");
      printWriter.print("      <h3 class='mb-4'>");
      printWriter.print("        <i class='fas fa-layer-group me-2'></i>");
      printWriter.print("        All Defensive Techniques");
      printWriter.print("        <span class='total-count ms-2'>" + lista.size() + "</span>");
      printWriter.print("      </h3>");
      if (!lista.isEmpty() && !((String)lista.get(0)).startsWith("Erro")) {
         printWriter.print("      <div class='row'>");
         int count = 0;

         for(Iterator var14 = lista.iterator(); var14.hasNext(); ++count) {
            String technique = (String)var14.next();
            if (count % 2 == 0) {
               printWriter.print("        <div class='col-md-6'>");
            }

            printWriter.print("          <div class='result-item'>");
            printWriter.print("            <span class='badge bg-primary me-2'>" + (count + 1) + "</span>");
            if (termoBusca != null && !termoBusca.trim().isEmpty() && technique.toLowerCase().contains(termoBusca.toLowerCase())) {
               printWriter.print(this.highlightText(technique, termoBusca));
            } else {
               printWriter.print(this.escapeHtml(technique));
            }

            printWriter.print("          </div>");
            if (count % 2 == 1) {
               printWriter.print("        </div>");
            }
         }

         if (count % 2 == 1) {
            printWriter.print("        </div>");
         }

         printWriter.print("      </div>");
      } else {
         printWriter.print("      <div class='alert alert-danger'>");
         printWriter.print("        <i class='fas fa-exclamation-triangle me-2'></i>");
         printWriter.print("        " + (lista.isEmpty() ? "No defensive techniques available" : this.escapeHtml((String)lista.get(0))));
         printWriter.print("      </div>");
      }

      printWriter.print("    </div>");
      printWriter.print("    <div class='text-center mt-4'>");
      printWriter.print("      <a href='" + req.getContextPath() + "/home' class='btn btn-secondary'>");
      printWriter.print("        <i class='fas fa-arrow-left me-2'></i> Back to Main Menu");
      printWriter.print("      </a>");
      printWriter.print("    </div>");
      printWriter.print("  </div>");
      printWriter.println("<div class=\"footer-section mt-5\" style=\"background-color: #1a1a1a; color: #f1f1f1; padding: 20px 0; text-align: center; font-family: Arial, sans-serif;\">");
      printWriter.println("    <div class=\"container\" style=\"max-width: 800px; margin: 0 auto;\">");
      printWriter.println("        <p style=\"margin: 5px 0; font-weight: bold;\">© 2025 Cybersecurity Management in Information Systems</p>");
      printWriter.println("        <p style=\"margin: 5px 0;\">© Federal University of the State of Rio de Janeiro (UNIRIO)</p>");
      printWriter.println("        <p style=\"margin: 5px 0;\">© Graduate Program in Computer Science (PPGI)</p>");
      printWriter.println("        <p style=\"margin: 5px 0; font-style: italic;\">PhD Student: Roberto Monteiro Dias</p>");
      printWriter.println("    </div>");
      printWriter.println("</div>");
      printWriter.print("  <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js'></script>");
      printWriter.print("</body>");
      printWriter.print("</html>");
      printWriter.close();
   }

   private List<String> buscarDefensiveTechniques(List<String> lista, String termoBusca) {
      List<String> resultados = new ArrayList();
      String termoLower = termoBusca.toLowerCase().trim();
      Iterator var6 = lista.iterator();

      while(var6.hasNext()) {
         String item = (String)var6.next();
         if (item.toLowerCase().contains(termoLower)) {
            resultados.add(item);
         }
      }

      return resultados;
   }

   private String escapeHtml(String text) {
      return text == null ? "" : text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
   }

   private String highlightText(String text, String searchTerm) {
      if (text != null && searchTerm != null) {
         String textLower = text.toLowerCase();
         String searchLower = searchTerm.toLowerCase();
         int index = textLower.indexOf(searchLower);
         if (index == -1) {
            return this.escapeHtml(text);
         } else {
            String before = text.substring(0, index);
            String match = text.substring(index, index + searchTerm.length());
            String after = text.substring(index + searchTerm.length());
            return this.escapeHtml(before) + "<span class='highlight'>" + this.escapeHtml(match) + "</span>" + this.escapeHtml(after);
         }
      } else {
         return this.escapeHtml(text);
      }
   }
}
