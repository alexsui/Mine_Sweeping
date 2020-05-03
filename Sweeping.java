 package mineSweeping;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Sweeping implements ActionListener,MouseListener {
	
	ImageIcon bannerIcon =new ImageIcon("banner.jpg");
	ImageIcon bomb = new ImageIcon("炸彈.jpg");
	ImageIcon lose = new ImageIcon("踩到.png");
	ImageIcon win =new ImageIcon("win.png");
	ImageIcon winFlag =new ImageIcon("winflag.jpg");
	ImageIcon mark =new ImageIcon("mark.png");
	JButton bannerbtn =new JButton( bannerIcon);
	JFrame frame=new JFrame();
	
	
	//數據結構
	int ROW = 20;
	int COL = 20;
	int[][] data  = new int[ROW][COL];
	JButton[][] btns = new JButton[ROW][COL];
	int BOMBCOUNT =25;
	int bombcode = -1; //代表地雷
	int opened = 0;
	int unopened = ROW*COL;
	int second=0;
	
	JLabel unopen= new JLabel("未開: "+ unopened);
	JLabel open= new JLabel("已開: "+ opened);
	JLabel time = new JLabel("時間: "+second+"s");
	 Timer timer =new Timer(1000,this);
	
	 
    MenuBar menu = new MenuBar();
    
	Menu selectmodel = new Menu("level");
	MenuItem mod_easy = new MenuItem("easy");
	MenuItem mod_normal = new MenuItem("normal");
	MenuItem mod_hard = new MenuItem("hard");
	
	public Sweeping() {
		
      frame.setSize(400,500);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      
      
      setHeader();
      
      addBomb();
      
      setButton();
      
      timer.restart();
      frame.setVisible(true);
      menuDemo();
      
      
      
	}
	private void menuDemo() {
		
			frame.setMenuBar(menu);
		    
			selectmodel.add(mod_easy);
			mod_easy.addActionListener(this);
			
			selectmodel.add(mod_normal);
			mod_normal.addActionListener(this);
			
			selectmodel.add(mod_hard);
			mod_hard.addActionListener(this);
			menu.add(selectmodel);
			frame.setVisible(true);
	}
	
	
	
	private void addBomb() {
		Random rand = new Random();
		
		for(int i=0;i<BOMBCOUNT;) {
			int r = rand.nextInt(ROW);
			int c = rand.nextInt(COL);
			if(data[r][c]!=bombcode) {
				data[r][c]=bombcode;
				i++;
			}
		}
		//判斷格子周邊地雷個數
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COL;j++) {
				if(data[i][j]==bombcode)
					continue;
				int tempCount = 0;
				if(i>0 && j>0 && data[i-1][j-1]==bombcode)
					tempCount++;
				if(i>0 && data[i-1][j]== bombcode)
					tempCount++;
				if(i>0 && j<19 && data[i-1][j+1]== bombcode)
					tempCount++;
				if(j>0 && data[i][j-1]== bombcode)
					tempCount++;
				if(j<19 && data[i][j+1]== bombcode)
					tempCount++;
				if(j>0 && i<19 && data[i+1][j-1]== bombcode)
					tempCount++;
				if(i<19 && data[i+1][j]== bombcode)
					tempCount++;
				if(i<19 && j<19 && data[i+1][j+1]== bombcode)
					tempCount++;
				data[i][j]=tempCount;
			}
		}
	}
	
	
	public void setButton() {
		Container con =new Container();
		con.setLayout(new GridLayout(ROW,COL));
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COL;j++) {
				//JButton btn = new JButton(data[i][j]+"");
				JButton btn = new JButton();
				btn.setOpaque(true);
				btn.setBackground(Color.LIGHT_GRAY);
				
				btn.setMargin(new Insets(0, 0,0,0));
				btn.addActionListener(this);
				btn.addMouseListener(this);
				con.add(btn);
				btns[i][j]=btn;
			}
		}
		frame.add(con,BorderLayout.CENTER);
	}
	
	
	private void setHeader() {
		JPanel panel =new JPanel(new GridBagLayout());
				
		GridBagConstraints c1= new GridBagConstraints(0,0,3,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(bannerbtn,c1);
        bannerbtn.addActionListener(this);
		
        unopen.setOpaque(true);
        unopen.setBackground(Color.white);
        unopen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
        open.setOpaque(true);
        open.setBackground(Color.white);
        open.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		time.setOpaque(true);
		time.setBackground(Color.white);
		time.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		time.setOpaque(true);
		time.setBackground(Color.white);
		time.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		bannerbtn.setOpaque(true);
		bannerbtn.setBackground(Color.white);
		bannerbtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		
		
		GridBagConstraints c2= new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(unopen,c2);
		GridBagConstraints c3= new GridBagConstraints(1,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(open,c3);
		GridBagConstraints c4= new GridBagConstraints(2,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(time,c4);
		frame.add(panel,BorderLayout.NORTH);
	}
	public static void main(String[] args) {
		new Sweeping();
		

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof MenuItem) {
			MenuItem item =(MenuItem)e.getSource();
			if(item==mod_easy) {
				this.BOMBCOUNT=25;
				
			}
			 if(item==mod_normal) {
				this.BOMBCOUNT=35;
				
			}
			 if(item==mod_hard) {
				this.BOMBCOUNT=45;
				
			}
			 
			 restart();
		}
		if(e.getSource() instanceof Timer) {
			second++;
			time.setText("時間: "+second+"s");
			timer.restart();
		}
		JButton btn =(JButton) e.getSource();
		if(btn==bannerbtn) {
			restart();
			return;
		}
		for(int i =0;i<ROW;i++) {
			for(int j=0;j<COL; j++) {
			 if(btn==btns[i][j]) {
				if(data[i][j]==bombcode) {
					lose();
				}else
			    {
					openCell(i,j);
					checkWin();
			    }
				return;
			 }   
			}
		}
		
	
	}

			
		
	
	
	
		
	
	private void checkWin() {
		int count=0;
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COL;j++) {
				if(btns[i][j].isEnabled())
					count++;
			}
		}
			if(count==BOMBCOUNT) {
				timer.stop();
				for(int i=0;i<ROW;i++) {
					for(int j=0;j<COL;j++) {
						if(btns[i][j].isEnabled())
							btns[i][j].setIcon(winFlag);
					}
				}
				bannerbtn.setIcon(win);
				JOptionPane.showMessageDialog(frame, "你贏了!\n 點擊banner重新開始","贏了",JOptionPane.PLAIN_MESSAGE );
			}
		}
	
	
	private void lose() {
		timer.stop();
		bannerbtn.setIcon(lose);
		for(int i =0;i<ROW;i++) {
			for(int j=0;j<COL; j++) {
				if(btns[i][j].isEnabled()) {
					JButton btn = btns[i][j];
					if(data[i][j]==bombcode) {
						btn.setEnabled(false);
						btn.setIcon(bomb);
						btn.setDisabledIcon(bomb);
					}else {
						btn.setEnabled(false);
						btn.setIcon(null);
						btn.setOpaque(true);
						btn.setText(data[i][j]+"");
					}
				}
			    	
			}
		}
		JOptionPane.showMessageDialog(frame, "可惜你踩雷了!\n可以點擊上方Banner重新開始!", "踩雷啦",JOptionPane.PLAIN_MESSAGE);
	}
			
		
	
	
	private void openCell(int i ,int j) {
		JButton btn = btns[i][j];
		if(!btn.isEnabled())
			return;
		btn.setIcon(null);
		btn.setEnabled(false);
		btn.setOpaque(true);
		btn.setBackground(Color.green);
		btn.setText(data[i][j]+"");
		addOpenCount();
		
		if(i>0 && j>0 && data[i-1][j-1]==0)
			openCell(i-1,j-1);
		if(i>0 && data[i-1][j]==0)
			openCell(i-1,j);
		if(i>0 && j<19 && data[i-1][j+1]==0)
			openCell(i-1,j+1);
		if(j>0 && data[i][j-1]==0)
			openCell(i,j-1);
		if(j<19 && data[i][j+1]==0)
			openCell(i,j+1);
		if(i<19 && j>0 && data[i+1][j-1]==0)
			openCell(i+1,j-1);
		if(i<19 && data[i+1][j]==0)
			openCell(i+1,j);
		if(i<19 && j<19 && data[i+1][j+1]==0)
			openCell(i+1,j+1);
		
	}
	private void addOpenCount() {
		opened++;
		unopened--;
		unopen.setText("未開: "+ unopened);
		open.setText("已開: "+ opened);
	}
	private void restart() {
		bannerbtn.setIcon(bannerIcon);
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COL;j++) {
				data[i][j]=0;
				JButton btn =btns[i][j];
				btn.setEnabled(true);
				btn.setIcon(null);
				btn.setOpaque(true);
				btn.setText("");
				btn.setBackground(Color.GRAY);
			}
		}
		opened=0;
		unopened=COL*ROW;
		second=0;
		unopen.setText("未開: "+ unopened);
		open.setText("已開: "+ opened);
		time.setText("時間: "+second+"s");
		addBomb();
		timer.start();
		
	}
	



	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton()==MouseEvent.BUTTON3){
			JButton btn=(JButton)e.getSource();
			if(btn.getIcon()!= mark) {
				((JButton)e.getSource()).setIcon(mark);
					    
			}else {
				((JButton)e.getSource()).setIcon(null);
			}
	     }
	}
		
	
		
	



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
