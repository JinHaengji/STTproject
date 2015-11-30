package api;

import gui.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class STT 
{
		public STT(String pathF)
		{
			System.out.println("Stt class로 넘어옴");
			//url 객체 선언
			URL url;
			String key = "AIzaSyDXvTfW775bucO4mcVMB03m3xCB7K4ayfc";
			//String key = "AIzaSyDHX2gteZVJqAD7y5A5S85fou85rwFeO_M";
			
		      //HTTP POST로 요청
		      //두가지 data형식을 인식
		      //HOST 주소:https://www.google.com/speech-api/v2/recognize 결과값:JSON 한국어로 번역:'ko-KR'
		      String urlString = "https://www.google.com/speech-api/v2/recognize?output=json&lang=ko-KR&key=" + key;
		          try 
		          {
		             //URL클래스의 생성자로 주소를 넘겨준다.
		             url = new URL(urlString);
		             
		             //Http를 이용하여 해당 웹사이트에 접속해서 데이터를 주고 받을때 사용합니다.
		             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		             
		             //StringBuilder 객체 정의, 문자열을 담는 역할이지만 문자열을 수정가능
		             StringBuilder stringBuilder;
		   
		             //cache를 사용할 것 인지 (boolean)
		             //true의 경우 , 프로토콜은 가능한 때에 캐쉬 (cache) 를 사용할 수 있다.
		             connection.setDefaultUseCaches(true);
		             
		             //timeout은 어떠한 서버로 연결을 할 때 시도하여 성공하지 못하였을 때의 시간설정 (20초)
		             connection.setConnectTimeout(60000);
		             
		             //inputStream으로 읽어오면서 delay될 때의 시간설정(1분)
		             connection.setReadTimeout(120000);
		             
		             //InputStream으로 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의(서버에서 읽기모드 지정)
		             connection.setDoInput(true);
		             
		            //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션을 정의(서버에서 쓰기모드 지정)   
		             connection.setDoOutput(true);
		             
		             //true의 경우 , 프로토콜은 자동적으로 리다이렉트 (redirect)에 따름 
		             connection.setInstanceFollowRedirects(true);
		             
		             //POST방식으로 요청한다.
		             connection.setRequestMethod("POST");
		             
		            //요청 헤더를 정의한다.
		             connection.setRequestProperty("Content-Type", "audio/x-flac;rate=44100");
		             
		             /*Write the audio on bufferI/O*/
		             //지정된 경로의 파일을 선언
		             File file = new File(pathF);
		             //File file = new File("D://test1.flac");
		             
		             //파일의 길이만큼 크기생성
		             byte[] buffer = new byte[(int) file.length()];
		             
		             //inputstream 객체 생성
		             FileInputStream fis = new FileInputStream(file);
		             
		             //inputstream으로 파일을 읽음
		             fis.read(buffer);
		             
		             //닫기
		             fis.close();
		             
		             //outputstream생성
		             OutputStream os = connection.getOutputStream();
		             
		             //파일 읽기
		             os.write(buffer);
		             
		             //닫기
		             os.close();
		   
		             //google server와 연결
		             connection.connect();
		             System.out.println("connect to google server!");
   
		             //응답메세지
		             connection.getResponseMessage();
		             System.out.println("receive the reponse!");
		             
		             InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
		             BufferedReader br = new BufferedReader(inputStreamReader);
		   
		             stringBuilder = new StringBuilder();
		             
		             //System.out.println(stringBuilder);
		             String line = null;
		             List list = new ArrayList();
		             String totaljson = null;
		             String[] tokens = null;
		             String[] finalstrings = null;
		             
		             //파일에 아무값이 없을 때까지 반복
		               while ((line = br.readLine()) != null)
		               {
		            	 
		                 //stringBuilder.append(line + "\n");
		            	   list.add(line);
		         
		               } 
		               
		               //System.out.println(stringBuilder);
		               JSONObject jsonObj = new JSONObject();
		               JSONArray objj = JSONArray.fromObject(list); //1개 파일마다 결과값 전체 string 저장
		               
		               for(int i=0; i<objj.size(); i++)
		               {
		            	   //System.out.println(objj.getString(i)); 
		            	   //0에 result:[]이 있고 1에 두번째 result 결과가 있음. 
		            	   //내가 필요한건 getString(1)만
		            	   //근데 i를 for문 없이 1로 대체해서 출력하게 하면 결과가 다 안나올때도 있어서 안나옴ㅜㅜ
		            	   //이제여기서 transcript만 가져오기
		            	   
		            	   if(i==1)
		            	   {
		            		   if(objj.getString(i) != null)
		            		   {
		            			   totaljson = objj.getString(i);
		            			   tokens = totaljson.split("\"");
		            			   System.out.println(tokens[7] + "\n"); //이걸 하나의 배열에 한꺼번에 저장.
		  		                   //textarea창에 보이기
		  		                   project.showr.append(tokens[7] + "\n");
		            			   //stringBuilder.append(tokens[7]);
		            		   }   
		            		   else
		            			   totaljson = "no result";
		            	   }
		               }
		      }
		        catch (MalformedURLException e)
		          {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		      } catch (IOException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		      }
		}
}
