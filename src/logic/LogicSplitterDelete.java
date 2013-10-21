//@author A0088617R
package logic;
 
import java.util.Vector;

public class LogicSplitterDelete extends LogicSplitter{

	protected static Vector<String> splitInputDelete(String userInput,
			Vector<String> parameterList) {
		extractKeywordsAlongWithHashTags(userInput, parameterList);
		return parameterList;
	}

}
 