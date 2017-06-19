/**
 * @author Charbull
 * Jan 3, 2016 
 * semanticstore.jsonld.Topology_Instantiator.java
 */
package semanticstore.jsonld;

import semanticstore.jsonld.buildings.B3866;
import semanticstore.jsonld.buildings.BuildingsBUItem;
import semanticstore.jsonld.location.Building;
import semanticstore.jsonld.location.Floor;
import semanticstore.jsonld.location.Room;
import semanticstore.jsonld.location.Site;
import semanticstore.jsonld.timeseries.EWSType;
import semanticstore.jsonld.timeseries.TimeSeries;
import semanticstore.jsonld.util.NS;
import semanticstore.jsonld.util.RefId;
import semanticstore.jsonld.util.Topology;

public class Topology_Instantiator {

	public static void init()
	{
		//Initiate the Context
		Topology topology = new Topology(NS.BOC_URL);
		topology.addImport(NS.LOC_URL);
		topology.addImport(NS.BDEVICES_URL);
		topology.addImport(NS.QT_URL);
		topology.addImport(NS.EWS_URL);
		topology.addImport(NS.PROTOCOL_URL);
		topology.versionIRI = new RefId(NS.BOC_VERSION);

		Site BoC = new Site("BOC", "Boston One Campus");
		Building BA = new Building("BuildingA", "Building A (North)");

		Floor F1 = new Floor("F1", "Floor 1");
		Floor F2 = new Floor("F2", "Floor 2");
		Floor F3 = new Floor("F3", "Floor 3");

		Room R123 = new Room("R123", "Room 123");
		Room R456 = new Room("R456", "Room 456");

		BoC.hasLocation_Building = BA.getRefId();
		BA.hasLocation_Floor.add(F1.getRefId());
		BA.hasLocation_Floor.add(F2.getRefId());
		BA.hasLocation_Floor.add(F3.getRefId());
		F1.hasLocation_Room.add(R123.getRefId());
		F1.hasLocation_Room.add(R456.getRefId());


		B3866 hp_1_1_18 = new B3866("HP-1_1.18", "HP-1_1.18");
		hp_1_1_18.hasPhysicalLocation(R123.getRefId());
		hp_1_1_18.hasMonitoringLocation(R456.getRefId());
		TimeSeries ts_6549 = new TimeSeries("6549-fsdf-fsdfs13", null, new RefId(NS.EWS_NS+":Temperature"), new RefId(NS.QT_NS+":�F"));
		hp_1_1_18.hasTimeSeries(ts_6549.getRefId());

		BuildingsBUItem enterpriseServer = new BuildingsBUItem("server_1", "EServer", "EnterpriseServer");
		BuildingsBUItem as = new BuildingsBUItem("AS_1A1", "Automation Server of 1A1", "AutomationServer");
		BuildingsBUItem heatPump = new BuildingsBUItem("HP_1_1", "HeatPump of HP-1_1.18", "HeatPump");
		hp_1_1_18.controls(heatPump.getRefId());
		
		enterpriseServer.connectsTo(as.getRefId());
		as.monitors(hp_1_1_18.getRefId());


		Boc_Jsonld_Generator.ontElements.add(topology);
		Boc_Jsonld_Generator.ontElements.add(BoC);
		Boc_Jsonld_Generator.ontElements.add(BA);
		Boc_Jsonld_Generator.ontElements.add(F1);
		Boc_Jsonld_Generator.ontElements.add(F2);
		Boc_Jsonld_Generator.ontElements.add(F3);
		Boc_Jsonld_Generator.ontElements.add(R123);
		Boc_Jsonld_Generator.ontElements.add(R456);
		Boc_Jsonld_Generator.ontElements.add(hp_1_1_18);
		Boc_Jsonld_Generator.ontElements.add(heatPump);
		Boc_Jsonld_Generator.ontElements.add(enterpriseServer);
		Boc_Jsonld_Generator.ontElements.add(as);
		Boc_Jsonld_Generator.ontElements.add(ts_6549);
	}
}
