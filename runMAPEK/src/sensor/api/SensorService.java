package sensor.api;

import contextLibrary.IContextModel;

public interface SensorService {

	/**
	 * This method is invoked by the Monitor component to gather any changes in
	 * the context
	 * 
	 * @return IContexModel object - ContextData implementation
	 */
	IContextModel[] getSensedContext();

	
	/**
	 * This method is invoked by any other application to start the MAPE loop
	 */
	void startSensing();
	
	/**
	 * This is the standard method for any Interface
	 * @return String message
	 */
	String getAliveMessage();

}
