package tetris;
 
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
 
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
 
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener{
 private int mapRow = 21;
 private int mapCol = 12;
 private int mapGame[][] = new int[mapRow][mapCol];//����һ����ά����ռ䣬����������ǵĵ�ͼ��Ϣ
 
 int mark=0;
 
 private Timer timer;
 private int score = 0;//��¼�ɼ�
 Random random = new Random();
 private int curShapeType = -1;
 private int curShapeState = -1;//���õ�ǰ����״���ͺ͵�ǰ����״״̬
 private int nextShapeType = -1;
 private int nextShapeState = -1;//������һ�γ��ֵķ���������ͺ�״̬
 
 private int posx = 0;
 private int posy = 0;
 
 public void clear(){
	 int score=0;
	 int mark =0;
	 int turnState;
	 int x;
	 int y;
	 int i=0;
	 int j=0;
	 int flag=0;
 }
 
 private final int shapes[][][] = new int[][][]{
  //T���ΰ���ʱ���˳��洢
  {
  {0,1,0,0, 1,1,1,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 1,1,0,0, 0,1,0,0, 0,0,0,0},
  {1,1,1,0, 0,1,0,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 0,1,1,0, 0,1,0,0, 0,0,0,0}
  },
  //I���ΰ���ʱ���˳��洢
  {
  {0,0,0,0, 1,1,1,1, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 0,1,0,0, 0,1,0,0, 0,1,0,0},
  {0,0,0,0, 1,1,1,1, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 0,1,0,0, 0,1,0,0, 0,1,0,0}
  },
  //��Z�ΰ���ʱ���˳��洢
  {
  {0,1,1,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,0,0,0, 1,1,0,0, 0,1,0,0, 0,0,0,0},
  {0,1,1,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,0,0,0, 1,1,0,0, 0,1,0,0, 0,0,0,0}
  },
  //Z�ΰ���ʱ���˳��洢
  {
  {1,1,0,0, 0,1,1,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 1,1,0,0, 1,0,0,0, 0,0,0,0},
  {1,1,0,0, 0,1,1,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 1,1,0,0, 1,0,0,0, 0,0,0,0}
  },
  //J���ΰ���ʱ���˳��洢
  {
  {0,1,0,0, 0,1,0,0, 1,1,0,0, 0,0,0,0},
  {1,1,1,0, 0,0,1,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,0,0,0, 1,0,0,0, 0,0,0,0},
  {1,0,0,0, 1,1,1,0, 0,0,0,0, 0,0,0,0}
  },
  //L���ΰ���ʱ���˳��洢
  {
  {1,0,0,0, 1,0,0,0, 1,1,0,0, 0,0,0,0},
  {0,0,1,0, 1,1,1,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 0,1,0,0, 0,1,0,0, 0,0,0,0},
  {1,1,1,0, 1,0,0,0, 0,0,0,0, 0,0,0,0}
  },
  //�����ΰ���ʱ���˳��洢
  {
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0}
  }
 };
 private int rowRect = 4;
 private int colRect = 4;//�������ǰѴ洢��ͼ�񿴳���һ��4*4�Ķ�ά���飬��Ȼ���������ǲ���һά�������洢����ʵ�ʻ���Ҫ���ɶ�ά������ʵ��
 private int rectWidth = 10;
 
 public GamePanel()//���캯��----�����õ�ͼ
 {
 createRect();
 initMap();//��ʼ�������ͼ
 setWall();//����ǽ
// CreateRect();
 timer = new Timer(500,new TimerListener());
 timer.start();
 }
 
 class TimerListener implements ActionListener{
 public void actionPerformed(ActionEvent e)
 {
  moveDown();
 }
 }
 
 public final void setWall()//��0�к͵�11�ж���ǽ����20��Ҳ��ǽ
 {
 for(int i = 0; i < mapRow; i++)//�Ȼ���
 {
  mapGame[i][0] = 2;
  mapGame[i][11] = 2;
 }
 for(int j = 1; j < mapCol-1; j++)//�����һ��
 {
  mapGame[20][j] = 2;
 }
 mark=6;
 }
 
 public final void initMap()//��ʼ�������ͼ��ǽ��ID��2���ո��ID��0�������ID��1
 {
	 
 for(int i = 0; i < mapRow; i++)
 {
  for(int j = 0; j < mapCol; j++)
  {
  mapGame[i][j] = 0;
  }
 }
   mark=1;
 }
 
 public final void createRect()//��������---�����ǰ�ķ������ͺ�״̬�����ھ�������һ�εģ���������ھ����õ�ǰ�Ĳ���������һ�ε�״̬������
 {
 if(curShapeType == -1 && curShapeState == -1)//��ǰ�ķ���״̬��Ϊ1����ʾ��Ϸ�ſ�ʼ
 {
  curShapeType = random.nextInt(shapes.length);
  curShapeState = random.nextInt(shapes[0].length);
 }
 else
 {
  curShapeType = nextShapeType;
  curShapeState = nextShapeState;
 }
 nextShapeType = random.nextInt(shapes.length);
 nextShapeState = random.nextInt(shapes[0].length);
 posx = 0; 
 posy = 1;//ǽ�����ϽǴ�������
 if(gameOver(posx,posy,curShapeType,curShapeState))
 {
  JOptionPane.showConfirmDialog(null, "��Ϸ������", "��ʾ", JOptionPane.OK_OPTION);
  System.exit(0);
 }
 }
 
 
 public final boolean  gameOver(int x, int y, int ShapeType, int ShapeState)//�ж���Ϸ�Ƿ����
 {
 if(isOrNoMove(x,y,ShapeType,ShapeState))
 {
  return false;
 }
 return true;
 }
 
 public boolean isOrNoMove(int x, int y, int ShapeType, int ShapeState)//�жϵ�ǰ�����ͼ���Ƿ�����ƶ�,�����ص�ǿ��x,y��������ָ4*4�Ķ�ά���飨����ͼ�ε��Ǹ����飩�����Ͻ�Ŀ��
 {
 for(int i = 0; i < rowRect ; i++)
 {
  for(int j = 0; j < colRect; j++)
  {
  if(shapes[ShapeType][ShapeState][i*colRect+j] == 1 && mapGame[x+i][y+j] == 1
  || shapes[ShapeType][ShapeState][i*colRect+j] == 1 && mapGame[x+i][y+j] == 2)
  {
   return false;
  }
  }
 }
 return true;
 }
 
 
 public void turn()//��ת
 {
 int temp = curShapeState;
 curShapeState = (curShapeState+1) % shapes[0].length;
 if(isOrNoMove(posx,posy,curShapeType,curShapeState))
 {
 }
 else
 {
  curShapeState = temp;
 }
 repaint();
 }
 
 public void moveDown()//�����ƶ�
 {
 if(isOrNoMove(posx+1,posy,curShapeType,curShapeState))
 {
  posx++;
 }
 else
 {
  addToMap();//�����й̶��ڵ�ͼ��
  checkLine();
  createRect();//���´���һ���µķ���
 }
 repaint();
 mark=3;
 }
 
 public void moveLeft()//�����ƶ�
 {
 if(isOrNoMove(posx,posy-1,curShapeType,curShapeState))
 {
  posy--;
 }
 repaint();
 }
 
 public void moveRight()//�����ƶ�
 {
 if(isOrNoMove(posx,posy+1,curShapeType,curShapeState))
 {
  posy++;
 }
 repaint();
 }
 
 public void addToMap()//�̶�����������һͼ�񵽵�ͼ��
 {
 for(int i = 0; i < rowRect; i++)
 {
  for(int j = 0; j < colRect; j++)
  {
  if(shapes[curShapeType][curShapeState][i*colRect+j] == 1)
  {
   mapGame[posx+i][posy+j] = shapes[curShapeType][curShapeState][i*colRect+j];
  }
  }
 }
 }
 
 public void checkLine()//���һ����Щ�����Ƿ������е�
 {
 int count = 0;
 for(int i = mapRow-2; i >= 0; i--)
 {
  count = 0;
  for(int j = 1; j < mapCol-1; j++)
  {
  if(mapGame[i][j] == 1)
  {
   count++;
  }
  else
   break;
  }
  if(count >= mapCol-2)
  {
  for(int k = i; k > 0; k--)
  {
   for(int p = 1; p < mapCol-1; p++)
   {
   mapGame[k][p] = mapGame[k-1][p];
   }
  }
  score += 10;
  i++;
  }
 }
 }
 
 public void paint(Graphics g)//���»��ƴ���
 {
 super.paint(g);
 for(int i = 0; i < rowRect; i++)//������������ķ���
 {
  for(int j = 0; j < colRect; j++)
  {
  if(shapes[curShapeType][curShapeState][i*colRect+j] == 1)
  {
   g.fillRect((posy+j+1)*rectWidth, (posx+i+1)*rectWidth, rectWidth, rectWidth);
  }
  }
 }
 for(int i = 0; i < mapRow; i++)//���Ƶ�ͼ�����Ѿ��̶��õķ�����Ϣ
 {
  for(int j = 0; j < mapCol; j++)
  {
  if(mapGame[i][j] == 2)//��ǽ
  {
   g.drawRect((j+1)*rectWidth, (i+1)*rectWidth, rectWidth, rectWidth);
  }
  if(mapGame[i][j] == 1)//��С����
  {
   g.fillRect((j+1)*rectWidth, (i+1)*rectWidth, rectWidth, rectWidth);
  }
  }
 }
 g.drawString("score = "+ score, 225, 15);
 g.drawString("��һ�����飺", 225, 50);
 for(int i = 0; i < rowRect; i++)
 {
  for(int j = 0; j < colRect; j++)
  {
  if(shapes[nextShapeType][nextShapeState][i*colRect+j] == 1)
  {
   g.fillRect(225+(j*rectWidth), 100+(i*rectWidth), rectWidth, rectWidth);
  }
  }
 }
 }
 
 public void NewGame()//��Ϸ���¿�ʼ
 {
 score = 0;
 initMap();
 setWall();
 createRect();
 repaint();
 }
 
 public void StopGame()//��Ϸ��ͣ
 {
 timer.stop();
 }
 
 public void ContinueGame()
 {
 timer.start();
 }
 
 @Override
 public void keyTyped(KeyEvent e) {
 
 }
 
 @Override
 public void keyPressed(KeyEvent e) {
 switch(e.getKeyCode())
 {
 case KeyEvent.VK_UP://��----��ת
  turn();
  break;
 case KeyEvent.VK_DOWN://��----�����ƶ�
  moveDown();
  break;
 case KeyEvent.VK_LEFT://��----�����ƶ�
  moveLeft();
  break;
 case KeyEvent.VK_RIGHT://��----�����ƶ�
  moveRight();
  break;
 }
 
 }
 
 @Override
 public void keyReleased(KeyEvent e) {
 // TODO Auto-generated method stub
 
 }
 
}
