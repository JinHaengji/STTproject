package api;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import sound.CuttingFile;

//여러개의 flac 파일을 위해 STT를 그 개수만큼 부른다
public class CallSTT
{
	public CallSTT(int mil, String pathF)
	{

		System.out.println("callStt class로 넘어옴");

		String type_f = ".flac";
		
		for(int i=1; i<=mil; i++)
		{
			//stt인자에 파일 경로명을 넘겨주자
			STT stt = new STT(pathF+"_s"+i+type_f);
		}
	}
}
