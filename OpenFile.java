/**
 * Utilities for opening a text file.  The text file
 * can be opened and read from, or the file can be
 * opened (created) and written to.
 * @author   Arushi Arora
 * @version  1.0
 * @since    8/22/2017
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OpenFile
{
	public static void main(String [] args)
	{
		Scanner infile = OpenFile.openToRead("c.txt");
		PrintWriter outfile = OpenFile.openToWrite("b.txt");
		String temp = null;
		while(infile.hasNext())
		{
			temp = infile.nextLine();
			System.out.println(temp);
			outfile.println(temp);
		}
		infile.close();
		outfile.close();
	}
	
	/**
	 * Opens a file for reading.
	 *
	 * @param filestring   The name of the file to be opened.
	 * @return             A Scanner instance of the file to be opened.
	 */
	public static Scanner openToRead (String filestring)
	{		
		Scanner fromfile = null;
		File filename = new File(filestring);
		try
		{
			fromfile = new Scanner(filename);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("\n\nSorry, but no such file was found. \n\n");
			System.exit(1);
		}
	
		return fromfile;
	}
	
	/**
	 * Opens a file for writing.
	 *
	 * @param filestring   The name of the file to be opened (created).
	 * @return             A PrintWriter instance of the file to be opened (created).
	 */
	public static PrintWriter openToWrite (String filestring)
	{
		PrintWriter outfile = null;
		try
		{
			outfile = new PrintWriter(filestring);
		}
		catch(Exception e)
		{
			System.out.println("\n\nSorry , but the file could not be created.\n\n");
			System.exit(2);
		}
		return outfile;
	}
}