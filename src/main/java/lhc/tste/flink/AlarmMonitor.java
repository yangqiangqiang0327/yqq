package lhc.tste.flink;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlarmMonitor {
	static Logger LOG = LoggerFactory.getLogger(AlarmMonitor.class);
	private static final long PAUSE = 5000;
	private static final double TEMP_STD = 20;
	private static final double TEMP_MEAN = 80;

	public static void main(String[] args) throws Exception {

		int i = 0;
		ArrayList<StockEvent> events = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/stock.stream"));
		String line;
		while ((line = br.readLine()) != null) {
			// parseLine(line);
			StringTokenizer st = new StringTokenizer(line, ";");
			// int timestamp = Integer.parseInt(st.nextToken());
			// int symbol= Integer.parseInt(st.nextToken());
			int price = Integer.parseInt(st.nextToken());
			int volume = Integer.parseInt(st.nextToken());

			events.add(new StockEvent(i, i, 1, price, volume, "stock"));
			i++;
		}
		br.close();

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// setting Parallelism to 1
		// env.setParallelism(1);
		// env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		// Input stream of alarm events, event creation time is take as
		// timestamp
		// Setting the Watermark to same as creation time of the event.
		DataStream<String> input = env.readTextFile("src/main/resources/stock.stream");
		DataStream<StockEvent> parsed = input.map(new MapFunction<String, StockEvent>() {
			@Override
			public StockEvent map(String line) {
				StringTokenizer st = new StringTokenizer(line, ";");
				int timestamp = Integer.parseInt(st.nextToken());
				int symbol = Integer.parseInt(st.nextToken());
				int price = Integer.parseInt(st.nextToken());
				int volume = Integer.parseInt(st.nextToken());
				return new StockEvent(timestamp, symbol, 1, price, volume, "stock");
			}
		});

		DataStream<StockEvent> inputEventStream = env.fromCollection(events);

		Pattern<StockEvent, ?> pattern = Pattern.<StockEvent> begin("A").where(new SimpleCondition<StockEvent>() {
			@Override
			public boolean filter(StockEvent event) {
				LOG.info("A", event.id, event.price);
				return true;
			}
		}).followedByAny("B").where(new SimpleCondition<StockEvent>() {
			@Override
			public boolean filter(StockEvent event) {
				//return event.getVolume() > 10;
				return true;
			}

		}).oneOrMore().allowCombinations().followedByAny("C").where(new SimpleCondition<StockEvent>() {
			@Override
			public boolean filter(StockEvent value) throws Exception {
				//return value.getPrice() < 10;
				return true;
			}
		});
		;

		DataStream<String> alerts = CEP.pattern(inputEventStream, pattern)
				.select(new PatternSelectFunction<StockEvent, String>() {

					@Override
					public String select(Map<String, List<StockEvent>> pattern) throws Exception {
						StringBuilder builder = new StringBuilder();

						builder.append(pattern.get("A").get(0).getId()).append(",")
								.append(pattern.get("B").get(0).getId()).append(",")
								.append(pattern.get("C").get(0).getId()).append("houni youfa");

						return builder.toString();
					}
				});

		alerts.print();
		alerts.writeAsText("src/main/resources/resultsABC", FileSystem.WriteMode.OVERWRITE);

		env.execute();

	}
}
