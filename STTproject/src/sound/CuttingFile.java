package sound;

import gui.project;

import java.io.File;
import java.io.IOException;
import javaFlacEncoder.FLACFileWriter;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import api.CallSTT;

//1��. wav ������ 10�� ������ wav���Ϸ� split
public class CuttingFile {
	// projectŬ�������� ���� ������������ ��ü ����
	static project pj;

	static int mil = 0;

	// Ȯ���ڸ� ����
	static String type_w = ".wav";
	static String type_f = ".flac";

	// source.substring(0, source.lastIndexOf('.')); ����Ͽ� Ȯ���ڸ� ���� ��������
	// static String pathW =
	// pj.getwavp().substring(0,pj.getwavp().lastIndexOf("."));
	// str.substring(str.length()-3, str.length()));

	// static String pathW="E:/file/wav/test6_1114";
	static String pathW_F; //���� ������ �ִ� ��ο� ������ ������ ���
	static String pathW_w;

	// static String pathF_f = "D://����//file//flac//test6_1114" + type_f;

	public CuttingFile() throws UnsupportedAudioFileException, IOException {

		// ���� ��� �������ֱ�
		pathW_F = pj.getwavp();
		pathW_w = pathW_F + type_w;
		
		System.out.println("���� ���:" + pathW_F);

		// test6_1114.wav : 28��
		int totaltime = 0;
		totaltime = (int) totalLength(pathW_w);
		System.out.println("���� ��ü �ð�:" + totaltime);

		// totaltime�� �� ���ڸ����� �޾ƿ;���.
		// �׳� 10�ʸ��� ��� ���ư����ִ����� ���� �Ǵϱ�
		mil = (totaltime / 10) + 1; // STT�� ����

		// 10�ʸ��� �ڸ���
		for (int i = 0; i < mil; i++) {
			
			// ��µǴ� ������ �������ϸ� ���� 1,2,3 ���ʴ�� �ٰ� ��
			copyAudio(pathW_w, pathW_F + "_s" + (i + 1) + type_w, i * 10, 10); 
		}

		//2��. wav���ϵ��� flac���Ϸ� ��ȯ
		for (int i = 0; i < mil; i++) {
			File inputFile = new File(pathW_F + "_s" + (i + 1) + type_w);
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(inputFile);
			AudioSystem.write(audioInputStream, FLACFileWriter.FLAC, new File(
					pathW_F + "_s" + (i + 1) + type_f));
		}

		//3��. ������ flac���Ͽ� stt_api ����
		//CallSTT class�� �ҷ���
		CallSTT cstt = new CallSTT(mil, pathW_F);
	}

	public static void main(String[] args)
			throws UnsupportedAudioFileException, IOException {

	}

	//���� ��ü���̰� �� �� ���� �������� �Լ�
	public static double totalLength(String path_w)
			throws UnsupportedAudioFileException, IOException {
		File file = new File(path_w);
		AudioInputStream audioInputStream = AudioSystem
				.getAudioInputStream(file);
		AudioFormat format = audioInputStream.getFormat();
		long frames = audioInputStream.getFrameLength();
		double durationInSeconds = (frames + 0.0) / format.getFrameRate();

		return durationInSeconds;
	}

	//10�� ������ ���� �ڸ��� �Լ�
	public static void copyAudio(String sourceFileName,
			String destinationFileName, int startSecond, int secondsToCopy) {
		AudioInputStream inputStream = null;
		AudioInputStream shortenedStream = null;
		try {
			File file = new File(sourceFileName);
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
			AudioFormat format = fileFormat.getFormat();
			inputStream = AudioSystem.getAudioInputStream(file);
			int bytesPerSecond = format.getFrameSize()
					* (int) format.getFrameRate();
			inputStream.skip(startSecond * bytesPerSecond);
			long framesOfAudioToCopy = secondsToCopy
					* (int) format.getFrameRate();
			shortenedStream = new AudioInputStream(inputStream, format,
					framesOfAudioToCopy);
			File destinationFile = new File(destinationFileName);
			AudioSystem.write(shortenedStream, fileFormat.getType(),
					destinationFile);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			if (shortenedStream != null)
				try {
					shortenedStream.close();
				} catch (Exception e) {
					System.out.println(e);
				}
		}
	}

}
