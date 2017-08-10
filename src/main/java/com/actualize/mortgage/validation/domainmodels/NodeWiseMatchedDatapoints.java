package com.actualize.mortgage.validation.domainmodels;

import java.util.List;

public class NodeWiseMatchedDatapoints {
	
	private int datapointMatchCount;
	private List<DataPointDetails> dataPoints;

	public int getDatapointMatchCount() {
		return datapointMatchCount;
	}

	public void setDatapointMatchCount(int datapointMatchCount) {
		this.datapointMatchCount = datapointMatchCount;
	}

	public List<DataPointDetails> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(List<DataPointDetails> dataPoints) {
		this.dataPoints = dataPoints;
	}
}
