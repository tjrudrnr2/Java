package Seo;

import java.util.Random;

public class Shape
{
	int ShapeTable[][][] = new int[][][]
	{       	
		{{0, 0}, {0, 1}, {1, 1}, {1, 2},
		 {1, 0}, {2, 0}, {0, 1},  {1, 1},
		 {0, 0}, {0 , 1},{1 , 1},{1 , 2},
		 {1 , 0},{2, 0}, {0, 1},  {1, 1}}, // S�ڸ��
		
		{{0, 0}, {0, 1}, {1, 1}, {2, 1},
		 {1, 0}, {0, 1}, {1, 1}, {0, 2},
		 {0, 0}, {0, 1}, {1, 1}, {2, 1},
		 {1, 0}, {0, 1}, {1, 1}, {0, 2}},  // S�ݴ� ���
		
		{{0, 1}, {1, 1}, {2, 1}, {3, 1},
		 {1, 0}, {1, 1}, {1, 2}, {1, 3},
		 {0, 1}, {1, 1}, {2, 1}, {3, 1},
		 {1, 0}, {1, 1}, {1, 2}, {1, 3}},  // l�� ���
		
		{{1, 0}, {0, 1}, {1, 1}, {2, 1},
		 {1, 0}, {0, 1}, {1, 1}, {1, 2},
		 {0, 1}, {1, 1}, {2, 1}, {1, 2},
		 {1, 0}, {1, 1}, {2, 1}, {1, 2}},  // ���� ���
		
		{{0, 0}, {0, 1}, {1, 0}, {1, 1},
		 {0, 0}, {0, 1}, {1, 0}, {1, 1},
		 {0, 0}, {0, 1}, {1, 0}, {1, 1},
		 {0, 0}, {0, 1}, {1, 0}, {1, 1}},   // ���簢�� ���
		
		{{0, 1}, {1, 1}, {2, 1}, {2, 0},
		 {1, 0}, {1, 1}, {1, 2}, {0, 0},
		 {0, 1}, {1, 1}, {2, 1}, {0, 2},
		 {1, 0}, {1, 1}, {1, 2}, {0, 0}}, // ���� ���
		
		{{0, 0}, {0, 1}, {1, 1}, {2, 1},
  		 {1, 0}, {1, 1}, {1, 2}, {2, 0},
  		 {0, 1}, {1, 1}, {2, 1}, {0, 0},
     	 {1, 0}, {1, 1}, {1, 2}, {2, 0}}  // ���� �ݴ� ���
	};
	boolean drop = true;
	boolean gameover = false;
	Random rand = new Random();
	int idx = (int)(Math.random() * 7);
	int spinarr[][] = new int [16][2];
	int arr[][] = new int[4][2];	
	
	public int[][] getspinBlock(int idx)
	{
		try
		{
			for(int i=0;i<16;i++)
			{
				for(int j=0;j<2;j++)
				{
					spinarr[i][j] = ShapeTable[idx][i][j];
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("�ε��� ���0");
		}
		return spinarr;
	}
	public int[][] getShape(int idx)
	{
		try
		{
			for(int i = 0; i < 4; i++)
			{
				for(int j = 0; j < 2; j++)
				{
					arr[i][j]=ShapeTable[idx][i][j];
				}
			}			
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("�ε��� ���1");
		}
		return arr;
	}
    public void makeShape(int idx)
    {
        switch(idx)
        {
        case 0:
      //  	getShape(idx);
        case 1:
        	getShape(idx);
        case 2:
        	getShape(idx);
        case 3:
        	getShape(idx);
        case 4:
        	getShape(idx);
        case 5:
        	getShape(idx);
        case 6:
        	getShape(idx);
        default:
        	break;
        }
    }
}
