package kb.api;

import policiesLibrary.monitor.MonitoringPolicy;
import sloContractLibrary.QoSContract;

public interface KBService {

	String getAliveMessage();

	QoSContract[] getAnalyzingPolicies();

	MonitoringPolicy[] getMonitoringPolicies();

	String[] getPlanningPolicies();

	void saveHistory(long start, long end, double diff);

	double[][] getHistorics();
}
