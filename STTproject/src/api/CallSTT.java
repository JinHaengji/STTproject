package api;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import sound.CuttingFile;

//�������� flac ������ ���� STT�� �� ������ŭ �θ���
public class CallSTT
{
	public CallSTT(int mil, String pathF)
	{

		System.out.println("callStt class�� �Ѿ��");

		String type_f = ".flac";
		
		for(int i=1; i<=mil; i++)
		{
			//stt���ڿ� ���� ��θ��� �Ѱ�����
			STT stt = new STT(pathF+"_s"+i+type_f);
		}
	}
}
