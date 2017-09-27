package cep.itcase;

import java.util.List;
import java.util.Map;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CEPITCase {
	public static void main(String[] args) throws Exception {

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		DataStream<Event> input = env.fromElements(new Event(1, "barfoo", 1.0), new Event(2, "start", 2.0),
				new Event(3, "foobar", 3.0), new SubEvent(4, "foo", 4.0, 1.0), new Event(5, "middle", 5.0),
				new SubEvent(6, "middle", 6.0, 2.0), new SubEvent(7, "bar", 3.0, 3.0), new Event(42, "42", 42.0),
				new Event(8, "end", 1.0));

		Pattern<Event, ?> pattern = Pattern.<Event> begin("start").where(new SimpleCondition<Event>() {

			@Override
			public boolean filter(Event value) throws Exception {
				return value.getName().equals("start");
			}
		}).followedByAny("middle").subtype(SubEvent.class).where(new SimpleCondition<SubEvent>() {

			@Override
			public boolean filter(SubEvent value) throws Exception {
				return value.getName().equals("middle");
			}
		}).followedByAny("end").where(new SimpleCondition<Event>() {

			@Override
			public boolean filter(Event value) throws Exception {
				return value.getName().equals("end");
			}
		});

		@SuppressWarnings("serial")
		DataStream<String> result = CEP.pattern(input, pattern).select(new PatternSelectFunction<Event, String>() {

			@Override
			public String select(Map<String, List<Event>> pattern) {
				StringBuilder builder = new StringBuilder();

				builder.append(pattern.get("start").get(0).getId()).append(",")
						.append(pattern.get("middle").get(0).getId()).append(",")
						.append(pattern.get("end").get(0).getId());

				return builder.toString();
			}
		});

		result.print();
		result.writeAsText("src/main/resources/results.txt", FileSystem.WriteMode.OVERWRITE);

		// expected sequence of matching event ids
		/// expected = "2,6,8";

		env.execute();
	}

}