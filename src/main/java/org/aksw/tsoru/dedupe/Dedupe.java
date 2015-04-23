package org.aksw.tsoru.dedupe;

import java.io.IOException;

import org.json.simple.JSONObject;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Dedupe {

	public static final String NAMESPACE = "http://dedupe.aksw.org/";

	public static void main(String[] args) throws IOException {
		
		JSONObject root = Parser.parse("data/04105.json");
		
		RDFConverter.convert(root, NAMESPACE, "data/04105.nt");
		
	}

}
