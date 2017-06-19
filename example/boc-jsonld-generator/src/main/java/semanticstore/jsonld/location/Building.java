/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.location.Building.java
 */
package semanticstore.jsonld.location;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import semanticstore.jsonld.util.NS;
import semanticstore.jsonld.util.RefId;

@JsonldType(NS.building)
public class Building extends Location {

	
	public Building(String id, String hasName) {
		super(id, hasName);
		this.id = NS.BOC_NS+":" + id;
		this.hasName = hasName;
		
		hasLocation_Floor = new ArrayList<>();
		hasLocation_Room = new ArrayList<>();
	}
	

	@JsonldId
	public String id;

	@JsonInclude(Include.NON_NULL)
	@JsonldProperty(NS.hasName_loc)
	public String hasName;

	@JsonInclude(Include.NON_EMPTY)
	@JsonldProperty(NS.hasRoom)
	public List<RefId> hasLocation_Room;

	@JsonInclude(Include.NON_EMPTY)
	@JsonldProperty(NS.hasFloor)
	public List<RefId> hasLocation_Floor;
}
