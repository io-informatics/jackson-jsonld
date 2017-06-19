/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.util.jsonld.Topology.java
 */
package semanticstore.jsonld.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldLink(name = "owl", rel = "http://www.w3.org/2002/07/owl#")
@JsonldLink(name = "rdf", rel = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
@JsonldLink(name = "rdfs", rel = "http://www.w3.org/2000/01/rdf-schema#")
@JsonldLink(name = "xsd", rel = "http://www.w3.org/2001/XMLSchema#")
@JsonldLink(name = NS.PROTOCOL_NS, rel = NS.PROTOCOL_URL)
@JsonldLink(name = NS.BOC_NS, rel = NS.BOC_URL)
@JsonldType("owl:ontology")
public class Topology {

	@JsonldId
	String id;

	@JsonProperty("owl:imports")
	List<RefId> imports;

	@JsonProperty("owl:versionIRI")
	public RefId versionIRI;

	public Topology(String id)
	{
		this.id = id;
		imports = new ArrayList<>();
	}

	public void addImport(String ontologyURL)
	{
		imports.add(new RefId(ontologyURL));
	}


	/*
	{
		"@id": "http://www.schneider-electric.com/Clients/BoC",
		"@type": "owl:Ontology",
		"owl:imports": [{
			"@id": "http://www.schneider-electric.com/global/CoreSE_Location"
		},
		{
			"@id": "http://www.schneider-electric.com/builidings/BDevices/0.0.1"
		}],
		"owl:versionIRI": {
			"@id": "http://www.schneider-electric.com/Clients/BoC/0.01"
		}
	}*/

}
