package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import sound.CuttingFile;
//java GUI생성
public class project extends JFrame implements ActionListener{
	Container contentPane;
	JFileChooser chooser = new JFileChooser();

	Border blackline;
	
	JPanel panel1;
	JPanel panel2;
	public static JTextArea showr;
	
	JLabel sellan;
	JRadioButton eng;
	JRadioButton kor;
	ButtonGroup group;
	
	JLabel file;
	JLabel file2;
	
	JButton text;
	JButton noun;
	JButton learning;
	
	String wavp;
	static String wavp2; //wav파일 경로가 저장된 변수->다른 클래스로 넘어가기 위한 변수
	
	public project() {
		setTitle("Project_고객 상담 분석 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		
		//초기화
		selectlan();
		createmenu();
		
		setSize(1300,700);
		setVisible(true);
	}
	
	//다른 클래스로 wav경로를 넘기기위한 set, get함수
	public void setwavp(String wavp2)
	{
		this.wavp2 = wavp2;
	}
		
	public static String getwavp()
	{
		return wavp2;
	}
	

	void selectlan() {
		// TODO Auto-generated method stub
		panel1=new JPanel();
		panel1.setBackground(Color.white);
		panel1.setLayout(null);
		contentPane.add(panel1);
		
		//********언어를 선택하기 위한 라디오박스****************
		sellan=new JLabel("언어 선택 :");
		sellan.setSize(80,40);
		sellan.setLocation(30, 5);
		panel1.add(sellan);

		eng = new JRadioButton("영어");
		eng.setSize(60,40);
		eng.setLocation(100,5);
		eng.setBackground(Color.white);
		panel1.add(eng);
		
		kor = new JRadioButton("한국어");
		kor.setSize(80,40);
		kor.setLocation(160,5);
		kor.setBackground(Color.white);
		panel1.add(kor);
			
		group = new ButtonGroup();
		group.add(eng);
		group.add(kor);
		//*********************************************
		
		//************내가 선택한 파일의 경로******************
		file=new JLabel("");
		file2=new JLabel("파일경로 :");
		file.setSize(700,20);
		file.setLocation(630, 15);
		file2.setSize(100,20);
		file2.setLocation(550, 15);
		panel1.add(file);
		panel1.add(file2);
		//*********************************************
		
		//******************보여질부분*********************
		panel2=new JPanel();
		panel2.setBackground(Color.white);
		panel2.setLayout(new CardLayout());
		panel2.setSize(800,500);
		panel2.setLocation(250,50);
		panel1.add(panel2);
		
		showr=new JTextArea();
		blackline = BorderFactory.createLineBorder(Color.black);
		showr.setBackground(Color.white);
		showr.setBorder(blackline); 
	    panel2.add(showr);

	    //스크롤바 자동으로 생성
	    JScrollPane scrollPane = new JScrollPane(showr);
	    panel2.add(scrollPane);
		//**********************************************
		
		//*****************1단계 버튼**********************
		text=new JButton("음성파일 텍스트로 변환");
		text.setBackground(Color.YELLOW);
		text.setSize(400, 50);
		text.setLocation(25,579);
		panel1.add(text);
		text.addActionListener(this);
		//**********************************************
		
		//******************2단계 버튼*********************
		noun=new JButton("명사 추출");
		noun.setBackground(Color.YELLOW);
		noun.setSize(400, 50);
		noun.setLocation(440,579);
		panel1.add(noun);
		//**********************************************
		
		//******************3단계 버튼*********************
		learning=new JButton("감정 학습시키기");
		learning.setBackground(Color.YELLOW);
		learning.setSize(400, 50);
		learning.setLocation(855,579);
		panel1.add(learning);
		//***********************************************
	}

	//메뉴 선택창만들기
	void createmenu() {
		// TODO Auto-generated method stub
		JMenuBar mb = new JMenuBar();
		JMenuItem [] menuItem = new JMenuItem [3];
		
		JMenu fileMenu = new JMenu("File");
		String[] itemTitle = {"Open", "Save", "SaveAs"};		
				
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(new MenuActionListener());
			fileMenu.add(menuItem[i]);
		}
		mb.add(fileMenu);

		this.setJMenuBar(mb);
	}
	
	//메뉴들을 눌렀을 때
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			//열기 버튼 눌렀을 때
			if(cmd.equals("Open")) {
				if(chooser.showOpenDialog(project.this) == JFileChooser.APPROVE_OPTION){
					//파일 경로로 내용이 보여지도록 설정
					file.setText(chooser.getSelectedFile().toString());
					
					//파일 경로에서 확장자 값을 떼고 string 변수에 저장(wav)
					wavp=file.getText().substring(0, file.getText().length()-4);
					
					//경로가 E:/file/flac/test6_1114 변경되야함
					//따라서 E:\file\flac\test6_1114을 E:/file/flac/test6_1114로 변경
					wavp2=wavp.replace("\\","/");
					
					//다른 클래스에서 접근할 수 있도록 set함수에서 값 설정
					setwavp(wavp2);
 				 }
             }
				
			//저장버튼을 눌렀을 때
			else if(cmd.equals("Save")) {
				
			}
			
			//SaveAs 눌렀을 때 
			else {
				
			}
		}
	}

	//처음 실행되는 부분
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new project();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//1번 버튼을 눌렀을 때
		if (e.getSource()==text) {
			try {
				if(file.getText()==""){ //파일을 선택하지 않았을 때\
					//경고창 띄우기
					JOptionPane.showMessageDialog(this, " 파일을 선택해주세요. ", "오류", JOptionPane.ERROR_MESSAGE);
				}
				else {
					System.out.println("시작!!");
					//CuttingFile class로 넘어감
					CuttingFile cf = new CuttingFile();
				}
			} catch (UnsupportedAudioFileException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
		//2번 버튼을 눌렀을 때
		//3번 버튼을 눌렀을 때	
	}
}
