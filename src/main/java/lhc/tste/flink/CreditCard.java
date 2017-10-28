package lhc.tste.flink;

import org.apache.flink.streaming.api.windowing.time.Time;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import edu.umass.cs.sase.stream.StockEvent;

public class CreditCard {
	static Logger LOG = LoggerFactory.getLogger(AlarmMonitor.class);
	private static final long PAUSE = 5000;
	private static final double TEMP_STD = 20;
	private static final double TEMP_MEAN = 80;
	private static  int nbMatchs = 0;
	public static void main(String[] args) throws Exception {

		// ArrayList<StockEvent> events = new ArrayList<>();
		// BufferedReader br = new BufferedReader(new
		// FileReader("src/main/resources/stock.stream"));
		// String line;
		// while ((line = br.readLine()) != null) {
		// // parseLine(line);
		// StringTokenizer st = new StringTokenizer(line, ";");
		// int timestamp = Integer.parseInt(st.nextToken());
		// int symbol = Integer.parseInt(st.nextToken());
		// int price = Integer.parseInt(st.nextToken());
		// int volume = Integer.parseInt(st.nextToken());
		//
		// events.add(new StockEvent(timestamp, symbol, 1, price, volume,
		// "stock"));
		// }
		// br.close();
		System.out.println();
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// setting Parallelism to 1
		env.setParallelism(1);
		// env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		// Input stream of alarm events, event creation time is take as
		// timestamp
		// Setting the Watermark to same as creation time of the event.
		DataStream<String> input = env.readTextFile("/Users/kammoun/Desktop/creditCard.stream");
		DataStream<StockEvent> parsed = input.map(new MapFunction<String, StockEvent>() {
			@Override
			public StockEvent map(String line) {
				// StringTokenizer st = new StringTokenizer(line, ";");
				// int timestamp = Integer.parseInt(st.nextToken());
				// int symbol = Integer.parseInt(st.nextToken());
				// int price = Integer.parseInt(st.nextToken());
				// int volume = Integer.parseInt(st.nextToken());
				// return new StockEvent(timestamp, symbol, 1, price, volume,
				// "stock");

				StringTokenizer st = new StringTokenizer(line, ",");

				int timestamp = Integer.parseInt(st.nextToken());
				// amount
				int price = (int) Double.parseDouble(st.nextToken());
				// cardid
				int symbol = st.nextToken().hashCode();
				// location
				int volume = Integer.parseInt(st.nextToken());
				int id = Integer.parseInt(st.nextToken());

				return new StockEvent(id, timestamp, symbol, price, volume, "stock");
			}
		});

		// DataStream<StockEvent> inputEventStream = env.fromCollection(events);

		Pattern<StockEvent, ?> pattern = Pattern.<StockEvent> begin("A").where(new SimpleCondition<StockEvent>() {
			@Override
			public boolean filter(StockEvent event) {
				if (event.price < 0)
					return false;
				return true;
			}
		}).followedByAny("B").where(

				new IterativeCondition<StockEvent>() {
					private static final long serialVersionUID = -9216505110246259082L;

					@Override
					public boolean filter(StockEvent bEvent, Context<StockEvent> ctx) throws Exception {
						// System.out.println("B" + bEvent.id);

						StockEvent aEvents = null;
						Iterator<StockEvent> iteratorOverA = ctx.getEventsForPattern("A").iterator();
						while (iteratorOverA.hasNext()) {
							aEvents = iteratorOverA.next();
							if (bEvent.getPrice() > 0 && aEvents.getSymbol()== bEvent.getSymbol() && aEvents.getPrice() / bEvent.getPrice() >= 10) {
								return true;
							}

						}

						StockEvent lastb = null;
						Iterator<StockEvent> iteratorOverB = ctx.getEventsForPattern("B").iterator();
						while (iteratorOverB.hasNext()) {
							lastb = iteratorOverB.next();
							if (bEvent.getPrice() < lastb.getPrice()) {
								return true;
							}
						}

						return false;
					}
				}).oneOrMore().allowCombinations().followedByAny("C").where(

						new IterativeCondition<StockEvent>() {
							private static final long serialVersionUID = -9216505110246259082L;

							@Override
							public boolean filter(StockEvent cEvent, Context<StockEvent> ctx) throws Exception {
								// System.out.println("C" + cEvent.id);
								StockEvent bEvents = null;
								Iterator<StockEvent> iteratorOverB = ctx.getEventsForPattern("B").iterator();
								while (iteratorOverB.hasNext()) {
									bEvents = iteratorOverB.next();
									if ( cEvent.getSymbol()== bEvents.getSymbol() && cEvent.getPrice() < bEvents.getPrice()) {
										return true;
									}
								}
								return false;
							}
						}).within(Time. milliseconds(100));

		DataStream<String> alerts = CEP.pattern(parsed, pattern)
				.select(new PatternSelectFunction<StockEvent, String>() {

					@Override
					public String select(Map<String, List<StockEvent>> pattern) throws Exception {
						StringBuilder builder = new StringBuilder();
						for (int i = 0; i < pattern.get("A").size(); i++) {
							builder.append(pattern.get("A").get(i).getPrice()).append(",");

							for (int j = 0; j < pattern.get("B").size(); j++) {

								if (pattern.get("B").get(j).getPrice() <= pattern.get("A").get(i).getPrice() * 10 && (pattern.get("B").get(j).getTimestamp() > pattern.get("A").get(i).getTimestamp() ) ) {
									builder.append(pattern.get("B").get(j).getPrice()).append(",");

									for (int k = j; k < pattern.get("B").size(); k++) {

										if (pattern.get("B").get(k).getPrice() < pattern.get("B").get(j).getPrice()) {
											builder.append(pattern.get("B").get(k).getPrice()).append(",");

										}

									}

								}
							}

						
						builder.append(pattern.get("C").get(0).getId());
}nbMatchs++;
						return builder.toString();
					}
				});

		alerts.print();
		System.out.println(nbMatchs);
		// alerts.writeAsText("src/main/resources/resultsABC",
		// FileSystem.WriteMode.OVERWRITE);

		env.execute();

	}
}

