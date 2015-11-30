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
//java GUI����
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
	static String wavp2; //wav���� ��ΰ� ����� ����->�ٸ� Ŭ������ �Ѿ�� ���� ����
	
	public project() {
		setTitle("Project_�� ��� �м� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		
		//�ʱ�ȭ
		selectlan();
		createmenu();
		
		setSize(1300,700);
		setVisible(true);
	}
	
	//�ٸ� Ŭ������ wav��θ� �ѱ������ set, get�Լ�
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
		
		//********�� �����ϱ� ���� �����ڽ�****************
		sellan=new JLabel("��� ���� :");
		sellan.setSize(80,40);
		sellan.setLocation(30, 5);
		panel1.add(sellan);

		eng = new JRadioButton("����");
		eng.setSize(60,40);
		eng.setLocation(100,5);
		eng.setBackground(Color.white);
		panel1.add(eng);
		
		kor = new JRadioButton("�ѱ���");
		kor.setSize(80,40);
		kor.setLocation(160,5);
		kor.setBackground(Color.white);
		panel1.add(kor);
			
		group = new ButtonGroup();
		group.add(eng);
		group.add(kor);
		//*********************************************
		
		//************���� ������ ������ ���******************
		file=new JLabel("");
		file2=new JLabel("���ϰ�� :");
		file.setSize(700,20);
		file.setLocation(630, 15);
		file2.setSize(100,20);
		file2.setLocation(550, 15);
		panel1.add(file);
		panel1.add(file2);
		//*********************************************
		
		//******************�������κ�*********************
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

	    //��ũ�ѹ� �ڵ����� ����
	    JScrollPane scrollPane = new JScrollPane(showr);
	    panel2.add(scrollPane);
		//**********************************************
		
		//*****************1�ܰ� ��ư**********************
		text=new JButton("�������� �ؽ�Ʈ�� ��ȯ");
		text.setBackground(Color.YELLOW);
		text.setSize(400, 50);
		text.setLocation(25,579);
		panel1.add(text);
		text.addActionListener(this);
		//**********************************************
		
		//******************2�ܰ� ��ư*********************
		noun=new JButton("��� ����");
		noun.setBackground(Color.YELLOW);
		noun.setSize(400, 50);
		noun.setLocation(440,579);
		panel1.add(noun);
		//**********************************************
		
		//******************3�ܰ� ��ư*********************
		learning=new JButton("���� �н���Ű��");
		learning.setBackground(Color.YELLOW);
		learning.setSize(400, 50);
		learning.setLocation(855,579);
		panel1.add(learning);
		//***********************************************
	}

	//�޴� ����â�����
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
	
	//�޴����� ������ ��
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			//���� ��ư ������ ��
			if(cmd.equals("Open")) {
				if(chooser.showOpenDialog(project.this) == JFileChooser.APPROVE_OPTION){
					//���� ��η� ������ ���������� ����
					file.setText(chooser.getSelectedFile().toString());
					
					//���� ��ο��� Ȯ���� ���� ���� string ������ ����(wav)
					wavp=file.getText().substring(0, file.getText().length()-4);
					
					//��ΰ� E:/file/flac/test6_1114 ����Ǿ���
					//���� E:\file\flac\test6_1114�� E:/file/flac/test6_1114�� ����
					wavp2=wavp.replace("\\","/");
					
					//�ٸ� Ŭ�������� ������ �� �ֵ��� set�Լ����� �� ����
					setwavp(wavp2);
 				 }
             }
				
			//�����ư�� ������ ��
			else if(cmd.equals("Save")) {
				
			}
			
			//SaveAs ������ �� 
			else {
				
			}
		}
	}

	//ó�� ����Ǵ� �κ�
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new project();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//1�� ��ư�� ������ ��
		if (e.getSource()==text) {
			try {
				if(file.getText()==""){ //������ �������� �ʾ��� ��\
					//���â ����
					JOptionPane.showMessageDialog(this, " ������ �������ּ���. ", "����", JOptionPane.ERROR_MESSAGE);
				}
				else {
					System.out.println("����!!");
					//CuttingFile class�� �Ѿ
					CuttingFile cf = new CuttingFile();
				}
			} catch (UnsupportedAudioFileException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
		//2�� ��ư�� ������ ��
		//3�� ��ư�� ������ ��	
	}
}
