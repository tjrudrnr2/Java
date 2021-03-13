package Seo;
 // 컨트롤 스페이스 = 자동완성
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class maingame extends JFrame
{
	String input = "C:\\Users\\서경국\\tetris.txt";
	String output = "C:\\Users\\서경국\\tetrisoutput.txt";
	
	boolean gameflag = true;
	public int idxcnt = 0;
	public static int spincnt = 0;
	public static boolean stableflag;
	public static boolean scoreflag;
	public static boolean dropflag = true;
	public static boolean moveflag = true;
	public static int spinedBlock[][] = new int [16][2];
	public static int block[][] = new int[4][2];  // 떨어질 블럭의 모양을 담는 배열
	public static int blocked[][] = new int [20][10]; 
	// 0 빈 칸 , 1 쌓인 블럭이 있음, 2 떨어지는 블럭
	public static Button btn[][] = new Button[20][10];
	static void makeStable()  // 떨어지는 블록칸을 고정시킴
	{
		for(int i=0;i<=19;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(blocked[i][j]==2)
				{
					blocked[i][j]=1;
				}
			}
		}
	}
	static void getScore()
	{
		int blockCnt=0;
		int locationY=0;
		for(int j=19;j>=0;j--)
		{
			blockCnt=0;
			for(int i=0;i<10;i++)
			{
				if(blocked[j][i]==1)
				{
					blockCnt++;
				}
				if(blockCnt==10)
				{
					locationY=j;
					scoreflag = true;
					break;
				}
			}			
		}
		if(scoreflag)
		{
			System.out.println("한 줄 다 쌓음");
			scoreflag = false;
			for(int i=0;i<10;i++)
			{
				blocked[locationY][i]=0;
			}
			for(int i=locationY;i>0;i--)
			{
				for(int j=0;j<10;j++)
				{
					btn[i][j].setBackground(btn[i-1][j].getBackground());
					btn[i-1][j].setBackground(Color.WHITE);
					blocked[i][j]=blocked[i-1][j];
				}
			}
			for(int i=0;i<10;i++)
			{
				blocked[0][i]=0;
				btn[0][i].setBackground(Color.WHITE);
			}
		}
	}
	public static int findX()
	{
		int x=0;
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<20;j++)
			{
				if(blocked[j][i]==2)
				{
					x=i;
				}
			}
		}
		return x;
	}
	public static int findY()
	{
		int h=0;
		for(int i=19;i>0;i--)
		{
			for(int j=0;j<10;j++)
			{
				if(blocked[i][j]==2)
				{
					h = i;
				}
			}
		}
		return h;
	}
	void clearFalling()
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(blocked[i][j]==2)
				{
					blocked[i][j]=0;
					btn[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	static void spin(int idxcnt, int spincnt, int x, int height)
	{
		for(int i=0;i<4;i++)
		{
			int spinx = spinedBlock[((spincnt*4)%16) + i][0];
			int spiny = spinedBlock[((spincnt*4)%16) + i][1];
			System.out.println((spincnt*4)%16+i);
		//		System.out.println(spinx + " " + spiny);
			blocked[height+spinx][x + spiny -1]=2;
			btn[height+spinx][x + spiny -1].setBackground(Color.BLACK);
		}
	}
	static void down()
	{
		try
		{
			for(int i=0;i<10;i++) // 바닥에 도착했는지 확인
			{
				if(blocked[19][i]==2)
				{
					stableflag = true;
					dropflag = true;
					break;
				}
			}
			if(stableflag)
			{
				makeStable();
				stableflag=false;
				getScore();
				return;
			}
			for(int i=0;i<19;i++)
			{
				for(int j=0;j<10;j++)
				{
					if(blocked[18-i][j]==2 && blocked[19-i][j] == 1) // 떨어지는 블록 아래칸이 쌓인 블록일 경우
					{
						// 떨어지는 블록도 쌓인 상태로 변경
						stableflag = true;
						dropflag = true;
						break;
					}								
				}
			}
			if(stableflag)
			{
				makeStable();
				stableflag = false;
				getScore();
				return;				
			}
			for(int a=0;a<19;a++)					
			{
				for(int b=0;b<10;b++)
				{
					if(blocked[18-a][b] == 2) // 떨어지는 블록 구별
					{						
						blocked[18-a][b]=0;
						blocked[19-a][b]=2;
						btn[19-a][b].setBackground(btn[18-a][b].getBackground());															
						btn[18-a][b].setBackground(Color.WHITE);						
					}
				}
			}				
			if(stableflag)
			{
				makeStable();
				stableflag = false;
				return;
			}
			for(int j=0;j<10;j++)
			{
				if(blocked[0][j]==0)
				{
					btn[0][j].setBackground(Color.WHITE);					
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{	
			System.out.println("인덱스 벗어남3");
		}
	}
	
	class Down implements Runnable
	{
		public void run()
		{
			while(true)
			{
				if(dropflag == true)
				{			
					dropflag=false;
					Shape s = new Shape();
					idxcnt = s.idx;
					spinedBlock = s.getspinBlock(idxcnt); // 블록의 회전모양 담는 배열
					s.makeShape(s.idx);
					block = s.getShape(s.idx);
					int  tempx, tempy;
					try
					{
						for(int i=0;i<4;i++)
						{
							for(int j=0;j<2;j++)
							{
								tempx = block[i][0];								
								tempy = block[i][1] + 4;
								if(blocked[tempx][tempy] == 1)
								{
									gameflag = false;
									System.out.println("Game Over");
									return;
								}
								blocked[tempx][tempy]=2;
								btn[tempx][tempy].setBackground(Color.BLACK);
							}
						}			
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						System.out.println("인덱스 벗어남2");
					}
				}
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					down();
				}
				catch(ArrayIndexOutOfBoundsException e)
				{	
					System.out.println("인덱스 벗어남3");
				}
			}
		}
	}
	public maingame()
	{
		JPanel p1 = new JPanel(new GridLayout(20,10));
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<10;j++)
			{
				btn[i][j]=new Button();
				btn[i][j].setBackground(Color.WHITE);
				Button b =btn[i][j];
				b.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								
							}
						});
				b.addKeyListener(new KeyListener()
						{
							@Override
							public void keyTyped(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void keyPressed(KeyEvent e) {
								// TODO Auto-generated method stub
								if(e.getKeyCode()==37) // 왼쪽 
								{
									try
									{
										moveflag = true;
										for(int i=0;i<20;i++)  // 벽에 붙어있을 경우
										{
											if(blocked[i][0]==2)
											{
												return;
											}
										}
										for(int b=1;b<10;b++)					
										{
											for(int a=0;a<20;a++)
											{
												if(blocked[a][b] == 2 && blocked[a][b-1] == 1) // 움직일 블록 구별
												{	
													moveflag = false;
												}
											}
										}				
										if(moveflag)
										{
											for(int b=1;b<10;b++)					
											{
												for(int a=0;a<20;a++)
												{
													if(blocked[a][b] == 2 && blocked[a][b-1] == 0) // 움직일 블록 구별
													{	
														blocked[a][b]=0;
														blocked[a][b-1]=2;
														btn[a][b-1].setBackground(btn[a][b].getBackground());	
														btn[a][b].setBackground(Color.WHITE);						
													}
												}
											}				
											for(int j=0;j<20;j++)
											{
												if(blocked[j][9]==0)
												{
													btn[j][9].setBackground(Color.WHITE);									
												}
											}											
										}
									}
									catch(ArrayIndexOutOfBoundsException e1)
									{	
										System.out.println("인덱스 벗어남3");
									}
								}
								else if(e.getKeyCode() == 38) // 위쪽
								{
									int x = findX();
									int h = findY();
									clearFalling();
									++spincnt;
									spin(idxcnt, spincnt, x, h);
								}
								else if(e.getKeyCode() == 39) // 오른쪽
								{
									try
									{
										moveflag = true;
										for(int i=0;i<20;i++)
										{
											if(blocked[i][9]==2)
											{
												return;
											}
										}
										for(int b=0;b<9;b++)					
										{
											for(int a=0;a<20;a++)
											{
												if(blocked[a][8-b] == 2 && blocked[a][9-b] == 1) // 움직일 블록 구별
												{			
													moveflag = false;
												}
											}
										}		
										if(moveflag)
										{
											for(int b=0;b<9;b++)					
											{
												for(int a=0;a<20;a++)
												{
													if(blocked[a][8-b] == 2 && blocked[a][9-b] == 0) // 움직일 블록 구별
													{			
														blocked[a][8-b]=0;
														blocked[a][9-b]=2;
														btn[a][9-b].setBackground(btn[a][8-b].getBackground());	
														btn[a][8-b].setBackground(Color.WHITE);																				
													}
												}
											}	
											for(int j=0;j<20;j++)
											{
												if(blocked[j][0]==0)
												{
													btn[j][0].setBackground(Color.WHITE);												
												}
											}
										}																	
									}
									catch(ArrayIndexOutOfBoundsException e1)
									{	
										System.out.println("인덱스 벗어남3");
									}
								}
								else if(e.getKeyCode() == 40) // 아래쪽
								{
									down();
								}			
								else if(e.getKeyCode() == 83) // save
								{
									try{
										char c;
										FileWriter bw = new FileWriter("C:\\tet\\tetris.txt");
										for(int ii=0;ii<20;ii++)
										{
											for(int jj=0;jj<10;jj++)
											{
												c=Character.forDigit(blocked[ii][jj], 4);
												bw.write(c);
												bw.write(' ');
											}
											bw.write('\n');
										}
										bw.close();
									}
									catch(IOException e2)
									{
										e2.getStackTrace();
									}
								}
								else if(e.getKeyCode() == 76) // Load
								{
									try
									{
										clearFalling();
										int c;
										FileReader fw = new FileReader("C:\\tet\\tetris.txt");
										for(int i=0;i<20;i++)
										{
											for(int j=0;j<10;j++)
											{
												c=fw.read();
												System.out.println(c);
												if(c-'0'==2)
												{
													blocked[i][j]=2;
													btn[i][j].setBackground(Color.BLACK);
												}
												if(c-'0'==1)
												{
													blocked[i][j]=1;
													btn[i][j].setBackground(Color.BLACK);
												}
											}
										}
										fw.close();
									}
									catch(FileNotFoundException e1)
									{
										e1.getStackTrace();
									}
									catch(IOException e2)
									{
										e2.getStackTrace();
									}
								}
								System.out.println(e.getKeyCode()
										+"를 입력했습니다.");
							}

							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}					
						});		
				p1.add(btn[i][j]);
			}
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(p1);
		setResizable(false);
		setSize(400,600);
		setVisible(true);
		
		Thread t = new Thread(new Down());
		t.setDaemon(true);
		t.start();
	}
	public static void main(String[] args)
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<10;j++)
			{
				blocked[i][j]=0;
			}
		}
		new maingame();
	}
}