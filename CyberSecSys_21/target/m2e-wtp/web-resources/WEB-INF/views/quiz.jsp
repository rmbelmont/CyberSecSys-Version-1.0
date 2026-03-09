<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cyber Security Assessment - Mission ${currentQuestion + 1}</title>

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

        .mission-container {
            background: linear-gradient(135deg, #0f3460, #1a1a2e);
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.4);
            width: 90%;
            max-width: 700px;
            border: 3px solid #00ff88;
            position: relative;
            overflow: hidden;
        }

        .mission-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 5px;
            background: linear-gradient(90deg, #00ff88, #00ccff, #ff6b6b);
        }

        .mission-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            flex-wrap: wrap;
            gap: 15px;
            padding: 15px;
            background: rgba(26, 42, 108, 0.8);
            border-radius: 15px;
            border: 2px solid #00ccff;
        }

        .mission-timer {
            display: flex;
            align-items: center;
            gap: 10px;
            background: rgba(0, 0, 0, 0.7);
            padding: 12px 20px;
            border-radius: 25px;
            border: 2px solid #00ff88;
        }

        .timer-icon {
            font-size: 1.4em;
            color: #00ff88;
        }

        .timer-digits {
            color: #00ff88 !important;
            font-size: 24px !important;
            font-weight: 900 !important;
            display: inline-block !important;
            background: transparent !important;
            padding: 5px !important;
            border-radius: 5px !important;
            min-width: 80px !important;
            text-align: center !important;
            opacity: 1 !important;
            visibility: visible !important;
            text-shadow: 0 0 10px rgba(0, 255, 136, 0.7);
        }

        .mission-counter {
            font-size: 1.2em;
            color: #00ccff;
            background: rgba(0, 0, 0, 0.7);
            padding: 10px 20px;
            border-radius: 20px;
            border: 2px solid #00ccff;
            font-weight: bold;
        }

        .progress-container {
            margin-bottom: 25px;
        }

        .progress-label {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            color: #00ccff;
            font-size: 0.9em;
        }

        .progress-bar {
            height: 12px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #00ccff;
        }

        .progress {
            height: 100%;
            background: linear-gradient(90deg, #00ff88, #00cc66);
            border-radius: 10px;
            transition: width 0.5s ease;
        }

        .question-terminal {
            background: #1a1a2e;
            border-radius: 15px;
            padding: 25px;
            margin-bottom: 25px;
            border: 2px solid #00ff88;
            position: relative;
        }

        .question-terminal::before {
            content: 'TERMINAL_ACCESS';
            position: absolute;
            top: -10px;
            left: 20px;
            background: #00ff88;
            color: #1a1a2e;
            padding: 3px 10px;
            border-radius: 10px;
            font-size: 0.8em;
            font-weight: bold;
        }

        .question-text {
            font-size: 1.4em;
            color: #00ff88;
            line-height: 1.5;
            font-family: 'Courier New', monospace;
            text-shadow: 0 0 5px rgba(0, 255, 136, 0.5);
        }

        .options-grid {
            display: grid;
            gap: 12px;
            margin-bottom: 25px;
        }

        .option-button {
            padding: 18px;
            background: linear-gradient(135deg, #2d388a, #1a2a6c);
            border: 2px solid #00ccff;
            border-radius: 12px;
            cursor: pointer;
            font-size: 1.1em;
            text-align: left;
            transition: all 0.3s ease;
            width: 100%;
            color: white;
            font-family: 'Segoe UI', sans-serif;
            position: relative;
            overflow: hidden;
        }

        .option-button:hover {
            border-color: #00ff88;
            background: linear-gradient(135deg, #3742fa, #2d388a);
            transform: translateX(5px);
            box-shadow: 0 5px 15px rgba(0, 255, 136, 0.3);
        }

        .option-button:active {
            transform: translateX(2px);
        }

        .option-number {
            font-weight: bold;
            color: #00ff88;
            margin-right: 10px;
        }

        .mission-controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 2px solid #00ccff;
        }

        .restart-btn {
            background: linear-gradient(135deg, #ff6b6b, #ff4757);
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1em;
            transition: all 0.3s ease;
            font-weight: bold;
        }

        .restart-btn:hover {
            background: linear-gradient(135deg, #ff4757, #ff2e4d);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 107, 107, 0.4);
        }

        /* Audio Controls */
        .audio-controls {
            position: fixed;
            bottom: 20px;
            right: 20px;
            z-index: 1000;
        }

        .music-btn {
            background: rgba(26, 42, 108, 0.9);
            border: 2px solid #00ff88;
            border-radius: 50%;
            width: 60px;
            height: 60px;
            cursor: pointer;
            font-size: 1.5em;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #00ff88;
        }

        .music-btn:hover {
            transform: scale(1.1);
            background: rgba(26, 42, 108, 1);
            box-shadow: 0 0 20px rgba(0, 255, 136, 0.5);
        }

        .volume-slider {
            position: absolute;
            bottom: 70px;
            right: 0;
            background: rgba(26, 42, 108, 0.95);
            padding: 15px;
            border-radius: 25px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            transform: scaleY(0);
            transform-origin: bottom;
            transition: transform 0.3s ease;
            border: 2px solid #00ccff;
        }

        .volume-slider.show {
            transform: scaleY(1);
        }

        .volume-slider input {
            width: 120px;
            margin: 5px 0;
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

        .mission-tag {
            background: #ff6b6b;
            color: white;
            padding: 8px 20px;
            border-radius: 25px;
            font-size: 0.9em;
            font-weight: bold;
            position: absolute;
            top: -15px;
            right: 20px;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0%, 100% { transform: scale(1); }
            50% { transform: scale(1.05); }
        }

        .sr-only {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            white-space: nowrap;
            border: 0;
        }

        @media (max-width: 768px) {
            .audio-controls {
                bottom: 10px;
                right: 10px;
            }

            .mission-header {
                flex-direction: column;
                align-items: stretch;
                gap: 15px;
            }

            .mission-timer, .mission-counter {
                justify-content: center;
                text-align: center;
            }
            
            .music-btn {
                width: 50px;
                height: 50px;
                font-size: 1.3em;
            }
        }

        .hacker-text {
            font-family: 'Courier New', monospace;
            background: rgba(0, 0, 0, 0.5);
            color: #00ff88;
            padding: 10px;
            border-radius: 5px;
            border-left: 3px solid #00ff88;
            margin: 10px 0;
        }
    </style>
</head>

<body>

    <div class="mission-container cyber-pulse">
        <div class="mission-tag">ACTIVE MISSION</div>

        <span class="sr-only" id="missionTitle">
            Cyber Security Assessment - Mission ${currentQuestion + 1} of ${totalQuestions}
        </span>

        <div class="mission-header">
            <div class="mission-timer" aria-labelledby="timerLabel">
                <span class="timer-icon" aria-hidden="true">⏱️</span>
                <span id="timerLabel" class="sr-only">Mission Time:</span>
                <span id="timerDigits" class="timer-digits" aria-live="polite">00:00</span>
            </div>

            <div class="mission-counter">
                MISSION ${currentQuestion + 1} / ${totalQuestions}
            </div>
        </div>

        <div class="progress-container">
            <div class="progress-label">
                <span>MISSION PROGRESS</span>
                <span id="progressPercentage">0%</span>
            </div>
            <div class="progress-bar">
                <div class="progress" id="progressBar"></div>
            </div>
        </div>

        <form id="missionForm" action="${pageContext.request.contextPath}/quiz" method="post">
            <div class="question-terminal">
                <div class="question-text">${questions[currentQuestion].question}</div>
            </div>

            <div class="options-grid" role="radiogroup" aria-labelledby="missionTitle">
                <c:forEach var="option" items="${questions[currentQuestion].options}" varStatus="loop">
                    <button type="submit"
                        name="answer"
                        value="${loop.index}"
                        class="option-button"
                        aria-label="Option ${loop.index + 1}: ${option}"
                        title="Select option ${loop.index + 1}: ${option}">
                        <span class="option-number">${loop.index + 1}.</span> ${option}
                    </button>
                </c:forEach>
            </div>
        </form>

        <div class="mission-controls">
            <button type="button" class="restart-btn" onclick="restartMission()">
                🔄 ABORT MISSION
            </button>
            
            <div class="hacker-text">
                >_ SELECT YOUR RESPONSE TO PROCEED
            </div>
        </div>
    </div>

    <!-- Audio Controls -->
    <div class="audio-controls" id="audioControls">
        <div class="volume-slider" id="volumeSlider">
            <input type="range" id="volumeControl" min="0" max="1" step="0.1" value="0.5">
        </div>
        <button type="button" class="music-btn" id="musicToggle">🔇</button>
    </div>

    <audio id="backgroundMusic" loop preload="none">
        <source src="https://assets.codepen.io/1468070/Into+the+Night+-+prazkhanal.mp3" type="audio/mpeg">
        <source src="https://cdn.pixabay.com/download/audio/2022/11/02/audio_ec4c1b7a9f.mp3" type="audio/mpeg">
        <source src="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3" type="audio/mpeg">
    </audio>

    <script>
        // ========== PROGRESS BAR CALCULATION ==========
        function updateProgressBar() {
            const currentQuestion = ${currentQuestion};
            const totalQuestions = ${totalQuestions};
            const progressPercentage = (currentQuestion / totalQuestions) * 100;
            
            const progressBar = document.getElementById('progressBar');
            const progressText = document.getElementById('progressPercentage');
            
            if (progressBar && progressText) {
                progressBar.style.width = progressPercentage + '%';
                progressText.textContent = Math.round(progressPercentage) + '%';
            }
        }

        // ========== MISSION TIMER ==========
        let startTime;
        let timerInterval;

        function startTimer() {
            const timerDisplay = document.getElementById("timerDigits");

            // Ensure timer is visible
            timerDisplay.style.color = "#00ff88";
            timerDisplay.style.display = "inline-block";
            timerDisplay.style.opacity = "1";
            timerDisplay.style.visibility = "visible";

            startTime = Date.now();
            updateTimer();

            timerInterval = setInterval(updateTimer, 1000);
        }

        function updateTimer() {
            const now = Date.now();
            const elapsed = now - startTime;

            const min = Math.floor(elapsed / 60000);
            const sec = Math.floor((elapsed % 60000) / 1000);

            document.getElementById("timerDigits").textContent =
                String(min).padStart(2, "0") + ":" + String(sec).padStart(2, "0");
        }

        function restartMission() {
            if (confirm("Abort current mission and return to command center?")) {
                clearInterval(timerInterval);
                window.location.href = "${pageContext.request.contextPath}/quiz?restart=true";
            }
        }

        document.getElementById("missionForm").addEventListener("submit", function () {
            clearInterval(timerInterval);
        });

        // ========== AUDIO SYSTEM ==========
        const backgroundMusic = document.getElementById("backgroundMusic");
        const musicToggle = document.getElementById("musicToggle");
        const volumeControl = document.getElementById("volumeControl");
        const volumeSlider = document.getElementById("volumeSlider");

        let isMusicPlaying = false;
        let audioInitialized = false;

        function initializeAudio() {
            loadMusicPreferences();
            setupEventListeners();
            setupVolumeControl();
            setupVolumeSlider();
            backgroundMusic.volume = 0.5;
        }

        function loadMusicPreferences() {
            try {
                const savedVolume = localStorage.getItem('cyberMusicVolume');
                const savedState = localStorage.getItem('cyberMusicEnabled');
                
                if (savedVolume) {
                    const volume = parseFloat(savedVolume);
                    backgroundMusic.volume = volume;
                    volumeControl.value = volume;
                }

                if (savedState === 'true') {
                    musicToggle.textContent = '🔊';
                } else {
                    musicToggle.textContent = '🔇';
                }
            } catch (error) {
                console.log('Audio preferences load error:', error);
            }
        }

        function setupEventListeners() {
            musicToggle.addEventListener("click", toggleMusic);
            document.addEventListener("click", firstInteraction);
            document.addEventListener("keydown", firstInteraction);
        }

        function firstInteraction() {
            if (!audioInitialized) {
                audioInitialized = true;
            }
        }

        function toggleMusic() {
            if (!audioInitialized) {
                audioInitialized = true;
            }

            if (isMusicPlaying) {
                pauseMusic();
            } else {
                playMusic();
            }
        }

        function playMusic() {
            backgroundMusic.play().then(() => {
                isMusicPlaying = true;
                musicToggle.textContent = "🔊";
                localStorage.setItem('cyberMusicEnabled', 'true');
            }).catch(e => {
                console.log("Audio play failed:", e);
                document.addEventListener('click', function retryPlay() {
                    backgroundMusic.play().then(() => {
                        isMusicPlaying = true;
                        musicToggle.textContent = "🔊";
                        localStorage.setItem('cyberMusicEnabled', 'true');
                    });
                    document.removeEventListener('click', retryPlay);
                });
            });
        }

        function pauseMusic() {
            backgroundMusic.pause();
            isMusicPlaying = false;
            musicToggle.textContent = "🔇";
            localStorage.setItem('cyberMusicEnabled', 'false');
        }

        function setupVolumeControl() {
            volumeControl.addEventListener("input", function () {
                const volume = parseFloat(this.value);
                backgroundMusic.volume = volume;
                localStorage.setItem('cyberMusicVolume', volume.toString());
            });
        }

        function setupVolumeSlider() {
            musicToggle.addEventListener("mouseenter", () => {
                volumeSlider.classList.add("show");
            });

            musicToggle.addEventListener("mouseleave", () => {
                setTimeout(() => {
                    if (!volumeSlider.matches(":hover")) {
                        volumeSlider.classList.remove("show");
                    }
                }, 1000);
            });

            volumeSlider.addEventListener("mouseleave", () => {
                setTimeout(() => {
                    volumeSlider.classList.remove("show");
                }, 1000);
            });
        }

        // ========== VISUAL EFFECTS ==========
        function initializeVisualEffects() {
            // Add entrance animation for options
            const options = document.querySelectorAll('.option-button');
            options.forEach((option, index) => {
                option.style.opacity = '0';
                option.style.transform = 'translateX(-20px)';
                
                setTimeout(() => {
                    option.style.transition = 'all 0.5s ease';
                    option.style.opacity = '1';
                    option.style.transform = 'translateX(0)';
                }, 100 + (index * 100));
            });

            // Add glitch effect to mission tag
            const missionTag = document.querySelector('.mission-tag');
            setInterval(() => {
                if (Math.random() > 0.8) {
                    missionTag.style.background = '#00ff88';
                    missionTag.style.color = '#1a1a2e';
                    setTimeout(() => {
                        missionTag.style.background = '#ff6b6b';
                        missionTag.style.color = 'white';
                    }, 100);
                }
            }, 3000);
        }

        // ========== INITIALIZATION ==========
        document.addEventListener("DOMContentLoaded", () => {
            updateProgressBar(); // Initialize progress bar
            startTimer();
            initializeAudio();
            initializeVisualEffects();
        });

        // Save preferences when leaving
        window.addEventListener('beforeunload', function() {
            localStorage.setItem('cyberMusicEnabled', isMusicPlaying.toString());
            localStorage.setItem('cyberMusicVolume', backgroundMusic.volume.toString());
        });
    </script>
</body>
</html>