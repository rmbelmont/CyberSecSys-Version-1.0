package servlets_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/forms/chatbot")
public class Form_14 extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        try {
            CyberSecView view = new CyberSecView();
            getServletContext().setAttribute("cyberSecView", view);
            System.out.println("CyberSecView inicializado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar CyberSecView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        
        String action = req.getParameter("action");
        String userMessage = req.getParameter("message");
        String clear = req.getParameter("clear");
        String back = req.getParameter("back");
        
        HttpSession session = req.getSession();
        ChatHistoryData chatHistory = getChatHistory(session);
        
        if ("true".equals(clear)) {
            chatHistory.clear();
            res.sendRedirect(req.getContextPath() + "/forms/chatbot");
            return;
        }
        
        if ("true".equals(back)) {
            res.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        
        if (action != null && !action.trim().isEmpty()) {
            String botResponse = processAction(action, req);
            chatHistory.addMessage("bot", botResponse);
        } else if (userMessage != null && !userMessage.trim().isEmpty()) {
            chatHistory.addMessage("user", userMessage);
            String botResponse = processUserMessage(userMessage, req);
            chatHistory.addMessage("bot", botResponse);
        }
        
        renderPage(req, res, chatHistory);
    }
    
    private void renderPage(HttpServletRequest req, HttpServletResponse res, ChatHistoryData chatHistory) throws IOException {
        PrintWriter out = res.getWriter();
        String contextPath = req.getContextPath();
        
        out.println("<!DOCTYPE html>");
        out.println("<html lang='pt-BR'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Assistente de Cibersegurança</title>");
        out.println("    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css'>");
        out.println("    <link href='https://fonts.googleapis.com/css2?family=Orbitron:wght@400;500;600;700;800;900&family=Rajdhani:wght@300;400;500;600;700&display=swap' rel='stylesheet'>");
        out.println(renderStyles());
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='app'>");
        out.println("        <!-- Sidebar -->");
        out.println("        <div class='sidebar'>");
        out.println("            <div class='sidebar-header'>");
        out.println("                <div class='logo'>");
        out.println("                    <i class='fas fa-robot'></i>");
        out.println("                    <span>Cyber<span class='highlight'>Bot</span></span>");
        out.println("                </div>");
        out.println("            </div>");
        out.println("            <div class='sidebar-menu'>");
        out.println("                <form action='" + contextPath + "/forms/chatbot' method='get' id='clearForm'>");
        out.println("                    <button type='submit' name='clear' value='true' class='menu-item'>");
        out.println("                        <i class='fas fa-trash-can'></i>");
        out.println("                        <span>Limpar Chat</span>");
        out.println("                    </button>");
        out.println("                </form>");
        out.println("                <form action='" + contextPath + "/home' method='get'>");
        out.println("                    <button type='submit' class='menu-item'>");
        out.println("                        <i class='fas fa-arrow-left'></i>");
        out.println("                        <span>Voltar</span>");
        out.println("                    </button>");
        out.println("                </form>");
        out.println("            </div>");
        out.println("            <div class='sidebar-footer'>");
        out.println("                <div class='user-info'>");
        out.println("                    <i class='fas fa-user-astronaut'></i>");
        out.println("                    <span>Operador</span>");
        out.println("                </div>");
        out.println("            </div>");
        out.println("        </div>");
        
        out.println("        <!-- Main Chat Area -->");
        out.println("        <div class='chat-main'>");
        out.println("            <!-- Chat Header -->");
        out.println("            <div class='chat-header'>");
        out.println("                <div class='chat-header-left'>");
        out.println("                    <div class='chat-avatar'>");
        out.println("                        <img src='https://cdn.pixabay.com/animation/2023/05/02/04/48/04-48-10-248_512.gif' alt='Bot' class='avatar-img'>");
        out.println("                        <div class='energy-field'></div>");
        out.println("                        <span class='online-indicator'></span>");
        out.println("                    </div>");
        out.println("                    <div class='chat-info'>");
        out.println("                        <h2>CYBER<span class='glitch'>BOT</span></h2>");
        out.println("                        <span class='chat-status'><i class='fas fa-bolt'></i> ONLINE • SISTEMA ATIVO</span>");
        out.println("                    </div>");
        out.println("                </div>");
        out.println("                <div class='chat-header-right'>");
        out.println("                    <span class='badge'><i class='fas fa-microchip'></i> v2.0.1</span>");
        out.println("                </div>");
        out.println("            </div>");
        
        out.println("            <!-- Messages Container -->");
        out.println("            <div class='messages-container' id='messagesContainer'>");
        
        if (chatHistory.getMessages().isEmpty()) {
            out.println(renderWelcomeMessage());
        } else {
            out.println(renderChatHistory(chatHistory));
        }
        
        out.println("            </div>");
        
        out.println("            <!-- Quick Actions -->");
        out.println("            <div class='quick-actions-wrapper'>");
        out.println("                <div class='quick-actions-grid'>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=approach' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #00ffaa20, #00ffaa40); color: #00ffaa;'>");
        out.println("                            <i class='fas fa-route'></i>");
        out.println("                        </div>");
        out.println("                        <span>ABORDAGENS</span>");
        out.println("                    </a>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=performance' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #ff336620, #ff336640); color: #ff3366;'>");
        out.println("                            <i class='fas fa-chart-line'></i>");
        out.println("                        </div>");
        out.println("                        <span>PERFORMANCE</span>");
        out.println("                    </a>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=component' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #ffaa0020, #ffaa0040); color: #ffaa00;'>");
        out.println("                            <i class='fas fa-puzzle-piece'></i>");
        out.println("                        </div>");
        out.println("                        <span>COMPONENTES</span>");
        out.println("                    </a>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=systems' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #aa00ff20, #aa00ff40); color: #aa00ff;'>");
        out.println("                            <i class='fas fa-server'></i>");
        out.println("                        </div>");
        out.println("                        <span>SISTEMAS</span>");
        out.println("                    </a>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=osapi' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #00ccff20, #00ccff40); color: #00ccff;'>");
        out.println("                            <i class='fas fa-code'></i>");
        out.println("                        </div>");
        out.println("                        <span>API DO SO</span>");
        out.println("                    </a>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=defensive' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #00ff8820, #00ff8840); color: #00ff88;'>");
        out.println("                            <i class='fas fa-shield-halved'></i>");
        out.println("                        </div>");
        out.println("                        <span>DEFESA</span>");
        out.println("                    </a>");
        out.println("                    <a href='" + contextPath + "/forms/chatbot?action=attack' class='quick-action-card'>");
        out.println("                        <div class='quick-action-icon' style='background: linear-gradient(135deg, #ff550020, #ff550040); color: #ff5500;'>");
        out.println("                            <i class='fas fa-bug'></i>");
        out.println("                        </div>");
        out.println("                        <span>ATAQUES</span>");
        out.println("                    </a>");
        out.println("                </div>");
        out.println("            </div>");
        
        out.println("            <!-- Input Area -->");
        out.println("            <div class='input-area-wrapper'>");
        out.println("                <form action='" + contextPath + "/forms/chatbot' method='get' class='input-form' autocomplete='off'>");
        out.println("                    <div class='input-group'>");
        out.println("                        <textarea");
        out.println("                            name='message'");
        out.println("                            class='chat-textarea'");
        out.println("                            placeholder='Digite sua mensagem...'");
        out.println("                            rows='1'");
        out.println("                            required");
        out.println("                        ></textarea>");
        out.println("                        <button type='submit' class='send-btn'>");
        out.println("                            <i class='fas fa-paper-plane'></i>");
        out.println("                        </button>");
        out.println("                    </div>");
        out.println("                </form>");
        out.println("            </div>");
        out.println("        </div>");
        out.println("    </div>");
        
        out.println(renderScripts());
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
    
    private String renderStyles() {
        return """
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }
                
                body {
                    font-family: 'Rajdhani', sans-serif;
                    background: #0a0a0f;
                    height: 100vh;
                    overflow: hidden;
                }
                
                .app {
                    display: flex;
                    height: 100vh;
                    width: 100vw;
                    max-width: 1400px;
                    margin: 0 auto;
                    background: #0d0d14;
                    box-shadow: 0 0 50px rgba(0, 255, 170, 0.2);
                    border: 1px solid rgba(0, 255, 170, 0.1);
                }
                
                /* Sidebar Cyberpunk */
                .sidebar {
                    width: 280px;
                    background: linear-gradient(180deg, #0a0a12 0%, #020208 100%);
                    color: #fff;
                    display: flex;
                    flex-direction: column;
                    box-shadow: 5px 0 30px rgba(0, 255, 170, 0.15);
                    position: relative;
                    overflow: hidden;
                    border-right: 1px solid rgba(0, 255, 170, 0.3);
                }
                
                .sidebar::before {
                    content: '';
                    position: absolute;
                    top: 0;
                    left: 0;
                    right: 0;
                    height: 2px;
                    background: linear-gradient(90deg, #00ffaa, #00ccff, #aa00ff);
                    animation: scanline 3s linear infinite;
                }
                
                @keyframes scanline {
                    0% { transform: translateX(-100%); }
                    100% { transform: translateX(100%); }
                }
                
                .sidebar::after {
                    content: '';
                    position: absolute;
                    top: 0;
                    left: 0;
                    right: 0;
                    bottom: 0;
                    background: repeating-linear-gradient(0deg, rgba(0,255,170,0.03) 0px, rgba(0,0,0,0) 1px, transparent 2px);
                    pointer-events: none;
                }
                
                .sidebar-header {
                    padding: 30px 24px;
                    border-bottom: 1px solid rgba(0, 255, 170, 0.2);
                }
                
                .logo {
                    display: flex;
                    align-items: center;
                    gap: 12px;
                    font-size: 1.5rem;
                    font-weight: 700;
                    font-family: 'Orbitron', sans-serif;
                    text-transform: uppercase;
                    letter-spacing: 2px;
                }
                
                .logo i {
                    color: #00ffaa;
                    font-size: 2rem;
                    filter: drop-shadow(0 0 10px #00ffaa);
                    animation: pulse 2s infinite;
                }
                
                .logo .highlight {
                    color: #00ffaa;
                    text-shadow: 0 0 10px #00ffaa;
                }
                
                .sidebar-menu {
                    flex: 1;
                    padding: 24px 16px;
                    display: flex;
                    flex-direction: column;
                    gap: 8px;
                }
                
                .menu-item {
                    display: flex;
                    align-items: center;
                    gap: 14px;
                    padding: 14px 20px;
                    width: 100%;
                    border: none;
                    background: transparent;
                    color: #8a8aa8;
                    border-radius: 8px;
                    cursor: pointer;
                    transition: all 0.3s ease;
                    font-size: 0.95rem;
                    font-weight: 500;
                    position: relative;
                    overflow: hidden;
                    border: 1px solid transparent;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                
                .menu-item::before {
                    content: '';
                    position: absolute;
                    left: 0;
                    top: 0;
                    height: 100%;
                    width: 0;
                    background: linear-gradient(90deg, rgba(0, 255, 170, 0.2), transparent);
                    transition: width 0.3s ease;
                    z-index: 0;
                }
                
                .menu-item:hover::before {
                    width: 100%;
                }
                
                .menu-item:hover {
                    color: #00ffaa;
                    border-color: rgba(0, 255, 170, 0.3);
                    transform: translateX(5px);
                    text-shadow: 0 0 8px #00ffaa;
                }
                
                .menu-item i, .menu-item span {
                    position: relative;
                    z-index: 1;
                }
                
                .menu-item i {
                    width: 22px;
                    font-size: 1.2rem;
                }
                
                .sidebar-footer {
                    padding: 24px;
                    border-top: 1px solid rgba(0, 255, 170, 0.2);
                }
                
                .user-info {
                    display: flex;
                    align-items: center;
                    gap: 12px;
                    color: #8a8aa8;
                    padding: 10px;
                    border-radius: 8px;
                    background: rgba(0, 255, 170, 0.05);
                    border: 1px solid rgba(0, 255, 170, 0.1);
                }
                
                .user-info i {
                    font-size: 1.8rem;
                    color: #00ffaa;
                    filter: drop-shadow(0 0 5px #00ffaa);
                }
                
                /* Main Chat Area */
                .chat-main {
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    background: #0a0a12;
                }
                
                /* Chat Header */
                .chat-header {
                    padding: 20px 30px;
                    background: rgba(10, 10, 18, 0.95);
                    border-bottom: 1px solid rgba(0, 255, 170, 0.3);
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    backdrop-filter: blur(10px);
                    position: relative;
                }
                
                .chat-header::before {
                    content: '';
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    height: 1px;
                    background: linear-gradient(90deg, transparent, #00ffaa, #00ccff, #aa00ff, transparent);
                }
                
                .chat-header-left {
                    display: flex;
                    align-items: center;
                    gap: 20px;
                }
                
                .chat-avatar {
                    position: relative;
                    width: 70px;
                    height: 70px;
                    border-radius: 50%;
                    overflow: hidden;
                    border: 2px solid #00ffaa;
                    box-shadow: 0 0 30px rgba(0, 255, 170, 0.5);
                    animation: glowPulse 2s infinite;
                }
                
                @keyframes glowPulse {
                    0%, 100% { box-shadow: 0 0 30px rgba(0, 255, 170, 0.5); }
                    50% { box-shadow: 0 0 50px rgba(0, 255, 170, 0.8); }
                }
                
                .avatar-img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
                
                .energy-field {
                    position: absolute;
                    top: -10px;
                    left: -10px;
                    right: -10px;
                    bottom: -10px;
                    border-radius: 50%;
                    border: 2px solid transparent;
                    border-top-color: #00ffaa;
                    border-bottom-color: #00ccff;
                    animation: rotate 3s linear infinite;
                    pointer-events: none;
                }
                
                @keyframes rotate {
                    from { transform: rotate(0deg); }
                    to { transform: rotate(360deg); }
                }
                
                .online-indicator {
                    position: absolute;
                    bottom: 3px;
                    right: 3px;
                    width: 16px;
                    height: 16px;
                    background: #00ffaa;
                    border: 3px solid #0a0a12;
                    border-radius: 50%;
                    z-index: 2;
                    animation: pulse 1.5s infinite;
                }
                
                @keyframes pulse {
                    0%, 100% { opacity: 1; transform: scale(1); }
                    50% { opacity: 0.5; transform: scale(1.2); }
                }
                
                .chat-info h2 {
                    font-size: 1.8rem;
                    font-weight: 800;
                    font-family: 'Orbitron', sans-serif;
                    background: linear-gradient(135deg, #fff, #00ffaa);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                    margin-bottom: 5px;
                    letter-spacing: 3px;
                }
                
                .glitch {
                    position: relative;
                    animation: glitch 3s infinite;
                }
                
                @keyframes glitch {
                    0%, 100% { transform: skew(0deg); opacity: 1; }
                    95% { transform: skew(0deg); opacity: 1; }
                    96% { transform: skew(5deg); opacity: 0.8; }
                    97% { transform: skew(-5deg); opacity: 0.9; }
                    98% { transform: skew(2deg); opacity: 0.7; }
                    99% { transform: skew(0deg); opacity: 1; }
                }
                
                .chat-status {
                    font-size: 0.9rem;
                    color: #00ffaa;
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    text-transform: uppercase;
                    letter-spacing: 2px;
                }
                
                .chat-status i {
                    font-size: 0.8rem;
                    animation: spark 1.5s infinite;
                }
                
                @keyframes spark {
                    0%, 100% { opacity: 1; transform: scale(1); }
                    50% { opacity: 0.5; transform: scale(1.5); }
                }
                
                .badge {
                    padding: 8px 16px;
                    background: rgba(0, 255, 170, 0.1);
                    border-radius: 30px;
                    font-size: 0.9rem;
                    font-weight: 600;
                    color: #00ffaa;
                    border: 1px solid rgba(0, 255, 170, 0.3);
                    backdrop-filter: blur(5px);
                    display: flex;
                    align-items: center;
                    gap: 6px;
                }
                
                .badge i {
                    font-size: 1rem;
                }
                
                /* Messages Container */
                .messages-container {
                    flex: 1;
                    overflow-y: auto;
                    padding: 30px;
                    background: #0a0a12;
                    background-image: 
                        radial-gradient(circle at 20% 30%, rgba(0, 255, 170, 0.03) 0%, transparent 20%),
                        radial-gradient(circle at 80% 70%, rgba(0, 204, 255, 0.03) 0%, transparent 20%);
                }
                
                .message {
                    display: flex;
                    gap: 16px;
                    margin-bottom: 24px;
                    animation: slideIn 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
                }
                
                .message.user {
                    flex-direction: row-reverse;
                }
                
                @keyframes slideIn {
                    from {
                        opacity: 0;
                        transform: translateY(30px) scale(0.9);
                    }
                    to {
                        opacity: 1;
                        transform: translateY(0) scale(1);
                    }
                }
                
                .message-avatar {
                    width: 45px;
                    height: 45px;
                    border-radius: 50%;
                    overflow: hidden;
                    flex-shrink: 0;
                    border: 2px solid #00ffaa;
                    box-shadow: 0 0 20px rgba(0, 255, 170, 0.3);
                }
                
                .message-avatar img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
                
                .message-avatar i {
                    width: 100%;
                    height: 100%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    background: linear-gradient(135deg, #00ffaa, #00ccff);
                    color: #0a0a12;
                    font-size: 1.3rem;
                }
                
                .message-content {
                    max-width: 70%;
                    padding: 16px 22px;
                    border-radius: 20px;
                    position: relative;
                    line-height: 1.6;
                    font-size: 0.95rem;
                    box-shadow: 0 4px 20px rgba(0, 255, 170, 0.15);
                    border: 1px solid rgba(0, 255, 170, 0.2);
                    backdrop-filter: blur(10px);
                }
                
                .user .message-content {
                    background: rgba(0, 255, 170, 0.15);
                    color: #fff;
                    border-bottom-right-radius: 4px;
                }
                
                .bot .message-content {
                    background: rgba(20, 20, 30, 0.9);
                    color: #e0e0ff;
                    border-bottom-left-radius: 4px;
                }
                
                .message-content::before {
                    content: '';
                    position: absolute;
                    bottom: 0;
                    width: 20px;
                    height: 20px;
                    background: inherit;
                    border: inherit;
                }
                
                .user .message-content::before {
                    right: -8px;
                    border-bottom-left-radius: 50%;
                    box-shadow: -4px 4px 0 0 rgba(0, 255, 170, 0.15);
                }
                
                .bot .message-content::before {
                    left: -8px;
                    border-bottom-right-radius: 50%;
                    box-shadow: 4px 4px 0 0 rgba(20, 20, 30, 0.9);
                }
                
                .message-content p {
                    margin-bottom: 0.8rem;
                }
                
                .message-content p:last-child {
                    margin-bottom: 0;
                }
                
                .message-content strong {
                    font-weight: 700;
                    color: #00ffaa;
                }
                
                .user .message-content strong {
                    color: #fff;
                    text-shadow: 0 0 10px rgba(255,255,255,0.5);
                }
                
                .message-content ul, .message-content ol {
                    margin: 0.8rem 0 0.8rem 1.5rem;
                }
                
                .message-content li {
                    margin-bottom: 0.4rem;
                }
                
                /* Welcome Message */
                .welcome-message {
                    text-align: center;
                    padding: 50px 30px;
                    max-width: 600px;
                    margin: 40px auto;
                    background: rgba(20, 20, 30, 0.7);
                    border-radius: 40px;
                    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.5), 0 0 50px rgba(0, 255, 170, 0.2);
                    border: 1px solid rgba(0, 255, 170, 0.3);
                    backdrop-filter: blur(10px);
                    animation: floatIn 0.6s ease;
                }
                
                @keyframes floatIn {
                    from {
                        opacity: 0;
                        transform: translateY(50px);
                    }
                    to {
                        opacity: 1;
                        transform: translateY(0);
                    }
                }
                
                .welcome-avatar {
                    width: 150px;
                    height: 150px;
                    margin: 0 auto 25px;
                    border-radius: 50%;
                    overflow: hidden;
                    border: 3px solid #00ffaa;
                    padding: 5px;
                    box-shadow: 0 0 50px rgba(0, 255, 170, 0.5);
                    animation: float 6s ease-in-out infinite;
                }
                
                @keyframes float {
                    0%, 100% { transform: translateY(0px); }
                    50% { transform: translateY(-10px); }
                }
                
                .welcome-avatar img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    border-radius: 50%;
                    border: 2px solid #00ffaa;
                }
                
                .welcome-message h1 {
                    font-size: 2.2rem;
                    font-weight: 800;
                    font-family: 'Orbitron', sans-serif;
                    margin-bottom: 20px;
                    background: linear-gradient(135deg, #fff, #00ffaa);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                    text-transform: uppercase;
                    letter-spacing: 4px;
                }
                
                .welcome-message p {
                    color: #a0a0cc;
                    font-size: 1.1rem;
                    line-height: 1.7;
                }
                
                /* Quick Actions */
                .quick-actions-wrapper {
                    padding: 20px 30px;
                    background: rgba(10, 10, 18, 0.95);
                    border-top: 1px solid rgba(0, 255, 170, 0.3);
                    backdrop-filter: blur(10px);
                }
                
                .quick-actions-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
                    gap: 15px;
                }
                
                .quick-action-card {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    gap: 10px;
                    padding: 16px 12px;
                    background: rgba(20, 20, 30, 0.7);
                    border-radius: 20px;
                    text-decoration: none;
                    color: #fff;
                    transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
                    border: 1px solid rgba(0, 255, 170, 0.2);
                    backdrop-filter: blur(5px);
                    position: relative;
                    overflow: hidden;
                }
                
                .quick-action-card::before {
                    content: '';
                    position: absolute;
                    top: 0;
                    left: -100%;
                    width: 100%;
                    height: 100%;
                    background: linear-gradient(90deg, transparent, rgba(0, 255, 170, 0.2), transparent);
                    transition: left 0.5s ease;
                }
                
                .quick-action-card:hover::before {
                    left: 100%;
                }
                
                .quick-action-card:hover {
                    transform: translateY(-8px) scale(1.02);
                    border-color: #00ffaa;
                    box-shadow: 0 15px 30px rgba(0, 255, 170, 0.3);
                }
                
                .quick-action-icon {
                    width: 60px;
                    height: 60px;
                    border-radius: 50%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    font-size: 1.6rem;
                    transition: all 0.3s ease;
                }
                
                .quick-action-card:hover .quick-action-icon {
                    transform: scale(1.1) rotate(360deg);
                }
                
                .quick-action-card span {
                    font-size: 0.85rem;
                    font-weight: 600;
                    text-align: center;
                    letter-spacing: 1px;
                }
                
                /* Input Area */
                .input-area-wrapper {
                    padding: 20px 30px;
                    background: rgba(10, 10, 18, 0.95);
                    border-top: 1px solid rgba(0, 255, 170, 0.3);
                    backdrop-filter: blur(10px);
                }
                
                .input-group {
                    display: flex;
                    gap: 15px;
                    align-items: flex-end;
                    background: rgba(20, 20, 30, 0.7);
                    border: 2px solid rgba(0, 255, 170, 0.3);
                    border-radius: 40px;
                    padding: 8px 8px 8px 25px;
                    transition: all 0.3s ease;
                }
                
                .input-group:focus-within {
                    border-color: #00ffaa;
                    background: rgba(30, 30, 40, 0.9);
                    box-shadow: 0 0 0 6px rgba(0, 255, 170, 0.15);
                    transform: translateY(-2px);
                }
                
                .chat-textarea {
                    flex: 1;
                    border: none;
                    background: transparent;
                    padding: 14px 0;
                    font-size: 1rem;
                    resize: none;
                    max-height: 140px;
                    outline: none;
                    font-family: inherit;
                    color: #fff;
                }
                
                .chat-textarea::placeholder {
                    color: #8a8aa8;
                }
                
                .send-btn {
                    width: 55px;
                    height: 55px;
                    border: none;
                    background: linear-gradient(135deg, #00ffaa, #00ccff);
                    color: #0a0a12;
                    border-radius: 50%;
                    cursor: pointer;
                    transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    font-size: 1.3rem;
                    box-shadow: 0 4px 20px rgba(0, 255, 170, 0.5);
                }
                
                .send-btn:hover {
                    transform: scale(1.1) rotate(15deg);
                    box-shadow: 0 6px 30px rgba(0, 255, 170, 0.8);
                }
                
                /* Scrollbar */
                .messages-container::-webkit-scrollbar {
                    width: 8px;
                }
                
                .messages-container::-webkit-scrollbar-track {
                    background: #1a1a24;
                    border-radius: 4px;
                }
                
                .messages-container::-webkit-scrollbar-thumb {
                    background: linear-gradient(135deg, #00ffaa, #00ccff);
                    border-radius: 4px;
                }
                
                .messages-container::-webkit-scrollbar-thumb:hover {
                    background: linear-gradient(135deg, #00ccff, #00ffaa);
                }
                
                /* Responsive */
                @media (max-width: 768px) {
                    .sidebar {
                        display: none;
                    }
                    
                    .app {
                        width: 100%;
                        border-radius: 0;
                    }
                    
                    .message-content {
                        max-width: 85%;
                    }
                    
                    .quick-actions-grid {
                        grid-template-columns: repeat(4, 1fr);
                        gap: 8px;
                    }
                    
                    .quick-action-card {
                        padding: 12px 8px;
                    }
                    
                    .quick-action-icon {
                        width: 45px;
                        height: 45px;
                        font-size: 1.2rem;
                    }
                    
                    .chat-info h2 {
                        font-size: 1.2rem;
                    }
                }
            </style>
        """;
    }
    
    private String renderWelcomeMessage() {
        return """
            <div class='welcome-message'>
                <div class='welcome-avatar'>
                    <img src='https://cdn.pixabay.com/animation/2023/05/02/04/48/04-48-10-248_512.gif' alt='Bot Avatar'>
                </div>
                <h1>⚡ SECBOT ONLINE ⚡</h1>
                <p>Sistema de inteligência artificial especializado em cibersegurança. Inicializando protocolos de defesa... Aguardando comandos, operador.</p>
            </div>
        """;
    }
    
    private String renderChatHistory(ChatHistoryData chatHistory) {
        StringBuilder html = new StringBuilder();
        
        for (ChatMessage message : chatHistory.getMessages()) {
            String messageClass = "user".equals(message.getSender()) ? "user" : "bot";
            
            html.append("<div class='message ").append(messageClass).append("'>");
            html.append("    <div class='message-avatar'>");
            
            if ("user".equals(message.getSender())) {
                html.append("        <i class='fas fa-user-astronaut'></i>");
            } else {
                html.append("        <img src='https://cdn.pixabay.com/animation/2023/05/02/04/48/04-48-10-248_512.gif' alt='Bot'>");
            }
            
            html.append("    </div>");
            html.append("    <div class='message-content'>");
            
            String content = message.getContent();
            content = content.replace("**", "<strong>").replace("**", "</strong>");
            content = content.replace("\n", "<br>");
            
            html.append("<p>").append(content).append("</p>");
            html.append("    </div>");
            html.append("</div>");
        }
        
        return html.toString();
    }
    
    private String renderScripts() {
        return """
            <script>
                // Auto-scroll para o final
                const messagesContainer = document.getElementById('messagesContainer');
                if (messagesContainer) {
                    messagesContainer.scrollTop = messagesContainer.scrollHeight;
                }
                
                // Auto-resize do textarea
                const textarea = document.querySelector('.chat-textarea');
                if (textarea) {
                    textarea.addEventListener('input', function() {
                        this.style.height = 'auto';
                        this.style.height = Math.min(this.scrollHeight, 140) + 'px';
                    });
                    
                    textarea.addEventListener('keydown', function(e) {
                        if (e.key === 'Enter' && !e.shiftKey) {
                            e.preventDefault();
                            if (this.value.trim()) {
                                this.form.submit();
                            }
                        }
                    });
                    
                    textarea.focus();
                }
                
                // Confirmar limpeza do chat
                document.getElementById('clearForm')?.addEventListener('submit', function(e) {
                    if (!confirm('>> ATENÇÃO: Limpar todos os dados da conversa? <<')) {
                        e.preventDefault();
                    }
                });
                
                // Efeito de loading nos botões
                document.querySelectorAll('form').forEach(form => {
                    form.addEventListener('submit', function() {
                        const btn = this.querySelector('button[type=submit]');
                        if (btn && !btn.classList.contains('menu-item')) {
                            btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
                            btn.disabled = true;
                        }
                    });
                });
                
                // Observer para novas mensagens
                const observer = new MutationObserver(function(mutations) {
                    mutations.forEach(function(mutation) {
                        if (mutation.addedNodes.length > 0) {
                            messagesContainer.scrollTo({
                                top: messagesContainer.scrollHeight,
                                behavior: 'smooth'
                            });
                        }
                    });
                });
                
                if (messagesContainer) {
                    observer.observe(messagesContainer, { childList: true, subtree: true });
                }
            </script>
        """;
    }
    
    private String processUserMessage(String userMessage, HttpServletRequest req) {
        String normalizedMessage = userMessage.toLowerCase().trim();
        
        try {
            CyberSecView view = (CyberSecView) req.getServletContext().getAttribute("cyberSecView");
            
            if (view == null) {
                return "⚠️ **ERRO DE CONEXÃO** ⚠️\n\nSistema temporariamente indisponível. Tente novamente.";
            }
            
            if (containsAny(normalizedMessage, "hello", "hi", "hey", "ola", "olá", "oi", "bom dia", "boa tarde", "boa noite")) {
                return "🤖 **SAUDAÇÕES, OPERADOR!** 🤖\n\n" +
                       "Sistema de Cibersegurança ativo. Protocolos:\n\n" +
                       "▸ ABORDAGENS - Frameworks de segurança\n" +
                       "▸ PERFORMANCE - Métricas e KPIs\n" +
                       "▸ COMPONENTES - Arquitetura\n" +
                       "▸ SISTEMAS - Soluções\n" +
                       "▸ API DO SO - Funções do sistema\n" +
                       "▸ DEFESA - Técnicas\n" +
                       "▸ ATAQUES - Mitigações\n\n" +
                       "**Aguardando comando...**";
            }
            
            if (containsAny(normalizedMessage, "approach", "abordagem", "método", "method", "estratégia", "strategy")) {
                return buildListResponse("📚 **ABORDAGENS DE SEGURANÇA**", 
                    "Protocolos de segurança estabelecidos:", 
                    view.getApproach(),
                    "💠 **STATUS:** Protocolos carregados e prontos para implementação.");
            }
            
            if (containsAny(normalizedMessage, "performance", "metric", "métrica", "mttd", "mttr", "indicador", "kpi")) {
                return buildListResponse("📊 **MÉTRICAS DE PERFORMANCE**", 
                    "Monitorando indicadores críticos:", 
                    view.getPerformanceEvaluation(),
                    "📈 **ANÁLISE:** Sistemas operando dentro dos parâmetros.");
            }
            
            if (containsAny(normalizedMessage, "component", "componente", "element", "elemento")) {
                return buildListResponse("⚙️ **COMPONENTES DO SISTEMA**", 
                    "Arquitetura de defesa em camadas:", 
                    view.getComponent(),
                    "🛡️ **INTEGRIDADE:** Todos os componentes verificados.");
            }
            
            if (containsAny(normalizedMessage, "system", "sistema", "ferramenta", "tool", "software")) {
                return buildListResponse("💻 **SISTEMAS ESPECIALIZADOS**", 
                    "Soluções implementadas:", 
                    view.getSystems(),
                    "🔧 **STATUS:** Sistemas sincronizados e operacionais.");
            }
            
            if (containsAny(normalizedMessage, "os", "api", "function", "função", "operating", "sistema operacional")) {
                return buildListResponse("🖥️ **FUNÇÕES DE API DO SO**", 
                    "APIs de segurança disponíveis:", 
                    view.getOSAPISystemFunctions3(),
                    "⚡ **ACESSO:** APIs liberadas para consulta.");
            }
            
            if (containsAny(normalizedMessage, "defensive", "defesa", "defensiva", "protection", "proteção")) {
                return buildListResponse("🛡️ **TÉCNICAS DEFENSIVAS**", 
                    "Estratégias de proteção ativas:", 
                    view.getDefensiveTechnique7(),
                    "🎯 **PROTEÇÃO:** Barreiras de defesa estabelecidas.");
            }
            
            if (containsAny(normalizedMessage, "attack", "ataque", "mitigation", "mitigação", "ransomware", "malware")) {
                return buildListResponse("🎯 **MITIGAÇÃO DE ATAQUES**", 
                    "Protocolos contra ameaças:", 
                    view.getAttackEnterpriseMitigation1(),
                    "⚠️ **ALERTA:** Medidas preventivas ativadas.");
            }
            
            if (containsAny(normalizedMessage, "help", "ajuda", "suporte", "support", "comandos")) {
                return "ℹ️ **COMANDOS DISPONÍVEIS** ℹ️\n\n" +
                       "▸ `abordagens` - Frameworks de segurança\n" +
                       "▸ `performance` - Métricas e indicadores\n" +
                       "▸ `componentes` - Arquitetura de defesa\n" +
                       "▸ `sistemas` - Soluções implementadas\n" +
                       "▸ `api` - Funções do sistema operacional\n" +
                       "▸ `defesa` - Técnicas defensivas\n" +
                       "▸ `ataques` - Mitigação de ameaças\n" +
                       "▸ `sobre` - Informações do sistema\n\n" +
                       "**Utilize os cards coloridos para acesso rápido.**";
            }
            
            if (containsAny(normalizedMessage, "sobre", "about", "quem é você", "quem é")) {
                return "🤖 **IDENTIFICAÇÃO DO SISTEMA** 🤖\n\n" +
                       "**Designação:** CyberBot v2.0.1\n" +
                       "**Especialidade:** Cibersegurança Corporativa\n" +
                       "**Base de conhecimento:** NIST, ISO 27001, MITRE ATT&CK\n" +
                       "**Desenvolvimento:** PPGI - UNIRIO\n" +
                       "**Status:** 🟢 ONLINE\n" +
                       "**Nível de segurança:** MÁXIMO";
            }
            
            if (containsAny(normalizedMessage, "thank", "thanks", "obrigado", "valeu", "obrigada")) {
                return "😊 **MISSÃO CUMPRIDA** 😊\n\n" +
                       "Sempre à disposição para proteger seus sistemas.\n" +
                       "**\"A segurança é nossa prioridade.\"** 🛡️";
            }
            
            return "🤔 **COMANDO NÃO RECONHECIDO** 🤔\n\n" +
                "Áreas de conhecimento disponíveis:\n\n" +
                "▸ **ABORDAGENS** - Frameworks e metodologias\n" +
                "▸ **PERFORMANCE** - Métricas e KPIs\n" +
                "▸ **COMPONENTES** - Elementos de arquitetura\n" +
                "▸ **SISTEMAS** - Ferramentas e soluções\n" +
                "▸ **API DO SO** - Funções do sistema\n" +
                "▸ **DEFESA** - Técnicas de proteção\n" +
                "▸ **ATAQUES** - Mitigação de ameaças\n\n" +
                "**Digite 'help' para lista completa de comandos.**";
                
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ **ERRO CRÍTICO NO SISTEMA** ❌\n\nFalha na comunicação. Reiniciando protocolos... Tente novamente.";
        }
    }
    
    private String processAction(String action, HttpServletRequest req) {
        try {
            CyberSecView view = (CyberSecView) req.getServletContext().getAttribute("cyberSecView");
            
            if (view == null) {
                return "⚠️ **ERRO DE CONEXÃO** ⚠️\n\nSistema temporariamente indisponível.";
            }
            
            switch (action) {
                case "approach":
                    return buildListResponse("📚 **ABORDAGENS DE SEGURANÇA**", 
                        "Protocolos de segurança estabelecidos:", 
                        view.getApproach(),
                        "💠 **STATUS:** Protocolos carregados e prontos para implementação.");
                    
                case "performance":
                    return buildListResponse("📊 **MÉTRICAS DE PERFORMANCE**", 
                        "Monitorando indicadores críticos:", 
                        view.getPerformanceEvaluation(),
                        "📈 **ANÁLISE:** Sistemas operando dentro dos parâmetros.");
                    
                case "component":
                    return buildListResponse("⚙️ **COMPONENTES DO SISTEMA**", 
                        "Arquitetura de defesa em camadas:", 
                        view.getComponent(),
                        "🛡️ **INTEGRIDADE:** Todos os componentes verificados.");
                    
                case "systems":
                    return buildListResponse("💻 **SISTEMAS ESPECIALIZADOS**", 
                        "Soluções implementadas:", 
                        view.getSystems(),
                        "🔧 **STATUS:** Sistemas sincronizados e operacionais.");
                    
                case "osapi":
                    return buildListResponse("🖥️ **FUNÇÕES DE API DO SO**", 
                        "APIs de segurança disponíveis:", 
                        view.getOSAPISystemFunctions(),
                        "⚡ **ACESSO:** APIs liberadas para consulta.");
                    
                case "defensive":
                    return buildListResponse("🛡️ **TÉCNICAS DEFENSIVAS**", 
                        "Estratégias de proteção ativas:", 
                        view.getDefensiveTechnique7(),
                        "🎯 **PROTEÇÃO:** Barreiras de defesa estabelecidas.");
                    
                case "attack":
                    return buildListResponse("🎯 **MITIGAÇÃO DE ATAQUES**", 
                        "Protocolos contra ameaças:", 
                        view.getAttackEnterpriseMitigation(),
                        "⚠️ **ALERTA:** Medidas preventivas ativadas.");
                    
                default:
                    return "🤔 **COMANDO NÃO RECONHECIDO**\n\nUtilize um dos comandos disponíveis nos cards acima.";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ **ERRO NO PROCESSAMENTO** ❌\n\nFalha ao acessar dados. Reiniciando...";
        }
    }
    
    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    private String buildListResponse(String title, String intro, List<String> items, String footer) {
        StringBuilder response = new StringBuilder();
        response.append(title).append("\n\n");
        response.append(intro).append("\n\n");
        
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                response.append("**").append(i + 1).append(".** ").append(items.get(i)).append("\n");
            }
        } else {
            response.append("*Informação não disponível no momento*\n");
        }
        
        response.append("\n").append(footer);
        return response.toString();
    }
    
    private ChatHistoryData getChatHistory(HttpSession session) {
        ChatHistoryData chatHistory = (ChatHistoryData) session.getAttribute("chatHistory");
        if (chatHistory == null) {
            chatHistory = new ChatHistoryData();
            session.setAttribute("chatHistory", chatHistory);
        }
        return chatHistory;
    }
}


