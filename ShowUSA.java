/**
 *  ShowUSA.java
 *  Provide a description here.
 *  @author Arushi Arora
 *  @version 1.0
 *  @since 8/30/2016
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ShowUSA extends JFrame
{
        /**
         *  Creates a ShowUSA object, adding a JPanel
         *  to the JFrame.
         */
        public ShowUSA()
        {
            super("United States of America");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GetAndDrawCities cities = new GetAndDrawCities();
            getContentPane().add(cities);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public static void main(String[] args)
        {
                new ShowUSA();
               
            } 
}
/**
 *  Makes a JPanel, calls the two methods needed to display the cities and the capitals
 */
    class GetAndDrawCities extends JPanel
    {
            private static final int PREF_W = 1000;
        private static final int PREF_H = 600;
        public GetAndDrawCities()
        {
                setBackground(Color.white);
                
            }
        @Override
        public Dimension getPreferredSize()
        {
                return new Dimension(PREF_W, PREF_H);
            }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            addCities(g);
            addCapitals(g);
        } 
   
        
    /**
     *  Here, you should open up the cities text file, and translate the
     *  numbers you find to coordinate values to be plotted.  The
     *  dots should be gray and of diameter 4.
     */
    public void addCities(Graphics g)
    {
                Scanner infile = OpenFile.openToRead("cities.txt"); //call to the OpenFile class 
                
                String line = null; //each line of the file
                int firstComma; //first separator of the zip code and state      
                int secondComma; // separates state and city
                int thirdComma; //separates city and latitude
                int fourthComma; //separates latitude and longitude
                
                String latitude; //latitude and longitude read from each line
                String longitude;
                
                double lat; //parsed from the string
                double longit;
                int intLat; //converted from the double values
                int intLongit;
                
                int diameter = 4; //diameter of the circles to be plotted
                
                final int lowestValueLat = 20000000; //lowest value of the latitudes multiplied by 1 million (to be used for converting to ints)
                final int lowestValueLongit = 60000000; //lowest value of the longitudes multiplied by 1 million (to be used for converting to ints)
            
                while (infile.hasNext())
                {
                        line = infile.nextLine();
                        
                        firstComma = line.indexOf(","); //finding the indexes of each comma 
                        secondComma = line.indexOf(",", firstComma+1);
                        thirdComma = line.indexOf(",", secondComma+1);
                        fourthComma = line.indexOf(",", thirdComma+1);
                        latitude = line.substring(thirdComma+1, fourthComma); //latitude is between the third and fourth comma 
                        longitude = line.substring(fourthComma+2); //everything after the fourth comma is the longitude 
                       
                        lat = new Double(Double.parseDouble(latitude)); //parsing to make the values doubles 
                        longit = new Double(Double.parseDouble(longitude));
                        
                        intLat = (int) ((lat * 1000000) - lowestValueLat) / 50000; //multiplying by a million, subtracting the lowest value, dividing by the range
                        intLongit = (int) ((longit * 1000000) - lowestValueLongit) / 70000; //(maintains precision of the doubles but is set as an int)

                        g.setColor(Color.gray);
                        
                        g.fillOval((PREF_W - intLongit), (PREF_H - intLat), diameter, diameter); //making circles with the values
                        
                }
                if (infile!=null) infile.close(); //closing the text file 
   
                
    }
    /**
     *  Open up the capitals text file, and, for each city you find in
     *  this file, compare to the cities in cities.txt.  If you find a
     *  match, print a red dot with diameter 12.
     */
    public void addCapitals(Graphics g)
    {
            
                String cityLine = null; //each line of the file
                int firstComma; //first separator of the zip code and state      
                int secondComma; // separates state and city
                int thirdComma; //separates city and latitude
                int fourthComma; //separates latitude and longitude
                
                String latitude; //latitude and longitude read from each line
                String longitude;
                String citiesCity;
                String citiesState;
                double lat; //parsed from the string
                double longit;
                int intLat; //converted from the double values
                int intLongit;
                
                int diameter = 12; //diameter of the circles to be plotted
                
                final int lowestValueLat = 20000000; //lowest value of the latitudes multiplied by 1 million (to be used for converting to ints)
                final int lowestValueLongit = 60000000; //lowest value of the longitudes multiplied by 1 million (to be used for converting to ints)
            
                
                Scanner inCapitals = OpenFile.openToRead("capitals.txt");
                String capitalLine = null; //each line of the capitals file
                String capitalsCity;
                String capitalsState;
                
               
                while (inCapitals.hasNext())   //35004,AL,Acmar,33.584132, 86.51557
                {
                    capitalLine = inCapitals.nextLine();
                    capitalsCity = (capitalLine.substring(0, capitalLine.indexOf(","))).trim(); //the city name from the capitals file
                    capitalsState = (capitalLine.substring(capitalLine.indexOf(",") + 1)).trim(); //the state name from the capitals file
                    //System.out.println("City 2 = " + capitalsCity + " State 2 = " + capitalsState);            
                    
                    Scanner inCities = OpenFile.openToRead("cities.txt"); //call to the OpenFile class 
                
                    while (inCities.hasNext()) //Albany, NY
                    {
                            cityLine = inCities.nextLine();
                        
                            firstComma = cityLine.indexOf(",");
                            secondComma = cityLine.indexOf(",", firstComma+1);
                            citiesState = (cityLine.substring(firstComma+1, secondComma)).trim(); //the state name from the city file
                            thirdComma = cityLine.indexOf(",", secondComma+1);
                            citiesCity = (cityLine.substring(secondComma+1, thirdComma)).trim(); //the city name from the city file
                            fourthComma = cityLine.indexOf(",", thirdComma+1);
                            latitude = cityLine.substring(thirdComma+1, fourthComma); //latitude is between the third and fourth comma 
                            longitude = cityLine.substring(fourthComma+2); //everything after the fourth comma is the longitude 
                               
                            lat = new Double(Double.parseDouble(latitude)); //parsing to make the values doubles 
                            longit = new Double(Double.parseDouble(longitude));
                                
                            intLat = (int) ((lat * 1000000) - lowestValueLat) / 50000; //multiplying by a million, subtracting the lowest value, dividing by the range
                            intLongit = (int) ((longit * 1000000) - lowestValueLongit) / 70000; //(maintains precision of the doubles but is set as an int)

                            
                            if (citiesCity.equals(capitalsCity) && (citiesState.equals(capitalsState))) //if a match is found, make a red dot
                            {
                                //System.out.println("EQUAL");
                                g.setColor(Color.RED);
                                
                                g.fillOval((PREF_W - intLongit), (PREF_H - intLat), diameter, diameter); //making circles with the values
                            }
                    }
                    if (inCities!=null) inCities.close();
                }
                if (inCapitals!=null) inCapitals.close();
    
    }
    public boolean checkForCapital(String city,String state)
    {
        return true;
    }
}
