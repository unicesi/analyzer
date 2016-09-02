package org.glassfish.movieplex7.interceptors;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import pascani.lang.Event;
import pascani.lang.PascaniRuntime.Context;
import pascani.lang.infrastructure.LocalProbe;

/**
 * Session Bean implementation class ProbeBean
 */
@Singleton
@LocalBean
@Startup
public class ProbeBean implements ProbeBeanLocal {

	public static LocalProbe probe;
	
	public void metodo(){
		System.out.println("Instanciado");
		if(probe == null) {
			try {
				System.setProperty("pascani.uri","amqp://guest:guest@172.17.0.2:5672");
				probe = new LocalProbe("ProbeTiempo", Context.LIBRARY);
				System.out.println("Pasó");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void post(Event<?> e) {
		metodo();
		probe.recordEvent(e);
	}

}
