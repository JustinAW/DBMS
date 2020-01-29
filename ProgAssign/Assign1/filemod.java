/**
 * filemod.java
 * Author: Justin Weigle
 * Edited: 28 Jan 2020
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class filemod
{
	private FileWriter x;
	
	/**
	 * Adds a new record to the end of the file fun.dat
	 */
	public void addRecord (Scanner s)
	{		
		// open the file fun.dat for modification
		openFile();
		// Get the time, age, and name of the new entry		
		String inputTime = getTime(s);
		String inputAge = getAge(s);
		String inputName = getName(s);
		// Format the new entry
		StringBuffer newEntryBuff = new StringBuffer();
		newEntryBuff.append(
				inputTime + " " + inputAge + " " + inputName + "\n");
		String newEntry;
		newEntry = newEntryBuff.toString();	// convert to string
		// add new entry to file
		try {
			x.append(newEntry);
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeFile();
	}
	
	/**
	 * Replaces the specified record and field with new input from the user
	 * THIS METHOD ASSUMES THERE ARE SPACES BETWEEN FIELDS IN ALL RECORDS
	 */
	public void modifyRecord (Scanner s)
	{
		int editLine = chooseRecord(s); // pick record by line number
		int field = chooseField(s); // pick field 1: time, 2: age, 3: name
		String newText = null;
		if (field == 1)
		{
			newText = getTime(s);
		}
		else if (field == 2)
		{
			newText = getAge(s);
		}
		else if (field == 3)
		{
			newText = getName(s);
		}
		else // return if an invalid choice is made
		{
			System.out.println("Invalid option chosen");
			return;
		} 
		// find and replace the relevant record's field's value
		List<String> newLines = new ArrayList<>();
		int i = 1;
		try {
			for (String line : Files.readAllLines(Paths.get("fun.dat"),
					StandardCharsets.UTF_8))
			{
				if (i == editLine)
				{
					if (field == 1)
					{
						String keep = line.substring(5, 20);
						String replacementLine = newText + keep;
						System.out.println("Replacement: " + replacementLine);
						newLines.add(replacementLine);
					}
					else if (field == 2)
					{
						String keepBegin = line.substring(0,6);
						String keepEnd = line.substring(9, 20);
						String replacementLine = keepBegin + newText + keepEnd;
						System.out.println("Replacement: " + replacementLine);
						newLines.add(replacementLine);
					}
					else if (field == 3)
					{
						String keep = line.substring(0, 10);
						String replacementLine = keep + newText;
						System.out.println("Replacement: " + replacementLine);
						newLines.add(replacementLine);
					}
				}
				else
				{
					newLines.add(line);
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// overwrite file with updated file contents
		try {
			Files.write(Paths.get("fun.dat"), newLines,
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asks the user which record they want to modify
	 */
	private int chooseRecord (Scanner s)
	{
		System.out.println("Choose a record number to modify");
		int recordChoice = s.nextInt();
		s.nextLine(); // clear the newline from the buffer
		return recordChoice;
	}
	
	/**
	 * Asks the user which field of a record they want to modify
	 */
	private int chooseField (Scanner s)
	{
		System.out.println("Choose a field in the record to modify");
		System.out.println("1: time\n2: age\n3: name");
		int fieldChoice = s.nextInt();
		s.nextLine(); // clear the newline from the buffer
		return fieldChoice;
	}
	
	/**
	 * Asks the user for a new time input
	 */
	private String getTime (Scanner s)
	{
		String inputTime = "";
		// check time format using regex, allows format hh:mm or h:mm
		while (!(inputTime.matches("(\\d+):(\\d{2})")))
		{
			System.out.println("Enter a valid time. Format: hh:mm");
			inputTime = getNewInput(s);
		}
		// make sure time is the correct length
		if (inputTime.length() < 5)
		{
			StringBuffer pad = new StringBuffer();
			pad.append(inputTime);
			while (pad.length() < 5)
			{
				pad.insert(0, "0");
			}
			inputTime = pad.toString();
		}
		System.out.println(inputTime);
		return inputTime;
	}
	
	/**
	 * Asks the user for a new age input
	 */
	private String getAge (Scanner s)
	{
		String inputAge = "";
		// check formatting using regex, also check length
		while (!(inputAge.matches("(\\d+)")) || !(inputAge.length() < 4))
		{
			System.out.println("Enter an age. Range: 0-999");
			inputAge = getNewInput(s);
		}
		// if length is less than 3, add spaces to make it 3
		if(inputAge.length() < 3)
		{
			StringBuffer pad = new StringBuffer();
			pad.append(inputAge);
			while (pad.length() < 3)
			{
				pad.append(" ");
			}
			inputAge = pad.toString();
		}
		System.out.println(inputAge);
		return inputAge;
	}
	
	/**
	 * Asks the user for a new name input
	 */
	private String getName (Scanner s)
	{
		String inputName = "";
		// check formatting using regex
		while (!(inputName.matches("(\\w+)")))
		{
			System.out.println("Enter a name.");
			inputName = getNewInput(s);
		}
		// if length is less than 10, add spaces to make it 10
		if(inputName.length() < 10)
		{
			StringBuffer pad = new StringBuffer();
			pad.append(inputName);
			while (pad.length() < 10)
			{
				pad.append(" ");
			}
			inputName = pad.toString();
		}
		// if length is longer than 10, truncate end off
		if(inputName.length() > 10)
		{
			inputName = inputName.substring(0, 10);
		}
		System.out.println(inputName);
		return inputName;
	}
	
	/**
	 * Uses a Scanner to get the next line from the console for user input
	 */
	private String getNewInput (Scanner s)
	{
		String userIn = s.nextLine();
		return userIn;
	}

	/**
	 * Uses FileWriter to open the file fun.dat with the append setting
	 * toggled to true so that a new entry may be added
	 */
	private void openFile ()
	{
		try {
			x = new FileWriter("fun.dat", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the file opened using openFile
	 */
	private void closeFile ()
	{
		try {
			x.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
