//@author A0088617R
package logic;
 
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicSplitterUpdate extends LogicSplitter{

	protected static Vector<String> splitInputUpdate(String userInput, Vector<String> parameterList) throws Exception {
		int[] reminderFound = { 0 };
		int[] timeCount = { 0 };
		int index=-1;
		Vector<Integer> timeIndexes = new Vector<Integer>();
		Exception exception = new Exception();
		index= getFirstNumber(userInput.trim());
		if(index==-1)
		{
			message=18;
			throw exception;
		}
		else
		{
			String formattedIndex=String.format("%d",index);
			userInput=userInput.substring(userInput.indexOf(formattedIndex)+formattedIndex.length());
		}
		try {
			userInput = extractTimeFieldsAndUpdateInputString(
					reminderFound, userInput, parameterList, timeIndexes,
					timeCount);
		} catch (Exception e) {
			message = 13;
			throw exception;
		}
		try { 
			extractKeywordsAlongWithHashTags(userInput, parameterList);
		} catch (Exception e) {
			message = 14;
			throw exception;
		}
		try {
			shiftKeywordsToSecondIndex(parameterList);
		} catch (Exception e) {
			message = 16;
			throw exception;
		}
		try {
			processEndStartTime(userInput, parameterList, timeIndexes);
		} catch (Exception e) {
			message = 13;
			throw exception;
		}
		for(int i=parameterList.size();i<5;i++)
		{
			parameterList.add("-1");
		}
		parameterList.add(String.format("%d",index));
		return parameterList;
	}
	
	public static int getFirstNumber(String input) {
		Pattern pat = Pattern.compile("(^[0-9]{1,} )");
		Matcher mat = pat.matcher(input);
		if(mat.find()) {
			int posStart = mat.start();
			int posEnd = mat.end()-1;
			int firstNumber = Integer.parseInt(input.substring(posStart, posEnd));
			return firstNumber;
		}
		return -1;
	}
}
