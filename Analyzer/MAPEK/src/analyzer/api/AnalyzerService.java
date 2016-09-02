package analyzer.api;

import contextLibrary.IContextModel;

public interface AnalyzerService {

	/**
	 * This method exposes the service to ananyzer relevant context information.
	 * It is used by the Monitor component
	 * 
	 * @param contextInformation
	 */
	void reportContextInformation(IContextModel[] contextInformation);

	String getAliveMessage();

	void correlationAnalysis(int sloFuture, int sloTxFuture);
}
