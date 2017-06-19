/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.location.Location.java
 */
package semanticstore.jsonld.location;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import semanticstore.jsonld.util.NS;
import semanticstore.jsonld.util.RefId;

@JsonldLink(name = NS.LOC_NS, rel = NS.LOC_URL)
public abstract class Location {

	public Location(String id, String hasName)
	{
		this.id = NS.BOC_NS+":" + id;
		this.hasName = hasName;
		
		hasLocation = new ArrayList<RefId>();
	}
	
	@JsonldId
	public  String id;
	
	@JsonInclude(Include.NON_EMPTY)
	@JsonldProperty(NS.hasLocation)
	public List<RefId> hasLocation;
	
	@JsonldProperty(NS.hasName_loc)
	@JsonInclude(Include.NON_NULL)
	public String hasName;
	
	@JsonIgnore
	public RefId getRefId()
	{
		return new RefId(id);
	}
	
	public void addLocation(RefId refId)
	{
		hasLocation.add(refId);
	}

}

