package org.glassfish.movieplex7.interceptors;

import javax.ejb.Local;
import javax.ejb.Remote;

import pascani.lang.Event;

@Local
@Remote
public interface ProbeBeanLocal {
	public void post(Event<?> e);
}
