//@author A0088617R
package logic;
 
import java.util.Vector;

public class LogicSplitterSearch extends LogicSplitter{

	protected static Vector<String> splitInputSearch(String userInput, Vector<String> parameterList) throws Exception {
		int[] reminderFound = { 0 };
		int[] timeCount = { 0 };
		Vector<Integer> timeIndexes = new Vector<Integer>();
		Exception exception=new Exception();
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
		return parameterList;
		
	}

}
