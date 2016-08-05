package clases;

import org.osoa.sca.annotations.Reference;

import sensor.api.SensorService;

import amc.api.AMCService;

public class Servicios implements Runnable {
	
	@Reference
	private SensorService sensor;

	@Override
	public void run() {
		int cont=1;
		while(true){
		System.out.println("Iniciando sensor... times count: "+ cont++);
		sensor.startSensing();
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}
