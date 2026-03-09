package servlets_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuizServlet extends HttpServlet {
   private List<Question> questions;

   public void init() throws ServletException {
      super.init();
      this.questions = new ArrayList();
      this.questions.add(new Question("What tools, methods, models, and techniques are used in the development of cybersecurity ontologies for IS?", new String[]{"Requirements Analysis, Prototyping, Data Modeling and Penetration Testing", "Automation Tools, Framework Development, Ontology Modeling, and Risk Analysis", "Machine Learning, Firewalls, Cryptography and Intrusion Detection Systems", "Project Management, Waterfall Model, Agile Development and ITIL Framework"}, 1));
      this.questions.add(new Question("How is the performance evaluation of these ontologies conducted across the dimensions of people, processes, and technology?", new String[]{"Risk Analysis, Cost-Benefit Analysis, ROI Calculation and Budget Assessment", "User Training, Documentation Review, Process Mapping and Technology Audit", "Usability Evaluation, Professional Assessment, Qualitative Analysis and Quality Criteria Validation", "Stakeholder Interviews, SWOT Analysis, Gap Analysis and Benchmarking"}, 2));
      this.questions.add(new Question("What aspects influence the development and application of ontologies in each dimension of IS?", new String[]{"Financial aspects, Scalability requirements, Compliance needs and Vendor selection", "Technical implementation aspects, Human and behavioral aspects, Risk and threat assessment, Standards and ontology integration", "Project timeline, Budget constraints, Team expertise and Technology infrastructure", "Data privacy regulations, International standards, Industry best practices and Legal compliance"}, 1));
      this.questions.add(new Question("How to mitigate cyber attacks based on time manipulation carried out through rootkits that compromise the integrity and synchronization between Federal Government IS?", new String[]{"Implementing advanced firewalls, intrusion detection systems, and regular security patches", "Using blockchain technology for immutable timestamps and decentralized time synchronization", "Deploying GISSI as a decision support solution for detection, analysis and rapid response to cyber threats", "Establishing strict access controls, multi-factor authentication and encryption protocols"}, 2));
      this.questions.add(new Question("What are the types and systems used with cybersecurity ontologies?", new String[]{"Operating Systems, Network Systems, Database Systems and Application Systems", "Monitoring Systems, Detection Systems, Prevention Systems and Response Systems", "Collaborative Systems, Cyber-Physical Systems, Intelligent Systems and System-of-Systems", "Management Systems, Control Systems, Communication Systems and Security Systems"}, 2));
      this.questions.add(new Question("What is the primary purpose of Operational Activity Mapping?", new String[]{"To create network diagrams and infrastructure layouts", "To identify organizational activities, their performers, and establish dependencies between activities and systems/people", "To map software dependencies and API integrations", "To track project timelines and resource allocation"}, 1));
      this.questions.add(new Question("How is cybersecurity performance assessed in information systems?", new String[]{"Through network security protocols and firewall configurations", "By implementing access controls, authentication, hardening, analysis, and isolation techniques", "Using encryption algorithms and digital signatures", "Via user training and security awareness programs"}, 1));
      this.questions.add(new Question("What is the purpose of the OS API Allocate Memory function?", new String[]{"To free up memory space by removing unused data from RAM", "To request and assign a memory region for process or application use", "To optimize memory usage by compressing data in storage", "To monitor memory consumption and generate performance reports"}, 1));
      this.questions.add(new Question("What is User Behavior Analytics (UBA) in cybersecurity?", new String[]{"A method that analyzes network traffic patterns to detect external threats and malware", "A process that detects insider threats and financial fraud by analyzing user behavior patterns and anomalies using algorithms and statistical analysis", "A technique for hardening user credentials and enforcing multi-factor authentication", "A system for inventorying user assets and managing software vulnerabilities"}, 1));
      this.questions.add(new Question("What is Network Mapping in cybersecurity?", new String[]{"A technique for encrypting network traffic and securing data transmission", "A process of identifying and modeling network layers, physical locations, and determining allowed pathways through the network", "A method for monitoring real-time network performance and bandwidth utilization", "A system for authenticating network devices and managing access permissions"}, 1));
      System.out.println("QuizServlet inicializado com " + this.questions.size() + " questões");
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession(true);
      System.out.println("=== DOGET ===");
      System.out.println("Session ID: " + session.getId());
      System.out.println("CurrentQuestion: " + session.getAttribute("currentQuestion"));
      System.out.println("Score: " + session.getAttribute("score"));
      String restart = request.getParameter("restart");
      if ("true".equals(restart) || session.getAttribute("currentQuestion") == null) {
         System.out.println("Inicializando nova sessão...");
         this.initializeSession(session);
      }

      int currentQuestion = (Integer)session.getAttribute("currentQuestion");
      if (currentQuestion >= this.questions.size()) {
         System.out.println("Quiz concluído! Redirecionando para resultados...");
         long startTime = (Long)session.getAttribute("startTime");
         long elapsedTime = System.currentTimeMillis() - startTime;
         session.setAttribute("elapsedTime", elapsedTime);
         response.sendRedirect(request.getContextPath() + "/quiz-result");
      } else {
         request.setAttribute("currentQuestion", currentQuestion);
         request.setAttribute("questions", this.questions);
         request.setAttribute("totalQuestions", this.questions.size());
         System.out.println("Encaminhando para questão: " + (currentQuestion + 1));
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/quiz.jsp");
         rd.forward(request, response);
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();
      System.out.println("=== DOPOST ===");
      System.out.println("Session ID: " + session.getId());
      System.out.println("CurrentQuestion: " + session.getAttribute("currentQuestion"));
      System.out.println("Score: " + session.getAttribute("score"));
      if (session.getAttribute("currentQuestion") == null) {
         System.out.println("Sessão não inicializada. Inicializando...");
         this.initializeSession(session);
      }

      int current = (Integer)session.getAttribute("currentQuestion");
      int score = (Integer)session.getAttribute("score");
      if (current >= this.questions.size()) {
         System.out.println("Tentativa de acessar questão além do limite: " + current);
         long startTime = (Long)session.getAttribute("startTime");
         long elapsedTime = System.currentTimeMillis() - startTime;
         session.setAttribute("elapsedTime", elapsedTime);
         response.sendRedirect(request.getContextPath() + "/quiz-result");
      } else {
         System.out.println("Processando questão " + (current + 1));
         String answerParam = request.getParameter("answer");
         System.out.println("Resposta recebida: " + answerParam);
         if (answerParam != null && !answerParam.trim().isEmpty()) {
            try {
               int answer = Integer.parseInt(answerParam);
               System.out.println("Resposta convertida: " + answer);
               System.out.println("Resposta correta: " + ((Question)this.questions.get(current)).getCorrectAnswer());
               if (answer >= 0 && answer < 4) {
                  if (answer == ((Question)this.questions.get(current)).getCorrectAnswer()) {
                     ++score;
                     session.setAttribute("score", score);
                     System.out.println("✓ Resposta CORRETA! Nova pontuação: " + score);
                  } else {
                     System.out.println("✗ Resposta INCORRETA. Pontuação mantém: " + score);
                  }
               } else {
                  System.out.println("Resposta fora do range válido: " + answer);
               }
            } catch (NumberFormatException var11) {
               System.err.println("❌ Erro ao converter resposta: " + answerParam);
            }
         } else {
            System.out.println("Nenhuma resposta recebida");
         }

         ++current;
         session.setAttribute("currentQuestion", current);
         System.out.println("Próxima questão: " + current);
         if (current < this.questions.size()) {
            response.sendRedirect(request.getContextPath() + "/quiz");
         } else {
            long startTime = (Long)session.getAttribute("startTime");
            long elapsedTime = System.currentTimeMillis() - startTime;
            session.setAttribute("elapsedTime", elapsedTime);
            System.out.println("\ud83c\udf89 Quiz concluído! Pontuação final: " + score + "/" + this.questions.size());
            System.out.println("Tempo total: " + elapsedTime / 1000L + " segundos");
            response.sendRedirect(request.getContextPath() + "/quiz-result");
         }

      }
   }

   private void initializeSession(HttpSession session) {
      session.setAttribute("score", 0);
      session.setAttribute("currentQuestion", 0);
      session.setAttribute("startTime", System.currentTimeMillis());
      System.out.println("✅ Sessão inicializada - Score: 0, CurrentQuestion: 0");
   }
}
