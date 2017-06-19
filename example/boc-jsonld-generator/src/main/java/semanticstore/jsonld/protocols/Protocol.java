/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.protocols.Protocol.java
 */
package semanticstore.jsonld.protocols;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import semanticstore.jsonld.util.NS;
import semanticstore.jsonld.util.RefId;

public class Protocol {

	@JsonldId
	String id;
	
	@JsonInclude(Include.NON_NULL)
	@JsonldProperty(NS.hasName)
	String hasName;
	
	public Protocol(String id, String hasName)
	{
		this.id = NS.PROTOCOL_NS+":" + id;
		this.hasName = hasName;
	}
	
	@JsonIgnore
	public RefId getRefId()
	{
		return new RefId(id);
	}
	
}
