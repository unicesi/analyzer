package Main;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.postgresql.jdbc2.optional.SimpleDataSource;

import kb.lib.KBImpl;
import analyzer.lib.AnalyzerImpl;
import monitor.lib.MonitorImpl;

public class Main {

	public static MonitorImpl monitor;
	public static AnalyzerImpl analyzer;
	public static KBImpl kb;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		monitor = new MonitorImpl();
		analyzer = new AnalyzerImpl();
		kb = new KBImpl();
		monitor.setAnalyzerService(analyzer);
		monitor.setKbService(kb);
		
		analyzer.setKbService(kb);
		
		monitor.getSensedContext();

	}

}
