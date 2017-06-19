/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.buildings.B3866.java
 */
package semanticstore.jsonld.buildings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import semanticstore.jsonld.protocols.Protocol;
import semanticstore.jsonld.util.NS;

public class B3866 extends BuildingsBUItem {

	public B3866(String id, String hasName) 
	{
		super(id, hasName, "B3866");
		this.id = NS.BOC_NS+":" + id;
		this.hasName = hasName;
		this.hasCommunicationMedium((new Protocol("MSTP", "MSTP")).getRefId());
	}


	@JsonldId
	public  String id;

	@JsonInclude(Include.NON_NULL)
	@JsonldProperty(NS.hasName)
	public String hasName;


}
