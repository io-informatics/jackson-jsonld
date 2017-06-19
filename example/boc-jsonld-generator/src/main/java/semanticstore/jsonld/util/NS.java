/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.util.jsonld.NS.java
 */
package semanticstore.jsonld.util;

public class NS {

	public final static String BOC_NS = "boc"; 
	public final static String BOC_URL = "http://www.myontology.com/Clients/BoC#"; 
	public static String BOC_VERSION = "http://www.myontology.com/Clients/BoC/0.0.1" ;

	public final static String LOC_NS = "loc"; 
	public final static String LOC_URL = "http://www.myontology.com/global/CoreSE_Location#"; 

	public final static String BDEVICES_NS = "buildingBU"; 
	public final static String BDEVICES_URL = "http://www.myontology.com/builidings/BDevices#"; 

	public final static String QT_NS = "qt"; 
	public final static String QT_URL = "http://www.myontology.com/global/CoreSE_Quantity#"; 

	public final static String EWS_NS = "ews"; 
	public final static String EWS_URL = "http://www.myontology.com/global/EWS#";

	public final static String PROTOCOL_NS = "prot"; 
	public final static String PROTOCOL_URL = "http://www.myontology.com/global/Protocols#";

	//QT
	public final static String hasPhysicalLocation = NS.QT_NS+":hasPhysicalLocation";
	public final static String hasName = NS.QT_NS+":hasName";
	public final static String hasType = NS.QT_NS+":hasType";
	public static final String hasUnit = NS.QT_NS+":hasUnit";
	public final static String hasMonitoringLocation = NS.QT_NS+":hasMonitoringLocation";
	public final static String controls = NS.QT_NS+":controls";
	public final static String monitors = NS.QT_NS+":monitors";
	public final static String connectsTo = NS.QT_NS+":connectsTo";
	public final static String hasTimeSeries = NS.QT_NS+":hasTimeSeries";

	//Location
	public final static String site = NS.LOC_NS+":Site";
	public final static String building = NS.LOC_NS+":Building";
	public final static String floor = NS.LOC_NS+":Floor";
	public final static String room = NS.LOC_NS+":Room";

	public final static String hasName_loc = NS.LOC_NS+":hasName";
	public final static String hasLocation = NS.LOC_NS+":hasLocation";
	public final static String hasSite = NS.LOC_NS+":hasLocation_Site";
	public final static String hasBuilding = NS.LOC_NS+":hasLocation_Building";
	public final static String hasFloor = NS.LOC_NS+":hasLocation_Floor";
	public final static String hasRoom = NS.LOC_NS+":hasLocation_Room";

	//Protocol
	public final static String hasCommunicationMedium = NS.PROTOCOL_NS+":hasCommunicationMedium";

	//TimeSeries
	public final static String timeseries = NS.QT_NS+":TimeSeries";
}
