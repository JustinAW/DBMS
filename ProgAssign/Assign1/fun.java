/**
 * fun.java
 * Author: Justin Weigle
 * Edited: 28 Jan 2020
 * Depends: filemod.java
 */
import java.util.*;

public class fun {

	public static void main(String[] args)
	{
		filemod f = new filemod();
		Scanner s = new Scanner(System.in);
		int choice = 0;
		while (choice != 3)
		{
			System.out.println("What would you like to do?");
			System.out.println(
					"1: add a record\n2: modify an existing record\n3: exit");
			choice = s.nextInt();
			s.nextLine(); // clear the newline from the buffer
			if (choice == 1)
			{
				f.addRecord(s);
			}
			else if (choice == 2)
			{
				f.modifyRecord(s);
			}
		}
		s.close();
	}

}
