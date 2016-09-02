package org.glassfish.movieplex7.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transactional;

import pascani.lang.events.TimeLapseEvent;


public class SalesInterceptor{
	ProbeBeanLocal monitorProbe = lookupProbeBeanLocal();

	private ProbeBeanLocal lookupProbeBeanLocal() {
		try {
			Context c = new InitialContext();
			// IMPORTANTE: test es el nombre del proyecto
			System.out.println("FADSGHDGSFJHSFHGFSFDGAHGHST");
			return (ProbeBeanLocal) c
					.lookup("java:global/movieplex7/ProbeBean!org.glassfish.movieplex7.interceptors.ProbeBeanLocal");
		} catch (NamingException ne) {
			System.out
					.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
			throw new RuntimeException(ne);
		}
	}

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {

		System.out.println("SalesInterceptor - BEFORE calling method :"
				+ context.getMethod().getName());

		long inicio = System.nanoTime();
		Object result = context.proceed();
		long fin = System.nanoTime();

		System.out.println("SalesInterceptor  -AFTER calling method :"
				+ context.getMethod().getName());

		TimeLapseEvent event = new TimeLapseEvent(UUID.randomUUID(), inicio,
				fin);
		
		monitorProbe.post(event);

		return result;
	}
	
}
