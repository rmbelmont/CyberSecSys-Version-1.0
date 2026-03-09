<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Study Material - Cyber Security Training</title>
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
            color: white;
        }

        .study-container {
            background: linear-gradient(135deg, #0f3460, #1a1a2e);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.4);
            width: 90%;
            max-width: 1000px;
            margin: 0 auto;
            position: relative;
            overflow: hidden;
            border: 3px solid #00ff88;
        }

        .study-container::before {
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

        h1 {
            color: #00ff88;
            margin-bottom: 10px;
            font-size: 2.5em;
            text-align: center;
            text-shadow: 0 0 10px rgba(0, 255, 136, 0.5);
        }

        .subtitle {
            color: #00ccff;
            font-size: 1.2em;
            margin-bottom: 30px;
            text-align: center;
            font-family: 'Courier New', monospace;
        }

        .case-header {
            background: rgba(26, 42, 108, 0.8);
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 30px;
            border: 2px solid #00ccff;
            position: relative;
        }

        .case-header::before {
            content: 'CASE_FILE';
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

        .case-title {
            color: #00ff88;
            font-size: 1.4em;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .case-info {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-bottom: 20px;
        }

        .info-item {
            background: rgba(255, 255, 255, 0.1);
            padding: 15px;
            border-radius: 10px;
            border: 1px solid #00ccff;
        }

        .info-label {
            color: #00ccff;
            font-size: 0.9em;
            margin-bottom: 5px;
            font-family: 'Courier New', monospace;
        }

        .info-value {
            color: #00ff88;
            font-weight: bold;
            font-family: 'Courier New', monospace;
        }

        .content-section {
            background: rgba(26, 42, 108, 0.6);
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 25px;
            border: 2px solid #00ff88;
            position: relative;
        }

        .section-title {
            color: #00ff88;
            font-size: 1.3em;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .section-content {
            color: #00ccff;
            line-height: 1.7;
            font-size: 1.05em;
        }

        .section-content p {
            margin-bottom: 15px;
        }

        .technical-details {
            background: rgba(255, 255, 255, 0.05);
            padding: 20px;
            border-radius: 10px;
            border-left: 4px solid #ff6b6b;
            margin: 15px 0;
        }

        .technical-title {
            color: #ff6b6b;
            font-weight: bold;
            margin-bottom: 10px;
            font-family: 'Courier New', monospace;
        }

        .impact-list {
            list-style: none;
            padding: 0;
        }

        .impact-list li {
            padding: 10px 0;
            border-bottom: 1px solid rgba(0, 204, 255, 0.3);
            color: #00ccff;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .impact-list li:last-child {
            border-bottom: none;
        }

        .mission-controls {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 40px;
            flex-wrap: wrap;
        }

        .cyber-btn {
            padding: 15px 30px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1.1em;
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

        .start-assessment {
            background: linear-gradient(135deg, #00ff88, #00cc66);
            color: #1a1a2e;
            border: 2px solid #00ff88;
        }

        .start-assessment:hover {
            background: linear-gradient(135deg, #00cc66, #00aa44);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 255, 136, 0.4);
        }

        .back-to-training {
            background: linear-gradient(135deg, #00ccff, #0099cc);
            color: white;
            border: 2px solid #00ccff;
        }

        .back-to-training:hover {
            background: linear-gradient(135deg, #0099cc, #0077aa);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 204, 255, 0.4);
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

        .code-block {
            background: #1a1a2e;
            border: 1px solid #00ff88;
            border-radius: 8px;
            padding: 15px;
            margin: 15px 0;
            font-family: 'Courier New', monospace;
            color: #00ff88;
            overflow-x: auto;
        }

        /* Audio Controls Styles */
        .audio-controls {
            position: fixed;
            top: 20px;
            left: 20px;
            z-index: 1000;
            display: flex;
            align-items: center;
            gap: 10px;
            background: rgba(26, 42, 108, 0.9);
            padding: 10px;
            border-radius: 50px;
            border: 2px solid #00ff88;
            box-shadow: 0 4px 15px rgba(0, 255, 136, 0.3);
        }

        .music-btn {
            background: linear-gradient(135deg, #00ff88, #00cc66);
            border: 2px solid #00ff88;
            border-radius: 50%;
            width: 50px;
            height: 50px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.2em;
            transition: all 0.3s ease;
        }

        .music-btn:hover {
            transform: scale(1.1);
            box-shadow: 0 6px 20px rgba(0, 255, 136, 0.5);
        }

        .volume-slider {
            display: none;
            background: rgba(26, 42, 108, 0.9);
            padding: 15px;
            border-radius: 10px;
            border: 2px solid #00ccff;
            position: absolute;
            left: 70px;
            top: 0;
        }

        .volume-slider.show {
            display: block;
        }

        .volume-slider input {
            width: 120px;
            height: 6px;
            -webkit-appearance: none;
            background: linear-gradient(90deg, #00ff88, #00ccff);
            border-radius: 3px;
            outline: none;
        }

        .volume-slider input::-webkit-slider-thumb {
            -webkit-appearance: none;
            width: 18px;
            height: 18px;
            border-radius: 50%;
            background: #00ff88;
            cursor: pointer;
            border: 2px solid #1a1a2e;
            box-shadow: 0 0 10px rgba(0, 255, 136, 0.8);
        }

        .volume-slider input::-moz-range-thumb {
            width: 18px;
            height: 18px;
            border-radius: 50%;
            background: #00ff88;
            cursor: pointer;
            border: 2px solid #1a1a2e;
            box-shadow: 0 0 10px rgba(0, 255, 136, 0.8);
        }

        .audio-status {
            position: fixed;
            top: 80px;
            left: 20px;
            background: rgba(0, 255, 136, 0.9);
            color: #1a1a2e;
            padding: 10px 15px;
            border-radius: 10px;
            font-size: 0.9em;
            font-family: 'Courier New', monospace;
            z-index: 1000;
            border: 2px solid #00ff88;
            display: none;
        }

        .audio-status.show {
            display: block;
            animation: fadeOut 2s forwards;
        }

        @keyframes fadeOut {
            0% { opacity: 1; }
            70% { opacity: 1; }
            100% { opacity: 0; display: none; }
        }

        @media (max-width: 768px) {
            .study-container {
                padding: 25px 20px;
            }
            
            h1 {
                font-size: 2em;
            }
            
            .case-info {
                grid-template-columns: 1fr;
            }
            
            .mission-controls {
                flex-direction: column;
            }
            
            .cyber-btn {
                width: 100%;
            }

            .audio-controls {
                top: 10px;
                left: 10px;
                padding: 8px;
            }

            .volume-slider {
                left: 60px;
            }
        }

        .cyber-fade-in {
            animation: cyberFadeIn 1s ease;
        }

        @keyframes cyberFadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
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

    <!-- Audio Status Message -->
    <div class="audio-status" id="audioStatus"></div>

    <div class="study-container cyber-fade-in cyber-pulse">
        <div class="mission-badge">CASE STUDY</div>
        
        <h1>CYBER SECURITY CASE ANALYSIS</h1>
        <p class="subtitle">>_ BRAZILIAN NATIONAL OBSERVATORY INCIDENT</p>

        <div class="case-header">
            <div class="case-title">
                <span>📁</span>
                <h2>CASE OVERVIEW</h2>
            </div>
            <div class="case-info">
                <div class="info-item">
                    <div class="info-label">INCIDENT DATE</div>
                    <div class="info-value">2024-01-15 (Monday)</div>
                </div>
                <div class="info-item">
                    <div class="info-label">PRIMARY TARGET</div>
                    <div class="info-value">Time Service Division (DISHO)</div>
                </div>
                <div class="info-item">
                    <div class="info-label">ATTACK TYPE</div>
                    <div class="info-value">Time Manipulation Rootkit</div>
                </div>
                <div class="info-item">
                    <div class="info-label">SEVERITY</div>
                    <div class="info-value">CRITICAL</div>
                </div>
            </div>
        </div>

        <div class="content-section">
            <div class="section-title">
                <span>🎯</span>
                <h2>INCIDENT SUMMARY</h2>
            </div>
            <div class="section-content">
                <p>The Brazilian National Observatory suffered a sophisticated cyber attack on a Monday, during routine IT operations. Internal systems began experiencing widespread slowdowns, initially suspected to be network instability or server overload.</p>
                
                <p>Technical investigation revealed the attack originated from a security breach in the internal network of the Time Service Division (DISHO), with the Brazilian Legal Time servers being the primary target. A persistent infection was confirmed, designed to remain hidden and difficult to detect.</p>
            </div>
        </div>

        <div class="content-section">
            <div class="section-title">
                <span>🔍</span>
                <h2>ATTACK MECHANISM</h2>
            </div>
            <div class="section-content">
                <p>Detailed analysis of the system kernel revealed significant anomalies, including the presence of a hidden process:</p>
                
                <div class="technical-details">
                    <div class="technical-title">SYSTEM CALL MANIPULATION</div>
                    <p>The technique used by intruders involved manipulation of critical system calls, allowing malicious software to operate discreetly and effectively.</p>
                </div>
                
                <div class="technical-details">
                    <div class="technical-title">TIME MANIPULATION ROOTKIT</div>
                    <p>A rootkit with time manipulation capabilities selectively altered timestamps and system clocks, compromising the temporal integrity of affected servers.</p>
                </div>
                
                <div class="code-block">
                    // Kernel-level time manipulation<br>
                    system_time = manipulated_timestamp();<br>
                    // Legitimate process: system_time = authentic_clock_source();
                </div>
            </div>
        </div>

        <div class="content-section">
            <div class="section-title">
                <span>⚠️</span>
                <h2>IMPACT ASSESSMENT</h2>
            </div>
            <div class="section-content">
                <p>The attack caused critical failures across federal government systems:</p>
                
                <ul class="impact-list">
                    <li>
                        <span>🔴</span>
                        <span><strong>CAPES Grants Blocked:</strong> Automatic blocking of CAPES research grants (2023-2034 period)</span>
                    </li>
                    <li>
                        <span>💻</span>
                        <span><strong>Critical System Failures:</strong> SIAFI, SouGov, SIASG, and SCDP systems experienced operational failures</span>
                    </li>
                    <li>
                        <span>⏰</span>
                        <span><strong>Temporal Integrity Compromised:</strong> Systems operated under false perception of temporal normality</span>
                    </li>
                    <li>
                        <span>🎯</span>
                        <span><strong>Primary Impact:</strong> CAPES Grants and Aid Control System (SCBA) and System for Monitoring Grantees Abroad (Sac-Exterior) blocked scholarships due to incorrect date conflicts</span>
                    </li>
                </ul>
            </div>
        </div>

        <div class="content-section">
            <div class="section-title">
                <span>🛡️</span>
                <h2>MITIGATION STRATEGIES</h2>
            </div>
            <div class="section-content">
                <p>Key problems to be solved by IT professionals:</p>
                
                <ul class="impact-list">
                    <li>
                        <span>🔧</span>
                        <span>Restore temporal integrity while maintaining operational network infrastructure</span>
                    </li>
                    <li>
                        <span>🕵️</span>
                        <span>Eliminate persistent rootkit infection and hidden processes</span>
                    </li>
                    <li>
                        <span>⚡</span>
                        <span>Re-establish reliable time synchronization for critical systems</span>
                    </li>
                    <li>
                        <span>🌐</span>
                        <span>Restore normal operations for SCBA and Sac-Exterior scholarship systems</span>
                    </li>
                    <li>
                        <span>🔍</span>
                        <span>Implement advanced kernel-level integrity monitoring</span>
                    </li>
                </ul>
                
                <div class="hacker-text" style="margin-top: 15px;">
                    >_ AFFECTED SYSTEMS: SCBA & SAC-EXTERIOR - TEMPORAL INTEGRITY COMPROMISED
                </div>
            </div>
        </div>

        <div class="hacker-text">
            >_ CASE ANALYSIS COMPLETE. PREPARE FOR ASSESSMENT PHASE.
        </div>

        <div class="mission-controls">
            <a href="${pageContext.request.contextPath}/quiz?restart=true" class="cyber-btn start-assessment">
                🚀 START ASSESSMENT
            </a>
            <a href="${pageContext.request.contextPath}/training" class="cyber-btn back-to-training">
                ↩️ BACK TO TRAINING
            </a>
        </div>
    </div>

    <script>
        // ========== MUSIC SYSTEM ==========
        const backgroundMusic = document.getElementById('backgroundMusic');
        const musicToggle = document.getElementById('musicToggle');
        const volumeControl = document.getElementById('volumeControl');
        const volumeSlider = document.getElementById('volumeSlider');
        const audioControls = document.getElementById('audioControls');
        const audioStatus = document.getElementById('audioStatus');

        let isMusicPlaying = false;
        let audioInitialized = false;

        function showAudioStatus(message) {
            audioStatus.textContent = message;
            audioStatus.classList.add('show');
            setTimeout(() => {
                audioStatus.classList.remove('show');
            }, 2000);
        }

        function initializeAudio() {
            loadMusicPreferences();
            setupEventListeners();
            setupVolumeControl();
            setupVolumeSlider();
            
            backgroundMusic.load();
            showAudioStatus("🔊 Audio system ready");
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
                    backgroundMusic.volume = 0.5;
                    volumeControl.value = 0.5;
                }

                if (savedState === 'true') {
                    musicToggle.textContent = '🔊';
                    isMusicPlaying = true;
                } else {
                    musicToggle.textContent = '🔇';
                    isMusicPlaying = false;
                }
            } catch (error) {
                console.log('Error loading preferences:', error);
                backgroundMusic.volume = 0.5;
                volumeControl.value = 0.5;
            }
        }

        function setupEventListeners() {
            musicToggle.addEventListener('click', toggleMusic);
            
            document.addEventListener('click', handleFirstUserInteraction);
            document.addEventListener('keydown', handleFirstUserInteraction);
            document.addEventListener('touchstart', handleFirstUserInteraction);

            // Audio event listeners for better feedback
            backgroundMusic.addEventListener('canplaythrough', function() {
                console.log('Audio can play through');
                showAudioStatus("✅ Audio loaded");
            });

            backgroundMusic.addEventListener('error', function(e) {
                console.error('Audio error:', e);
                showAudioStatus("❌ Audio source error");
            });
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
                    showAudioStatus("🎵 Music playing");
                }).catch(error => {
                    console.log('Error playing music:', error);
                    isMusicPlaying = false;
                    musicToggle.textContent = '🔇';
                    localStorage.setItem('cyberMusicEnabled', 'false');
                    showAudioStatus("👆 Click to enable audio");
                    
                    // Add one-time retry on next click
                    const retryPlay = function() {
                        backgroundMusic.play().then(() => {
                            isMusicPlaying = true;
                            musicToggle.textContent = '🔊';
                            localStorage.setItem('cyberMusicEnabled', 'true');
                            showAudioStatus("🎵 Music playing");
                        });
                        document.removeEventListener('click', retryPlay);
                    };
                    document.addEventListener('click', retryPlay);
                });
            }
        }

        function pauseMusic() {
            backgroundMusic.pause();
            isMusicPlaying = false;
            musicToggle.textContent = '🔇';
            localStorage.setItem('cyberMusicEnabled', 'false');
            showAudioStatus("⏸️ Music paused");
        }

        function setupVolumeControl() {
            volumeControl.addEventListener('input', function() {
                const volume = parseFloat(this.value);
                backgroundMusic.volume = volume;
                localStorage.setItem('cyberMusicVolume', volume.toString());
                
                // Show volume feedback
                const volumePercent = Math.round(volume * 100);
                showAudioStatus(`🔊 Volume: ${volumePercent}%`);
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

        // ========== PAGE ANIMATIONS ==========
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Study Material - Initializing audio system...');
            initializeAudio();
            
            // Add entrance animations for content sections
            const sections = document.querySelectorAll('.content-section');
            sections.forEach((section, index) => {
                section.style.opacity = '0';
                section.style.transform = 'translateY(20px)';
                
                setTimeout(() => {
                    section.style.transition = 'all 0.6s ease';
                    section.style.opacity = '1';
                    section.style.transform = 'translateY(0)';
                }, 200 + (index * 150));
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

            // Add typewriter effect to subtitle
            const subtitle = document.querySelector('.subtitle');
            const originalText = subtitle.textContent;
            subtitle.textContent = '';
            let i = 0;
            
            function typeWriter() {
                if (i < originalText.length) {
                    subtitle.textContent += originalText.charAt(i);
                    i++;
                    setTimeout(typeWriter, 50);
                }
            }
            
            setTimeout(typeWriter, 1000);
        });

        window.addEventListener('beforeunload', function() {
            localStorage.setItem('cyberMusicEnabled', isMusicPlaying.toString());
            localStorage.setItem('cyberMusicVolume', backgroundMusic.volume.toString());
        });

        // Track study time
        let studyStartTime = Date.now();
        
        window.addEventListener('beforeunload', function() {
            const studyTime = Date.now() - studyStartTime;
            localStorage.setItem('lastStudyTime', studyTime.toString());
            console.log(`Study time: ${Math.round(studyTime / 1000)} seconds`);
        });

        let visits = localStorage.getItem('studyVisits') || 0;
        visits = parseInt(visits) + 1;
        localStorage.setItem('studyVisits', visits);
        
        console.log(`📚 Study sessions: ${visits}`);
    </script>
</body>
</html>