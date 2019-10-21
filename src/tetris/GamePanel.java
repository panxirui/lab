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
 private int mapGame[][] = new int[mapRow][mapCol];//开辟一个二维数组空间，用来存放我们的地图信息
 
 int mark=0;
 
 private Timer timer;
 private int score = 0;//记录成绩
 Random random = new Random();
 private int curShapeType = -1;
 private int curShapeState = -1;//设置当前的形状类型和当前的形状状态
 private int nextShapeType = -1;
 private int nextShapeState = -1;//设置下一次出现的方块组的类型和状态
 
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
  //T字形按逆时针的顺序存储
  {
  {0,1,0,0, 1,1,1,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 1,1,0,0, 0,1,0,0, 0,0,0,0},
  {1,1,1,0, 0,1,0,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 0,1,1,0, 0,1,0,0, 0,0,0,0}
  },
  //I字形按逆时针的顺序存储
  {
  {0,0,0,0, 1,1,1,1, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 0,1,0,0, 0,1,0,0, 0,1,0,0},
  {0,0,0,0, 1,1,1,1, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 0,1,0,0, 0,1,0,0, 0,1,0,0}
  },
  //倒Z形按逆时针的顺序存储
  {
  {0,1,1,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,0,0,0, 1,1,0,0, 0,1,0,0, 0,0,0,0},
  {0,1,1,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,0,0,0, 1,1,0,0, 0,1,0,0, 0,0,0,0}
  },
  //Z形按逆时针的顺序存储
  {
  {1,1,0,0, 0,1,1,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 1,1,0,0, 1,0,0,0, 0,0,0,0},
  {1,1,0,0, 0,1,1,0, 0,0,0,0, 0,0,0,0},
  {0,1,0,0, 1,1,0,0, 1,0,0,0, 0,0,0,0}
  },
  //J字形按逆时针的顺序存储
  {
  {0,1,0,0, 0,1,0,0, 1,1,0,0, 0,0,0,0},
  {1,1,1,0, 0,0,1,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,0,0,0, 1,0,0,0, 0,0,0,0},
  {1,0,0,0, 1,1,1,0, 0,0,0,0, 0,0,0,0}
  },
  //L字形按逆时针的顺序存储
  {
  {1,0,0,0, 1,0,0,0, 1,1,0,0, 0,0,0,0},
  {0,0,1,0, 1,1,1,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 0,1,0,0, 0,1,0,0, 0,0,0,0},
  {1,1,1,0, 1,0,0,0, 0,0,0,0, 0,0,0,0}
  },
  //田字形按逆时针的顺序存储
  {
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0},
  {1,1,0,0, 1,1,0,0, 0,0,0,0, 0,0,0,0}
  }
 };
 private int rowRect = 4;
 private int colRect = 4;//这里我们把存储的图像看成是一个4*4的二维数组，虽然在上面我们采用一维数组来存储，但实际还是要看成二维数组来实现
 private int rectWidth = 10;
 
 public GamePanel()//构造函数----创建好地图
 {
 createRect();
 initMap();//初始化这个地图
 setWall();//设置墙
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
 
 public final void setWall()//第0列和第11列都是墙，第20行也是墙
 {
 for(int i = 0; i < mapRow; i++)//先画列
 {
  mapGame[i][0] = 2;
  mapGame[i][11] = 2;
 }
 for(int j = 1; j < mapCol-1; j++)//画最后一行
 {
  mapGame[20][j] = 2;
 }
 mark=6;
 }
 
 public final void initMap()//初始化这个地图，墙的ID是2，空格的ID是0，方块的ID是1
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
 
 public final void createRect()//创建方块---如果当前的方块类型和状态都存在就设置下一次的，如果不存在就设置当前的并且设置下一次的状态和类型
 {
 if(curShapeType == -1 && curShapeState == -1)//当前的方块状态都为1，表示游戏才开始
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
 posy = 1;//墙的左上角创建方块
 if(gameOver(posx,posy,curShapeType,curShapeState))
 {
  JOptionPane.showConfirmDialog(null, "游戏结束！", "提示", JOptionPane.OK_OPTION);
  System.exit(0);
 }
 }
 
 
 public final boolean  gameOver(int x, int y, int ShapeType, int ShapeState)//判断游戏是否结束
 {
 if(isOrNoMove(x,y,ShapeType,ShapeState))
 {
  return false;
 }
 return true;
 }
 
 public boolean isOrNoMove(int x, int y, int ShapeType, int ShapeState)//判断当前的这个图形是否可以移动,这里重点强调x,y的坐标是指4*4的二维数组（描述图形的那个数组）的左上角目标
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
 
 
 public void turn()//旋转
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
 
 public void moveDown()//向下移动
 {
 if(isOrNoMove(posx+1,posy,curShapeType,curShapeState))
 {
  posx++;
 }
 else
 {
  addToMap();//将此行固定在地图中
  checkLine();
  createRect();//重新创建一个新的方块
 }
 repaint();
 mark=3;
 }
 
 public void moveLeft()//向左移动
 {
 if(isOrNoMove(posx,posy-1,curShapeType,curShapeState))
 {
  posy--;
 }
 repaint();
 }
 
 public void moveRight()//向右移动
 {
 if(isOrNoMove(posx,posy+1,curShapeType,curShapeState))
 {
  posy++;
 }
 repaint();
 }
 
 public void addToMap()//固定掉下来的这一图像到地图中
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
 
 public void checkLine()//检查一下这些行中是否有满行的
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
 
 public void paint(Graphics g)//重新绘制窗口
 {
 super.paint(g);
 for(int i = 0; i < rowRect; i++)//绘制正在下落的方块
 {
  for(int j = 0; j < colRect; j++)
  {
  if(shapes[curShapeType][curShapeState][i*colRect+j] == 1)
  {
   g.fillRect((posy+j+1)*rectWidth, (posx+i+1)*rectWidth, rectWidth, rectWidth);
  }
  }
 }
 for(int i = 0; i < mapRow; i++)//绘制地图上面已经固定好的方块信息
 {
  for(int j = 0; j < mapCol; j++)
  {
  if(mapGame[i][j] == 2)//画墙
  {
   g.drawRect((j+1)*rectWidth, (i+1)*rectWidth, rectWidth, rectWidth);
  }
  if(mapGame[i][j] == 1)//画小方格
  {
   g.fillRect((j+1)*rectWidth, (i+1)*rectWidth, rectWidth, rectWidth);
  }
  }
 }
 g.drawString("score = "+ score, 225, 15);
 g.drawString("下一个方块：", 225, 50);
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
 
 public void NewGame()//游戏重新开始
 {
 score = 0;
 initMap();
 setWall();
 createRect();
 repaint();
 }
 
 public void StopGame()//游戏暂停
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
 case KeyEvent.VK_UP://上----旋转
  turn();
  break;
 case KeyEvent.VK_DOWN://下----向下移动
  moveDown();
  break;
 case KeyEvent.VK_LEFT://左----向左移动
  moveLeft();
  break;
 case KeyEvent.VK_RIGHT://右----向右移动
  moveRight();
  break;
 }
 
 }
 
 @Override
 public void keyReleased(KeyEvent e) {
 // TODO Auto-generated method stub
 
 }
 
}
