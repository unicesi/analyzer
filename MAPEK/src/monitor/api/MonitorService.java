package monitor.api;

import contextLibrary.IContextModel;

public interface MonitorService {

	/**
	 * This method exposes the service to feed the context
	 * that has been sensed by the Sensor component
	 * @param contextEntitySensed
	 */
	void feedSensedContext(IContextModel[] contextEntitySensed);
	
	/**
	 * CODIGO TESIS
	 * Metodo Pull de iniciar el ciclo, el monitor obtiene la informacion sensada
	 */
	void getSensedContext();
	
	String getAliveMessage();
}
