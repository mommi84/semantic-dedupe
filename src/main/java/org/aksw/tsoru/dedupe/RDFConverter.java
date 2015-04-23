package org.aksw.tsoru.dedupe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class RDFConverter {
	
	private static Logger logger = Logger.getLogger(RDFConverter.class);

	public static void convert(JSONObject root, String namespace, String outpath) throws IOException {

		logger.info("Creating Jena model...");
		
		Model m = ModelFactory.createDefaultModel();
		
		Resource clazz = m.createResource(namespace + "Flat");

		JSONObject hits = (JSONObject) root.get("hits");
		JSONArray hitsArray = (JSONArray) hits.get("hits");

		for (int i = 0; i < hitsArray.size(); i++) {

			JSONObject hit = (JSONObject) hitsArray.get(i);
			JSONObject entry = (JSONObject) ((JSONObject) hit.get("_source"))
					.get("entry");

			String uri = (String) entry.get("portalUrl");
			Resource r = m.createResource(uri, clazz);
			
			Set<?> keys = entry.keySet();
			for(Object key : keys) {
				if(!key.equals("portalUrl")) {
					Property p = ResourceFactory.createProperty(namespace + (String) key);
					Object value = entry.get(key);
					if(value instanceof JSONArray) {
						JSONArray valueArray = (JSONArray) value;
						for(Object elem : valueArray) {
							save(m, r, p, elem);
						}
					} else {
						save(m, r, p, value);
					}
				}
			}

		}
		
		logger.debug("Listing statements...");
		
		StmtIterator it = m.listStatements();
		while(it.hasNext())
			logger.debug(it.next());
		
		logger.info("Writing model...");

		BufferedWriter out = new BufferedWriter(new FileWriter(outpath));
		m.write(out, "N-TRIPLES");
		
		logger.info("Write finished.");
		
	}

	private static void save(Model m, Resource r, Property key, Object elem) {
				
		if(elem instanceof String)
			m.add(r, key, (String) elem);
		if(elem instanceof Double)
			m.addLiteral(r, key, ((Double) elem).doubleValue());
		if(elem instanceof Boolean)
			m.addLiteral(r, key, ((Boolean) elem).booleanValue());
		
	}

}
