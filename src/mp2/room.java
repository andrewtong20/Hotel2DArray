package mp2;
/*
This is the class used to create each room in the 3x3 array. 
The fields of the object include its number, its occupancy status, whether its clean, its sleeping capacity, and the tab on the room.
Accessors were created to get any of these 5 values into the main, mutators were created to change the occupied and cleaned status, as well as the tab from the main.
Finally, I created methods to print the individual fields of the object.
*/

public class room 
{
    final private int roomNumber;//never changes
    private boolean occupied;
    private boolean clean;
    final private int occupancy;//never changes
    private double tab;
    
    //parameter constructor- I used this instead of the default constructor because our starting text file already has values in it
    public room(int inRoom, boolean inOccupied, boolean inClean, int inOccupancy, double inTab)
    {
        roomNumber=inRoom;
        occupied=inOccupied;
        clean=inClean;
        occupancy=inOccupancy;
        tab=inTab;
    }
    //mutators
    public void checkIn()
    {
        occupied=true;
    }
    
    public void checkOut()
    {
        occupied=false;
    }
    
    public void cleanRoom()
    {
        clean=true;
    }
    
    public void dirtyRoom()
    {
        clean=false;
    }
    
    public void addTab (double inTab)
    {
        tab+=inTab;
    }
    
    public void payTab (double inTab)
    {
        tab-=inTab;
    }
    
    //accessors
    public int getRoom()
    {
        return roomNumber;
    }
    
    public boolean getOccupiedStatus()
    {
        return occupied;
    }
    
    public boolean getCleanedStatus()
    {
        return clean;
    }
    
    public int getOccupancy()
    {
        return occupancy;
    }
    
    public double getTab()
    {
        return tab;//returns double to compare double values in bounds checks of main
    }
    
    public String getTabRounded()//converts tab to string for display to console. I use the getTab accessor for calculations only
    {
        return String.format("%.2f",tab);//rounds tab to 2 decimal places because it is money
    }
    
    public String printEmpty()//these print methods allow the user to see what the current state of the rooms are
    {
        return String.format("Room number: %d, Clean? %b",roomNumber,clean);
    }
    public String printOccupied()
    {
        return String.format("Room number: %d, Tab is: $%.2f",roomNumber,tab);
    }
    
    public String printClean()
    {
        return String.format("Room number: %d",roomNumber);
    }
    
    public String printTab()
    {
        return String.format("Occupied Room number: %d, Tab is: $%.2f",roomNumber, tab);
    }
    
    public String printAll()//converts the fields of the room object into a string to be printed
    {
        //tab is automatically rounded to 2 decimal places because it is money
        return String.format("Room number: %d, Occupied? %b, Clean? %b, Sleeping capacity is: %d, Tab is: $%.2f",roomNumber, occupied, clean,occupancy, tab);
        
    }
    
    public String textFileWrite()//same thing as printAll except modified for the text file to be separated only by commas
    {
        return String.format("%d, %b, %b, %d, %.2f", roomNumber, occupied, clean, occupancy, tab);
        
    }
    


}
