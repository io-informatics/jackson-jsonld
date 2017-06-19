/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.timeseries.EWSType.java
 */
package semanticstore.jsonld.timeseries;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import semanticstore.jsonld.util.NS;
import semanticstore.jsonld.util.RefId;

public class EWSType {

	@JsonldId
	String id;

	public EWSType(String id) 
	{
		this.id = NS.EWS_NS+":" + id;
	}
	
	@JsonIgnore
	public RefId getRefId()
	{
		return new RefId(id);
	}
	
}
