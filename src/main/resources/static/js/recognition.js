async function startAudioProcessing() {
	let audioContext;
	let analyser;
	let source;
	let stream;
	let recognition;
	let isRecognizing = false;

	// Start audio stream and detect sound
	async function startStream() {
		try {
			stream = await navigator.mediaDevices.getUserMedia({ audio: true });
			console.log("마이크 접근 허용됨");

			audioContext = new (window.AudioContext || window.webkitAudioContext)();
			analyser = audioContext.createAnalyser();
			source = audioContext.createMediaStreamSource(stream);
			source.connect(analyser);

			const dataArray = new Uint8Array(analyser.frequencyBinCount);

			function detectSound() {
				analyser.getByteFrequencyData(dataArray);
				const average = dataArray.reduce((sum, value) => sum + value, 0) / dataArray.length;

				if (average > 20 && !isRecognizing) {
					alert("소리 감지 " + average.toFixed(0));
					stopStream(); // Stop the audio stream
					setTimeout(startRecognition, 2000); // Start speech recognition after 2 seconds
				} else {
					requestAnimationFrame(detectSound);
				}
			}

			detectSound();
		} catch (error) {
			console.error("마이크 접근 오류:", error);
			alert("마이크 접근 오류: " + error.message);
		}
	}

	// Stop audio stream
	function stopStream() {
		if (source) source.disconnect();
		if (analyser) analyser.disconnect();
		if (audioContext) audioContext.close();
		if (stream) {
			stream.getTracks().forEach(track => track.stop());
		}
		alert("오디오 스트림 종료됨");
	}

	// Start speech recognition
	function startRecognition() {
		let playtts = 0;
		let tryagain = 0;
		let recogSuccess = 0;
		const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
		recognition = new SpeechRecognition();
		recognition.interimResults = false;
		recognition.continuous = true;
		recognition.lang = 'ko-KR';

		recognition.onstart = function() {
			alert("음성인식 시작");
			isRecognizing = true;
		};

		recognition.onend = function() {
			alert("음성인식 종료, playtts=" + playtts.toString());
			isRecognizing = false;

			if (recogSuccess === 0 && tryagain < 2) {
				alert("인식 불가: " + tryagain);
				tryagain += 1;
				recognition.stop();
				setTimeout(startRecognition, 2000); // Retry recognition after 2 seconds
				return;
			}
			tryagain = 0;
			recogSuccess = 0;
			stopRecognition(); // Stop speech recognition
			if (playtts === 1) {
				alert("playtts는 1");

				playTTS();

			} else {
				setTimeout(startStream, 2000); // Restart audio stream after 2 seconds
			}
		};

		recognition.onresult = function(e) {
			let texts = Array.from(e.results)
				.map(result => result[0].transcript)
				.join("");

			document.getElementById('audio-value').textContent = texts;

			// 인식된 텍스트를 #words div에 추가
			const wordsDiv = document.getElementById('words');
			wordsDiv.textContent += texts + "\n"; // 인식된 텍스트를 화면에 표시

			if (texts.includes('레시피')) {
				alert("레시피 인식");
				playtts = 1;
			} else {
				// Optionally display the result
				// alert(texts);
			}
			recogSuccess = 1;
		};

		recognition.onerror = function(e) {
			alert("음성 인식 오류: " + e.error);
		};

		recognition.start();
	}

	// Stop speech recognition
	function stopRecognition() {
		if (recognition) {
			recognition.stop();
		}
	}

	// Start the initial audio stream
	startStream();
}

startAudioProcessing();
