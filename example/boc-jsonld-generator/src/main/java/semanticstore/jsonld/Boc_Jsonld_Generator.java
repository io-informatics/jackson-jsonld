/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.Boc_Jsonld_Generator.java
 */
package semanticstore.jsonld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ioinformarics.oss.jackson.module.jsonld.JsonldGraph;
import ioinformarics.oss.jackson.module.jsonld.JsonldGraphBuilder;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;

public class Boc_Jsonld_Generator {

	public static List ontElements = new ArrayList();
	
	private static void populateTopology()
	{
		Topology_Instantiator.init();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String boc = new File(".").getAbsolutePath() + "/src/main/resources/boc.json";
		File jsonLDFile = new File(boc);
		OutputStream outputStream = new FileOutputStream(jsonLDFile);
		JsonldModule jsonldModule = new JsonldModule();
		ObjectMapper objectMapper = new ObjectMapper();
		
		 JsonldGraphBuilder builder =  JsonldGraph.Builder.create();
	        try {
	        	
	        	
	        	objectMapper.registerModule(jsonldModule);
	        	populateTopology();
				objectMapper.writer().writeValue(outputStream, builder.build(ontElements));
				
				
				
				
				
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
