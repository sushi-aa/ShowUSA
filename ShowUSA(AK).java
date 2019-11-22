/**
 *  ShowUSA.java
 *  Provide a description here.
 *  @author Your Name Here
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
 *  Add comments.
 */
class GetAndDrawCities extends JPanel 
{
	private static final int PREF_W = 1000;
	private static final int PREF_H = 600;
	private static final int COORDINATE_MULTIPLIER = 1000000;
	private static final int MINLAT = 20000000;
	private static final int MAXLAT = 50000000;
	private static final int MINLONG = 60000000;
	private static final int MAXLONG = 130000000;
	
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
		//int diameter = 40;
		int diameter = 4;
		
		String citiesFileName = "cities.txt";
		String outputFileName = "output.txt";
		File citiesFile = new File(citiesFileName);
        File outputFile = new File(outputFileName);
        Scanner inCities = null;
        String cityLine = null;
        String dataSeperator = ",";
        String longitudeStr = null;
        String latitudeStr = null;
        String zip = null;
        String stateCode = null;
        String city = null;
        double longitude = 0.0;
        double latitude = 0.0;
        int intLong = 0;
		int intLat = 0;
        int data = -1;
        
        g.setColor(Color.GRAY);
        
        /*
         * read cities file and process data.
         */ 
        try      
        {
            inCities = new Scanner(citiesFile);
            while (inCities.hasNext())      
    		{
    			cityLine = inCities.nextLine();
    			cityLine = cityLine.trim();
    			
    			data = cityLine.indexOf(dataSeperator);
    			if (data != -1) { zip = cityLine.substring(0, data).trim(); }
    			cityLine = cityLine.substring(data+1).trim();
    			data = cityLine.indexOf(dataSeperator);
			    if (data != -1) { stateCode = cityLine.substring(0, data).trim(); }
			    cityLine = cityLine.substring(data+1).trim();			    
			    data = cityLine.indexOf(dataSeperator);
			    if (data != -1) { city = cityLine.substring(0, data).trim(); }
			    cityLine = cityLine.substring(data+1).trim();			    
			    data = cityLine.indexOf(dataSeperator);
			    if (data != -1) { latitudeStr = cityLine.substring(0, data).trim(); }			    
			    longitudeStr = cityLine.substring(data+1).trim();
			    
			    latitude = Double.parseDouble(latitudeStr);
			    longitude = Double.parseDouble(longitudeStr);
			    
			    intLat = ((int) (latitude * COORDINATE_MULTIPLIER) - MINLAT) / ((MAXLAT - MINLAT) / PREF_H);       
			    intLong = ((int) (longitude * COORDINATE_MULTIPLIER) - MINLONG) / ((MAXLONG - MINLONG) / PREF_W);
			    
			    g.fillOval(PREF_W-intLong, PREF_H-intLat, diameter, diameter);
    		}
        }
        catch (FileNotFoundException e)      
        {
            System.err.printf("ERROR: Cannot open %s\n", citiesFile);
            System.exit(1);
        }
        
        if (inCities != null) inCities.close();
	}
	
	/**
	 *  Open up the capitals text file, and, for each city you find in
	 *  this file, compare to the cities in cities.txt.  If you find a
	 *  match, print a red dot with diameter 12.
	 */
	public void addCapitals(Graphics g)
	{
	    int diameter = 12;
	    String capitalsFileName = "capitals.txt";
        File capitalsFile = new File(capitalsFileName);
        Scanner inCapitals = null;
        String cStateCode = null;
        String cCity = null;
        String dataSeperator = ","; 
        String capitalLine = null;
		int data = -1;
		
		String citiesFileName = "cities.txt";
		File citiesFile = new File(citiesFileName);
        Scanner inCities = null;
        String cityLine = null;
        String longitudeStr = null;
        String latitudeStr = null;
        String zip = null;
        String stateCode = null;
        String city = null;
        double longitude = 0.0;
        double latitude = 0.0;
        int intLong = 0;
		int intLat = 0;
        /*
         * open the capitals.txt file for reading.
         */
        try      
        {
            inCapitals = new Scanner(capitalsFile);
            while (inCapitals.hasNext())      
    		{
    			capitalLine = inCapitals.nextLine();
    			capitalLine = capitalLine.trim();
    			
    			data = capitalLine.indexOf(dataSeperator);
    			if (data != -1) { cCity = capitalLine.substring(0, data).trim(); }
    			cStateCode = capitalLine.substring(data+1).trim();
    			//System.out.println("cCity " + cCity + " cState " + cStateCode);
    			/* for eacy pair of capital city and state find a match in the "cities.txt"
    			 * to get longitude and latitude values of the capital city.
    			 */
    			try      
                {
                    inCities = new Scanner(citiesFile);
                    while (inCities.hasNext())      
            		{
            			cityLine = inCities.nextLine();
            			cityLine = cityLine.trim();
            			
            			data = cityLine.indexOf(dataSeperator);
            			if (data != -1) { zip = cityLine.substring(0, data).trim(); }
            			cityLine = cityLine.substring(data+1).trim();
            			data = cityLine.indexOf(dataSeperator);
        			    if (data != -1) { stateCode = cityLine.substring(0, data).trim(); }
        			    cityLine = cityLine.substring(data+1).trim();			    
        			    data = cityLine.indexOf(dataSeperator);
        			    if (data != -1) { city = cityLine.substring(0, data).trim(); }
        			    cityLine = cityLine.substring(data+1).trim();			    
        			    data = cityLine.indexOf(dataSeperator);
        			    if (data != -1) { latitudeStr = cityLine.substring(0, data).trim(); }			    
        			    longitudeStr = cityLine.substring(data+1).trim();
        			    
        			    if (cCity.equals(city) && cStateCode.equals(stateCode))
        			    {
        			        /*System.out.println("cCity " + cCity + " cState " + cStateCode +
        			                           " City " + city + " State " + stateCode); */
            			    latitude = Double.parseDouble(latitudeStr);
            			    longitude = Double.parseDouble(longitudeStr);
            			    
            			    intLat = ((int) (latitude * COORDINATE_MULTIPLIER) - MINLAT) / ((MAXLAT - MINLAT) / PREF_H);       
            			    intLong = ((int) (longitude * COORDINATE_MULTIPLIER) - MINLONG) / ((MAXLONG - MINLONG) / PREF_W);
            			    g.setColor(Color.RED);
            			    g.fillOval(PREF_W-intLong, PREF_H-intLat, diameter, diameter);
            			}
        			}
                }
                catch (FileNotFoundException e)      
                {
                    System.err.printf("ERROR: Cannot open %s\n", citiesFile);
                    System.exit(1);
                }
    		}
        }
        catch (FileNotFoundException e)      
        {
            System.err.printf("ERROR: Cannot open %s\n", capitalsFile);
            System.exit(1);
        }
        if (inCities != null) inCities.close();
        if (inCapitals != null) inCapitals.close();
	}
	
	public boolean checkForCapital(String city,String state)
	{
		return true;
	}
}