/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.location.Room.java
 */
package semanticstore.jsonld.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import semanticstore.jsonld.util.NS;

@JsonldType(NS.room)
public class Room extends Location {

	public Room(String id, String hasName) 
	{
		super(id, hasName);
		this.id = NS.BOC_NS+":" + id;
		this.hasName = hasName;
	}


	@JsonldId
	public  String id;

	@JsonInclude(Include.NON_NULL)
	@JsonldProperty(NS.hasName_loc)
	public String hasName;


}
