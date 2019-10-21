package tetris;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Color;
 
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements ActionListener{
 @SuppressWarnings("unused")
private int widthFrame = 500;
 @SuppressWarnings("unused")
private int heightFrame = 600;
 private JMenu menuone = new JMenu("��Ϸ");//����һ���˵�
 private JMenuItem newGame = menuone.add("���¿�ʼ");//����һ�����ò˵�ѡ��
 private JMenuItem exitGame = menuone.add("��Ϸ�˳�");
 private JMenuItem stopGame = menuone.add("��Ϸ��ͣ");
 private JMenuItem goOnGame = menuone.add("��Ϸ����");
 
 private JMenu menutwo = new JMenu("����");//�����ڶ����˵�
 private JMenuItem aboutGame = menutwo.add("������Ϸ");
 GamePanel gamepanel = new GamePanel();
 private final JLabel lblNewLabel = new JLabel("\u7ED3\u5BF9\u7F16\u7A0B\uFF1A\u987E\u6653\u5065\uFF0C\u6F58\u559C\u777F\u3002");
 
 public GameFrame()//���캯��
 {
 	getContentPane().setForeground(Color.PINK);
 	getContentPane().setBackground(Color.PINK);
 	setBackground(Color.PINK);
 addKeyListener(gamepanel);
 newGame.addActionListener(this);
 exitGame.addActionListener(this);
 stopGame.addActionListener(this);
 goOnGame.addActionListener(this);
 aboutGame.addActionListener(this);
 
 getContentPane().add(gamepanel);
 gamepanel.setLayout(null);
 lblNewLabel.setBounds(260, 220, 205, 18);
 
 gamepanel.add(lblNewLabel);
 
 JMenuBar menu = new JMenuBar();
 menu.add(menuone);
 menu.add(menutwo);
 this.setJMenuBar(menu);
 
 this.setTitle("����˹����");
 this.setBounds(50, 10, 497, 324);
 this.setVisible(true);
 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
 }
 
 public void actionPerformed(ActionEvent e)
 {
 if(e.getSource() == newGame)//��Ϸ���¿�ʼ
 {
  gamepanel.NewGame();
 }
 if(e.getSource() == exitGame)//��Ϸ�˳�
 {
  System.exit(0);
 }
 if(e.getSource() == stopGame)//��Ϸ��ͣ
 {
  gamepanel.StopGame();
 }
 if(e.getSource() == goOnGame)//��Ϸ����
 {
  gamepanel.ContinueGame();
 }
 if(e.getSource() == aboutGame)//������Ϸ��Ϣ
 {
  JOptionPane.showMessageDialog(null, "���Ҽ��ƶ������Ͻ���ת", "��ʾ", JOptionPane.OK_OPTION);
 }
 }
 
 
 public static void main(String[] args) {
 new GameFrame();
 }
}
