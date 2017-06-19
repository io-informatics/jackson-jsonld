/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.timeseries.TimeSeries.java
 */
package semanticstore.jsonld.timeseries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import semanticstore.jsonld.util.NS;
import semanticstore.jsonld.util.RefId;

@JsonldLink(name = NS.EWS_NS, rel = NS.EWS_URL)
@JsonldType(NS.timeseries)
public class TimeSeries {

	@JsonldId
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonldProperty(NS.hasName)
	String hasName;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(NS.hasType)
	RefId hasType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(NS.hasUnit)
	RefId hasUnit;

	
	public TimeSeries(String id, String hasName, RefId hasType, RefId hasUnit) {
		super();
		this.id = NS.BOC_NS+":"+id;
		this.hasName = hasName;
		this.hasType = hasType;
		this.hasUnit = hasUnit;
	}

	@JsonIgnore
	public RefId getRefId()
	{
		return new RefId(id);
	}

}
