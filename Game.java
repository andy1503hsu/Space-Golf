import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Game extends JFrame //contains the entire game (menus, levels, etc.)
{
	JFrame frame;
	static Game game;
	Calibration calibration;
	StartMenu startMenu;
	LevelMenu levelMenu;
	HowToPlay howToPlay;
	HighScores highScores;
	Credit credit;
	EnterName enterName;	
	Hole1 hole1; Hole2 hole2; Hole3 hole3; Hole4 hole4; Hole5 hole5; Hole6 hole6; Hole7 hole7; Hole8 hole8; Hole9 hole9; Hole10 hole10; 
	boolean reset = true;
	JPanel cardPanel; //for CardLayout
	CardLayout cardLayout = new CardLayout(); //for CardLayout too
	boolean lock = false;
	int xOffset = 0;
	int yOffset = 0;
	int shotsTaken = 0;
	int vToMove = 28;
	boolean actually = false;
	String username;
	JTextArea name;
	boolean ifClassic = false; //THIS IS TO TELL IF PLAYING CLASSIC OR NOT.
	boolean classicComplete = false;

	public static void main(String[] args) //creates an object for the entire game, runs method "run"
	{
		game = new Game();
		game.run();
	}

	public void run() //frame is "created", objects of inner classes initialized, cardPanel created
	{
		frame = new JFrame("Space Golf");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(650, 650);	
		frame.setLocation(300,80);
		
		calibration = new Calibration();

		startMenu = new StartMenu(); //for the four options

		levelMenu = new LevelMenu(); //for the levels
		howToPlay = new HowToPlay(); //The instructions menu
		highScores = new HighScores(); //The highscore page
		credit = new Credit(); //The credits page
		enterName = new EnterName(); //The page where the player inputs their name
		hole1 = new Hole1(); hole2 = new Hole2(); //for all the holes
		hole3 = new Hole3(); hole4 = new Hole4(); 
		hole5 = new Hole5(); hole6 = new Hole6(); 
		hole7 = new Hole7(); hole8 = new Hole8(); 
		hole9 = new Hole9(); hole10 = new Hole10(); 
		frame.addMouseListener(calibration);
		frame.addMouseMotionListener(hole1);
		frame.addMouseListener(hole1);
		frame.addMouseMotionListener(hole2);
		frame.addMouseListener(hole2);
		frame.addMouseMotionListener(hole3);
		frame.addMouseListener(hole3);
		frame.addMouseMotionListener(hole4);
		frame.addMouseListener(hole4);
		frame.addMouseMotionListener(hole5);
		frame.addMouseListener(hole5);
		frame.addMouseMotionListener(hole6);
		frame.addMouseListener(hole6);
		frame.addMouseMotionListener(hole7);
		frame.addMouseListener(hole7);
		frame.addMouseMotionListener(hole8);
		frame.addMouseListener(hole8);
		frame.addMouseMotionListener(hole9);
		frame.addMouseListener(hole9);
		frame.addMouseMotionListener(hole10);
		frame.addMouseListener(hole10);
		cardPanel = new JPanel(); //this section related all to cardPanels (also see lines 12 and 13)
		cardPanel.setLayout(cardLayout);
		
		cardPanel.add(calibration, "calibration");
		cardPanel.add(startMenu, "startMenu");
		cardPanel.add(levelMenu, "levelMenu");
		cardPanel.add(howToPlay, "howToPlay");
		cardPanel.add(highScores, "highScores");
		cardPanel.add(credit, "credit");
		cardPanel.add(enterName, "enterName");
		cardPanel.add(hole1, "hole1");
		cardPanel.add(hole2, "hole2"); 
		cardPanel.add(hole3, "hole3");
		cardPanel.add(hole4, "hole4"); 
		cardPanel.add(hole5, "hole5"); 
		cardPanel.add(hole6, "hole6"); 
		cardPanel.add(hole7, "hole7"); 
		cardPanel.add(hole8, "hole8"); 
		cardPanel.add(hole9, "hole9"); 
		cardPanel.add(hole10, "hole10"); 
		
		frame.add(cardPanel);
		
		frame.setVisible(true);
	}
	class Calibration extends JPanel implements MouseListener, ActionListener //for calibrating offsets and frame
	{
		boolean clickCorrect = false;
		int widthValue, heightValue;

		JSlider sliderWidth, sliderHeight;

		public Calibration() //adds two buttons and a slider
		{
			clickCorrect = false;
		
			initializePanel();
		}

		public void initializePanel()
		{	
			setBackground(Color.RED);
			removeAll();
			revalidate();
			repaint();
			
			// Sliders and buttons are only added if mouse positioning is confirmed
			if (clickCorrect)
			{
				addSliders();
				addButtons();
			}
		}

		public void addSliders()
		{
			sliderWidth = new JSlider (JSlider.HORIZONTAL, 600, 650, 650);	
			sliderWidth.setMajorTickSpacing(10);	
			sliderWidth.setMinorTickSpacing(50);
			sliderWidth.setForeground(Color.BLUE);
			sliderWidth.setPaintTicks(true);
			sliderWidth.setLabelTable( sliderWidth.createStandardLabels(50) );
			sliderWidth.setPaintLabels(true);
			SliderWidthHandler sWidthHandler = new SliderWidthHandler();
			sliderWidth.addChangeListener( sWidthHandler ); 		
			widthValue = 650;
			
			sliderHeight = new JSlider (JSlider.HORIZONTAL, 600, 650, 650);	
			sliderHeight.setMajorTickSpacing(10);
			sliderHeight.setMinorTickSpacing(50);
			sliderHeight.setForeground(Color.BLUE);
			sliderHeight.setPaintTicks(true);
			sliderHeight.setLabelTable( sliderHeight.createStandardLabels(50) );
			sliderHeight.setPaintLabels(true);
			SliderHeightHandler sHeightHandler = new SliderHeightHandler();
			sliderHeight.addChangeListener( sHeightHandler ); 		
			heightValue = 650;
			
			add(sliderWidth);
			add(sliderHeight);
		}
		
		/** Adds the two calibration buttons with the default FlowLayout */
		public void addButtons()
		{	
			JButton confirm = new JButton("Confirm");
			confirm.addActionListener(this);
			
			JButton reset1 = new JButton("Reset");
			reset1.addActionListener(this);
			
			add(confirm);
			add(reset1);
		}

		/** Prints out instructions for calibrating */
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			Font f = new Font("Serif", Font.BOLD, 20);
			g.setFont(f);

			//If user has already calibrated the offsets, instructions for calibrating the frame dimensions are displayed
			if (clickCorrect) 
			{
				//Prints the actual instructions
				g.setColor(Color.ORANGE);
				g.drawString("After adjusting the frame using the sliders above, click confirm!", 30, 250);
				g.drawString("Or click reset if you messed up.", 170, 280);
				
				//Draws the arrows that point to the sliders
				g.setColor(new Color(200, 100, 255));
				g.drawLine(165, 55, 350, 230); g.drawLine(165, 55, 165, 80); g.drawLine(165, 55, 190, 60); 
				g.drawLine(350, 55, 350, 230); g.drawLine(350, 55, 335, 80); g.drawLine(350, 55, 365, 80); 
				
				//Draws the border that user should calibrate "to"
				g.setColor(Color.CYAN);
				for (int i = 0; i < 12; i++) g.drawLine(600, 15 + 50*i, 600, 40 + 50*i);
				for (int i = 0; i < 12; i++) g.drawLine(15 + 50*i, 600, 40 + 50*i, 600);
				g.drawString("Adjust the frame so that the border \"touches\" these lines", 60, 593);
			}
			//If not, instructions for calibrating the offsets are displayed
			else
			{
				//Prints the actual instructions
				g.setColor(Color.CYAN);
				g.drawString("Hi there! Thanks for being curious enough to open this :)", 80, 200);
				g.drawString("After a few calibrating steps, Space Golf will be up and running!", 50, 230);
				g.drawString("Click on the dot to start!", 350,445);

				//Prints the arrow pointing to the dot
				g.drawLine(590,590,450,450); g.drawLine(590,590,550,590); g.drawLine(590,590,590,550);
				
				//Prints the dot itself
				g.setColor(Color.GREEN);
				g.fillOval(599,599,3,3);
			}
		}

		/** Controls the two JButtons and executes corresponding commands */
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();

			/** If user has already calibrated the mouse offsets and "Confirm" is clicked, the Start Menu will be displayed
			 *  Assumption: Assumes that the user has calibrated the frame dimensions beforehand
			 */
			if (clickCorrect && cmd.equals("Confirm")) 
			{
				/** When testing this program itself, cardLayout is not declared and remains null
				 *  Consequently, this try-catch block distinguishes testing from actual running
				 *  Clicking "Confirm" while testing will display in terminal that the "move" to the next screen is sucessful
				 *  The transition to the Start Menu only happens during the "actual" game
				 */
				try
				{
					cardLayout.next(cardPanel);
				}
				catch (NullPointerException error)
				{
					System.out.println("Would move to next menu in the cardLayout.");
					System.out.println("Testing of transition successful.");
					getOffsets();
				}
			}

			/** Clicking "Reset" sets everything to its runtime default */
			if (cmd.equals("Reset"))
			{
				clickCorrect = false;
				widthValue = 650;
				heightValue = 650;
				sliderHeight.setValue(650);
				sliderWidth.setValue(650);
				initializePanel();
			}
		}
		
		/** Used to calibrate mouse offsets */
		public void mouseClicked (MouseEvent e)
		{
			// If user has already calibrated the offsets, clicking will not change them
			if (!clickCorrect)
			{
				clickCorrect = true;
				int xCoord = e.getX();
				int yCoord = e.getY();
				xOffset = xCoord - 600;
				yOffset = yCoord - 600;
				initializePanel();
			}
		}

		/** Manages the width slider, changes the width value and the frame dimensions accordingly */
		private class SliderWidthHandler implements ChangeListener
		{
			public void stateChanged(ChangeEvent e)
			{
				widthValue = sliderWidth.getValue();
				frame.setSize(widthValue, heightValue);
				repaint();
			}	
		}

		/** Manages the height slider, changes the height value and the frame dimensions accordingly */
		private class SliderHeightHandler implements ChangeListener
		{
			public void stateChanged(ChangeEvent e) //change frame width
			{
				heightValue = sliderHeight.getValue();
				frame.setSize(widthValue, heightValue);
				repaint();
			}	
		}
		
		/** 
		 * Returns the offsets
		 * @return A 1 x 2 array that contains the x and y offsets
		 */
		public int[] getOffsets()
		{
			int [] returning = {xOffset, yOffset};
			System.out.println(returning[0] + " " + returning[1]);
			return returning;
		}

		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) {}
	}

	class StartMenu extends JPanel implements ActionListener //Creates the buttons on the title screen
	{
		JLabel blank1, blank2;
		JButton play, howTo, highScores, credits;
		
		public StartMenu()//Makes the buttons
		{
			setLayout(new GridLayout(6,2, 10, 25));
			for(int i = 0; i < 3; i++)
			{
				JLabel filler = new JLabel("");
				add(filler);
			}
			
			play = new JButton("Start");
			play.addActionListener(this);
			play.setBackground(Color.RED); //Sets the color of the buttons
			add(play);
			
			blank1 = new JLabel("");
			add(blank1);
			
			howTo = new JButton("How to Play");
			howTo.addActionListener(this);
			howTo.setBackground(Color.YELLOW);
			add(howTo);
			
			blank2 = new JLabel("");
			add(blank2);
			
			highScores = new JButton("High Scores");
			highScores.addActionListener(this);
			highScores.setBackground(Color.GREEN);
			add(highScores);
			
			JLabel blank3 = new JLabel("");
			add(blank3);
			
			Color c = new Color(205, 0, 255);
			credits = new JButton("Credits");
			credits.addActionListener(this);
			credits.setBackground(c);
			add(credits);
			
			JLabel blank4 = new JLabel("");
			add(blank4);
			
			JLabel blank5 = new JLabel("");
			add(blank5);
		}
		
		public void paintComponent(Graphics g) //Writes out Space Golf
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto.jpg").getImage();
			g.drawImage(background, 0, 0,615,637, null);
			
			Font f = new Font("Serif", Font.BOLD, 50);
			g.setFont(f);
			g.setColor(Color.RED);
			g.drawString("S", 50, 260); //The different colors make the RAINBOW
			g.drawLine(50,265,78,265);
			Color c = new Color(255,50,0);
			g.setColor(c);
			g.drawString("p", 78, 260);
			g.drawLine(78,265,106,265);
			Color d = new Color(255, 100, 0);
			g.setColor(d);
			g.drawString("a", 106, 260);
			g.drawLine(106,265,134,265);
			g.setColor(Color.ORANGE);
			g.drawString("c", 134, 260);
			g.drawLine(134,265,162,265);
			g.setColor(Color.YELLOW);
			g.drawString("e", 158, 260);
			g.drawLine(162,265,186,265);
			g.setColor(Color.GREEN);
			g.drawString("G", 100, 325);
			g.drawLine(100,330,138,330);
			g.setColor(Color.CYAN);
			g.drawString("o", 138, 325);
			g.drawLine(138,330,166,330);
			Color e = new Color(0, 50, 255);
			g.setColor(e);
			g.drawString("l", 166, 325);
			g.drawLine(166,330,184,330);
			g.setColor(Color.MAGENTA);
			g.drawString("f", 184, 325);
			g.drawLine(184,330,202,330);
			
		}
		
		public void actionPerformed(ActionEvent e) //Brings you to the corresponding pages
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Start")) cardLayout.show(cardPanel, "levelMenu");
			else if (cmd.equals("How to Play")) cardLayout.show(cardPanel, "howToPlay");
			else if (cmd.equals("High Scores")) cardLayout.show(cardPanel, "highScores");
			else if (cmd.equals("Credits")) cardLayout.show(cardPanel, "credit");
		}
	}
	
	class LevelMenu extends JPanel implements ActionListener //The level menu and the buttons
	{
		public LevelMenu() //creates the buttons
		{
			setLayout(new GridLayout(14,7,0,10));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
			
			for(int i = 0; i < 16; i++) //Spacing everything out.
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton classic = new JButton("Classic"); //This button is to start a classic game
			//classic.setPreferredSize(new Dimension (200,50));
			classic.addActionListener(this);		  //with 10 holes.
			classic.setBackground(Color.WHITE); 
			add(classic);
			
			for(int i = 0; i < 12; i++)
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton level1 = new JButton("Hole 1"); //These are the individual holes
			level1.addActionListener(this);
			level1.setBackground(Color.GREEN);
			add(level1);
			
			JLabel blah1 = new JLabel(" "); //These '//' are to tell me which are JLabels
			add(blah1);
			
			JButton level2 = new JButton("Hole 2");
			level2.addActionListener(this);
			level2.setBackground(Color.GREEN);
			add(level2);
			
			for(int i = 0; i < 11; i++) //
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton level3 = new JButton("Hole 3");
			level3.addActionListener(this);
			level3.setBackground(Color.GREEN);
			add(level3);
			
			JLabel blah2 = new JLabel(" "); //I do these individual labels to save space
			add(blah2);
			
			JButton level4 = new JButton("Hole 4");
			level4.addActionListener(this);
			level4.setBackground(Color.GREEN);
			add(level4);
			
			for(int i = 0; i < 11; i++) //
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton level5 = new JButton("Hole 5");
			level5.addActionListener(this);
			level5.setBackground(Color.YELLOW);
			add(level5);
			
			JLabel blah3 = new JLabel(" "); //
			add(blah3);
						
			JButton level6 = new JButton("Hole 6");
			level6.addActionListener(this);
			level6.setBackground(Color.YELLOW);
			add(level6);
			
			for(int i = 0; i < 11; i++) //
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton level7 = new JButton("Hole 7");
			level7.addActionListener(this);
			level7.setBackground(Color.YELLOW);
			add(level7);
			
			JLabel blah4 = new JLabel(" "); //
			add(blah4);
					
			JButton level8 = new JButton("Hole 8");
			level8.addActionListener(this);
			level8.setBackground(Color.YELLOW);
			add(level8);
			
			for(int i = 0; i < 11; i++) //
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton level9 = new JButton("Hole 9");
			level9.addActionListener(this);
			level9.setBackground(Color.RED);
			add(level9);
			
			JLabel blah5 = new JLabel(" "); //
			add(blah5);
			
			JButton level10 = new JButton("Hole 10");
			level10.addActionListener(this);
			level10.setBackground(Color.RED);
			add(level10);
			
			for(int i = 0; i < 9;i++)
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
		}
		
		public void paintComponent(Graphics g) //This is to write out the words on this card.
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto.jpg").getImage();
			g.drawImage(background, 0, 0,615,637, null);

			Font f = new Font("Serif", Font.BOLD, 40);
			g.setFont(f);
			g.setColor(Color.RED);
			g.drawString("L", 195, 70);
			g.drawLine(195, 75, 220,75); //400
			Color c = new Color(255,50,0);
			g.setColor(c);
			g.drawString("e", 225, 70); //28
			g.drawLine(220,75,243,75);
			Color d = new Color(255, 100, 0);
			g.setColor(d);
			g.drawString("v", 243, 70);
			g.drawLine(243,75,264,75);
			Color e = new Color(255,150,0);
			g.setColor(e);
			g.drawString("e",264,70);
			g.drawLine(264,75,283,75);
			g.setColor(Color.ORANGE);
			g.drawString("l",283,70);
			g.drawLine(283,75,301,75);
			g.setColor(Color.YELLOW);
			g.drawString("S", 305, 70);
			g.drawLine(301,75,328,75);
			Color h = new Color(210,255,0);
			g.setColor(h);
			g.drawString("e", 328, 70);
			g.drawLine(328,75,346,75);
			g.setColor(Color.GREEN);
			g.drawString("l", 346, 70);
			g.drawLine(346,75,358,75);
			g.setColor(Color.CYAN);
			g.drawString("e", 358, 70);
			g.drawLine(358,75,377,75);
			g.setColor(Color.BLUE);
			g.drawString("c", 377, 70);
			g.drawLine(377,75,395,75);
			Color i = new Color(200,0,255);
			g.setColor(i);
			g.drawString("t", 395, 70);
			g.drawLine(395,75,410,75);
			
			Font k = new Font("Seriif", Font.BOLD, 30);
			g.setFont(k);
			g.setColor(Color.RED);
			g.drawString("I", 180, 155);
			g.drawLine(180,160,191,160);
			g.setColor(c);
			g.drawString("n",191, 155);
			g.drawLine(191,160,210,160);
			g.setColor(d);
			g.drawString("d",210, 155);
			g.drawLine(210,160,288,160);
			g.setColor(e);
			g.drawString("i",228,155);
			g.drawLine(228,160,236,160);
			g.setColor(Color.ORANGE);
			g.drawString("v", 236,155);
			g.drawLine(236,160,252,160);
			g.setColor(Color.YELLOW);
			g.drawString("i", 252, 155);
			g.drawLine(252,160,260,160);
			g.setColor(h);
			g.drawString("d", 260, 155);
			g.drawLine(260,160,277,160);
			Color j = new Color(150,255,0);
			g.setColor(j);
			g.drawString("u", 277,155);
			g.drawLine(277,160,295,160);
			g.setColor(Color.GREEN);
			g.drawString("a", 295,155);
			g.drawLine(295,160,313,160);
			Color l = new Color(0,255,150);
			g.setColor(l);
			g.drawString("l", 312, 155);
			g.drawLine(313,160,317,160);
			
			g.setColor(Color.CYAN);
			g.drawString("L", 325, 155);
			g.drawLine(325,160,343,160);
			Color m = new Color(0,150,255);
			g.setColor(m);
			g.drawString("e", 343,155);
			g.drawLine(343,160,360,160);
			g.setColor(Color.BLUE);
			g.drawString("v", 360, 155);
			g.drawLine(360,160,377,160);
			Color n = new Color(128,0,255);
			g.setColor(n);
			g.drawString("e", 377, 155);
			g.drawLine(377,160,394,160);
			g.setColor(i);
			g.drawString("l", 394, 155);
			g.drawLine(394,160,402,160);
			g.setColor(Color.MAGENTA);
			g.drawString("s", 402, 155);
			g.drawLine(402,160,420,160);
			
		}
		
		public void actionPerformed(ActionEvent e) //if player clicks any button...
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) cardLayout.show(cardPanel, "startMenu"); //This brings player back to start menu.
			if(cmd.equals("Classic"))
			{
				ifClassic = true;
				cardLayout.show(cardPanel, "enterName"); //Brings player to hole 1.
			}
			if(cmd.equals("Hole 1")) cardLayout.show(cardPanel, "hole1"); //Brings player to corresponding hole.
			if(cmd.equals("Hole 2")) cardLayout.show(cardPanel, "hole2"); 
			if(cmd.equals("Hole 3")) cardLayout.show(cardPanel, "hole3");
			if(cmd.equals("Hole 4")) cardLayout.show(cardPanel, "hole4");
			if(cmd.equals("Hole 5")) cardLayout.show(cardPanel, "hole5");
			if(cmd.equals("Hole 6")) cardLayout.show(cardPanel, "hole6");
			if(cmd.equals("Hole 7")) cardLayout.show(cardPanel, "hole7");
			if(cmd.equals("Hole 8")) cardLayout.show(cardPanel, "hole8");
			if(cmd.equals("Hole 9")) cardLayout.show(cardPanel, "hole9");
			if(cmd.equals("Hole 10")) cardLayout.show(cardPanel, "hole10");
		}
	}
	
	class HowToPlay extends JPanel implements ActionListener //The instructions page
	{
		public HowToPlay() //creates the JTextArea and the back button
		{
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back"); //The back button.
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
			
			JLabel blank = new JLabel(); //Spacing.
			blank.setPreferredSize(new Dimension(590,100));
			add(blank);
		}
		
		public void paintComponent(Graphics g) //This writes out How to Play
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto.jpg").getImage();
			g.drawImage(background, 0, 0,615,637, null);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("How To Play", 170, 100);
			g.drawLine(170,105,413,105);
			
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.setColor(Color.YELLOW);
			g.drawString("Welcome to Space Golf! Here, we'll explain the rules", 45, 140); //These are the instructions
			g.drawString("to the game. The ball is the white circle. To move the", 45, 170);
			g.drawString("ball, start at the center, then drag in what direction", 45, 200);
			g.drawString("you want the ball to move. The further away you drag,", 45, 230);
			g.drawString(" the more initial velocity the ball will have. Be", 45, 260);
			g.drawString("careful, as every planet(the other circles) has", 45, 290);
			g.drawString("gravity! The 'hole' is the rainbow spiral. You have ", 45, 320);
			g.drawString("to get the ball there to proceed to the next level.", 45, 350); 
			g.drawString(" In later levels, you'll have to move from planet to", 45, 380);
			g.drawString(" planet in numerical order before you can proceed to", 45,410);
			g.drawString(" the next level. Your score in classic mode is tracked.", 45, 440);
			g.drawString(" The individual holes are 'fore' practice. The color", 45, 470);
			g.drawString(" of the levels shows the difficulty.", 45, 500);
			g.drawString("Well, good luck! The later levels are", 45, 560);
			g.drawString("pretty unfair.", 45, 580);
		}
		
		public void actionPerformed(ActionEvent e) //Brings the player back to the title screen
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) cardLayout.show(cardPanel, "startMenu");
		}
	}
	
	class HighScores extends JPanel implements ActionListener //Where the highscores will be shown. WORK IN PROGRESS
	{
		String [] usernames = new String[5];
		int [] shots = new int[5];
		int i = 0;
		PrintWriter makesOutput = null;
		Scanner input = null;
		Timer moveTimer;
		public HighScores() //creates the back button
		{
			tryCatchInput();
			while (input.hasNext())
			{
				i++; //1,2
				if (i % 2 == 1) usernames[i/2] = input.next();
				if (i % 2 == 0) shots[i/2 -1] = Integer.parseInt(input.next());
			}
			//for (int j = 0; j < 5; j++)System.out.println(usernames[j]);
			//for (int j = 0; j < 5; j++)System.out.println(shots[j]);
			i = 0;
			repaint();
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
			
		}
		
		class BallMover implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				tryCatchInput();
				while (input.hasNext())
				{
					i++;
					if (i % 2 == 1) usernames[i/2] = input.next();
					if (i % 2 == 0) shots[i/2 -1] = Integer.parseInt(input.next());
				}
				i = 0;
				if (shotsTaken > shots[4]) {}
				else
				{
					tryCatchOutput();
					String file = "";
					for (int lines = 0; lines < 5; lines++)
					{
						if (shotsTaken < shots[lines]) //if user has better score than shots[lines]
						{
							for (int j = 4; j > lines; j--)
							{
								usernames[j] = usernames[j-1];
								shots[j] = shots[j-1];
							}
							usernames[lines] = username;
							shots[lines] = shotsTaken;
							actually = true;
							lines = 6;
						}
					}
					//for (int j = 0; j < 5; j++)System.out.println(usernames[j] + "    " + shots[j]);
					for (int lines = 0; lines < 5; lines++)
					{
						file = file + usernames[lines] + "      ";
						file = file + shots[lines] + "\n";
					}
					makesOutput.print(file);
					makesOutput.close();
					repaint();
				}
				moveTimer.stop();
			}
		}
		public void paintComponent (Graphics g) //just has the background right now
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto.jpg").getImage();
			g.drawImage(background, 0, 0,615,637, null);
			g.setColor(new Color(255,100,0));
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("High Scores", 200, 120);
			g.drawLine(200,125,400,125);
			
			g.setColor(Color.YELLOW);
			if (classicComplete)
			{
				tryCatchInput();
				for (int i = 1; i <= 10; i++)
				{
					if (i % 2 == 1) usernames[i/2] = input.next();
					if (i % 2 == 0) shots[i/2 -1] = Integer.parseInt(input.next());
				}
			}

			for (int i = 0; i < 5; i++)
			{
				if (i == 0) g.setColor(Color.YELLOW);
				if (i == 1) g.setColor(Color.GREEN);
				if (i == 2) g.setColor(Color.CYAN);
				if (i == 3) g.setColor(new Color(0, 50, 255));
				if (i == 4) g.setColor(Color.MAGENTA);
				g.drawString(usernames[i], 175, 200 + 50*i);
				g.drawString("" + shots[i], 425, 200 + 50*i);
			}
			if (classicComplete)
			{
				boolean leaderboard = false;
				g.setFont(new Font("Serif", Font.BOLD, 25));
				for (int i = 0; i < 5; i++)
				{
					String check = usernames[i];
					if (username.equals(usernames[i]) && actually) 
					{
						g.drawRect(153, 160 + 50*i, 320, 50);
						g.setColor(Color.ORANGE);
						g.drawString("Congratulations!!", 220, 500);
						g.drawString("You made it to the leaderboard!!",140, 530);
						leaderboard = true;
					}
				}
				if (!leaderboard && username.length() != 0) 
				{
					g.setColor(Color.ORANGE);
					g.drawString("Your score: " + shotsTaken, 230, 500);
					g.drawString("Looks like you didn't score 'high' enough :( ", 90, 530);
					g.drawString("Better luck next time!!", 200, 560);
				}
			}
			
		}
		public void tryCatchInput()
		{
			try
			{
				input = new Scanner(new File("HighScores.txt"));
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Cannot find file");
				System.exit(1);
			}
		}
		public void tryCatchOutput()
		{
			try 
			{    
				makesOutput = new PrintWriter(new File("HighScores.txt")); 
			}
			catch (FileNotFoundException e) 
			{ 
				System.err.println("Cannot create file to be written to.");   
				System.exit(1);
			}
		}
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				shotsTaken = 0;
				classicComplete = false;
				username = "";
				cardLayout.show(cardPanel, "startMenu");
				actually = false;
			}
		}
	}
	
	class Credit extends JPanel implements ActionListener //show the credit menu.
	{
		public Credit() //creates the JTextArea and back button
		{
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
			
			JLabel blank = new JLabel();
			blank.setPreferredSize(new Dimension(590,100));
			add(blank);
		}
		
		public void paintComponent(Graphics g) //prints out how to play
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto.jpg").getImage();
			g.drawImage(background, 0, 0,615,637, null);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Credits", 220, 100);
			g.drawLine(220,105,363,105);
			
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("This game was based off a flash game found online game",35,140);
			g.drawString("called Gravitee. It was made by Eric Liu and Andy Hsu",35,170);
			g.drawString("with the aid of Mr. Kim. Special mention given to Mr.",35,200);
			g.drawString("Lordan for teaching us physics so that we could solve",35,230);
			g.drawString("this on our own, albeit with a lot of trial and error.",35,260);
			g.drawString("We'd also like to thank Newton and Kepler for their ",35,290);
			g.drawString("groundbreaking discoveries in the world of physics",35,320);
			g.drawString("and astronomy.", 35, 350);
			g.drawString("Finally, we'd like to berate Java for not allowing",35,510);
			g.drawString("physics to work well in its system. Also, Mr. Kim,",35,530);
			g.drawString("why do you like the slingshot effect so much?", 35,550);
		}
		
		public void actionPerformed(ActionEvent e)//Brings player back to the start menu.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) cardLayout.show(cardPanel, "startMenu");
		}
	}
	
	public String calcARG(int num, double[][]constants, double[][]changing, Ellipse2D.Double oval, double xMove, double yMove) //calculates radius to each planet as well as gravity
	{
		changing[num][0]= calcAngle(num, constants, changing, oval);
		changing[num][0] = changing[num][0] * 3.14159/180;
		changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
		changing[num][1] = changing[num][1]/15;
		
		changing[num][2] = (3*constants[num][3])/(Math.pow(changing[num][1],2)); 

		xMove = xMove + changing[num][2]*Math.cos(changing[num][0]);
		yMove = yMove + -1*changing[num][2]*Math.sin(changing[num][0]);


		String sendBack = "" + xMove + "A" + yMove;
		return(sendBack);
	}
	
	
	public double calcAngle(int num, double [][]constants, double [][]changing, Ellipse2D.Double oval) //calculates the angle from the golf ball to a planet
	{
		if (oval.getCenterX() > constants[num][4] && oval.getCenterY() < constants[num][5]) 
		{
			changing[num][0] = 180/3.14159 *Math.atan( (constants[num][5]-oval.getCenterY())/(oval.getCenterX()-constants[num][4]) );
			changing[num][0] = -180 + changing[num][0];
		}
		else if (oval.getCenterX() > constants[num][4] && oval.getCenterY() > constants[num][5])
		{
			changing[num][0] = 180/3.14159 *Math.atan( (oval.getCenterY()-constants[num][5])/(oval.getCenterX()-constants[num][4]) );
			changing[num][0] = 180 - changing[num][0];
		}
		else if (oval.getCenterX() < constants[num][4] && oval.getCenterY() > constants[num][5])
		{
			changing[num][0] = 180/3.14159 *Math.atan( (oval.getCenterY()-constants[num][5])/(constants[num][4]-oval.getCenterX()) );
		}
		else 
		{
			changing[num][0] = 180/3.14159 *Math.atan( (constants[num][5] - oval.getCenterY())/(constants[num][4]-oval.getCenterX()) );
			changing[num][0] = changing[num][0] *-1;
		}
		return(changing[num][0]);
	}

	public int[] outOfBounds(Ellipse2D.Double oval) //if the ball goes off screen, draw a line pointing to where it is and print the distance away
	{
		int[] sendBack = new int[7];
		if (oval.getCenterY() < 0 && (oval.getCenterX() > 0 && oval.getCenterX() < 600)) //top
		{
			sendBack[0] = (int)oval.getCenterX();  sendBack[1] = 50; sendBack[2] = (int)oval.getCenterX(); sendBack[3] = 0;
			sendBack[4] = (int)(-1*oval.getCenterY()); sendBack[5] = ((int)oval.getCenterX() - 10); sendBack[6] = 60;
		}
		if (oval.getCenterY() < 0 && oval.getCenterX() > 600) //top right
		{
			sendBack[0] = 600; sendBack[1] = 0; sendBack[2] = 550; sendBack[3] = 50;
			sendBack[4] = (int)(Math.pow(Math.pow(oval.getCenterY(), 2) + Math.pow(oval.getCenterX() - 600, 2),.5)); sendBack[5] = 540; sendBack[6] = 65;
		}
		if (oval.getCenterX() > 600 && (oval.getCenterY() > 0 && oval.getCenterY() < 600)) //right
		{
			sendBack[0] = 550; sendBack[1] = (int)oval.getCenterY(); sendBack[2] = 600; sendBack[3] = (int)oval.getCenterY();
			sendBack[4] = (int)(oval.getCenterX() - 600); sendBack[5] =  530; sendBack[6] =  (int)(oval.getCenterY() + 5);
		}
		if (oval.getCenterX() > 600 && oval.getCenterY() > 600) //bottom right
		{
			sendBack[0] = 600; sendBack[1] = 600; sendBack[2] = 550; sendBack[3] = 550;
			sendBack[4] = (int)(Math.pow(Math.pow(oval.getCenterY()-600, 2) + Math.pow(oval.getCenterX() - 600, 2),.5)); sendBack[5] = 540; sendBack[6] = 545;
		}
		if (oval.getCenterY() > 600 && (oval.getCenterX() > 0 && oval.getCenterX() < 600)) //bottom
		{
			sendBack[0] = (int)oval.getCenterX();  sendBack[1] = 550; sendBack[2] = (int)oval.getCenterX(); sendBack[3] = 600;
			sendBack[4] = (int)(oval.getCenterY()- 600); sendBack[5] = ((int)oval.getCenterX() - 10); sendBack[6] = 540;
		}
		if (oval.getCenterY() > 600 && oval.getCenterX() < 0) //bottom left
		{
			sendBack[0] = 0; sendBack[1] = 600; sendBack[2] = 50; sendBack[3] = 550;
			sendBack[4] = (int)(Math.pow(Math.pow(oval.getCenterY()-600, 2) + Math.pow(oval.getCenterX(), 2),.5)); sendBack[5] = 40; sendBack[6] = 540;
		}
		if (oval.getCenterX() < 0 && (oval.getCenterY() > 0 && oval.getCenterY() < 600)) //left
		{
			sendBack[0] = 0; sendBack[1] = (int)oval.getCenterY(); sendBack[2] = 50; sendBack[3] = (int)oval.getCenterY();
			sendBack[4] = (int)(-1*oval.getCenterX()); sendBack[5] =  60; sendBack[6] =  (int)(oval.getCenterY() + 5);
		}
		else if (oval.getCenterX() < 0 && oval.getCenterY() < 0)//top left
		{
			sendBack[0] = 0; sendBack[1] = 0; sendBack[2] = 50; sendBack[3] = 50;
			sendBack[4] = (int)(Math.pow(Math.pow(oval.getCenterY(), 2) + Math.pow(oval.getCenterX(), 2),.5)); sendBack[5] = 60; sendBack[6] = 55;
		}
		if (sendBack[4] > 750) sendBack[4] = -1;
		return(sendBack);
	}
	
	class EnterName extends JPanel //The panel where the user enters their name.
	{
		public EnterName()
		{
			setLayout(new BorderLayout());
			
			TheBack north = new TheBack();
			TheName center = new TheName();
			
			add(north, BorderLayout.NORTH);
			add(center, BorderLayout.CENTER);
		}
	}
	
	class TheBack extends JPanel implements ActionListener //Creates the back button portion of the panel
	{
		public TheBack()
		{
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back"); //The back button
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto.jpg").getImage(); //The background
			g.drawImage(background, 0, 0,615,637, null);
		}
		
		public void actionPerformed(ActionEvent e) //Returns to level select
		{
			String cmd = e.getActionCommand();
			if(cmd == "Back")
			{
				cardLayout.show(cardPanel, "levelMenu");
				name.setText("");
			}
		}
	}
	
	class TheName extends JPanel implements ActionListener //
	{
		boolean idiot= false;
		String bored = "!";
		public TheName()
		{
			setLayout(new GridLayout(13,5,0,10));
			Font x = new Font("Serif", Font.BOLD, 20);
			
			for(int i = 0; i <=31; i++)
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			name = new JTextArea("", 1,1);
			name.setEditable(true);
			name.setFont(x);
			name.setTabSize(10);
			
			add(name);
			
			for(int i = 0; i <= 3; i++)
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
			
			JButton enter = new JButton("Enter");
			enter.addActionListener(this);
			enter.setBackground(Color.WHITE);
			add(enter);
			
			for(int i = 0; i <= 24; i++)
			{
				JLabel blah = new JLabel(" ");
				add(blah);
			}
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto.jpg").getImage();
			g.drawImage(background, 0, 0,615,637, null);
			
			g.setColor(Color.YELLOW);
			Font p = new Font("Serif", Font.BOLD, 30);
			g.setFont(p);
			g.drawString("Please insert your name", 130,190);
			g.drawString("(1-10 charcters)", 175, 220); 
			g.drawString("Can't be all spaces!!", 140,250);
			if (idiot)
			{ 
				bored = bored + "!";
				g.drawString("FOLLOW THE INSTRUCTIONS" + bored, (int)(55-bored.length()*4.5), 150);
				idiot = false;
			}
		}
		
		public void actionPerformed(ActionEvent e)
		{
			username = name.getText();
			String cmd = e.getActionCommand();
			idiot = true;
			for (int i = 0; i < username.length(); i++)
			{
				if (!idiot) {}
				else if (username.charAt(i) == ' ') idiot = true;
				else idiot = false;
			}
			if(cmd == "Enter" && username.length() > 0 && username.length() < 11 && !idiot)
			{
				idiot = false;
				bored = "!";
				cardLayout.show(cardPanel, "hole1");
				name.setText("");
			}
			else 
			{
				idiot = true;
				repaint();
			}
		}
	}
	class Hole1 extends JPanel implements MouseListener, MouseMotionListener, ActionListener //The first level
	{
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 116; double xLocBack = 116;
		double yLoc = 209; double yLocBack = 209;
		boolean printOut = false;
		int numPlanets = 2;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		boolean holeComplete = false;
		int [] numbers = new int[7];

		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];
		public Hole1() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 100;
			constants[0][1] = 225;
			constants[0][2] = 25;
			constants[0][3] = 1.1261; //mass = 0, gravity is nonexistent
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
		
			constants[1][0] = 400; //xcoord
			constants[1][1] = 200; //ycoord
			constants[1][2] = 50;  //radius
			constants[1][3] = 4.5045; //mass
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int j = 0; j < numPlanets; j++) 
				{
					String xMoveyMove = game.calcARG(j,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (oval.contains(440,188) || oval.contains(465,188) || oval.contains(453,175) || oval.contains(453,200) || oval.contains(443.7, 196.3) || oval.contains(461.3, 178.7) || oval.contains(443.7, 178.7) || oval.contains(461.3, 196.3))
			{		
					moveTimer.stop();
					holeComplete = true;
			}
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
			}
			
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if (oval.contains(440,188) || oval.contains(465,188) || oval.contains(453,175) || oval.contains(453,200) || oval.contains(443.7, 196.3) || oval.contains(461.3, 178.7) || oval.contains(443.7, 178.7) || oval.contains(461.3, 196.3))
			{		
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto2.png").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			
			if(reset)
			{
				xLoc = 116; xLocBack = 116;
				yLoc = 209; yLocBack = 209;
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				reset = false;
				shotsTaken = 0;
				printOut = false;
				back = true;
				holeComplete = false;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			g.setColor(Color.ORANGE); //drawing the 2 planets and hoop
			g.fillOval(100,225,50,50);
			g.setColor(Color.GREEN);
			g.fillOval(400,200,100,100);
			g.fillOval(599,599,2,2);
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f = new Font("Serif", Font.BOLD, 20);
			g.setFont(f);

			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			g.setColor(Color.BLUE);
			
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}
			
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 1 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 1 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			g.setColor(Color.RED);
			g.fillOval(440,175,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(441,176,23,23);
			g.setColor(Color.YELLOW);
			g.fillOval(443,178,20,20);
			g.setColor(Color.green);
			g.fillOval(446,180,17,17);
			g.setColor(Color.BLUE);
			g.fillOval(449,183,14,14);
			g.setColor(Color.magenta);
			g.fillOval(452,186,11,11);
			g.setColor(Color.BLACK);
			g.fillOval(455,189,6,6);
			
			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
			if (shotsTaken == 0)
			{
				g.setColor(Color.YELLOW);
				g.drawString("Golf Ball (click, drag mouse, release)", 30, 120);
				g.drawString("The 'hole'", 410, 100);
				g.drawString("Planet over here", 70, 350);
				g.drawString("Bigger planet over here (more gravity!!)", 200, 400);
				//g.drawString("Have fun! Beware of level 10 :) ", 180, 500);
				g.drawString("Tip: For maximum distance, drag radially *away* from", 70, 500);
				g.drawString("the planet the golf ball is resting on.", 140, 525);
				g.setColor(Color.RED);
				g.drawLine(124,130, 124, 215); //golf ball
				g.drawLine(125, 260, 125,335); //orange planet
				g.drawLine(450, 250, 340, 386); //green planet
				g.drawLine(450,105, 450, 185); //the hole
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole2");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) 
		{		
		}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				ifClassic = false;
				holeComplete = false;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
				cardLayout.show(cardPanel, "levelMenu");
			}
		}
	}
		
	class Hole2 extends JPanel implements ActionListener, MouseListener, MouseMotionListener //hole 2
	{
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 85; double xLocBack = 85;
		double yLoc = 240; double yLocBack = 240;
		int numPlanets = 2;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		boolean back = false;
		boolean holeComplete = false;
		int [] numbers = new int[7];
		boolean printOut = false;
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		
		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole2() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));

			constants[0][0] = 100;
			constants[0][1] = 200;
			constants[0][2] = 50;
			constants[0][3] = 4.5045; //mass = 0, gravity is nonexistent
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
		
			constants[1][0] = 400; //xcoord
			constants[1][1] = 200; //ycoord
			constants[1][2] = 40;  //radius
			constants[1][3] = 2.8829; //mass
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse and has set a trajectory for the ball
		{
			public void actionPerformed(ActionEvent e) 
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				
				hitPlanet(0);

				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure the golf ball stays at the edge of the planet
		{
			if (oval.contains(483,200) || oval.contains(483,225) || oval.contains(475,213) || oval.contains(500,213) || oval.contains(473.7,203.7) || oval.contains(491.3,221.3) || oval.contains(473.7, 221.3) || oval.contains(491.3, 203.7))
			{
				moveTimer.stop();
				holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
			
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if (oval.contains(483,200) || oval.contains(483,225) || oval.contains(475,213) || oval.contains(500,213) || oval.contains(473.7,203.7) || oval.contains(491.3,221.3) || oval.contains(473.7, 221.3) || oval.contains(491.3, 203.7))
			{
				moveTimer.stop();
				holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
		public void paintComponent(Graphics g) //paints 2 planets, wormhole, and ball
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto2.png").getImage();
			if(reset)
			{
				xLoc = 85; xLocBack = 85;
				yLoc = 240; yLocBack = 240;
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				reset = false;
				if (!ifClassic) shotsTaken = 0;
				printOut = false;
				holeComplete = false;
				back = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			g.drawImage(background, 0, 0,600,600, null);
			g.setColor(Color.GREEN);
			g.fillOval(100,200,100,100);
			g.setColor(Color.PINK);
			g.fillOval(400,200,80,80);
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f = new Font("Serif", Font.BOLD, 20);
			g.setFont(f);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 2 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 2 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			g.setColor(Color.RED);
			g.fillOval(470,200,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(471,201,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(473,203,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(476,205,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(479,208,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(482,211,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(485,214,6,6); //+3, +3
			g.setColor(Color.BLUE);

			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				holeComplete = false;
				ifClassic = false;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole3");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}

	//hole 3
	class Hole3 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 174; double xLocBack = 174;
		double yLoc = 230; double yLocBack = 230;
		int numPlanets = 3;
		boolean holeComplete = false;
		boolean next =false;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		String hitPlanet = "1";
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		int [] numbers = new int[7];
		boolean printOut = false;

		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole3() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));

			constants[0][0] = 100;
			constants[0][1] = 200;
			constants[0][2] = 38;
			constants[0][3] = 2.6018; //mass = 0, gravity is nonexistent
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
		
			constants[1][0] = 360; //xcoord
			constants[1][1] = 60; //ycoord
			constants[1][2] = 50;  //radius
			constants[1][3] = 4.5045; //mass
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			constants[2][0] = 400; //xcoord
			constants[2][1] = 300; //ycoord
			constants[2][2] = 39;  //radius
			constants[2][3] = 2.7405; //mass
			constants[2][4] = constants[2][0] + constants[2][2];
			constants[2][5] = constants[2][1] + constants[2][2];
			
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse and has set a trajectory for the ball
		{
			public void actionPerformed(ActionEvent e) 
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				
				hitPlanet(0);

				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure the golf ball stays at the edge of the planet
		{
			if ((oval.contains(483,300) || oval.contains(483,325) || oval.contains(470,313) || oval.contains(495,313) || oval.contains(473.7, 321.3) || oval.contains(491.3, 303.7) || oval.contains(473.7, 303.7) ||oval.contains(491.3, 321.3))&& next)
			{
				moveTimer.stop();
				holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
			
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
				
				if (hitPlanet.indexOf("" + (num+1)) == -1) hitPlanet = hitPlanet + " " + (num+1);
				if (hitPlanet.indexOf("2") != -1) next = true;
			}
			if ((oval.contains(483,300) || oval.contains(483,325) || oval.contains(470,313) || oval.contains(495,313) || oval.contains(473.7, 321.3) || oval.contains(491.3, 303.7) || oval.contains(473.7, 303.7) || oval.contains(491.3, 321.3))&& next)
			{
				moveTimer.stop();
				holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
		
		public void paintComponent(Graphics g) //draws three planets, wormhole, ball, and numbers "1" "2" and "3"
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto2.png").getImage();
			g.drawImage(background, 0, 0,600,600, null);
			
			if(reset)
			{
				xLoc = 174; xLocBack = 174;
				yLoc = 230; yLocBack = 230;
				reset = false;
				hitPlanet = "1";
				if (!ifClassic) shotsTaken = 0;
				holeComplete = false;
				next = false;
				printOut = false;
				back = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			g.setColor(Color.PINK);
			g.fillOval(100,200,75,75);
			
			g.setColor(Color.YELLOW);
			g.fillOval(400,300,78,78);
			g.setColor(Color.BLUE);
			g.fillOval(360,60,100,100);
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f = new Font("Serif", Font.BOLD, 30);
			g.setFont(f);
			g.setColor(Color.GREEN);
			g.drawString("1",130,247);
			if (hitPlanet.indexOf("2") == -1) g.setColor(Color.BLACK);
			g.drawString("2",400,120);
			
			
			Font f1 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f1);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}
			
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 3 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 3 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			g.setColor(Color.RED);
			g.fillOval(470,300,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(471,301,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(473,303,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(476,305,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(479,308,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(482,311,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(485,314,6,6); //+3, +3
			
			
			g.setColor(Color.BLUE);
			Font f2 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f2);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				holeComplete = false;
				ifClassic = false;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
				cardLayout.show(cardPanel, "levelMenu");
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole4");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}
	
	class Hole4 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 113; double xLocBack = 113;
		double yLoc = 335; double yLocBack = 335;
		int numPlanets = 3;
		boolean next =false;
		boolean holeComplete = false;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		String hitPlanet = "1";
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		int [] numbers = new int[7];
		boolean printOut = false;

		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole4() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));

			constants[0][0] = 35;
			constants[0][1] = 295;
			constants[0][2] = 39;
			constants[0][3] = 2.7405; //mass = 0, gravity is nonexistent
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
		
			constants[1][0] = 365; //xcoord
			constants[1][1] = 85; //ycoord
			constants[1][2] = 36;  //radius
			constants[1][3] = 2.3351; //mass
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			constants[2][0] = 400; //xcoord
			constants[2][1] = 225; //ycoord
			constants[2][2] = 60;  //radius
			constants[2][3] = 6.4865; //mass
			constants[2][4] = constants[2][0] + constants[2][2];
			constants[2][5] = constants[2][1] + constants[2][2];
			
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse and has set a trajectory for the ball
		{
			public void actionPerformed(ActionEvent e) 
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				
				hitPlanet(0);

				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure the golf ball stays at the edge of the planet
		{
			if ((oval.contains(563,225) || oval.contains(563,250) || oval.contains(550,238) || oval.contains(575,238) || oval.contains(571.3, 246.3)|| oval.contains(553.7, 228.9) || oval.contains(571.3, 228.9) || oval.contains(553.7, 246.3))&& next)
			{
				moveTimer.stop();
				holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
			
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
				if (num == 0);
				else if (hitPlanet.indexOf("" + (num+1)) == -1 && hitPlanet.indexOf("" + (num)) != -1) hitPlanet = hitPlanet + " " + (num+1);
				if (hitPlanet.indexOf("2") != -1 && hitPlanet.indexOf("3") != -1) next = true;
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if ((oval.contains(563,225) || oval.contains(563,250) || oval.contains(550,238) || oval.contains(575,238) || oval.contains(571.3, 246.3)|| oval.contains(553.7, 228.9) || oval.contains(571.3, 228.9) || oval.contains(553.7, 246.3))&& next)
			{
				moveTimer.stop();
				holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
		
		public void paintComponent(Graphics g) //draws three planets, wormhole, ball, and numbers "1" "2" and "3"
		{
			super.paintComponent(g);
			Image background = new ImageIcon("SpacePhoto2.png").getImage();
			g.drawImage(background, 0, 0,600,600, null);
			if(reset)
			{
				xLoc = 113; xLocBack = 113;
				yLoc = 335; yLocBack = 335;
				reset = false;
				hitPlanet = "1";
				if (!ifClassic) shotsTaken = 0;
				next = false;
				back = true;
				printOut = false;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			g.setColor(Color.YELLOW);
			g.fillOval(35,295,78,78);
			g.setColor(Color.CYAN);
			g.fillOval(365,85,72,72);
			g.setColor(Color.MAGENTA);
			g.fillOval(400,225,120,120);
			
				
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f1 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f1);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 4 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 4 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			Font f = new Font("Serif", Font.BOLD, 30);
			g.setFont(f);
			g.setColor(Color.GREEN);
			g.drawString("1",67,343);
			if (hitPlanet.indexOf("2") == -1) g.setColor(Color.BLACK);
			g.drawString("2",392,130);
			if (hitPlanet.indexOf("2") == -1 || hitPlanet.indexOf("3") == -1) g.setColor(Color.BLACK);
			g.drawString("3",453,295);

			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}
			
			g.setColor(Color.RED);
			g.fillOval(550,225,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(551,226,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(553,228,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(556,230,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(559,233,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(560,236,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(563,239,6,6); //+3, +3
		
			g.setColor(Color.BLUE);
			Font f2 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f2);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				holeComplete = false;
				ifClassic = false;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole5");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}

	class Hole5 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 440; double xLocBack = 440;
		double yLoc = 495; double yLocBack = 495;
		int numPlanets = 2;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		boolean holeComplete = false;
		int [] numbers = new int[7];
		boolean printOut = false;
		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole5() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 380;
			constants[0][1] = 375;
			constants[0][2] = 60;
			constants[0][3] = 6.4865; //mass = 0, gravity is nonexistent
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
		
			constants[1][0] = 100; //xcoord
			constants[1][1] = 125; //ycoord
			constants[1][2] = 25;  //radius
			constants[1][3] = 1.1261; //mass
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			if (oval.contains(165,140) || oval.contains(165,165) || oval.contains(152,153) || oval.contains(177,153) || oval.contains(173.3, 161.3) || oval.contains(155.7, 143.7) || oval.contains(173.3, 143.7)|| oval.contains(155.7, 161.3))
			{
					moveTimer.stop();
					holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if (oval.contains(165,140) || oval.contains(165,165) || oval.contains(152,153) || oval.contains(177,153) || oval.contains(173.3, 161.3) || oval.contains(155.7, 143.7) || oval.contains(173.3, 143.7)|| oval.contains(155.7, 161.3))
			{
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto3.jpeg").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			g.setColor(Color.PINK);
			g.fillOval(380,375,120,120);
			g.setColor(Color.ORANGE);
			g.fillOval(100,125,50,50);
			if(reset)
			{
				xLocBack = 440;
				yLocBack = 495;
				reset = false;
				if (!ifClassic) shotsTaken = 0;
				printOut = false;
				holeComplete = false;
				back = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f3 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f3);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 5 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 5 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			g.setColor(Color.RED);
			g.fillOval(152,140,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(153,141,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(155,143,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(158,145,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(161,148,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(164,151,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(167,154,6,6); //+3, +3
			g.setColor(Color.BLUE);
			Font f = new Font("Serif", Font.BOLD, 20);
			g.setFont(f);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				holeComplete = false;
				ifClassic = false;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole6");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}
	class Hole6 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 290; double xLocBack = 290; 
		double yLoc = 120; double yLocBack = 120;
		int numPlanets = 3;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		boolean holeComplete = false;
		int [] numbers = new int[7];
		boolean printOut = false;
		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole6() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 225;
			constants[0][1] = 240;
			constants[0][2] = 75;
			constants[0][3] = 8.4; //mass = 0, gravity is nonexistent
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
		
			constants[1][0] = 265; //xcoord
			constants[1][1] = 50; //ycoord
			constants[1][2] = 35;  //radius
			constants[1][3] = 2.2072; //mass
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			constants[2][0] = 273; //xcoord
			constants[2][1] = 510; //ycoord
			constants[2][2] = 25;  //radius
			constants[2][3] = 1.1261; //mass
			constants[2][4] = constants[2][0] + constants[2][2];
			constants[2][5] = constants[2][1] + constants[2][2];
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			if (oval.contains(298,428) || oval.contains(298,453) || oval.contains(285,441) || oval.contains(310,441)|| oval.contains(306.3, 449.3) || oval.contains(288.7, 431.7) || oval.contains(306.3, 431.7)|| oval.contains(288.7, 449.3))
			{
					moveTimer.stop();
					holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if (oval.contains(298,428) || oval.contains(298,453) || oval.contains(285,441) || oval.contains(310,441)|| oval.contains(306.3, 449.3) || oval.contains(288.7, 431.7) || oval.contains(306.3, 431.7)|| oval.contains(288.7, 449.3))
			{
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto3.jpeg").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			g.setColor(Color.MAGENTA);
			g.fillOval(225,240,150,150);
			g.setColor(Color.ORANGE);
			g.fillOval(265,50,70,70);
			g.setColor(Color.GREEN);
			g.fillOval(273,510,50,50);
			if (reset)
			{
				reset = false;
				xLocBack = 290;
				yLocBack = 120;
				if (!ifClassic) shotsTaken = 0;
				printOut = false;
				holeComplete = false;
				back = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f3 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f3);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 6 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 6 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			g.setColor(Color.RED);
			g.fillOval(285,428,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(286,429,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(288,431,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(291,434,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(294,437,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(297,440,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(300,443,6,6); //+3, +3
			g.setColor(Color.BLUE);
			Font f = new Font("Serif", Font.BOLD, 20);
			g.setFont(f);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				holeComplete = false;
				ifClassic = false;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole7");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}
	
	class Hole7 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{	
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 275; double xLocBack = 275;
		double yLoc = 434; double yLocBack = 434;
		String hitPlanet = "1";
		int numPlanets = 3;
		boolean next = false;
		boolean holeComplete = false;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		int [] numbers = new int[7];
		boolean printOut = false;

		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole7() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 233;
			constants[0][1] = 450;
			constants[0][2] = 50;
			constants[0][3] = 4.5045; //CHANGE
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
			
			constants[1][0] = 360; //xcoord
			constants[1][1] = 120; //ycoord
			constants[1][2] = 40;  //radius
			constants[1][3] = 2.8829;
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			constants[2][0] = 75; //xcoord
			constants[2][1] = 100; //ycoord
			constants[2][2] = 35;  //radius
			constants[2][3] = 2.2072; //DONT CHANGE
			constants[2][4] = constants[2][0] + constants[2][2];
			constants[2][5] = constants[2][1] + constants[2][2];
			

			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			if ((oval.contains(288,240) || oval.contains(288,265) || oval.contains(275,253) || oval.contains(300,253)|| oval.contains(296.3, 261.3) || oval.contains(278.6,243.7)|| oval.contains(278.6,261.3)|| oval.contains(296.3,243.7)) && next)
			{
					moveTimer.stop();
					holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
				if (num == 0);
				else if (hitPlanet.indexOf("" + (num+1)) == -1 && hitPlanet.indexOf("" + (num)) != -1) hitPlanet = hitPlanet + " " + (num+1);
				if (hitPlanet.indexOf("2") != -1 && hitPlanet.indexOf("3") != -1) next = true;
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if ((oval.contains(288,240) || oval.contains(288,265) || oval.contains(275,253) || oval.contains(300,253)|| oval.contains(296.3, 261.3) || oval.contains(278.6,243.7)|| oval.contains(278.6,261.3)|| oval.contains(296.3,243.7)) && next)
			{
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto3.jpeg").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			if (reset)
			{
				reset = false;
				xLocBack = 275;
				yLocBack = 434;
				if (!ifClassic) shotsTaken = 0;
				hitPlanet = "1";
				holeComplete = false;
				next = false;
				printOut = false;
				back = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f3 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f3);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 7 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 7 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}

			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			g.setColor(Color.YELLOW);
			g.fillOval(75,100,70,70); //2
			g.setColor(Color.CYAN);
			g.fillOval(233,450,100,100); //1
			g.setColor(Color.PINK);
			g.fillOval(360,120,80,80); //3
			
			g.setColor(Color.RED);
			g.fillOval(275,240,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(276,241,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(278,243,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(281,245,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(284,248,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(287,251,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(290,254,6,6); //+3, +3
			
			Font f = new Font("Serif", Font.BOLD, 30);
			g.setFont(f);
			g.setColor(Color.GREEN);
			g.drawString("1",275,507);
			if (hitPlanet.indexOf("2") == -1) g.setColor(Color.BLACK);
			g.drawString("2",392,165);
			if (hitPlanet.indexOf("2") == -1 || hitPlanet.indexOf("3") == -1) g.setColor(Color.BLACK);
			g.drawString("3",103,143);
			
			g.setColor(Color.BLUE);
			Font f1 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f1);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				hitPlanet = "1";
				holeComplete = false;
				ifClassic = false;	
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole8");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}
	
	class Hole8 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{	
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 130; double xLocBack = 130;
		double yLoc = 195; double yLocBack = 195;
		String hitPlanet = "1";
		int numPlanets = 3;
		boolean next = false;
		boolean holeComplete = false;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		int [] numbers = new int[7];
		boolean printOut = false;

		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole8() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 50;
			constants[0][1] = 155;
			constants[0][2] = 40;
			constants[0][3] = 2.8829; //CHANGE
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];

			constants[1][0] = 460; //xcoord
			constants[1][1] = 340; //ycoord
			constants[1][2] = 35;  //radius
			constants[1][3] = 2.2072;
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			constants[2][0] = 240; //xcoord
			constants[2][1] = 230; //ycoord
			constants[2][2] = 60;  //radius
			constants[2][3] = 6.4865; //DONT CHANGE
			constants[2][4] = constants[2][0] + constants[2][2];
			constants[2][5] = constants[2][1] + constants[2][2];
			

			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			if ((oval.contains(128,438) || oval.contains(128,463) || oval.contains(115,451) || oval.contains(140,451) || oval.contains(136.3, 459.3)  || oval.contains(118.7,441.7) || oval.contains(136.3, 441.7) || oval.contains(118.7, 459.3))&& next)
			{
					moveTimer.stop();
					holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
				if (num == 0);
				else if (hitPlanet.indexOf("" + (num+1)) == -1 && hitPlanet.indexOf("" + (num)) != -1) hitPlanet = hitPlanet + " " + (num+1);
				if (hitPlanet.indexOf("2") != -1 && hitPlanet.indexOf("3") != -1) next = true;
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if ((oval.contains(128,438) || oval.contains(128,463) || oval.contains(115,451) || oval.contains(140,451) || oval.contains(136.3, 459.3)  || oval.contains(118.7,441.7) || oval.contains(136.3, 441.7) || oval.contains(118.7, 459.3))&& next)
			{
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto3.jpeg").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			if (reset)
			{
				reset = false;
				xLocBack = 130;
				yLocBack = 195;
				if (!ifClassic) shotsTaken = 0;
				hitPlanet = "1";
				next = false;
				printOut = false;
				holeComplete = false;
				back = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f3 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f3);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 8 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 8 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			g.setColor(Color.CYAN);
			g.fillOval(50,155,80,80);
			g.setColor(Color.MAGENTA);
			g.fillOval(240,230,120,120);
			g.setColor(Color.YELLOW);
			g.fillOval(460,340,70,70);
			
			Font f = new Font("Serif", Font.BOLD, 30);
			g.setFont(f);

			g.setColor(Color.RED);
			g.fillOval(115,438,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(116,439,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(118,441,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(121,443,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(124,446,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(127,449,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(130,452,6,6); //+3, +3

			g.setColor(Color.GREEN);
			g.drawString("1",85,205);
			if (hitPlanet.indexOf("2") == -1) g.setColor(Color.BLACK);
			g.drawString("2",488,385);
			if (hitPlanet.indexOf("2") == -1 || hitPlanet.indexOf("3") == -1) g.setColor(Color.BLACK);
			g.drawString("3",293,300);
			
			g.setColor(Color.BLUE);
			Font f1 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f1);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				next = false;
				holeComplete = false;
				ifClassic = false;
				hitPlanet = "1";
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole9");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = e.getX() - (xMouseStart+xOffset); //velocity is the distance between the two endpoints of the black line
				velocityY = e.getY() - (yMouseStart+yOffset); //i forgot why you need to do 8 and 32...whatever it works ok lol
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			//System.out.println(xMouse + "/n" + yMouse);
			xMouseCurrent = e.getX()-xOffset; //for a split second, xMouseCurrent and xMouseStart will be the same! it's intended to be like that
			yMouseCurrent = e.getY() -yOffset; //these two variables are to draw the black line
			
			//obviously, when user stops dragging, the mouse is released! so go see mouseReleased
		}
	}
	
	class Hole9 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{	
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 285; double xLocBack = 285;
		double yLoc = 234; double yLocBack = 234;
		String hitPlanet = "1";
		int numPlanets = 4;
		boolean next = false;
		boolean holeComplete = false;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		int [] numbers = new int[7];
		boolean printOut = false;
		boolean justStarted = false;
		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];
		public Hole9() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 250;
			constants[0][1] = 250;
			constants[0][2] = 40;
			constants[0][3] = 2.8829; //CHANGE
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
			
			constants[1][0] = 450; //xcoord
			constants[1][1] = 150; //ycoord
			constants[1][2] = 45;  //radius
			constants[1][3] = 3.6486;
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			constants[2][0] = 130; //xcoord
			constants[2][1] = 100; //ycoord
			constants[2][2] = 45;  //radius
			constants[2][3] = 3.6486; //DONT CHANGE
			constants[2][4] = constants[2][0] + constants[2][2];
			constants[2][5] = constants[2][1] + constants[2][2];
			
			constants[3][0] = 230; //xcoord
			constants[3][1] = 470; //ycoord
			constants[3][2] = 40;  //radius
			constants[3][3] = 2.8829; //DONT CHANGE
			constants[3][4] = constants[3][0] + constants[3][2];
			constants[3][5] = constants[3][1] + constants[3][2];
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					justStarted = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			if ((oval.contains(68,328) || oval.contains(68,353) || oval.contains(55,341) || oval.contains(80,341) || oval.contains(76.3,349.3) || oval.contains(58.7,331.7) || oval.contains(76.3,331.7) || oval.contains(58.7,349.3)) && next)
			{
					moveTimer.stop();
					holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
				if (num == 0);
				else if (hitPlanet.indexOf("" + (num+1)) == -1 && hitPlanet.indexOf("" + (num)) != -1) hitPlanet = hitPlanet + " " + (num+1);
				if (hitPlanet.indexOf("2") != -1 && hitPlanet.indexOf("3") != -1 && hitPlanet.indexOf("4") != -1) next = true;
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if ((oval.contains(68,328) || oval.contains(68,353) || oval.contains(55,341) || oval.contains(80,341) || oval.contains(76.3,349.3) || oval.contains(58.7,331.7) || oval.contains(76.3,331.7) || oval.contains(58.7,349.3)) && next)
			{
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto4.jpg").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			if (reset)
			{
				reset = false;
				xLocBack = 285;
				yLocBack = 234;
				if (!ifClassic) shotsTaken = 0;
				hitPlanet = "1";
				next = false;
				holeComplete = false;
				printOut = false;
				back = true;
				justStarted = true;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					back = true;
					printOut = true;
				}
			}
			Font f3 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f3);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 9 Complete!", 410, 30);
				else 
				{
					g.drawString("Hole 9 Complete!", 280, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Next Level", 482, 30);
				}
			}
			g.setColor(Color.ORANGE);
			g.fillOval(250,250,80,80); //1
			g.setColor(Color.BLUE);
			g.fillOval(130,100,90,90); //3
			g.setColor(Color.RED);
			g.fillOval(450,150,90,90); //2
			g.setColor(Color.MAGENTA);
			g.fillOval(230,470,80,80); //4
			
			g.setColor(Color.RED);
			g.fillOval(55,328,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(56,329,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(58,331,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(61,333,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(64,336,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(67,339,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(70,342,6,6); //+3, +3
		
			Font f = new Font("Serif", Font.BOLD, 30);
			g.setFont(f);	
			g.setColor(Color.GREEN);
			g.drawString("1", 283, 302);
			if (hitPlanet.indexOf("2") == -1) g.setColor(Color.BLACK);
			g.drawString("2",486,206);
			if (hitPlanet.indexOf("2") == -1 || hitPlanet.indexOf("3") == -1) g.setColor(Color.BLACK);
			g.drawString("3",168,155);
			if (hitPlanet.indexOf("2") == -1 || hitPlanet.indexOf("3") == -1 || hitPlanet.indexOf("4") == -1) g.setColor(Color.BLACK);
			g.drawString("4",260,520);
			
			g.setColor(Color.BLUE);
			Font f1 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f1);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
		
			if (justStarted)
			{
				g.setFont(new Font("Serif", Font.BOLD, 15));
				g.setColor(Color.YELLOW);
				g.drawString("Since these are the hardest levels, ", 185, 345);
				g.drawString("the creators decided to make it a litttttttle harder ;)", 135, 370);
				g.drawString("Don't worry, all of the controls are still the same. ", 135, 400);
				g.drawString("However, there is now a 'limit' for how much you can drag.", 115, 425);
				g.drawString("Any further dragging will NOT increase your initial velocity.", 110, 450);
			}
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				cardLayout.show(cardPanel, "levelMenu");
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				next = false;
				holeComplete = false;
				ifClassic = false;
				hitPlanet = "1";
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				cardLayout.show(cardPanel, "hole10");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = xMouseCurrent - xMouseStart; //velocity is the distance between the two endpoints of the black line
				velocityY = yMouseCurrent - yMouseStart; //i forgot why you need to do 8 and 32...whatever it works ok lol
				//System.out.println("X: " + velocityX + "\nY: " + velocityY + "\n");
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			xMouseCurrent = e.getX()-xOffset; 
			yMouseCurrent = e.getY() -yOffset; 
			double ang = calcAngle(xMouseCurrent, yMouseCurrent, oval);
			if (Math.pow((Math.pow(xMouseCurrent-xMouseStart,2) + Math.pow(yMouseCurrent-yMouseStart,2)), .5) > 260)
			{
				double offset = Math.pow((Math.pow(xMouseCurrent-xMouseStart,2) + Math.pow(yMouseCurrent-yMouseStart,2)), .5) - 260;
				xMouseCurrent = xMouseCurrent - offset*Math.cos(ang * 3.14159/180);
				yMouseCurrent = yMouseCurrent + offset*Math.sin(ang * 3.14159/180);
			}
		}
	}
		
	class Hole10 extends JPanel implements ActionListener, MouseListener, MouseMotionListener
	{	
		double velocityX; //basically distance between current ball coordinates and where you dragged it 
		double velocityY;
		boolean inBall; //true when user starts dragging IN THE CIRCLE
		boolean dragging = false; //becomes true when the user starts dragging ANYWHERE
		double xMouseStart; //coordinates of where the ball is, when it is stationary (so the value stays the same as you drag around)
		double yMouseStart;
		double xMouseCurrent; //coordinates of the mouse when you are dragging (this is to draw the line)
		double yMouseCurrent;
		double xMove = 0; //used in the timer, "tells" ball where to move during the animation sequence
		double yMove= 0;
		Timer moveTimer;
		int i = 0; //see timer (basically a counter)
		int ballR = 8;
		//everything that has to be changed per level is below
		double xLoc = 292; double xLocBack = 292;
		double yLoc = 144; double yLocBack = 144;
		int numPlanets = 2;
		double [][] constants = new double [numPlanets][6]; //X,Y,radius of Planet, mass, centerX, centerY
		double [][] changing = new double [numPlanets][3]; //angle, radius TO planet, gravity
		Ellipse2D.Double oval = new Ellipse2D.Double(xLoc,yLoc,ballR*2,ballR*2); //this is where the ball STARTS! this will be modified as we go along (see line 90)
		boolean back = false;
		boolean holeComplete = false;
		int [] numbers = new int[7];
		boolean printOut = false;
		int shots = 0;

		final int arrayLength = 5000;
		int [] xLocation = new int[arrayLength];
		int [] yLocation = new int[arrayLength];

		public Hole10() //sets constants, creates timer and back button
		{
			setBackground(Color.GRAY);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			constants[0][0] = 225;
			constants[0][1] = 160;
			constants[0][2] = 75;
			constants[0][3] = 9.9; //CHANGE
			constants[0][4] = constants[0][0] + constants[0][2];
			constants[0][5] = constants[0][1] + constants[0][2];
			
			constants[1][0] = 65;
			constants[1][1] = 140;
			constants[1][2] = 20;
			constants[1][3] = 1.12; //CHANGE
			constants[1][4] = constants[1][0] + constants[1][2];
			constants[1][5] = constants[1][1] + constants[1][2];
			
			BallMover ballmover = new BallMover(); //timer class
			moveTimer = new Timer(20, ballmover);
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton test = new JButton("Back");
			test.addActionListener(this);
			test.setBackground(Color.BLACK);
			test.setForeground(Color.WHITE);
			add(test);
		}
		
		class BallMover implements ActionListener //"activated" after player releases mouse (and sets a trajectory)
		{
			public void actionPerformed(ActionEvent e) //"activated" after player releases mouse (and sets a trajectory)
			{
				i++;
				if ( i == 1)
				{	
					xMove = (velocityX/(vToMove)); //basically, every single frame, the ball will travel 1/100 closer to the end point.
					yMove = (velocityY/(vToMove)); //these two variables allow for the ball to MOVE there and just show up immediately
					shotsTaken++;
					shots++;
					xLocBack = xLoc;
					yLocBack = yLoc;
					printOut = false;
					for (int j = 0; j < arrayLength; j++)
					{
						xLocation[j] = 1000;
						yLocation[j] = 1000;
					}
				}
				for (int i = 0; i < numPlanets; i++) 
				{
					String xMoveyMove = game.calcARG(i,constants,changing, oval, xMove, yMove);
					xMove = Double.parseDouble(xMoveyMove.substring(0, xMoveyMove.indexOf('A')));
					yMove = Double.parseDouble(xMoveyMove.substring(xMoveyMove.indexOf('A') + 1, xMoveyMove.length()));
				}
				xLoc = xLoc + xMove;
				yLoc = yLoc + yMove;
				xLocation[i] = (int)(xLoc + ballR);
				yLocation[i] = (int)(yLoc + ballR);
				oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
				hitPlanet(0);
				
				repaint();
				grabFocus(); //i just copied this from KeyTimer2 lol
			}
		}
		public void hitPlanet(int num) //makes sure that the golf ball stays at the EDGE of the planet
		{
			if (oval.contains(593,580) || oval.contains(593,615) || oval.contains(580,593) || oval.contains(615,593) || oval.contains(601.3,583.7) || oval.contains(583.7,601.3) || oval.contains(601.3,601.3) || oval.contains(583.7,583.7))
			{
					moveTimer.stop();
					holeComplete = true;
			}
			changing[num][1] = Math.pow(Math.pow(oval.getCenterX()- constants[num][4],2)+Math.pow(oval.getCenterY()-constants[num][5],2),.5);
			if (changing[num][1] < (constants[num][2]+ballR) )
			{
				changing[num][0] = calcAngle(num, constants, changing, oval);
				double changeRadius = (constants[num][2]+ballR) - changing[num][1]; //how much the ball needs to be moved "out"
				changing[num][0] = changing[num][0] * 3.14159/180;
				xLoc = xLoc - changeRadius*Math.cos(changing[num][0]);
				yLoc = yLoc + changeRadius*Math.sin(changing[num][0]);
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();	
			}
			oval = new Ellipse2D.Double((int)xLoc,(int)yLoc, ballR*2,ballR*2);
			if (oval.contains(593,580) || oval.contains(593,615) || oval.contains(580,593) || oval.contains(615,593) || oval.contains(601.3,583.7) || oval.contains(583.7,601.3) || oval.contains(601.3,601.3) || oval.contains(583.7,583.7))
			{
					moveTimer.stop();
					holeComplete = true;
			}
			if (num < (numPlanets-1)) hitPlanet(num+1);
		}
			
		public void paintComponent (Graphics g) //paints the ball, planets, wormhole
		{
			super.paintComponent(g);
			
			Image background = new ImageIcon("SpacePhoto4.jpg").getImage();
			g.drawImage(background, 0, 0,600,600, null); 
			if (reset)
			{
				reset = false;
				xLocBack = 292;
				yLocBack = 144;
				holeComplete = false;
				if (!ifClassic) shotsTaken = 0;
				printOut = false;
				back = true;
				shots = 0;
			}
			if (back) 
			{
				i = 0;
				xLoc = xLocBack;
				yLoc = yLocBack;
				xMove = 0;
				yMove = 0;
				for (int j = 0; j < arrayLength; j++)
				{
					xLocation[j] = 1000;
					yLocation[j] = 1000;
				}
				back = false;
				moveTimer.stop();
				repaint();
			}
			
			if ((oval.getCenterX() > 600 || oval.getCenterX() < 0) || (oval.getCenterY() > 600 || oval.getCenterY() < 0)) 
			{
				numbers = game.outOfBounds(oval);
				if (numbers[4] != -1)
				{
					g.setColor(Color.WHITE);
					if (!holeComplete)g.drawLine(numbers[0], numbers[1], numbers[2], numbers[3]);
					if (!holeComplete)g.drawString("" + numbers[4], numbers[5], numbers[6]);
				}
				else 
				{
					printOut = true;
					back = true;
				}
			}
			Font f3 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f3);
			g.setColor(Color.RED);
			if (printOut && i == 0) g.drawString("The ball went out of bounds!", 250, 30);
			g.setColor(Color.WHITE);
			if (!holeComplete)g.fillOval((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //okay SO this is why we use the timer. xMove/yMove changes the coordinates of the circle!
			oval = new Ellipse2D.Double((int)(xLoc),(int)(yLoc),ballR*2,ballR*2); //need to change the oval(for the .contains() method) to where the ball actually is! 
			if (holeComplete)
			{
				oval = new Ellipse2D.Double(100,100,16,16);
				g.setColor(Color.CYAN);
				if(!ifClassic) g.drawString("Hole 10 Complete!", 403, 30);
				else 
				{
					g.drawString("Hole 10 Complete!", 273, 30);
					g.setColor(Color.GREEN);
					g.fillRect(470,10, 120, 30);
					g.setColor(Color.WHITE);
					g.drawString("Finish!", 500, 30);
				}
			}
			Color c = new Color(255,150,75);
			g.setColor(c);
			g.fillOval(225,160,150,150);
			
			g.setColor(Color.YELLOW);
			for (int j = 2; j < arrayLength; j++)
			{
				if (xLocation[j] == 1000 && yLocation[j] == 1000)
				{
					break;
				}
				if (j % 6 < 3) g.drawLine(xLocation[j-1], yLocation[j-1], xLocation[j], yLocation[j]);
			}

			Color d = new Color(255,60,0);
			Color e = new Color(255,70,0);
			Color f = new Color(255,80,0);
			Color h = new Color(255,90,0);
			Color j = new Color(255,100,0);
			Color i = new Color(255,110,10);
			Color k = new Color(255,120,20);
			Color l = new Color(255,130,30);
			Color m = new Color(255,140,40);
			Color n = new Color(255,150,50);
			
			g.setColor(n);
			g.fillOval(230,160,140,140); //10, 0, -10
			g.setColor(m);
			g.fillOval(230,160,130,130); //0, 0, -10
			g.setColor(l);
			g.fillOval(240,165,115,115); //10, 5, -15
			g.setColor(k);
			g.fillOval(240,170,110,110); //0, 5, -5
			g.setColor(j);
			g.fillOval(250,175,100,100); //10, 5, -10
			g.setColor(i);
			g.fillOval(255,185,90,90); //5, 10, -10
			g.setColor(h);
			g.fillOval(260,190,80,80); //5, 5, -10
			g.setColor(f);
			g.fillOval(270,200,70,70); //10, 10, -10
			g.setColor(e);
			g.fillOval(280,210,50,50); //10, 10, -20
			g.setColor(d);
			g.fillOval(290,210,30,30); //10, 0, -20
			
			
			g.setColor(Color.CYAN);
			g.fillOval(65,140, 40,40);
			
			g.setColor(Color.RED);
			g.fillOval(580,580,25,25);
			g.setColor(Color.ORANGE);
			g.fillOval(581,581,23,23); //+1, +1
			g.setColor(Color.YELLOW);
			g.fillOval(583,583,20,20); //+2, +2
			g.setColor(Color.green);
			g.fillOval(585,585,17,17); //+3, +2
			g.setColor(Color.BLUE);
			g.fillOval(588,588,14,14); //+3, +3
			g.setColor(Color.magenta);
			g.fillOval(591,591,11,11); //+3, +3
			g.setColor(Color.BLACK);
			g.fillOval(594,594,6,6); //+3, +3
			
			g.setColor(Color.BLUE);
			Font f1 = new Font("Serif", Font.BOLD, 20);
			g.setFont(f1);
			g.drawString("Shots Taken: " + shotsTaken, 80, 30);
			
			g.setColor(Color.BLUE);
			if (inBall) g.drawLine((int)(xLoc+ballR),(int)(yLoc+ballR),(int)(xMouseCurrent),(int)(yMouseCurrent)); //this is for the line you see while dragging the mouse
			
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.setColor(Color.YELLOW);
			if (shots == 8) g.drawString("Having some trouble??", 190, 450);
			if (shots == 16) g.drawString("I wonder why there are two planets...", 155, 450);
			if (shots == 20) g.drawString("Hint: Use a gravity assist", 170, 450);
		}
		
		public void actionPerformed(ActionEvent e) //Brings player back to the title screen.
		{
			String cmd = e.getActionCommand();
			if (cmd.equals("Back")) 
			{
				reset = true; //this is so that the ball resets to its original position.
				holeComplete = false;
				ifClassic = false;
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0; //reset of counter
				moveTimer.stop(); //stop the animation (since we're at the new location now)
				repaint();
				cardLayout.show(cardPanel, "levelMenu");				
			}
		}
		public void mouseClicked (MouseEvent e) 
		{
			int xMouse = e.getX() - xOffset;
			int yMouse = e.getY() - yOffset;
			Rectangle rect = new Rectangle(470,10,120,30);
			if (ifClassic && holeComplete && rect.contains(xMouse,yMouse)) 
			{
				reset = true; //this is so that the ball resets to its original position.
				xMove = 0; //these two lines might be unneccesary (timer stops anyway) but i do it just in case
				yMove = 0;
				i = 0;
				ifClassic = false;
				holeComplete = false;
				repaint();
				moveTimer.stop();
				classicComplete = true;
				highScores.moveTimer.start();
				cardLayout.show(cardPanel, "highScores");
			}
		}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited (MouseEvent e) {}
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) //see mouseDragged first
		{
			if (inBall) //if the user started dragging within the ball, the second they release the mouse, the ball SHOULD start moving! 
			{
				
				velocityX = xMouseCurrent - xMouseStart; //velocity is the distance between the two endpoints of the black line
				velocityY = yMouseCurrent - yMouseStart; //i forgot why you need to do 8 and 32...whatever it works ok lol
				//System.out.println("X: " + velocityX + "\nY: " + velocityY + "\n");
				moveTimer.start(); //see timer! 
				repaint();
			}
			//the timer will, by default, stop itself after 100 frames
			inBall = false; //reset booleans
			dragging = false;
			repaint();
			xMouseStart = 0; //reset everything!!!
			yMouseStart = 0;
			xMouseCurrent = 0;
			yMouseCurrent = 0;
		}
		public void mouseMoved (MouseEvent e) {}
		public void mouseDragged (MouseEvent e) //activates the "line"
		{
			if (dragging == false) //if user starts dragging (anywhere!)...
			{
				dragging = true;
				xMouseStart = e.getX()-xOffset; //find the coordinates of where they "started" the dragging
				yMouseStart = e.getY()-yOffset; //the -8 and -32 are to offset 
				
				//notice how this if-statement only runs ONCE per drag! (dragging is switched back to false in mouseReleased)
			}
			
			if (oval.contains(xMouseStart, yMouseStart)) //if the oval contains the coordinates of where it started, set inBall to true (to draw the black line)
			{
				inBall = true;
				reset = false; //this tells game that player is in the middle of a game.
			}
			repaint();
			
			xMouseCurrent = e.getX()-xOffset; 
			yMouseCurrent = e.getY() -yOffset; 
			double ang = calcAngle(xMouseCurrent, yMouseCurrent, oval);
			if (Math.pow((Math.pow(xMouseCurrent-xMouseStart,2) + Math.pow(yMouseCurrent-yMouseStart,2)), .5) > 325)
			{
				double offset = Math.pow((Math.pow(xMouseCurrent-xMouseStart,2) + Math.pow(yMouseCurrent-yMouseStart,2)), .5) - 325;
				xMouseCurrent = xMouseCurrent - offset*Math.cos(ang * 3.14159/180);
				yMouseCurrent = yMouseCurrent + offset*Math.sin(ang * 3.14159/180);
			}
		}
	}
	public double calcAngle(double xCoord, double yCoord, Ellipse2D.Double oval) //calculates the angle from the golf ball to a planet
	{
		double angle = 0;
		if (oval.getCenterX() > xCoord && oval.getCenterY() < yCoord) 
		{
			angle = 180/3.14159 *Math.atan( (yCoord-oval.getCenterY())/(oval.getCenterX()-xCoord) );
			angle = -180 + angle;
		}
		else if (oval.getCenterX() > xCoord && oval.getCenterY() > yCoord)
		{
			angle = 180/3.14159 *Math.atan( (oval.getCenterY()-yCoord)/(oval.getCenterX()-xCoord) );
			angle = 180 - angle;
		}
		else if (oval.getCenterX() < xCoord && oval.getCenterY() > yCoord)
		{
			angle = 180/3.14159 *Math.atan( (oval.getCenterY()-yCoord)/(xCoord-oval.getCenterX()) );
		}
		else 
		{
			angle = 180/3.14159 *Math.atan( (yCoord - oval.getCenterY())/(xCoord-oval.getCenterX()) );
			angle = angle *-1;
		}
		return(angle);
	}
}
