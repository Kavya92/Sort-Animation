
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


// Defining class in which main method and interactions with application
public class SortAnimationApp extends JFrame
{
  
  public static final int  WIDTH = 750;                              // setting width and height of the frame
  public static final int  HEIGHT = 550;
  SortPanel st;                                                     //  declaring sortpanels
  SortPanel st1;
  
  public static void main(String args[])
  {  
   SortAnimationApp animationFrame = new SortAnimationApp();                      // creating a sortanimationframe 
   animationFrame.setLayout(new GridBagLayout());                                // setting bridbaglayout to the frame
   animationFrame.setSize(WIDTH,HEIGHT);                                        // setting size for the frame
   animationFrame.setVisible(true);                                             // setting visibility to true
   animationFrame.pack();                                                      
   animationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  }
  
  //Method to add sort panel and name to the frame
  SortAnimationApp()
   {
      super("Sorting Animation");                                             // adding name to the frame
      st = new SortPanel();                                                   // adding two parallel sort panels and setting layout to them
      st1 = new SortPanel();      
      add(st,BorderLayout.EAST);
      add(Box.createRigidArea(new Dimension(20,50)));
      add(st1,BorderLayout.WEST);
   }

  
}