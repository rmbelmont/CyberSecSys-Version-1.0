<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cyber Security Training - Command Center</title>
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
        }

        .game-container {
            background: white;
            border-radius: 20px;
            padding: 50px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.3);
            width: 90%;
            max-width: 900px;
            text-align: center;
            position: relative;
            overflow: hidden;
            border: 3px solid #00ff88;
        }

        .game-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 5px;
            background: linear-gradient(90deg, #00ff88, #00ccff, #ff6b6b);
        }

        .logo {
            font-size: 4em;
            margin-bottom: 20px;
            animation: float 3s ease-in-out infinite;
        }

        @keyframes float {
            0%, 100% { transform: translateY(0px); }
            50% { transform: translateY(-10px); }
        }

        h1 {
            color: #2f3542;
            margin-bottom: 10px;
            font-size: 2.8em;
            background: linear-gradient(135deg, #3742fa, #ff4757);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .subtitle {
            color: #636e72;
            font-size: 1.3em;
            margin-bottom: 40px;
            font-weight: 300;
        }

        .mission-briefing {
            background: linear-gradient(135deg, #0f3460, #1a1a2e);
            border-radius: 15px;
            padding: 30px;
            margin: 30px 0;
            text-align: left;
            border: 2px solid #00ff88;
            color: white;
            position: relative;
        }

        .mission-title {
            color: #00ff88;
            font-size: 1.5em;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .mission-content {
            line-height: 1.8;
            color: #00ccff;
            font-size: 1.1em;
        }

        .mission-content p {
            margin-bottom: 15px;
        }

        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin: 40px 0;
        }

        .feature-card {
            background: linear-gradient(135deg, #f8f9ff, #e3f2fd);
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.3s ease;
            border: 2px solid transparent;
        }

        .feature-card:hover {
            transform: translateY(-5px);
            border-color: #00ff88;
            box-shadow: 0 10px 25px rgba(0, 255, 136, 0.2);
        }

        .feature-icon {
            font-size: 2.5em;
            margin-bottom: 15px;
        }

        .feature-title {
            font-weight: bold;
            color: #1a2a6c;
            margin-bottom: 10px;
            font-size: 1.2em;
        }

        .feature-description {
            color: #2d388a;
            font-size: 0.95em;
        }

        .instructions {
            background: linear-gradient(135deg, #fff9e6, #ffeaa7);
            border: 2px solid #ffd166;
            border-radius: 12px;
            padding: 25px;
            margin: 30px 0;
            text-align: left;
        }

        .instructions-title {
            color: #e17055;
            font-size: 1.4em;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .instructions-list {
            list-style: none;
            padding: 0;
        }

        .instructions-list li {
            padding: 12px 0;
            border-bottom: 1px solid #ffeaa7;
            display: flex;
            align-items: center;
            gap: 15px;
            color: #2d3436;
            font-size: 1.05em;
        }

        .instructions-list li:last-child {
            border-bottom: none;
        }

        .step-number {
            background: #00ff88;
            color: #1a2a6c;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 0.9em;
        }

        /* Mission Controls Container */
        .mission-controls {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
            flex-wrap: wrap;
        }

        .cyber-btn {
            padding: 20px 40px;
            border: none;
            border-radius: 50px;
            cursor: pointer;
            font-size: 1.2em;
            font-weight: bold;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            min-width: 200px;
            font-family: 'Segoe UI', sans-serif;
        }

        .start-button {
            background: linear-gradient(135deg, #00ff88, #00cc66);
            color: white;
            box-shadow: 0 10px 30px rgba(0, 255, 136, 0.3);
        }

        .start-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 40px rgba(0, 255, 136, 0.4);
            background: linear-gradient(135deg, #00cc66, #00aa44);
        }

        .back-button {
            background: linear-gradient(135deg, #00ccff, #0099cc);
            color: white;
            box-shadow: 0 10px 30px rgba(0, 204, 255, 0.3);
        }

        .back-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 40px rgba(0, 204, 255, 0.4);
            background: linear-gradient(135deg, #0099cc, #0077aa);
        }

        .mission-stats {
            display: flex;
            justify-content: space-around;
            margin: 30px 0;
            flex-wrap: wrap;
            gap: 15px;
        }

        .stat-item {
            text-align: center;
        }

        .stat-number {
            font-size: 2em;
            font-weight: bold;
            color: #00ff88;
            display: block;
        }

        .stat-label {
            color: #00ccff;
            font-size: 0.9em;
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
            top: -10px;
            right: 20px;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0%, 100% { transform: scale(1); }
            50% { transform: scale(1.05); }
        }

        @media (max-width: 768px) {
            .game-container {
                padding: 30px 20px;
            }
            
            h1 {
                font-size: 2.2em;
            }
            
            .logo {
                font-size: 3em;
            }
            
            .features-grid {
                grid-template-columns: 1fr;
            }
            
            .mission-controls {
                flex-direction: column;
                align-items: center;
            }
            
            .cyber-btn {
                padding: 15px 30px;
                font-size: 1.1em;
                width: 100%;
                max-width: 300px;
            }

            .audio-controls {
                bottom: 10px;
                right: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="game-container cyber-pulse">
        <div class="logo">⚡</div>
        <h1>CYBER SECURITY TRAINING</h1>
        <p class="subtitle">Advanced Threat Analysis Simulation</p>
        
        <div class="mission-briefing">
            <div class="mission-tag">CLASSIFIED</div>
            <div class="mission-title">
                <span>🎯</span>
                <h2>MISSION BRIEFING</h2>
            </div>
            <div class="mission-content">
                <p>Welcome to <strong>Cyber Security Training</strong>, where you will analyze a real-world cyber attack scenario!</p>
                <p>You are about to embark on a mission to study a <strong>simulated attack on the Brazilian National Observatory</strong>, involving time manipulation rootkits and their impact on government systems.</p>
                <p>First, you will study the complete case material and then answer specific questions about the incident in the assessment phase.</p>
            </div>
        </div>

        <div class="features-grid">
            <div class="feature-card">
                <div class="feature-icon">📚</div>
                <div class="feature-title">Case Study Material</div>
                <div class="feature-description">Detailed cyber attack scenario for analysis</div>
            </div>
            <div class="feature-card">
                <div class="feature-icon">⏱️</div>
                <div class="feature-title">Real-time Monitoring</div>
                <div class="feature-description">Track your performance with precision timing</div>
            </div>
            <div class="feature-card">
                <div class="feature-icon">🔒</div>
                <div class="feature-title">Cybersecurity Focus</div>
                <div class="feature-description">Questions based on real security scenarios</div>
            </div>
            <div class="feature-card">
                <div class="feature-icon">🎵</div>
                <div class="feature-title">Immersive Audio</div>
                <div class="feature-description">Ambient music for enhanced concentration</div>
            </div>
        </div>

        <div class="instructions">
            <div class="instructions-title">
                <span>📝</span>
                <h2>MISSION PROTOCOL</h2>
            </div>
            <ul class="instructions-list">
                <li>
                    <div class="step-number">1</div>
                    <div>
                        <strong>Study the Case Material</strong><br>
                        Analyze the detailed cyber attack scenario
                    </div>
                </li>
                <li>
                    <div class="step-number">2</div>
                    <div>
                        <strong>Understand the Attack</strong><br>
                        Examine the time manipulation rootkit and its impacts
                    </div>
                </li>
                <li>
                    <div class="step-number">3</div>
                    <div>
                        <strong>Complete the Assessment</strong><br>
                        Answer specific questions about the case study
                    </div>
                </li>
                <li>
                    <div class="step-number">4</div>
                    <div>
                        <strong>Monitor Your Progress</strong><br>
                        Timer starts when you begin the assessment
                    </div>
                </li>
                <li>
                    <div class="step-number">5</div>
                    <div>
                        <strong>Review Your Results</strong><br>
                        Discover your final score and total time
                    </div>
                </li>
            </ul>
        </div>

        <div class="mission-stats">
            <div class="stat-item">
                <span class="stat-number">1</span>
                <span class="stat-label">Real Case</span>
            </div>
            <div class="stat-item">
                <span class="stat-number">📚</span>
                <span class="stat-label">Study Material</span>
            </div>
            <div class="stat-item">
                <span class="stat-number">10</span>
                <span class="stat-label">Questions</span>
            </div>
            <div class="stat-item">
                <span class="stat-number">100%</span>
                <span class="stat-label">Practical</span>
            </div>
        </div>

        <!-- Mission Controls - Both buttons side by side -->
        <div class="mission-controls">
            <a href="${pageContext.request.contextPath}/home" class="cyber-btn back-button">
                ↩️ BACK
            </a>
            <button class="cyber-btn start-button" onclick="startTraining()">
                🚀 BEGIN TRAINING
            </button>
        </div>

        <div style="margin-top: 30px; color: #636e72; font-size: 0.9em;">
            <p>💡 <strong>Pro Tip:</strong> Use ambient music to enhance your concentration during the training!</p>
        </div>
    </div>

    <!-- Audio Controls -->
    <div class="audio-controls" id="audioControls">
        <div class="volume-slider" id="volumeSlider">
            <input type="range" id="volumeControl" min="0" max="1" step="0.1" value="0.5">
        </div>
        <button type="button" class="music-btn" id="musicToggle">
            🔇
        </button>
    </div>

    <!-- Audio Element with multiple fallback sources -->
    <audio id="backgroundMusic" loop preload="none">
        <source src="https://assets.codepen.io/1468070/Into+the+Night+-+prazkhanal.mp3" type="audio/mpeg">
        <source src="https://cdn.pixabay.com/download/audio/2022/11/02/audio_ec4c1b7a9f.mp3?filename=relaxing-abstract-background-ig-version-60s-15643.mp3" type="audio/mpeg">
        <source src="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3" type="audio/mpeg">
        Your browser does not support the audio element.
    </audio>

    <script>
        // ========== MUSIC SYSTEM ==========
        const backgroundMusic = document.getElementById('backgroundMusic');
        const musicToggle = document.getElementById('musicToggle');
        const volumeControl = document.getElementById('volumeControl');
        const volumeSlider = document.getElementById('volumeSlider');
        const audioControls = document.getElementById('audioControls');

        let isMusicPlaying = false;
        let audioInitialized = false;

        function initializeAudio() {
            loadMusicPreferences();
            setupEventListeners();
            setupVolumeControl();
            setupVolumeSlider();
            
            backgroundMusic.load();
        }

        function loadMusicPreferences() {
            try {
                const savedVolume = localStorage.getItem('cyberMusicVolume');
                const savedState = localStorage.getItem('cyberMusicEnabled');
                
                if (savedVolume) {
                    const volume = parseFloat(savedVolume);
                    backgroundMusic.volume = volume;
                    volumeControl.value = volume;
                } else {
                    backgroundMusic.volume = 0.3;
                    volumeControl.value = 0.3;
                }

                if (savedState === 'true') {
                    musicToggle.textContent = '🔊';
                } else {
                    musicToggle.textContent = '🔇';
                }
            } catch (error) {
                console.log('Error loading preferences:', error);
                backgroundMusic.volume = 0.3;
                volumeControl.value = 0.3;
            }
        }

        function setupEventListeners() {
            musicToggle.addEventListener('click', toggleMusic);
            
            document.addEventListener('click', handleFirstUserInteraction);
            document.addEventListener('keydown', handleFirstUserInteraction);
            document.addEventListener('touchstart', handleFirstUserInteraction);
        }

        function handleFirstUserInteraction() {
            if (!audioInitialized) {
                audioInitialized = true;
                
                const savedState = localStorage.getItem('cyberMusicEnabled');
                if (savedState === 'true') {
                    setTimeout(() => {
                        playMusic();
                    }, 500);
                }
                
                document.removeEventListener('click', handleFirstUserInteraction);
                document.removeEventListener('keydown', handleFirstUserInteraction);
                document.removeEventListener('touchstart', handleFirstUserInteraction);
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
            console.log('Attempting to play music...');
            
            if (backgroundMusic.readyState === 0) {
                backgroundMusic.load();
            }
            
            const playPromise = backgroundMusic.play();
            
            if (playPromise !== undefined) {
                playPromise.then(() => {
                    console.log('Music playing successfully');
                    isMusicPlaying = true;
                    musicToggle.textContent = '🔊';
                    localStorage.setItem('cyberMusicEnabled', 'true');
                }).catch(error => {
                    console.log('Error playing music:', error);
                    isMusicPlaying = false;
                    musicToggle.textContent = '🔇';
                    localStorage.setItem('cyberMusicEnabled', 'false');
                });
            }
        }

        function pauseMusic() {
            backgroundMusic.pause();
            isMusicPlaying = false;
            musicToggle.textContent = '🔇';
            localStorage.setItem('cyberMusicEnabled', 'false');
        }

        function setupVolumeControl() {
            volumeControl.addEventListener('input', function() {
                const volume = parseFloat(this.value);
                backgroundMusic.volume = volume;
                localStorage.setItem('cyberMusicVolume', volume.toString());
            });
        }

        function setupVolumeSlider() {
            musicToggle.addEventListener('mouseenter', function() {
                volumeSlider.classList.add('show');
            });

            audioControls.addEventListener('mouseleave', function() {
                setTimeout(() => {
                    if (!volumeSlider.matches(':hover')) {
                        volumeSlider.classList.remove('show');
                    }
                }, 1000);
            });

            volumeSlider.addEventListener('mouseleave', function() {
                setTimeout(() => {
                    volumeSlider.classList.remove('show');
                }, 1000);
            });
        }

        // ========== MISSION START ==========
        function startTraining() {
            localStorage.setItem('cyberMusicEnabled', isMusicPlaying.toString());
            localStorage.setItem('cyberMusicVolume', backgroundMusic.volume.toString());
            
            const button = document.querySelector('.start-button');
            const originalText = button.innerHTML;
            button.innerHTML = '🚀 INITIATING MISSION...';
            button.disabled = true;
            
            // Redirecionar para a página de estudo
            setTimeout(() => {
                window.location.href = '${pageContext.request.contextPath}/study-material';
            }, 1000);
        }

        // ========== INITIALIZATION ==========
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Command Center - Initializing audio system...');
            initializeAudio();
            
            const cards = document.querySelectorAll('.feature-card');
            cards.forEach((card, index) => {
                card.style.animationDelay = `${index * 0.2}s`;
                card.style.opacity = '0';
                card.style.transform = 'translateY(20px)';
                
                setTimeout(() => {
                    card.style.transition = 'all 0.6s ease';
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, 100 + (index * 200));
            });
        });

        window.addEventListener('beforeunload', function() {
            localStorage.setItem('cyberMusicEnabled', isMusicPlaying.toString());
            localStorage.setItem('cyberMusicVolume', backgroundMusic.volume.toString());
        });

        let visits = localStorage.getItem('trainingVisits') || 0;
        visits = parseInt(visits) + 1;
        localStorage.setItem('trainingVisits', visits);
        
        console.log(`🎮 Training sessions: ${visits}`);
    </script>
</body>
</html>