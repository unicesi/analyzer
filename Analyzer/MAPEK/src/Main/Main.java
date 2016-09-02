package Main;

import kb.lib.KBImpl;
import monitor.lib.MonitorImpl;
import analyzer.lib.AnalyzerImpl;

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
