//@author A0088617R
package Test; 
import static org.junit.Assert.*;
import logic.LogicAnalyzer;
import logic.PatternLib;

import org.junit.Before;
import org.junit.Test;
public class TestAnalyzer {
	private static String[] newparameterList={"add", "abcd #ardcrd#high", "2012-11-07T17:00:00.000+08:00", "2012-11-07T18:00:00.000+08:00", "r-1min", "-1"};
	private static String[] newparameterList1={"add"," abcdef #a#b#c", "5:00pm tmr", "r-1min", "-1", "-1"};
	private static String[] newparameterList2={"add", "abcdef #high #low", "2012-11-13T17:00:00.000+08:00", "2012-11-15T18:00:00.000+08:00", "r-1min", "-1"};
	private static String[] newparameterList3={"add","abcdasfaef #a#b#c #high", "2012-11-11T17:00:00.000+08:00","2012-11-12T18:00:00.000+08:00", "r-1min", "-1"};
	private static String[] newparameterList4={"add", "fenbojaefnsfnmgpwkr #low", "2012-11-10T17:30:00.000+08:00", "2012-11-11T21:00:00.000+08:00", "r-1min", "-1"};
	private static String[] newparameterList5={"add", "fenbojaefnsfnmgpwkr #low", "2012-12-12T17:00:00.000+08:00", "2012-11-11T21:00:00.000+08:00", "r-2hr", "-1"};
	private static String[] newparameterList6={"add", "fenbojamgpwkr #low", "2012-12-12T17:00:00.000+08:00", "2012-11-11T21:00:00.000+08:00", "-1","-1"};
	private static String[] newparameterList7={"add", "fenbojamgpwkr #low", "r-1min", "2012-12-12T17:00:00.000+08:00", "2012-11-11T21:00:00.000+08:00","-1"};
	private static String[] newparameterList8={"add", "jet bijaer n gwajngomn egtaejn ","21:00","-1","-1","-1"};
	private static String[] newparameterList9={"add", "fhebi", "-1", "-1", "-1", "-1"};
	private static String[] newparameterList10={"add", " ", "5:00pm tmr", "r-1min", "-1", "-1"};
	private static String[] newparameterList11={"update", " ", "5:00pm tmr", "r-1min", "-1", "5"};
	private static String[] newparameterList12={"update", "2", "-1", "-1", "-1", "-1"};
	private static String[] newparameterList13={"search", "abcd #ghi #klm", "-1", "-1", "-1", "-1"};
	private static String[] newparameterList14={"delete", "99", "-1", "-1", "-1", "-1"};
	private static String[] newparameterList15={"owrjngjowrn","-1", "-1", "-1", "-1", "-1"};
	private static String[] newparameterList16={"delete", "adfae", "-1", "-1", "-1", "-1"};
	@Before
	public void setup()
	{
		PatternLib.getInstance();
		LogicAnalyzer.setUp();
	}
	@Test
	public void testGetEventString1() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","abcd..HIGH..#ardcrd..false..16:59 07/11/2012..17:00 07/11/2012..18:00 07/11/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList).substring(15));
	}
	@Test
	public void testGetEventString2() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","abcdef..NORMAL..#a#b#c..false..16:59 11/11/2012..17:00 11/11/2012..invalid..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList1).substring(15));
	}
	@Test
	public void testGetEventString3() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","abcdef..HIGH..#low..false..16:59 13/11/2012..17:00 13/11/2012..18:00 15/11/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList2).substring(15));
	}
	@Test
	public void testGetEventString4() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","abcdasfaef..HIGH..#a#b#c..false..16:59 11/11/2012..17:00 11/11/2012..18:00 12/11/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList3).substring(15));
	}
	@Test
	public void testGetEventString5() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","fenbojaefnsfnmgpwkr..LOW....false..17:29 10/11/2012..17:30 10/11/2012..21:00 11/11/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList4).substring(15));
	}
	@Test
	public void testGetEventString6() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","fenbojaefnsfnmgpwkr..LOW....false..19:00 11/11/2012..21:00 11/11/2012..17:00 12/12/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList5).substring(15));
	}
	@Test
	public void testGetEventString7() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","fenbojamgpwkr..LOW....false..invalid..21:00 11/11/2012..17:00 12/12/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList6).substring(15));
	}
	@Test
	public void testGetEventString8() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","fenbojamgpwkr..LOW....false..20:59 11/11/2012..21:00 11/11/2012..17:00 12/12/2012..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList7).substring(15));
	}
	@Test
	public void testGetEventString9() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","jet bijaer n gwajngomn egtaejn..NORMAL....false..invalid..21:00 10/11/2012..invalid..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList8).substring(15));
	}
	@Test
	public void testGetEventString10() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","fhebi..NORMAL....false..invalid..invalid..invalid..invalid",LogicAnalyzer.getAddUpdateEventString(newparameterList9).substring(15));
	}
	@Test
	public void testGetEventString11() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","",LogicAnalyzer.getAddUpdateEventString(newparameterList10));
	}
	@Test
	public void testGetEventString12() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","",LogicAnalyzer.getAddUpdateEventString(newparameterList11));
	}
	
	@Test
	public void testGetHashTags() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","[]",LogicAnalyzer.getHashTags(newparameterList11).toString());
	}
	
	@Test
	public void testGetHashTags1() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","[ardcrd, high]",LogicAnalyzer.getHashTags(newparameterList).toString());
	}
	
	@Test
	public void testGetHashTags2() {
		LogicAnalyzer.setUp();
		assertEquals("testGetEventString","[a, b, c]",LogicAnalyzer.getHashTags(newparameterList1).toString());
	}
	@Test
	public void testGetInteger() {
		LogicAnalyzer.setUp();
		assertEquals("testGetInteger",Integer.parseInt("2"),LogicAnalyzer.getInteger(newparameterList12));
	}
	
	@Test
	public void testGetInteger2() {
		LogicAnalyzer.setUp();
		assertEquals("testGetInteger",Integer.parseInt("99"),LogicAnalyzer.getInteger(newparameterList14));
	}
	
	@Test
	public void testGetInteger3() {
		LogicAnalyzer.setUp();
		assertEquals("testGetInteger",Integer.parseInt("-1"),LogicAnalyzer.getInteger(newparameterList16));
	}
}
