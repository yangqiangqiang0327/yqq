package lhc.tste.flink;

import java.util.ArrayList;

public class Match {
	/**
	 * The events selected for this match
	 */
	public ArrayList<StockEvent> eventList;

	public ArrayList<StockEvent> getEventList() {
		if(eventList==null)
			eventList= new ArrayList<>();
		return eventList;
	}

	public void setEventList(ArrayList<StockEvent> eventList) {
		this.eventList = eventList;
	}
}
