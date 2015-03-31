package org.aksw.tsoru.dedupe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author Tommaso Soru <t.soru@informatik.uni-leipzig.de>
 *
 */
public class Parser {

	public static JSONObject parse(String f) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(f));
		String jsonstring = "";
		while(in.hasNextLine()) {
			jsonstring += in.nextLine();
		}
		JSONObject root = (JSONObject) JSONValue.parse(jsonstring);
		in.close();
		
		return root;
		
	}
	

}
