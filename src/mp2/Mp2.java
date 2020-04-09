/*
Andrew Tong
Mr. Tomczak
10/1/19
MP2

This program builds a 3x3 array of room objects to make a "hotel."
Each room object contains the following fields: Number, Occupancy status, Cleaned status, Sleeping capacity, and Account Balance
The data for the hotel is kept in a text file, with each room being on a separate line and the field values separated by a comma.
Inside this program, the user encounters a repeating menu, where they can add a guest to the hotel, checkout a guest, clean a room, 
add and payoff the tab, print a list of rooms and their info, and lookup by floor and door number. 
The repeating menu also has the last option of quitting and outputting all the data back into CSV form in a text file.

 */
package mp2;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Mp2 {

    public static void main(String[] args) throws FileNotFoundException 
    {
        File inFile=new File("hotelData.txt");
        Scanner fileScanner=new Scanner(inFile);//reads in specified file
        Scanner consoleScanner=new Scanner(System.in);//another scanner to read in user input from console
        final int rowLength=3;
        final int columnLength=3;
        room[][] hotelArray=new room[3][3];//creates an array of room objects
        
        for (int i=0; i<rowLength; i++)//fills the hotel 3x3 array with room objects
        {
            for (int j=0; j<columnLength; j++)
            {
                String input=fileScanner.nextLine();
                hotelArray[i][j]=roomMaker(input);
            }
        }

        System.out.println("Hello, hotel manager, welcome to Andrew's mp2! \nThis program helps you manage your hotel through a 3x3 array of room objects. \nEach room object contains the following fields: Number, Occupancy status, Cleaned status, Sleeping capacity, and Account Balance.\nThe data for the hotel is kept in a text file, with each room being on a separate line and the field values separated by a comma.\nInside this program, you will encounter a repeating menu, which has options that will be displayed after you type in your name. \nAlright, let's begin! What is your name?");
        String user=consoleScanner.nextLine();//asks user for name for user engagement
        int choice=0;//increases scope to entire do while

        //creation of repeating menu using do while loop
        do
        {
            System.out.println("Options:\n1: Check into a room");
            System.out.println("2: Check out of a room");
            System.out.println("3: Clean a room");
            System.out.println("4: Add to tab");
            System.out.println("5: Payoff tab");
            System.out.println("6: Print list of all rooms");
            System.out.println("7: Lookup by Room");
            System.out.println("8: Lookup by Floor");
            System.out.println("9: Save/exit and print room list to mp2.out.txt");
            System.out.println(user+", please enter which number option you would like (1, 2, 3, 4, 5, 6, 7, 8, or 9). Do not enter a non-integer.\nI would recommend choosing option 6 first to print the entire roomlist in order to see the current state of all the fields of the room.");
            System.out.println("If you enter more than one choice at one time, only the first option will be considered.");//bounds check by clearing the buffer
           nonIntegerBoundsCheck(consoleScanner);//makes sure that choice chosen is an integer
           choice=consoleScanner.nextInt();
           
           switch (choice)
           {
               case 1: 
                   consoleScanner.nextLine();//clears buffer so that only the first choice is considered, not any future choices
                   System.out.println("\nThe current empty rooms and their clean statuses are: ");
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           if (hotelArray[i][j].getOccupiedStatus()==false)
                           {
                               System.out.println(hotelArray[i][j].printEmpty());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                               //I decided to not print it in the form of a 2d array because it would be unnecessarily difficult to scroll across horizontally.
                           }
                           
                       }
                       
                   }
                   System.out.println("\nYou cannot check into an occupied or a dirty room. What room do you want to check into? (please enter the room number)?");
                   System.out.println("If no options are available, enter 1 to return to the menu.");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   
                   int roomChoice1=consoleScanner.nextInt();
                   consoleScanner.nextLine();////this works so that only the first choice is considered, not any future choices
                   
                   int floorChoice1=roomChoice1/10;//find the floor
                   int individualRoom1=roomChoice1%10;//find the individual room on the floor
                   
                   if (roomChoice1==1) 
                   {
                       System.out.println("\nReturned to the menu.");
                       break;//return to menu option (I wanted to add the break option)
                   }
                       
                   //check if room is out of bounds
                   if (floorChoice1>3||floorChoice1<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
        
                    else if (individualRoom1>3||individualRoom1<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
                   
                   
                   room roomObject1=hotelArray[floorChoice1-1][individualRoom1-1];//these values are subtracted by 1 because the index of row and column starts at 0
                   
                   //bounds checks for rooms that cannot be acted upon
                   if (roomObject1.getOccupiedStatus()==true)
                   {
                       System.out.println("\nRoom is already occupied! Pick another room to check in.");
                       break;
                   }
                   
                   else if (roomObject1.getCleanedStatus()==false)
                   {
                       System.out.println("\nRoom is dirty! Please clean the room and try to check in again.");
                       break;
                   }
                   
                   System.out.println("\nHow many guests are there? The sleeping capacity of your room is "+roomObject1.getOccupancy()+". If you have more guests, please check into multiple rooms. (integers greater than 0 and up to the sleeping capacity will be accepted)");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   
                   
                   int guests=consoleScanner.nextInt();
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices

                   if (guests>roomObject1.getOccupancy())//check to make sure that there aren't too many guests
                   {
                       System.out.println("\nToo many guests! Please check into multiple rooms and try again.");
                       break;
                   }
                   if (guests<=0)//if user enters in a nonpositive number
                   {
                       System.out.println("Guests cannot be negative or 0!");
                   }
                   
                   roomObject1.checkIn();
                   roomObject1.dirtyRoom();//after check in, the room is now dirty because guests are in it
                   System.out.println("\nGuests fit- checked in! Choose option 7 to lookup by room and see its resulting information.");
                   break;  
               case 2:
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\nThe current occupied rooms are: ");
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           if (hotelArray[i][j].getOccupiedStatus()==true)
                           {
                               System.out.println(hotelArray[i][j].printOccupied());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                               //I decided to not print it in the form of a 2d array because it would be unnecessarily difficult to scroll across horizontally.
                           }  
                           
                       }
                   }
                   
                   System.out.println("\nYou cannout check out of an unoccupied room. Be aware that you cannot check out of a rooom with an unpaid tab. What room do you want to check out of (please enter the room number)?");
                   System.out.println("If no options are available, enter 1 to return to the menu.");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   
                   int roomChoice2=consoleScanner.nextInt();
                   int floorChoice2=roomChoice2/10;//find the floor
                   int individualRoom2=roomChoice2%10;//find the individual room on the floor
                   consoleScanner.nextLine();////this works so that only the first choice is considered, not any future choices
                   
                   if (roomChoice2==1) 
                   {
                       System.out.println("\nReturned to the menu.");
                       break;//return to menu option
                   }
                   
                   //check if room is out of bounds
                   if (floorChoice2>3||floorChoice2<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
        
                    else if (individualRoom2>3||individualRoom2<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
                   
                   room roomObject2=hotelArray[floorChoice2-1][individualRoom2-1];//these values are subtracted by 1 because the index of row and column starts at 0
                   
                   //bounds checks for rooms that cannot be acted upon
                   double epsilon=1E-6;//for rounding errors inherent in double calculations
                   if (Math.abs(roomObject2.getTab())>epsilon)//epsilon approximates 0
                   {
                       System.out.println("\nCan't check out yet! Still have to pay off the tab!");
                       break;
                   }
                   else if (roomObject2.getOccupiedStatus()==false)
                   {
                       System.out.println("\nCan't check out! The room is not occupied.");
                       break;
                   }
                   
                   roomObject2.checkOut();
                   System.out.println("\nChecked out! Choose option 7 to lookup by room and see its resulting information.");
                   break;
               case 3:
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\nThe current dirty rooms are: ");
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           if (hotelArray[i][j].getCleanedStatus()==false)
                           {
                               System.out.println(hotelArray[i][j].printClean());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                           //I decided to not print it in the form of a 2d array because it would be unnecessarily difficult to scroll across horizontally.
                           }
                           
                       }
                   }
                   System.out.println("\nYou cannot clean an already clean room. What room do you want to clean (please enter the room number)?");
                   System.out.println("If no options are available, enter 1 to return to the menu.");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   
                   int roomChoice3=consoleScanner.nextInt();
                   int floorChoice3=roomChoice3/10;//find the floor
                   int individualRoom3=roomChoice3%10;//find the individual room on the floor
                   consoleScanner.nextLine();////this works so that only the first choice is considered, not any future choices
                   
                   if (roomChoice3==1) 
                   {
                       System.out.println("\nReturned to the menu.");
                       break;//return to menu option
                   }
                   
                   //check if room is out of bounds
                   if (floorChoice3>3||floorChoice3<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
        
                    else if (individualRoom3>3||individualRoom3<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
                   
                   room roomObject3=hotelArray[floorChoice3-1][individualRoom3-1];//these values are subtracted by 1 because the index of row and column starts at 0
                   
                   //bounds checks for rooms that cannot be acted upon
                   if (roomObject3.getCleanedStatus()==true)
                   {
                       System.out.println("\nNo need to clean the room; it's already clean!");
                       break;
                   }
                   roomObject3.cleanRoom();
                   System.out.println("\nRoom cleaned! Choose option 7 to lookup by room and see its resulting information.");
                   break;
               case 4:
                   consoleScanner.nextLine();////this works so that only the first choice is considered, not any future choices
                   System.out.println("\nThe current tabs of all of the occupied rooms are: ");
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           if (hotelArray[i][j].getOccupiedStatus()==true)
                           {
                               System.out.println(hotelArray[i][j].printTab());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                           //I decided to not print it in the form of a 2d array because it would be unnecessarily difficult to scroll across horizontally.
                           }
                           
                       }
                   }
                   
                   System.out.println("\nYou cannot add to the tab of an unoccupied room. What room do you want to add to tab (please enter the room number)?");
                   System.out.println("If no options are available, enter 1 to return to the menu.");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   int roomChoice4=consoleScanner.nextInt();
                   int floorChoice4=roomChoice4/10;//find the floor
                   int individualRoom4=roomChoice4%10;//find the individual room on the floor
                   consoleScanner.nextLine();////this works so that only the first choice is considered, not any future choices
                   
                   if (roomChoice4==1) 
                   {
                       System.out.println("\nReturned to the menu.");
                       break;//return to menu option
                   }
                   
                   //check if room is out of bounds
                   if (floorChoice4>3||floorChoice4<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
        
                    else if (individualRoom4>3||individualRoom4<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
                   
                   room roomObject4=hotelArray[floorChoice4-1][individualRoom4-1];//these values are subtracted by 1 because the index of row and column starts at 0
                   //bounds checks for rooms that cannot be acted upon
                   if (roomObject4.getOccupiedStatus()==false)
                   {
                       System.out.println("\nCan't add to the tab if the room is not occupied!");
                       break;
                   }
                   
                   System.out.println("\nHow much do you want to add to the tab (positive doubles only please)? The current tab is $"+roomObject4.getTabRounded());
                   nonDoubleBoundsCheck(consoleScanner);//bounds check for nondouble answers
                   double amountAdded=consoleScanner.nextDouble();
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   
                   roomObject4.addTab(amountAdded);
                   System.out.println("\nTab added! Choose option 7 to lookup by room and see its resulting information.");
                   break;
               case 5:
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\nThe current tabs of all of the occupied rooms are: ");
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           if (hotelArray[i][j].getOccupiedStatus()==true)
                           {
                               System.out.println(hotelArray[i][j].printTab());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                           //I decided to not print it in the form of a 2d array because it would be unnecessarily difficult to scroll across horizontally.
                           }
                           
                       }
                   }
                   System.out.println("\nWhat room do you want to payoff the tab (please enter the room number)?");
                   System.out.println("If no options are available, enter 1 to return to the menu.");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   int roomChoice5=consoleScanner.nextInt();
                   int floorChoice5=roomChoice5/10;//find the floor
                   int individualRoom5=roomChoice5%10;//find the individual room on the floor
                   consoleScanner.nextLine();//to clear the scanner so that user can't type two responses separated by a space
                   
                   if (roomChoice5==1) 
                   {
                       System.out.println("\nReturned to the menu.");
                       break;//return to menu option
                   }
                   
                   //check if room is out of bounds
                   if (floorChoice5>3||floorChoice5<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
        
                    else if (individualRoom5>3||individualRoom5<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
                   
                   
                   room roomObject5=hotelArray[floorChoice5-1][individualRoom5-1];//these values are subtracted by 1 because the index of row and column starts at 0
                   //bounds checks for rooms that cannot be acted upon
                   if (roomObject5.getOccupiedStatus()==false)
                   {
                       System.out.println("\nCan't payoff the tab if the room is not occupied!");
                       break;
                   }
                   
                   System.out.println("\nHow much do you want to payoff from the tab? The amount on the tab is currently $"+roomObject5.getTabRounded()+". Please enter a correct monetary amount (no partial cents) and don't overpay!");//add the current tab
                   nonDoubleBoundsCheck(consoleScanner);//bounds check for nondouble answers
                   double amountPaid=consoleScanner.nextDouble();
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   
                   epsilon=1E-6;//for rounding errors in double calculations
                   if (amountPaid-roomObject5.getTab()>epsilon)
                   {
                       System.out.println("\nYou paid more than the tab. Please try again!");
                       break;
                   }
                   
                   roomObject5.payTab(amountPaid);
                   System.out.println("\nTab paid! Choose option 7 to lookup by room and see its resulting information.");
                   break;
               case 6:
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\nYou chose to print a list of all the rooms and its info.");
                   
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           System.out.println(hotelArray[i][j].printAll());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                           //I decided to not print it in the form of a 2d array because it would be unnecessarily difficult to scroll across horizontally.
                       }
                       System.out.println();
                   }
                   
                   break;
               case 7:
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\nWhat room do you want to look up (please enter the room number)?");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   int roomChoice7=consoleScanner.nextInt();
                   int floorChoice7=roomChoice7/10;//find the floor
                   int individualRoom7=roomChoice7%10;//find the individual room on the floor
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   
                  
                   if (floorChoice7>3||floorChoice7<=0) //check if room is out of bounds
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
        
                    else if (individualRoom7>3||individualRoom7<=0)
                    {
                        System.out.println("\nThis room does not exist! Please try again.");
                        break;
                    }
                   
                   room roomObject7=hotelArray[floorChoice7-1][individualRoom7-1];//these values are subtracted by 1 because the index of row and column starts at 0
                   
                   System.out.println("\n"+roomObject7.printAll());
                   break;
               case 8:
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\nWhat floor do you want to look up (1,2,or 3)?");
                   nonIntegerBoundsCheck(consoleScanner);//bounds check for noninteger answers
                   int floorChoice8=consoleScanner.nextInt();
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   
                   //check if floor is out of bounds
                   if (floorChoice8>3 || floorChoice8<=0)
                   {
                       System.out.println("\nThis floor does not exist. Please try again!");
                       break;
                   }
                   
                   for (int j=0;j<columnLength;j++)
                   {
                       System.out.println(hotelArray[floorChoice8-1][j].printAll());
                   }
                   
                   break;
               case 9: 
                   consoleScanner.nextLine();//this works so that only the first choice is considered, not any future choices
                   System.out.println("\n"+user+", thank you for using this program! The roomlist was also printed out to mp2.out.txt.");
                   PrintWriter out=new PrintWriter("mp2.out.txt");
                   
                   for (int i=0;i<rowLength;i++)
                   {
                       
                       for (int j=0; j<columnLength; j++)
                       {
                           out.println(hotelArray[i][j].textFileWrite());//this is a print line to separate each room's details in different line (this is for viewing pleasure because I could keep this in a 3x3 array, but that would be a long text)
                       }
                   }
                   out.close();//fully saves the out file
                   break;
               default: System.out.println(user+", wrong input. Option chosen is not one of the 9 listed. Please try again!");//bounds check if user does not enter one of the 9 acceptable inputs
                   break;
                   
           }

        }
        
        while(choice!=9);//continues repeating the menu until 9 is hit; quits the do-while repeating menu if choice is 9

    }
    
    public static room roomMaker(String input)//reads in file text and creates the room objects, does one line at a time
    {
        int roomNumber;
        boolean occupied;
        boolean clean;
        int capacity;
        double tab;
        
        String[] rowArray= input.trim().split(",");//splits the elements in each row (by the comma) to be stored into the five variables
        roomNumber=Integer.parseInt(rowArray[0]);//parses each value into their respective columns
        occupied=Boolean.parseBoolean(rowArray[1]);
        clean=Boolean.parseBoolean(rowArray[2]);
        capacity=Integer.parseInt(rowArray[3]);
        tab=Double.parseDouble(rowArray[4]);
        
        return new room(roomNumber,occupied,clean,capacity,tab);//creates the room object with these five parameters
    }
    
    public static void nonIntegerBoundsCheck(Scanner consoleScanner)
    {
        while(!consoleScanner.hasNextInt())//bounds check for noninteger answers
        {
            
            System.out.println("Please try again and enter an integer value.");
            consoleScanner.next();
            
        }
    }
    
    public static void nonDoubleBoundsCheck(Scanner consoleScanner)
    {
        while(!consoleScanner.hasNextDouble())//bounds check for nondouble answers
        {

            System.out.println("Please try again and enter a double value (this is money after all).");
            consoleScanner.next();
            
        }
    }

}
