package lhc.tste.flink;

import java.util.List;
import java.util.Map;
import lhc.tste.flink.StockEvent;
import lhc.tste.flink.Match;
import org.apache.flink.cep.PatternSelectFunction;

class MyPatternSelectFunction<StockEvent, Match> implements PatternSelectFunction<StockEvent, Match> {

	// @Override
	// public String select(Map<String, List<StockEvent>> pattern) throws
	// Exception {
	// // TODO Auto-generated method stub
	// StockEvent startEvent = pattern.get("start").get(0);
	// StockEvent endEvent = pattern.get("end").get(0);
	// return startEvent.getId()+""+endEvent.getId();
	// }
	//
	// @Override
	// public Object select(Map arg0) throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public String select(Map<Match, List<StockEvent>> arg0) throws Exception
	// {
	// StockEvent startEvent = arg0.get("start").get(0);
	//
	// StockEvent endEvent = arg0.get("end").get(0);
	// return (String) (startEvent.toString() +""+ endEvent.toString());
	// }

	@Override
	public Match select(Map<String, List<StockEvent>> arg0) throws Exception {

		StockEvent startEvent = arg0.get("start").get(0);
		StockEvent endEvent = arg0.get("end").get(0);
		

		return null;// new Match();
	}

}
