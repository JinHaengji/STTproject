<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Web Speech API Demo</title>
    <style>
      body
      {
        max-width: 500px;
        margin: 2em auto;
        font-size: 20px;
      }

      h1
      {
        text-align: center;
      }

      .buttons-wrapper
      {
        text-align: center;
      }

      .hidden
      {
        display: none;
      }

      #transcription,
      #log
      {
        display: block;
        width: 100%;
        height: 5em;
        overflow-y: scroll;
        border: 1px solid #333333;
        line-height: 1.3em;
      }

      .button-demo
      {
        padding: 0.5em;
        display: inline-block;
        margin: 1em auto;
      }
    </style>
  </head>
  <body>
    <h1>Web Speech API</h1>
    <h2>Result</h2>
    <textarea id="transcription" readonly="readonly"></textarea>

    <span>Results:</span>
    <label><input type="radio" name="recognition-type" value="final" checked="checked" /> Final only</label>
    <label><input type="radio" name="recognition-type" value="interim" /> Interim</label>

    <h3>ing</h3>
    <div id="log"></div>

    <div class="buttons-wrapper">
      <button id="button-play-ws" class="button-demo">Play demo</button>
      <button id="button-stop-ws" class="button-demo">Stop demo</button>
      <button id="clear-all" class="button-demo">Clear all</button>
    </div>
    <span id="ws-unsupported" class="hidden">Web Speech Api</span>

    <script>
      // Test browser support
      window.SpeechRecognition = window.SpeechRecognition       ||
                                 window.webkitSpeechRecognition ||
                                 null;

      if (window.SpeechRecognition === null) 
      {
        document.getElementById('ws-unsupported').classList.remove('hidden');
        document.getElementById('button-play-ws').setAttribute('disabled', 'disabled');
        document.getElementById('button-stop-ws').setAttribute('disabled', 'disabled');
      } 
      else 
      {
        var recognizer = new window.SpeechRecognition();
        var transcription = document.getElementById('transcription'); //결과 받아올 변수
        var log = document.getElementById('log');
		
        // 사용자가 멈출때 쓰일 인자
        recognizer.continuous = true;

        // 인식 시작
        recognizer.onresult = function(event) 
        {
          transcription.textContent = '';

          for (var i = event.resultIndex; i < event.results.length; i++)
          {
            if (event.results[i].isFinal) //인식 끝나면 정확도까지 보여줌
            {
              transcription.textContent = event.results[i][0].transcript + ' (Confidence: ' + event.results[i][0].confidence + ')';
            } 
            else
            {
              transcription.textContent += event.results[i][0].transcript; //인식 결과가 계속 더해짐
            }
          }
          
        };

        //에러 처리
        recognizer.onerror = function(event)
        {
          log.innerHTML = 'Recognition error: ' + event.message + '<br />' + log.innerHTML;
        };

        document.getElementById('button-play-ws').addEventListener('click', function() 
        {
          //연속적인 결과 얻을때
          recognizer.interimResults = document.querySelector('input[name="recognition-type"][value="interim"]').checked;

          try 
          {
            recognizer.start(); //인식 시작
            log.innerHTML = 'Recognition started' + '<br />' + log.innerHTML;
          } 
          catch(ex)
          {
            log.innerHTML = 'Recognition error: ' + ex.message + '<br />' + log.innerHTML; //에러 메시지 출력
          }
        });

        document.getElementById('button-stop-ws').addEventListener('click', function() 
        {
          recognizer.stop();
          log.innerHTML = 'Recognition stopped' + '<br />' + log.innerHTML;
        });

        document.getElementById('clear-all').addEventListener('click', function()
        {
          transcription.textContent = '';
          log.textContent = '';
        });
      }
      
      Console.log("1");
      alert("1");
    </script>
  </body>
</html>