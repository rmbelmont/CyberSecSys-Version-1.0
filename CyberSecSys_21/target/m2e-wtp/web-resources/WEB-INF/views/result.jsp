<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get session attributes with safe default values
    Integer score = (Integer) session.getAttribute("score");
    Long elapsedTime = (Long) session.getAttribute("elapsedTime");
    
    // DEBUG
    System.out.println("=== MISSION_RESULT.JSP ===");
    System.out.println("Session score: " + score);
    System.out.println("Session elapsedTime: " + elapsedTime);
    System.out.println("Session ID: " + session.getId());
    
    // Default values if attributes don't exist
    if (score == null) {
        score = 0;
        System.out.println("Score was null, set to 0");
    }
    if (elapsedTime == null) {
        elapsedTime = 0L;
        System.out.println("ElapsedTime was null, set to 0");
    }
    
    int totalQuestions = 10;
    double percentage = totalQuestions > 0 ? (score * 100.0) / totalQuestions : 0;
    
    // Determine emoji, message and color based on score
    String trophy = "🏆";
    String message = "";
    String messageColor = "";
    String rating = "";
    String rankColor = "";
    
    if (percentage >= 90) {
        trophy = "🏆";
        message = "EXCELLENT! CYBER SECURITY MASTER DETECTED!";
        messageColor = "#00ff88";
        rating = "ELITE OPERATIVE";
        rankColor = "#00ff88";
    } else if (percentage >= 80) {
        trophy = "⭐";
        message = "IMPRESSIVE! YOUR SKILLS ARE REMARKABLE!";
        messageColor = "#00ccff";
        rating = "SENIOR AGENT";
        rankColor = "#00ccff";
    } else if (percentage >= 70) {
        trophy = "🎯";
        message = "OUTSTANDING! MISSION ACCOMPLISHED!";
        messageColor = "#3742fa";
        rating = "FIELD SPECIALIST";
        rankColor = "#3742fa";
    } else if (percentage >= 60) {
        trophy = "👍";
        message = "GOOD WORK! CONTINUE YOUR TRAINING!";
        messageColor = "#ffa502";
        rating = "SECURITY ANALYST";
        rankColor = "#ffa502";
    } else if (percentage >= 50) {
        trophy = "💪";
        message = "AVERAGE PERFORMANCE! ROOM FOR IMPROVEMENT!";
        messageColor = "#ff9ff3";
        rating = "JUNIOR AGENT";
        rankColor = "#ff9ff3";
    } else {
        trophy = "📚";
        message = "MORE TRAINING REQUIRED! DON'T GIVE UP!";
        messageColor = "#ff4757";
        rating = "RECRUIT";
        rankColor = "#ff4757";
    }
    
    // Calculate formatted time
    long totalSeconds = elapsedTime / 1000;
    long minutes = totalSeconds / 60;
    long seconds = totalSeconds % 60;
    String formattedTime = String.format("%02d:%02d", minutes, seconds);
    
    // Time per question
    double timePerQuestion = totalQuestions > 0 ? (double) totalSeconds / totalQuestions : 0;
    String timePerQuestionStr = String.format("%.1f", timePerQuestion);
    
    System.out.println("Final result: " + score + "/" + totalQuestions + " (" + percentage + "%)");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mission Complete - Cyber Security Assessment</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #1a2a6c, #2d388a, #4a4ab8);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
        }

        .mission-report {
            background: linear-gradient(135deg, #0f3460, #1a1a2e);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.4);
            width: 90%;
            max-width: 800px;
            text-align: center;
            position: relative;
            overflow: hidden;
            border: 3px solid #00ff88;
        }

        .mission-report::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 5px;
            background: linear-gradient(90deg, #00ff88, #00ccff, #ff6b6b);
        }

        .mission-badge {
            position: absolute;
            top: -15px;
            right: 20px;
            background: #ff6b6b;
            color: white;
            padding: 8px 20px;
            border-radius: 25px;
            font-size: 0.9em;
            font-weight: bold;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0%, 100% { transform: scale(1); }
            50% { transform: scale(1.05); }
        }

        .rank-icon {
            font-size: 5em;
            margin-bottom: 10px;
            animation: bounce 2s infinite;
        }

        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% { transform: translateY(0); }
            40% { transform: translateY(-15px); }
            60% { transform: translateY(-7px); }
        }

        .rank-badge {
            display: inline-block;
            background: <%= rankColor %>;
            color: #1a1a2e;
            padding: 10px 25px;
            border-radius: 50px;
            font-size: 1.1em;
            font-weight: bold;
            margin-bottom: 20px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            border: 2px solid <%= messageColor %>;
        }

        h1 {
            color: #00ff88;
            margin-bottom: 10px;
            font-size: 2.5em;
            text-shadow: 0 0 10px rgba(0, 255, 136, 0.5);
        }

        .mission-status {
            color: #00ccff;
            font-size: 1.2em;
            margin-bottom: 30px;
            font-family: 'Courier New', monospace;
        }

        .score-terminal {
            font-size: 4em;
            font-weight: bold;
            background: linear-gradient(135deg, #00ff88, #00ccff);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin: 20px 0;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
            font-family: 'Courier New', monospace;
        }

        .score-text {
            font-size: 1.3em;
            color: #00ccff;
            margin-bottom: 10px;
            font-family: 'Courier New', monospace;
        }

        .performance-terminal {
            font-size: 1.2em;
            color: #00ff88;
            margin: 25px 0;
            padding: 20px;
            border-radius: 15px;
            background: rgba(26, 42, 108, 0.8);
            border: 2px solid <%= messageColor %>;
            font-family: 'Courier New', monospace;
            text-shadow: 0 0 5px rgba(0, 255, 136, 0.5);
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 15px;
            margin: 30px 0;
        }

        .stat-terminal {
            background: linear-gradient(135deg, #2d388a, #1a2a6c);
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            text-align: center;
            border: 2px solid #00ccff;
            transition: transform 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .stat-terminal:hover {
            transform: translateY(-5px);
            border-color: #00ff88;
            box-shadow: 0 8px 25px rgba(0, 255, 136, 0.3);
        }

        .stat-icon {
            font-size: 2em;
            margin-bottom: 10px;
            color: #00ff88;
        }

        .stat-value {
            font-size: 1.8em;
            font-weight: bold;
            color: #00ff88;
            margin-bottom: 5px;
            font-family: 'Courier New', monospace;
        }

        .stat-label {
            color: #00ccff;
            font-size: 0.9em;
            font-family: 'Courier New', monospace;
        }

        .intel-report {
            background: rgba(26, 42, 108, 0.8);
            padding: 20px;
            border-radius: 12px;
            margin: 25px 0;
            border: 2px solid #00ccff;
            position: relative;
        }

        .intel-report::before {
            content: 'INTEL_REPORT';
            position: absolute;
            top: -10px;
            left: 20px;
            background: #00ccff;
            color: #1a1a2e;
            padding: 3px 10px;
            border-radius: 10px;
            font-size: 0.8em;
            font-weight: bold;
            font-family: 'Courier New', monospace;
        }

        .intel-title {
            color: #00ccff;
            font-size: 1.2em;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
            font-family: 'Courier New', monospace;
        }

        .intel-breakdown {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-top: 15px;
        }

        .intel-item {
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            border-bottom: 1px solid rgba(0, 204, 255, 0.3);
            font-family: 'Courier New', monospace;
        }

        .intel-item:last-child {
            border-bottom: none;
        }

        .intel-label {
            color: #00ccff;
        }

        .intel-value {
            font-weight: bold;
            color: #00ff88;
        }

        .mission-controls {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 30px;
        }

        .cyber-btn {
            padding: 15px 25px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1em;
            font-weight: bold;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            min-width: 160px;
            font-family: 'Segoe UI', sans-serif;
        }

        .restart-mission {
            background: linear-gradient(135deg, #00ff88, #00cc66);
            color: #1a1a2e;
            border: 2px solid #00ff88;
        }

        .restart-mission:hover {
            background: linear-gradient(135deg, #00cc66, #00aa44);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 255, 136, 0.4);
        }

        .command-center {
            background: linear-gradient(135deg, #00ccff, #0099cc);
            color: white;
            border: 2px solid #00ccff;
        }

        .command-center:hover {
            background: linear-gradient(135deg, #0099cc, #0077aa);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 204, 255, 0.4);
        }

        .progress-ring {
            position: relative;
            width: 150px;
            height: 150px;
            margin: 20px auto;
        }

        .progress-ring-circle {
            transform: rotate(-90deg);
            transform-origin: 50% 50%;
        }

        .progress-ring-bg {
            fill: none;
            stroke: rgba(255, 255, 255, 0.1);
            stroke-width: 8;
        }

        .progress-ring-fill {
            fill: none;
            stroke: <%= messageColor %>;
            stroke-width: 8;
            stroke-dasharray: 439.8;
            stroke-dashoffset: 439.8;
            transition: stroke-dashoffset 1.5s ease;
            filter: drop-shadow(0 0 5px <%= messageColor %>);
        }

        .progress-text {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 2em;
            font-weight: bold;
            color: #00ff88;
            font-family: 'Courier New', monospace;
        }

        .training-tip {
            margin-top: 25px;
            padding: 20px;
            background: rgba(255, 217, 102, 0.1);
            border-radius: 12px;
            border: 2px solid #ffd166;
            position: relative;
        }

        .training-tip::before {
            content: 'TRAINING_PROTOCOL';
            position: absolute;
            top: -10px;
            left: 20px;
            background: #ffd166;
            color: #1a1a2e;
            padding: 3px 10px;
            borderRadius: 10px;
            font-size: 0.8em;
            font-weight: bold;
            font-family: 'Courier New', monospace;
        }

        .training-title {
            color: #ffd166;
            font-size: 1.1em;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .training-text {
            color: #ffd166;
            line-height: 1.5;
            font-family: 'Courier New', monospace;
        }

        @media (max-width: 768px) {
            .mission-report {
                padding: 25px 20px;
            }
            
            h1 {
                font-size: 2em;
            }
            
            .score-terminal {
                font-size: 3em;
            }
            
            .stats-grid {
                grid-template-columns: 1fr 1fr;
            }
            
            .intel-breakdown {
                grid-template-columns: 1fr;
            }
            
            .mission-controls {
                flex-direction: column;
            }
            
            .cyber-btn {
                width: 100%;
            }
        }

        .cyber-fade-in {
            animation: cyberFadeIn 1s ease;
        }

        @keyframes cyberFadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .hacker-text {
            font-family: 'Courier New', monospace;
            background: rgba(0, 0, 0, 0.5);
            color: #00ff88;
            padding: 10px;
            border-radius: 5px;
            border-left: 3px solid #00ff88;
            margin: 10px 0;
            text-shadow: 0 0 5px rgba(0, 255, 136, 0.5);
        }

        .cyber-pulse {
            animation: cyberPulse 3s infinite;
        }

        @keyframes cyberPulse {
            0%, 100% { 
                box-shadow: 0 0 5px rgba(0, 255, 136, 0.5);
            }
            50% { 
                box-shadow: 0 0 20px rgba(0, 255, 136, 0.8), 0 0 30px rgba(0, 204, 255, 0.6);
            }
        }
    </style>
</head>
<body>
    <div class="mission-report cyber-fade-in cyber-pulse">
        <div class="mission-badge">MISSION REPORT</div>
        
        <div class="rank-icon"><%= trophy %></div>
        
        <div class="rank-badge"><%= rating %></div>
        
        <h1>MISSION COMPLETE</h1>
        <p class="mission-status">>_ CYBER SECURITY ASSESSMENT TERMINATED</p>
        
        <div class="score-terminal">
            <%= score %>/<%= totalQuestions %>
        </div>
        
        <div class="score-text">
            >_ OBJECTIVES COMPLETED: <%= score %> OF <%= totalQuestions %>
        </div>

        <div class="progress-ring">
            <svg class="progress-ring-circle" width="150" height="150">
                <circle class="progress-ring-bg" cx="75" cy="75" r="65"></circle>
                <circle class="progress-ring-fill" cx="75" cy="75" r="65" 
                        style="stroke-dashoffset: <%= 439.8 - (439.8 * percentage / 100) %>;"></circle>
            </svg>
            <div class="progress-text"><%= (int)percentage %>%</div>
        </div>
        
        <div class="performance-terminal">
            <strong>>_ <%= message %></strong>
        </div>

        <div class="stats-grid">
            <div class="stat-terminal">
                <div class="stat-icon">⏱️</div>
                <div class="stat-value"><%= formattedTime %></div>
                <div class="stat-label">MISSION TIME</div>
            </div>
            <div class="stat-terminal">
                <div class="stat-icon">📊</div>
                <div class="stat-value"><%= (int)percentage %>%</div>
                <div class="stat-label">SUCCESS RATE</div>
            </div>
            <div class="stat-terminal">
                <div class="stat-icon">⚡</div>
                <div class="stat-value"><%= timePerQuestionStr %>s</div>
                <div class="stat-label">TIME PER OBJECTIVE</div>
            </div>
            <div class="stat-terminal">
                <div class="stat-icon">🎯</div>
                <div class="stat-value"><%= score %></div>
                <div class="stat-label">SCORE</div>
            </div>
        </div>

        <div class="intel-report">
            <div class="intel-title">
                <span>📈</span>
                <h3>DETAILED MISSION ANALYSIS</h3>
            </div>
            <div class="intel-breakdown">
                <div>
                    <div class="intel-item">
                        <span class="intel-label">TOTAL OBJECTIVES:</span>
                        <span class="intel-value"><%= totalQuestions %></span>
                    </div>
                    <div class="intel-item">
                        <span class="intel-label">OBJECTIVES COMPLETED:</span>
                        <span class="intel-value"><%= score %></span>
                    </div>
                </div>
                <div>
                    <div class="intel-item">
                        <span class="intel-label">MISSION DURATION:</span>
                        <span class="intel-value"><%= minutes %>m <%= seconds %>s</span>
                    </div>
                    <div class="intel-item">
                        <span class="intel-label">AVG PER OBJECTIVE:</span>
                        <span class="intel-value"><%= timePerQuestionStr %>s</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="hacker-text">
            >_ MISSION CODE: CYBER-SEC-<%= System.currentTimeMillis() %>
        </div>

        <% if (percentage < 70) { %>
        <div class="training-tip">
            <div class="training-title">
                <span>💡</span>
                <h3>ENHANCEMENT PROTOCOL</h3>
            </div>
            <div class="training-text">
                <p>>_ CONTINUE TRAINING! EACH ATTEMPT IS A NEW LEARNING OPPORTUNITY. 
                REVIEW AREAS WHERE YOU ENCOUNTERED DIFFICULTIES. 💪</p>
            </div>
        </div>
        <% } %>

        <div class="mission-controls">
            <a href="${pageContext.request.contextPath}/quiz?restart=true" class="cyber-btn restart-mission">
                🔄 NEW MISSION
            </a>
            <a href="${pageContext.request.contextPath}/home" class="cyber-btn command-center">
                🏠 COMMAND CENTER
            </a>
        </div>
    </div>

    <script>
        // Animate the progress ring
        document.addEventListener('DOMContentLoaded', function() {
            const percentage = <%= percentage %>;
            const fill = document.querySelector('.progress-ring-fill');
            const radius = fill.r.baseVal.value;
            const circumference = 2 * Math.PI * radius;
            
            setTimeout(() => {
                fill.style.strokeDasharray = circumference;
                fill.style.strokeDashoffset = circumference - (percentage / 100) * circumference;
            }, 500);
            
            // Entrance effects for terminals
            const terminals = document.querySelectorAll('.stat-terminal');
            terminals.forEach((terminal, index) => {
                terminal.style.opacity = '0';
                terminal.style.transform = 'translateY(20px)';
                
                setTimeout(() => {
                    terminal.style.transition = 'all 0.6s ease';
                    terminal.style.opacity = '1';
                    terminal.style.transform = 'translateY(0)';
                }, 300 + (index * 100));
            });

            // Add glitch effect to mission badge
            const missionBadge = document.querySelector('.mission-badge');
            setInterval(() => {
                if (Math.random() > 0.7) {
                    missionBadge.style.background = '#00ff88';
                    missionBadge.style.color = '#1a1a2e';
                    setTimeout(() => {
                        missionBadge.style.background = '#ff6b6b';
                        missionBadge.style.color = 'white';
                    }, 100);
                }
            }, 3000);
        });

        // Share mission results
        function shareResults() {
            const score = <%= score %>;
            const percentage = <%= (int)percentage %>;
            const time = '<%= formattedTime %>';
            const rating = '<%= rating %>';
            
            const shareText = `🎯 I just completed the Cyber Security Training with ${score}/10 objectives (${percentage}%) in ${time}! My rank: ${rating}.`;
            
            if (navigator.share) {
                navigator.share({
                    title: 'My Cyber Security Mission Results',
                    text: shareText
                });
            } else if (navigator.clipboard) {
                navigator.clipboard.writeText(shareText).then(() => {
                    alert('Mission results copied to clipboard!');
                });
            } else {
                alert(shareText);
            }
        }
    </script>
</body>
</html>