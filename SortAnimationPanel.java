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


//link for sorting algorithms  
//http://www.java2novice.com   - Bubble sort, insertion sort , selection sort
//https://rosettacode.org/wiki/Sorting_algorithms/Shell_sort  - Shell sort

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.Graphics;

//This class animates the entire panel with sorting algorithms using multithreading

public class SortAnimationPanel extends JPanel implements Runnable
{
  // declaring instance variables;
  public int randInt[];
  private String algo;
  private Thread thread;
  private String comboAlgorithm;
  private int[] array;
  private int[] tempMergArr;
  private int length;
  private int lowerIndex,
            higherIndex;
  public boolean orderPanel = true;
  private volatile boolean running = true;
  public volatile boolean paused = false;
  private volatile boolean suspended = false;
  private final Object pauseLock = new Object();
  
  
/***************************************************
Function  : SortAnimationPanel
Use       : constructor to call JPanel class
Parameter : Nothing
Returns   : Nothing

*****************************************************/
  SortAnimationPanel()
  {
    super();                                                        // calling super class constructor
    super.setPreferredSize(new Dimension(400,400));
    super.setVisible(true);
  }
 
  
/***************************************************
Function  : populateArray
Use       : creates random values and assign to array
Parameter : Nothing
Returns   : Nothing

*****************************************************/
  public void populateArray()
  {  
    Random rand = new Random();                                   // calling random class to generate random numbers
    randInt = new int[this.getWidth()];                           // initializing array to its panel width
    rand.setSeed(System.currentTimeMillis());
    for(int i = 0; i < this.getWidth();i++)                       // assigning the random values to array 
    {
      randInt[i] = rand.nextInt(this.getHeight() -1) + 1;
    }
    this.repaint();                                               // calling paint method
  }
  
/***************************************************
Function  : paintComponent
Use       : paint the component using graphics
Parameter : Graphics object
Returns   : Nothing

*****************************************************/
 
  @Override
    protected void paintComponent(Graphics g) 
     {
       super.paintComponent(g);                               //calling paintComponent method
       g.clearRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
       g.fillRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
       g.setColor(Color.BLACK);
       this.repaint();   
       if(randInt==null) return;                              //check if array is null
        for(int i = 0;i <this.getWidth();i++)                 // drawing the lines using graphics
        {
            g.setColor(Color.RED);
            g.drawLine(i,randInt[i],i,this.getHeight() );
        }
    }
  
  
/***************************************************
Function  : intializeThread
Use       : intialize the thread
Parameter : String object
Returns   : Nothing

*****************************************************/  
  
  public void intializeThread(String algorithm)
  {
      thread = new Thread(this);                                   // creating a new thread
      thread.start();                                              // starting a thread
      this.comboAlgorithm = algorithm;
      this.setThreadState();                                       // calling the thread state method
  }
 
  
/***************************************************
Function  : setThreadState()
Use       : setting the thread state after the sort completes
Parameter : Nothing
Returns   : Nothing

*****************************************************/  
  
  public void setThreadState()
  {
    synchronized(pauseLock)
    {
      try
      {
        while(suspended)
        {
        pauseLock.wait();     //waiting the thread if the thread in suspended state
        }
        if(paused)             
        pauseLock.wait();     //will make thread to block until notify is called
      } 
       catch(InterruptedException e)
       {
         System.out.println("Exception occured" + e);
       }
      }
        
    }
 
 
/***************************************************
Function  : run
Use       : thread to execute
Parameter : Nothing
Returns   : Nothing

*****************************************************/ 
  
  public void run()
  { // checking for appropriate sorting algorithm
     
    if(comboAlgorithm.equals("Shell Sort"))
    {
      this.shellSort(randInt);
    }
     
    if(comboAlgorithm.equals("Selection Sort"))
    {
      this.selectionSort(randInt);
    }
      
    if(comboAlgorithm.equals("Insertion Sort"))
    {
      this.insertionSort(randInt);
    }
    if(comboAlgorithm.equals("Bubble Sort"))
    {
      this.bubbleSort(randInt);
    }
    
  }
  
/***************************************************
Function  : selectionSort
Use       : sorting the algorithm using appropraite selections
Parameter : array of random values
Returns   : sorted array

*****************************************************/ 
 
     public int[] selectionSort(int[] arr)
     {
       try
       {
         for (int i = 0; i < arr.length - 1; i++)                     //looping over the array to sort the elements
          {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
              if(!orderPanel)                                         // checking for Descending order
            {
                if (arr[j] < arr[index])
                    index = j;
            }
            else
            {
              if (arr[j] > arr[index])                                //checking for Aescending order
                    index = j;
            }
            int smallerNumber = arr[index]; 
            arr[index] = arr[i];
            arr[i] = smallerNumber;                                 //swapping the values
            thread.sleep(100);
            repaint();
            this.setThreadState();
        }
         }
         catch(Exception e)
         {
           System.out.println("Exception occured " + e);
         }
        return arr;
    }
   
/***************************************************
Function  : insertionSort
Use       : sorting the algorithm using appropraite insertions
Parameter : array of random values
Returns   : nothing

*****************************************************/ 
 
    public void insertionSort(int[] arr) 
    {
      int i, j, newValue;
      try
      { 
        //looping over the array to sort the elements
        for (i = 1; i < arr.length; i++) 
        {
          thread.sleep(100);
          newValue = arr[i];
          j = i;
          if(!orderPanel)                                    //condition for Descending order
          {
            while (j > 0 && arr[j - 1] > newValue) 
            {
              arr[j] = arr[j - 1];                            // swapping the elements         
              repaint();                                      // painting the GUI
              this.setThreadState();
              j--;
            }
           }
          else                                             // condition for Ascending order
          {
            while (j > 0 && arr[j - 1] < newValue) 
            {
              arr[j] = arr[j - 1];
              repaint();                                    // painting the GUI
              this.setThreadState();
              j--;
            }
          }
          arr[j] = newValue;
            
        }
      }
      catch(Exception e)
      {
        System.out.println("Exception occured" + e); 
      }
     
      
    }
      
    
/***************************************************
Function  : bubbleSort
Use       : sorting the algorithm using appropraite insertions
Parameter : array of random values
Returns   : nothing

*****************************************************/ 
    
     public void bubbleSort(int [] intArray)
    {
      int n = intArray.length;
      int temp = 0;
         try
         {
                for(int i=0; i < n; i++)
                {
                    thread.sleep(100);                              //sleep the thread to particular to view the results in panel
                    this.setThreadState();
                        for(int j=1; j < (n-i); j++)
                        { 
                               if(!orderPanel)                            //check for Descending order
                               {
                                if(intArray[j-1] > intArray[j])
                                {
                                        //swap the elements!
                                        temp = intArray[j-1];
                                        intArray[j-1] = intArray[j];
                                        intArray[j] = temp;
                                         repaint();
                                        
                                       
                                }
                               }
                               else if(intArray[j-1] < intArray[j])         //check for Ascending order
                               {
                                        temp = intArray[j-1];                                      
                                        intArray[j-1] = intArray[j];
                                        intArray[j] = temp;
                                        repaint();
                                        
                               }
                               
                        }
                }
         }
         catch(Exception e)
         {
           System.out.println("Exception occured" + e);
         }
    }
                
  
     
/***************************************************
Function  : bubbleSort
Use       : sorting the algorithm using appropraite insertions
Parameter : array of random values
Returns   : nothing

*****************************************************/ 

  public void shellSort(int[] a) 
   {
     int increment = a.length / 2;
     while (increment > 0) 
     {
       
       for (int i = increment; i < a.length; i++)            //looping over the array
       {
         int j = i;
         int temp = a[i];                                    // swapping the values to a temporary array
         try
         {
         if(!orderPanel)                                        // checking for Descending order
          {
             while (j >= increment && a[j - increment] > temp) 
             {
               a[j] = a[j - increment];                         //swapping the values
               thread.sleep(100);
               repaint();
               this.setThreadState();
               j = j - increment;
             }
         }
         
         else
         {
           while (j >= increment && a[j - increment] < temp)   //checking for Ascending order
           {
             a[j] = a[j - increment];
             thread.sleep(200);
               repaint();
               this.setThreadState();
             j = j - increment;
           }
     
         }
         }
         catch(Exception e)
         {
           System.out.println("Exception occured" + e);
         }
         a[j] = temp;
         }
       
       if (increment == 2) 
       {
         increment = 1;
       } 
       else 
       {
         increment *= (5.0 / 11);
       }
     }
 }
  
     
   
        
/***************************************************
Function  : checkPaused()
Use       : pausing the thread
Parameter : nothing
Returns   : nothing

*****************************************************/       
     
    
     public void checkPaused()
     {
       paused = true;
     }
     
/***************************************************
Function  : threadResume()
Use       : resuming the thread
Parameter : nothing
Returns   : nothing

*****************************************************/       
     
     
     public void threadResume()
     {
       synchronized(pauseLock)
       {
         try
         {
         paused = false;
         pauseLock.notifyAll();                 //notifying the thread to resume its execution 
         }
         catch(Exception e)
         {
         System.out.println("Exception occured" + e);
         }
       }
       
     }
     
     
}