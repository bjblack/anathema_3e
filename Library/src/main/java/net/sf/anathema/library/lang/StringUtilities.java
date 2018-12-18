package net.sf.anathema.library.lang;

import net.sf.anathema.library.io.InputOutput;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class StringUtilities
{
	public static final String EMPTY_STRING = "";
	
	public static String getFileNameRepresentation (String string)
	{
		try
		{
			string = URLEncoder.encode (string, InputOutput.ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException (e);
		}
		StringBuilder fileName = new StringBuilder (string.length ());
		for (int index = 0; index < string.length (); index++)
		{
			char character = string.charAt (index);
			if (Character.isJavaIdentifierPart (character))
			{
				fileName.append (character);
			}
		}
		if (Strings.isNullOrEmpty (string))
		{
			fileName.setLength (0);
			fileName.append ("None");
		}
		return fileName.toString ();
	}
	
	
	public static List<Integer> allIndicesOf (String string, char character)
	{
		List<Integer> indexList = new ArrayList<> ();
		for (int index = string.indexOf (character, 0); index != -1; index = string.indexOf (character, ++index))
		{
			indexList.add (index);
		}
		return indexList;
	}
	
	/**
	 * Returns the optimal breakpoints to split a String into <code>lines</code> lines in a sorted array. If the number
	 * of possible breakpoints is too low to satisfy <code>lines</code>, 0 is returned.
	 */
	public static int[] findBreakPoints (String textString, int lines)
	{
		SortedSet<Integer> breakSet = new TreeSet<> ();
		breakSet.addAll (allIndicesOf (textString, ' '));
		breakSet.addAll (allIndicesOf (textString, '-'));
		List<Integer> breakList = new ArrayList<> ();
		for (int value : breakSet)
		{
			breakList.add (value + 1);
		}
		int[] lineBreaks = new int[lines - 1];
		int textLength = textString.length ();
		int currentBreakPoint = 0;
		for (int searchedBreakPoint = 0; searchedBreakPoint < lines - 1; searchedBreakPoint++)
		{
			int lastAllowedBreakPointIndex = breakList.size () - (lines - 1 - searchedBreakPoint);
			int niceTextStartPosition = textLength / lines * (searchedBreakPoint + 1);
			while (currentBreakPoint < lastAllowedBreakPointIndex)
			{
				if (breakList.get (currentBreakPoint) >= niceTextStartPosition)
				{
					break;
				}
				currentBreakPoint++;
			}
			if (breakList.size () > currentBreakPoint)
			{
				lineBreaks[searchedBreakPoint] = breakList.get (currentBreakPoint);
			}
			else
			{
				lineBreaks[searchedBreakPoint] = 0;
			}
			currentBreakPoint++;
		}
		Arrays.sort (lineBreaks);
		return lineBreaks;
	}
	
	public static String insertLineBreakEveryXCharacters (String textString, String lineBreak, int numberOfCharacters)
	{
		List<String> lines = new ArrayList<> ();
		while (textString.length () > 0)
		{
			int lineLength = 0;
			if (textString.length () < numberOfCharacters)
			{
				lineLength = textString.length ();
			}
			else
			{
				String[] words = textString.split (" ");
				for (int i = 0; i != words.length; i++)
				{
					if (lineLength + words[i].length () + 1 > numberOfCharacters)
					{
						break;
					}
					else
					{
						lineLength += words[i].length () + 1;
					}
				}
			}
			lines.add (textString.substring (0, lineLength));
			textString = textString.substring (lineLength);
		}
		return Joiner.on (lineBreak).join (lines);
	}
	
	public static String joinStringsWithDelimiter (List<String> stringsToJoin, String delimiter)
	{
		return joinStringsWithDelimiter (stringsToJoin, delimiter, "-");
	}
	
	public static String joinStringsWithDelimiter (List<String> stringsToJoin, String delimiter, String nullString)
	{
		String finalText = Joiner.on (delimiter).join (stringsToJoin);
		if (Strings.isNullOrEmpty (finalText))
		{
			finalText = nullString;
		}
		return finalText;
	}
	
	public static char lastCharacter (String string)
	{
		return string.charAt (string.length () - 1);
	}
	
	public static String cutOffLastCharacters (String string, int characterCount)
	{
		return string.substring (0, string.length () - characterCount);
	}
	
	public static boolean isNullOrTrimmedEmpty (String text)
	{
		return text == null || text.trim ().length () == 0;
	}
}
