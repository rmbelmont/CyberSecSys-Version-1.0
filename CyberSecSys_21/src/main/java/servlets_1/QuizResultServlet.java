package servlets_1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuizResultServlet extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();
      Integer score = (Integer)session.getAttribute("score");
      Integer currentQuestion = (Integer)session.getAttribute("currentQuestion");
      Long elapsedTime = (Long)session.getAttribute("elapsedTime");
      if (score != null && currentQuestion != null) {
         int totalQuestions = 10;
         int percentage = (int)((double)score / (double)totalQuestions * 100.0D);
         String timeFormatted = this.formatTime(elapsedTime);
         request.setAttribute("score", score);
         request.setAttribute("totalQuestions", Integer.valueOf(totalQuestions));
         request.setAttribute("percentage", percentage);
         request.setAttribute("elapsedTime", timeFormatted);
         request.setAttribute("elapsedMillis", elapsedTime);
         request.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(request, response);
      } else {
         response.sendRedirect(request.getContextPath() + "/quiz?restart=true");
      }
   }

   private String formatTime(long millis) {
      long seconds = millis / 1000L;
      long minutes = seconds / 60L;
      seconds %= 60L;
      return String.format("%02d:%02d", minutes, seconds);
   }
}
