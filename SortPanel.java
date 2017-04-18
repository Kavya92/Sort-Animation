/************************************************************
 *                                                          *
 *  CSCI 470/680-E        Assignment 5         Summer 2016  *                                             
 *                                                          *
 *  Programmer: Venkata Kavya Devarapally (Team Lead)       *
 *              Amujuri Manjusha                            *
 *                                                          *
 *  Date Due:   08/04/2016                                  *                          
 *                                                          *
 *  Purpose:    The program is to understand multithreadhing* 
 *              and different sorting techniques using GUI  *
 *                                                          *
 *                                                          *
 ***********************************************************/  
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.JApplet;
import java.util.Random;


//class sortpanel extends jpanel and it adds all objects to the panel
public class SortPanel extends JPanel
{
  public JButton populate;                                            // declaring buttons
  public JButton start;
  public JButton resume;
  public JButton stop;
  public JButton pause;
  public JButton ascending;
  public JButton descending;
  public JComboBox<String> sortCombo;
  public String[] sortAlgo = {"Select","Shell Sort","Selection Sort","Insertion Sort","Bubble Sort"}; 
                                                            // all the selected algorithms in the combo box goes into the string
  public SortAnimationPanel animationPanel;
  public  JPanel sortpanel;
  Border border;
  final ButtonGroup bg=new ButtonGroup();                 // declaring button group  
  GridBagLayout layout1 = new GridBagLayout();           // declaring gridbag layout
  GridBagConstraints gbc = new GridBagConstraints(); 
  public boolean orderPanel = true;
  public String algo;
 
  // constructor
  SortPanel()
  {
   super();                                             //calling the super class constructor 
   setLayout(new GridLayout(1,2));
   animationPanel = new SortAnimationPanel();            //calling the instance of Animation Panel class       
    createButton();                                      //calling the methods
    createCombo();
    prepareGUI();
    //setting the initial buttons
    populate.setEnabled(true);
    start.setEnabled(false);
    resume.setEnabled(false);
    stop.setEnabled(false);
    pause.setEnabled(false);
    ascending.setEnabled(false);
    descending.setEnabled(false);
    sortCombo.setEnabled(false);
  }
  
/***************************************************
Function  : createButton
Use       : creates buttons and sets its features
Parameter : Nothing
Returns   : Nothing

*****************************************************/
  public void createButton()
  {
     populate = new JButton("Populate Array");                   // creating populate button and setting its features
    populate.setPreferredSize(new Dimension(110,25));
    populate.setBackground(Color.BLACK);
    populate.setForeground(Color.WHITE);
    populate.setBorder(new LineBorder(Color.RED));
    
    start = new JButton("Sort");                             //  creating sort button and setting its features
    start.setPreferredSize(new Dimension(60,25));
    start.setBackground(Color.BLACK);
    start.setForeground(Color.WHITE);
    start.setBorder(new LineBorder(Color.RED));
    
    resume = new JButton("Resume");                       // creating resume button and setting its features
    resume.setPreferredSize(new Dimension(60,25));
    resume.setBackground(Color.BLACK);
    resume.setForeground(Color.WHITE);
    resume.setBorder(new LineBorder(Color.RED));
    
    pause = new JButton("Pause");                       // creating pause button and setting its features
    pause.setPreferredSize(new Dimension(60,25));
    pause.setBackground(Color.BLACK);
    pause.setForeground(Color.WHITE);
    pause.setBorder(new LineBorder(Color.RED));
    
    stop = new JButton("Stop");                       // creating stop button and setting its features. 
    stop.setPreferredSize(new Dimension(60,25)); 
    stop.setBackground(Color.BLACK);
    stop.setForeground(Color.WHITE);
    stop.setBorder(new LineBorder(Color.RED));
    
    ascending = new JButton("Ascending");           // creating ascending order button and setting its features
    ascending.setPreferredSize(new Dimension(110,25));
    ascending.setBackground(Color.BLACK);
    ascending.setForeground(Color.WHITE);
    ascending.setBorder(new LineBorder(Color.RED));
    
    descending = new JButton("Descending");         // creating descending order button and setting its features
    descending.setPreferredSize(new Dimension(110,25));
    descending.setBackground(Color.BLACK);
    descending.setForeground(Color.WHITE);
    descending.setBorder(new LineBorder(Color.RED));        
      
    // adding all the buttons to button group
      bg.add(populate);
      bg.add(start);
      bg.add(resume);
      bg.add(pause);
      bg.add(stop);      
      bg.add(ascending);
      bg.add(descending);
      
  
    //creating a buttonHandler
    PopulateButtonHandler btn = new PopulateButtonHandler();
    populate.addActionListener(btn);                             // adding populate button to actionlistner to perform corresponding action  
    
    
    //creating a buttonHandler
    StartButtonHandler btn1 = new StartButtonHandler();
    start.addActionListener(btn1);                              // adding start button to actionlistner to perform corresponding action  
    ascending.addActionListener(btn1);                         // adding ascending button to actionlistner to perform corresponding action  
    descending.addActionListener(btn1);                        // adding descending button to actionlistner to perform corresponding action
     
     
    //creating a buttonHandler
    ControlButtonHandler btn3 = new ControlButtonHandler();
    pause.addActionListener(btn3);                            // adding pause button to actionlistner to perform corresponding action 
    resume.addActionListener(btn3);                          // adding resume button to actionlistner to perform corresponding action 
    stop.addActionListener(btn3);                            // adding stop button to actionlistner to perform corresponding action 
  }
  
  // class MyItemListener handles actions of itemlistener.It defines itemchange state
  public class MyItemListener implements ItemListener
  {
      // method to change the item state
      public void itemStateChanged(ItemEvent event)
      {
        sortCombo.setEnabled(false);      
        ascending.setEnabled(true);
        descending.setEnabled(true);
      }
  }
  
  // class ControlButtonHandler handles actions. It performs resume,pause and stop actions
  public class ControlButtonHandler implements ActionListener
  {
    // override method to perform pause, resume amd stop actions
    @Override
      public void actionPerformed(ActionEvent event)        
      {
         if(event.getSource() == pause)                // condition is checked  when we click pause
        {
         
          animationPanel.checkPaused();               // then it pauses the animation panel
          pause.setEnabled(false);                 
          resume.setEnabled(true);
          animationPanel.paused = true;
          
        }
        else if(event.getSource() == resume)         // condition is checked  when we click resume after the pause
        {
          resume.setEnabled(false);
          pause.setEnabled(true);
          animationPanel.threadResume();           // then it resumes the animation panel again
        }
        else if(event.getSource() == stop)         // condition is checked  when we click stop
        {
         animationPanel.randInt = null;           // once we click stop the animation panel is cleared
         revalidate();
         repaint();
         resume.setEnabled(false);
         pause.setEnabled(true);
         start.setEnabled(false);
         sortCombo.setEnabled(false);
         pause.setEnabled(false);
         populate.setEnabled(true);
         stop.setEnabled(false);
        }
      }
  }
  
  
  public class PopulateButtonHandler implements ActionListener
   {
    // override method to enable populate array and combobox
     @Override
      public void actionPerformed(ActionEvent event)
      {
       populate.setEnabled(false);
       sortCombo.setEnabled(true);
       animationPanel.populateArray();  
      }
   }
  
 // class StartButtonHandler handles actions. It decides when to enable and disable the buttons
  public class StartButtonHandler implements ActionListener
  {
    // override method to perform start, ascending amd descending actions
    @Override
      public void actionPerformed(ActionEvent event)
      {
        if(event.getSource() == start)                //condition is checked  when we click sort 
        {
         descending.setEnabled(false);              // enables and disables the following buttons
         ascending.setEnabled(false);
         pause.setEnabled(true);
         stop.setEnabled(true);         
         algo = sortCombo.getSelectedItem().toString();    // gets the selected algorithm into the algo string
         animationPanel.intializeThread(algo);             // then initializes the thread       
         populate.setEnabled(true);         
          revalidate();
          repaint();
        }
        
        else if(event.getSource() == ascending)         //condition is checked  when we click ascending
        {
          ascending.setEnabled(false);
          descending.setEnabled(false);
          start.setEnabled(true);
          orderPanel = true;
          animationPanel.orderPanel = true;           // sets the order of the panel to ascending 
        }
        else if(event.getSource() == descending)     //condition is checked  when we click descending
        {
          ascending.setEnabled(false);
          descending.setEnabled(false);              // sets the order of the panel to descending 
          start.setEnabled(true);
          animationPanel.orderPanel = false;
        }
       
      
      }
      
   }
    
 /***************************************************
Function  : createCombo
Use       : Prepares combo box and its structure
Parameter : Nothing
Returns   : Nothing

*****************************************************/ 
  public void createCombo()
  {
    sortCombo = new JComboBox<>(sortAlgo);                   // creating a combo box anf setting its features
    sortCombo.setPreferredSize(new Dimension(100,25));
    sortCombo.setBackground(Color.BLACK);
    sortCombo.setForeground(Color.WHITE);
    sortCombo.setBorder(BorderFactory.createLineBorder(Color.red)); 
   
    MyItemListener btn2 = new MyItemListener();
    sortCombo.addItemListener(btn2);
     
  }

  /***************************************************
Function  : prepareGUI
Use       : Prepares layout for GUI
Parameter : Nothing
Returns   : Nothing

*****************************************************/
  
  public void prepareGUI()
  {
    border= BorderFactory.createRaisedBevelBorder();       //Creating border to the layout
    this.setBorder(border);
    this.setLayout(layout1);                               //adding grid bag layout
    this.setVisible(true);
    
    //adding the buttons and comboboxes to the panel
    
    gbc.fill = GridBagConstraints.BOTH;                     //adding grid bag constraints 
    gbc.gridx =0;
    gbc.gridy =0 ;
    gbc.gridheight = 4;
    gbc.gridwidth = 4;
    this.add(animationPanel, gbc);
     
   
    gbc.gridx = 0;
    gbc.gridy = 4;  
    gbc.gridwidth = 1;
    this.add(populate,gbc);  
   
   
    gbc.gridx = 1;
    gbc.gridy = 4;    
    gbc.gridwidth = 1;
    this.add(start, gbc);
        
     gbc.gridx = 2;
     gbc.gridy = 4;
     gbc.gridheight = 1;
    gbc.gridwidth = 1;
    this.add(sortCombo, gbc);
    
     gbc.gridx = 3;
    gbc.gridy = 4;  
    gbc.gridwidth = 1;
    this.add(stop,gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 8;  
    gbc.gridwidth = 1;
    this.add(ascending,gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 8;  
    gbc.gridwidth = 1;
   this.add(descending,gbc); 
    
    gbc.gridx = 2;
    gbc.gridy = 8;  
    gbc.gridwidth = 1;
    this.add(pause,gbc);     
    
    
     
    gbc.gridx = 3 ;
    gbc.gridy = 8;  
    gbc.gridwidth = 1;
    this.add(resume,gbc);   
    
  }
 
  
}
  
