package analyzer.lib;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import sloContractLibrary.ContextCondition;
import sloContractLibrary.QoSContract;
import sloContractLibrary.SloPredicate;
import analyzer.api.AAnalyzer;
import contextLibrary.ContextEntity;
import contextLibrary.ContextProperty;
import contextLibrary.Diagnosis;
import contextLibrary.Information;

/**
 * Component: Analyzer Class : AnalyzerImpl
 * 
 * @author lcastane[at]icesi.edu.co Date: (07/2011)
 * @version 3.0
 * 
 */

public class AnalyzerImpl extends AAnalyzer {

	/*
	 * Constructor
	 */
	public AnalyzerImpl() {
		// Time Stamp
		Calendar calendarTimeStamp = Calendar.getInstance();
		// System.out.println("Analyzer started at: " +
		// calendarTimeStamp.getTimeInMillis());

		information = null;
		diagnosis = new ArrayList<Diagnosis>();
		System.out.println("[Analyzer Service Started]");
	}

	// sloFuture es el umbral del día que se predijo
	public void correlationCompliance(double d, int sloTxFuture) {
		// Metodo para recuperar el treshold
		System.out.println("[Analyzer Predictive Service] - Threshold: "
				+ sloTxFuture + " <vs.> Predicted: " + d);

		if (sloTxFuture <= d) {
			System.out.println("[Analyzer Predictive Service] - SLO Ok");
		} else {
			System.out.println("[Analyzer PredictiveService] - SLO Nok");
		}

	}

	/**
	 * This method is specialized for the case of study
	 * 
	 * @param infoToAnalyze
	 * @return
	 */
	protected Diagnosis contractVsInformation(ArrayList<Object> infoToAnalyze) {

		if (infoToAnalyze.size() > 1) {

			QoSContract contract = (QoSContract) infoToAnalyze.get(0);
			// The Predicate holds the information of the Contract
			SloPredicate predicate = contract.getProperty().get(0)
					.getObligation().get(0).getPredicate();
			// The ContextCondition is the information and data types involved
			// in the Contract
			ContextCondition contCond = contract.getProperty().get(0)
					.getObligation().get(0).getContextCondition();
			/*
			 * [lcastane 04/2012] Information: At this point this implementation
			 * is specific for the type of context we know is going to be
			 * analyzed and the correspondent qos contract (C3-Throughput).
			 * However, any following implementation must override this method.
			 */

			// 2.1 Matches the Context Conditions, SloPredicates and the
			// Information
			/*
			 * lcastane[15/2012] This evaluation is only for the case of study.
			 * Further implementations must consider to evaluate data types and
			 * values before this comparison
			 */
			// int currentDay = (Integer) (((Information) infoToAnalyze.get(1))
			// .getEntity().getProperty(0).getValue());
			int currentDay = 26;
			int currentTxmin = (Integer) (((Information) infoToAnalyze.get(2))
					.getEntity().getProperty(0).getValue());
			System.out.println("[Analyzer Service] - Current info: "
					+ currentDay + ", " + currentTxmin);

			// 2.2 Compares: if equals or below diagnosis false, else diagnosis
			// true
			ArrayList list = predicate.getValues();
			for (int i = 0; i < list.size(); i++) {
				int sloDay = (Integer) list.get(i);
				int sloTx = (Integer) list.get(++i);
				if (sloDay == currentDay) {
					System.out.println("[Analyzer Service] - SLO info Day:"
							+ sloDay + ", Tx/Min:" + sloTx);
					ContextEntity desired = new ContextEntity(
							contract.getName(), ContextEntity.TYPE_INFO);
					ContextProperty propDesired1 = new ContextProperty(
							ContextProperty.TYPE_INTEGER, "DiaMes", sloDay);
					desired.addProperty(propDesired1);
					ContextProperty propDesired2 = new ContextProperty(
							ContextProperty.TYPE_INTEGER, "Tx/min", sloTx);
					desired.addProperty(propDesired2);

					ContextEntity current = new ContextEntity(
							contract.getName(), ContextEntity.TYPE_INFO);
					ContextProperty propCurrent1 = new ContextProperty(
							ContextProperty.TYPE_INTEGER, "DiaMes", currentDay);
					desired.addProperty(propCurrent1);
					ContextProperty propCurrent2 = new ContextProperty(
							ContextProperty.TYPE_INTEGER, "Tx/min",
							currentTxmin);
					desired.addProperty(propCurrent2);

					System.out.println("[Analyzer Service] - Slo: " + sloTx
							+ " <Vs.> Current: " + currentTxmin);

					if (sloTx >= currentTxmin) {
						System.out.println("[Analyzer Service] - SLO Ok");
						System.out
								.println("[Analyzer Predictive Service] - Predicitive Analysis started");
						System.out
								.println("[Analyzer Predictive Service] - Retreiving tomorrow's info");
						int futDay = (Integer) list.get(currentDay == 31 ? 0
								: ++i);
						int futTrshld = (Integer) list.get(++i);
						System.out
								.println("[Analyzer Predictive Service] -	Tomorrow's info. Day: "
										+ futDay + ", Threshold: " + futTrshld);
						correlationAnalysis(futDay, futTrshld);
						return new Diagnosis(false, desired, current);
					} else {
						System.out.println("[Analyzer Service] - SLO Nok");
						System.out
								.println("[Analyzer Predictive Service] - Predicitive Analysis started");
						System.out
								.println("[Analyzer Predictive Service] - Retreiving tomorrow's info");
						int futDay = (Integer) list.get(currentDay == 31 ? 0
								: ++i);
						int futTrshld = (Integer) list.get(++i);
						System.out
								.println("[Analyzer Predictive Service] -	Tomorrow's info. Day: "
										+ futDay + ", Threshold: " + futTrshld);
						correlationAnalysis(futDay, futTrshld);
						return new Diagnosis(true, desired, current);
					}

				}
			}

		}
		return null;
	}

	// sloFuture -> slo next day
	@Override
	public void correlationAnalysis(int sloFuture, int sloTxFuture) {
		SimpleRegression regression = new SimpleRegression();
		System.out
				.println("[Analyzer Predictive Service] - Correlation Analysis");
		System.out
				.println("[Analyzer Predictive Service] -	Retrieving stored Throughput data");
		double[][] data = kbService.getHistorics();// new
													// double[][]{{0,20},{1,18},{2,18},{3,5},{4,1}};

		System.out
				.println("[Analyzer Predictive Service] - Adding data to regression");
		regression.addData(data);

		// TODO predecir fecha, fecha+hora.... definir unidad de tiempo
		System.out.println("[Analyzer Predictive Service] - Predicting day "
				+ sloFuture + "'s Throughput");
		correlationCompliance(regression.predict(sloFuture), sloTxFuture);
	}

}
