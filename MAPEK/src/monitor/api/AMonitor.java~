package monitor.api;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import kb.api.KBService;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import pascani.lang.Event;
import pascani.lang.events.TimeLapseEvent;
import pascani.lang.infrastructure.ProbeProxy;
import pascani.lang.util.ServiceManager;
import policiesLibrary.monitor.MonitoringPolicy;
import analyzer.api.AnalyzerService;
import contextLibrary.ContextData;
import contextLibrary.ContextEntity;
import contextLibrary.IContextModel;
import contextLibrary.Information;

@Service(MonitorService.class)
public abstract class AMonitor implements MonitorService {

	/*
	 * Attributes
	 */
	protected ArrayList<ContextData> contextDatas;
	protected MonitoringPolicy[] monitoringPolicies;
	protected ContextData[] sensedContextData;
	protected ArrayList<Information> information;
	protected static ProbeProxy PROBE;
	static{
	try {
		System.setProperty("pascani.uri","amqp://guest:guest@172.17.0.2:5672");

		PROBE = new ProbeProxy("ProbeTiempo");
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	/*
	 * References to other components in the MAPE loop
	 */
	protected KBService kbService;
	protected AnalyzerService analyzerService;

	@Reference
	public void setKbService(KBService kbService) {
		this.kbService = kbService;
	}

	@Reference
	public void setAnalyzerService(AnalyzerService analyzerService) {
		this.analyzerService = analyzerService;
	}
	
	public void getSensedContext(){

		List<Event<?>> events = PROBE.fetchAndClean(-1, System.nanoTime());
//		List<Event<?>> events = PROBE.fetch(-1, System.nanoTime());
		
		
		//EJEMPLO PRO
//		List<Event<?>> events =  new ArrayList<Event<?>>();
//		
//		TimeLapseEvent e1 = new TimeLapseEvent(UUID.randomUUID(), end-1000000000, end);
//		TimeLapseEvent e2 = new TimeLapseEvent(UUID.randomUUID(), end-2000000000, end);
//		TimeLapseEvent e3 = new TimeLapseEvent(UUID.randomUUID(), end-300000000, end+10000);
//		events.add(e1);events.add(e2);events.add(e3);
		processEvents(events);
		
		sensedContextData = new ContextData[contextDatas.size()];
		if (contextDatas != null && contextDatas.size() > 0) {
			for (int i = 0; i < contextDatas.size(); i++) {
				sensedContextData[i] = contextDatas.get(i);
			}
		}
		
		startMonitoring();
		
		if (information != null && information.size() > 0) {
			// invokes the analyzer component to analyze these information
//			try {
				//TODO
//				if (analyzerService == null) {
//					analyzerService = (AnalyzerService) Naming
//							.lookup("//localhost:1101/Analyzer");
//				}
				
				System.out
						.println("[Monitor Service] - Testing Analyzer component connection: "
								+ analyzerService.getAliveMessage());

				Information[] monitoringInformation = new Information[information
						.size()];
				if (information != null && information.size() > 0) {
					for (int i = 0; i < information.size(); i++) {
						monitoringInformation[i] = information.get(i);
					}
				}

				// Information is sent to the Analyzer
				analyzerService
						.reportContextInformation(monitoringInformation);
				System.out
						.println("[Monitor Service] - Information sent to be analyzer by the Analyzer component");
				
	//		} catch (MalformedURLException e) {
	//			System.err
	//					.println("[Monitor Service] - (MalformedURLException)");
	//			// e.printStackTrace();
	//		} catch (RemoteException e) {
	//			System.err
	//					.println("[Monitor Service] - Analyzer component connection failed (RemoteException)");
	//			// e.printStackTrace();
	//		} catch (NotBoundException e) {
	//			System.err
	//					.println("[Monitor Service] - (NotBoundException)");
	//			// e.printStackTrace();
	//		}
		}
	}
	
	protected abstract void processEvents(List<Event<?>> events);

	/**
	 * STANDBY
	 * The Sensor component invokes this method to initiate the monitoring
	 * process
	 * */
	@Override
	public void feedSensedContext(IContextModel[] contextDataSensed) {
		/*
		 * Info: An external sensor component invoked this service after
		 * gathering the context changes into ContextData objects.
		 */

		System.out.println("[Monitor Service] - Context has been sensed,"
				+ contextDataSensed.length);
		sensedContextData = (ContextData[]) contextDataSensed;
		if (sensedContextData == null) {
			System.out.println("No context to be sensed");
		} else {
			/*
			 * Step 1: Starts to monitor the context Data sent by the Sensor
			 * component
			 */
			startMonitoring();

			/*
			 * Step 4: Connects to the analyzer component and if there is information
			 * to be analyzed the component is invoked 
			 */
			if (information != null && information.size() > 0) {
				// invokes the analyzer component to analyze these information
//				try {
					//TODO
//					if (analyzerService == null) {
//						analyzerService = (AnalyzerService) Naming
//								.lookup("//localhost:1101/Analyzer");
//					}
					
					System.out
							.println("[Monitor Service] - Testing Analyzer component connection: "
									+ analyzerService.getAliveMessage());

					Information[] monitoringInformation = new Information[information
							.size()];
					if (information != null && information.size() > 0) {
						for (int i = 0; i < information.size(); i++) {
							monitoringInformation[i] = information.get(i);
						}
					}

					// Information is sent to the Analyzer
					analyzerService
							.reportContextInformation(monitoringInformation);
					System.out
							.println("[Monitor Service] - Information sent to be analyzer by the Analyzer component");
					
					// Time Stamp
					Calendar calendarTimeStamp = Calendar.getInstance();
					//System.out.println("Monitor ended at: " + calendarTimeStamp.getTimeInMillis());
					
//				} catch (MalformedURLException e) {
//					System.err
//							.println("[Monitor Service] - (MalformedURLException)");
//					// e.printStackTrace();
//				} catch (RemoteException e) {
//					System.err
//							.println("[Monitor Service] - Analyzer component connection failed (RemoteException)");
//					// e.printStackTrace();
//				} catch (NotBoundException e) {
//					System.err
//							.println("[Monitor Service] - (NotBoundException)");
//					// e.printStackTrace();
//				}
			} else
				System.out
						.println("[Monitor Service] - No information has been gathered");
		}

	}

	/**
	 * Performs the monitoring actions
	 */
	@SuppressWarnings("unchecked")
	private void startMonitoring() {
		System.out.println("[Monitor Service] - Starting monitoring");
		/*
		 * Step 2: Connects to the Knowledge Base component to gather the
		 * monitoring policies
		 */
		loadMonitoringPolicies();
		/*
		 * Step 3: Builds Information objects with the data.
		 */
		buildInformation();
	}

	/**
	 * This method connects the Monitor component to the KB component to load
	 * the monitoring policies as MonitoringPolicy objects
	 */
	private void loadMonitoringPolicies() {
//		try {
			//TODO
//			if (kbService == null) {
//				kbService = (KBService) Naming.lookup("//localhost:1103/KB");
//			}
			
			System.out
					.println("[Monitor Service] - Testing KB component connection: "
							+ kbService.getAliveMessage());
			// 1. Invokes and loads the monitoring policies from the kb component
			monitoringPolicies = kbService.getMonitoringPolicies();
			
			System.out.println("[Monitor Service] - "+ monitoringPolicies.length+ " Monitoring policies loaded from the KB component");

//		} catch (MalformedURLException e) {
//			System.err.println("[Monitor Service] - (MalformedURLException)");
//			// e.printStackTrace();
//		} catch (RemoteException e) {
//			System.err
//					.println("[Monitor Service] - KB component connection failed (RemoteException)");
//			// e.printStackTrace();
//		} catch (NotBoundException e) {
//			System.err.println("[Monitor Service] - (NotBoundException)");
//			// e.printStackTrace();
//		}catch (Exception e){
//			System.out.println("[Monitor Service Error]");
//			e.printStackTrace();
//		}

	}

	/**
	 * This method builds Information objects by matching the context data
	 * sensed and the monitoring policies. This Information objects will be send
	 * to the Analyzer component <b>pre:</b> sensedContextData != null,
	 * monitoringPolicies!= null
	 */
	private void buildInformation() {
		System.out.println("[Monitor Service] - Building information");
		for (int i = 0; i < sensedContextData.length; i++) {
			ContextData data = sensedContextData[i];
			ContextEntity entity = data.getEntity();
			System.out.println("[Monitor Service] - Sensed ContexData No."
					+ (i + 1) + " Entity:" + entity.getName() + " Properties:"
					+ entity.getPropertiesSize());
			// 1. Search for sensed context over the monitoring policies
			String qosPorperty = searchInMonitoringPolicies(entity.getName());
			// 2. If matches it builds information about it: ContextData +
			// MonitoringPolicy.Name
			if (!qosPorperty.equals("NA")) {
				information.add(new Information(Information.TYPE_INFO_INT,
						qosPorperty, entity));
			}

		}
	}

	/**
	 * This method searches the contextData names in the monitoring policies and
	 * returns the name of the policy where it was found.
	 * @param contextEntityName
	 * @return the name of the policy: qosContract ID + QoS Property (example:
	 *         "C3-Throughput") or "NA" if not found
	 */
	protected abstract String searchInMonitoringPolicies(String contextEntityName) ;
	
	
	@Override
	public String getAliveMessage() {
		return "A Monitor Service is Alive!";
	}

}
