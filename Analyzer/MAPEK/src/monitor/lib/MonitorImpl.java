package monitor.lib;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pascani.lang.Event;
import pascani.lang.events.TimeLapseEvent;
import pascani.lang.util.EventFilter;
import policiesLibrary.monitor.MonitoringPolicy;
import monitor.api.*;
import contextLibrary.*;

/**
 * Component: Monitor Class : MonitorImpl
 * 
 * @author lcastane[at]icesi.edu.co Date: (07/2011)
 * @version 3.0
 * 
 */

public class MonitorImpl extends AMonitor {

	protected int throughput;

	/*
	 * Constructor
	 */
	public MonitorImpl() {

		contextDatas = new ArrayList<ContextData>();
		sensedContextData = null;
		information = new ArrayList<Information>();
		throughput = 0;
		System.out.println("[Monitor Service Started]");
	}

	/**
	 * CODIGO TESIS
	 */
	protected void processEvents(List<Event<?>> events) {

		List<TimeLapseEvent> tlEvents = new EventFilter(events)
				.filter(TimeLapseEvent.class);

		throughput = tlEvents.size();

		if (throughput > 0) {

			buildContextData(tlEvents);
		}		
	}

	/**
	 * CODIGO TESIS
	 * 
	 * @param events
	 */
	private void buildContextData(List<TimeLapseEvent> events) {

		for (int i = 0; i < events.size(); i++) {

			double value = events.get(i).value();
			
			long start = events.get(i).start();
			
			long end = events.get(i).end();

			kbService.saveHistory(start, end, value);

		}

		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdfH = new SimpleDateFormat("H");
		SimpleDateFormat sdfM = new SimpleDateFormat("m");
		SimpleDateFormat sdfS = new SimpleDateFormat("s");

		// FORMATO HHmmss para guardar en BD la estampa de tiempo de una
		// diferencia
		String horaString = sdfH.format(now.getTime())
				+ (sdfM.format(now.getTime()).length() == 1 ? "0" : "")
				+ sdfM.format(now.getTime())
				+ (sdfS.format(now.getTime()).length() == 1 ? "0" : "")
				+ sdfS.format(now.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String fechaString = sdf.format(now.getTime());

		ContextEntity entityDay = new ContextEntity("DiaMes",
				ContextEntity.TYPE_INFO);
		ContextProperty propDay = new ContextProperty(
				ContextProperty.TYPE_INTEGER, "DiaMes",
				Integer.parseInt(fechaString));
		entityDay.addProperty(propDay);
		ContextData data1 = new ContextData(entityDay,
				ContextData.TYPE_INFO_STRING_MSG, propDay);
		contextDatas.add(data1);
		System.out.println("[Sensor Service] - Context Data "
				+ data1.getEntity().getName() + "value: "
				+ data1.getObservableProperty().getValue());

		/*
		 * Data about Tx/min
		 */
		ContextEntity entityTxMin = new ContextEntity("Tx/min",
				ContextEntity.TYPE_INFO);
		ContextProperty propTxMin = new ContextProperty(
				ContextProperty.TYPE_TIME_SECONDS, "Tx/Min", throughput);
		entityTxMin.addProperty(propTxMin);
		ContextData data2 = new ContextData(entityTxMin,
				ContextData.TYPE_EVENT_OCURRENCE, propTxMin);
		contextDatas.add(data2);

		System.out.println("[Sensor Service] - Context Data "
				+ data2.getEntity().getName() + ", value: "
				+ data2.getObservableProperty().getValue());
		System.out.println("[Sensor Service] - Resume: Total Events="
				+ throughput);
	}

	protected String searchInMonitoringPolicies(String contextEntityName) {
		/*
		 * [lcastane 21/03/2012] Future extension of this method: The same
		 * context entity might be required for more than one qosContract,
		 * therefore this method should return all the monitoring policies names
		 * where this context entity is found.
		 */
		System.out
				.println("[Monitor Service] - Searching context in monitoring policies");
		// System.out.println("Searching: " + contextEntityName);

		// 1. iterates over the monitoring policies set
		for (int i = 0; i < monitoringPolicies.length; i++) {
			MonitoringPolicy policy = monitoringPolicies[i];
			ArrayList<String> observations = policy.getMonitoringContext()
					.getObservations();

			// 2. iterates over the observation set of the policy
			for (int j = 0; j < observations.size(); j++) {
				// System.out.println("Comparing with: " + observations.get(j));

				// 3. Searches the entity name in the set of observations and
				// returns the name of the policy if found
				if (observations.get(j).equalsIgnoreCase(contextEntityName)) {
					return policy.getName();
				}
			}
		}
		return "NA";

	}

}
