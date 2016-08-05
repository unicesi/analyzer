package amc.api;


import sloContractLibrary.*;

public interface AMCService {

	QoSContract[] getQosContract();

	String getAliveMessage();

	
}
