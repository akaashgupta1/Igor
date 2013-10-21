//@author A0088617R
package logic;
 
import java.util.Vector;

public class LogicSplitterDone extends LogicSplitter {

	protected static Vector<String> splitInputDone(String userInput,
			Vector<String> parameterList) {
		extractKeywordsAlongWithHashTags(userInput, parameterList);
		return parameterList;
	}

}
 