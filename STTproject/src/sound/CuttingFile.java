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

//1번. wav 파일을 10초 단위의 wav파일로 split
public class CuttingFile {
	// project클래스에서 값을 가져오기위해 객체 생성
	static project pj;

	static int mil = 0;

	// 확장자명 설정
	static String type_w = ".wav";
	static String type_f = ".flac";

	// source.substring(0, source.lastIndexOf('.')); 사용하여 확장자명 빼고 가져오기
	// static String pathW =
	// pj.getwavp().substring(0,pj.getwavp().lastIndexOf("."));
	// str.substring(str.length()-3, str.length()));

	// static String pathW="E:/file/wav/test6_1114";
	static String pathW_F; //현재 파일이 있는 경로와 파일을 저장할 경로
	static String pathW_w;

	// static String pathF_f = "D://졸작//file//flac//test6_1114" + type_f;

	public CuttingFile() throws UnsupportedAudioFileException, IOException {

		// 파일 경로 설정해주기
		pathW_F = pj.getwavp();
		pathW_w = pathW_F + type_w;
		
		System.out.println("파일 경로:" + pathW_F);

		// test6_1114.wav : 28초
		int totaltime = 0;
		totaltime = (int) totalLength(pathW_w);
		System.out.println("파일 전체 시간:" + totaltime);

		// totaltime의 맨 앞자리수를 받아와야함.
		// 그냥 10초마다 몇번 돌아갈수있는지를 세면 되니까
		mil = (totaltime / 10) + 1; // STT로 전달

		// 10초마다 자르기
		for (int i = 0; i < mil; i++) {
			
			// 출력되는 파일은 원본파일명 끝에 1,2,3 차례대로 붙게 함
			copyAudio(pathW_w, pathW_F + "_s" + (i + 1) + type_w, i * 10, 10); 
		}

		//2번. wav파일들을 flac파일로 변환
		for (int i = 0; i < mil; i++) {
			File inputFile = new File(pathW_F + "_s" + (i + 1) + type_w);
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(inputFile);
			AudioSystem.write(audioInputStream, FLACFileWriter.FLAC, new File(
					pathW_F + "_s" + (i + 1) + type_f));
		}

		//3번. 각각의 flac파일에 stt_api 적용
		//CallSTT class를 불러옴
		CallSTT cstt = new CallSTT(mil, pathW_F);
	}

	public static void main(String[] args)
			throws UnsupportedAudioFileException, IOException {

	}

	//파일 전체길이가 몇 초 인지 가져오는 함수
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

	//10초 단위로 파일 자르는 함수
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
