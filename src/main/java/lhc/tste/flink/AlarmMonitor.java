package lhc.tste.flink;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class AlarmMonitor {

	private static final long PAUSE = 5000;
	private static final double TEMP_STD = 20;
	private static final double TEMP_MEAN = 80;

	public static void main(String[] args) throws Exception {

    	
    
    		int i = 0;
    		ArrayList<StockEvent> events = new ArrayList<>();
    		BufferedReader br = new BufferedReader(new FileReader(
    				"src/main/resources/stock.stream"));
    		String line;
    		while ((line = br.readLine()) != null) {
    			// parseLine(line);
    			StringTokenizer st = new StringTokenizer(line, ";");
    			//int timestamp = Integer.parseInt(st.nextToken());
    			//int symbol= Integer.parseInt(st.nextToken());
    			int price = Integer.parseInt(st.nextToken());
    			int volume = Integer.parseInt(st.nextToken());
    			
    			
    			events.add( new StockEvent(i,i, 1,price,volume, "stock"));
    			i++;
    		}
    		br.close();
    		
    
    	
    	
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // setting Parallelism to 1 
      //  env.setParallelism(1);
      //  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // Input stream of alarm events, event creation time is take as timestamp
        // Setting the Watermark to same as creation time of the event.
        DataStream<String> input = env.readTextFile("src/main/resources/stock.stream");
        DataStream<StockEvent> parsed = input.map(new MapFunction<String, StockEvent>() {
            @Override
            public StockEvent map(String line) {
            	StringTokenizer st = new StringTokenizer(line, ";");
    			int timestamp = Integer.parseInt(st.nextToken());
    			int symbol= Integer.parseInt(st.nextToken());
    			int price = Integer.parseInt(st.nextToken());
    			int volume = Integer.parseInt(st.nextToken());
                return new StockEvent(timestamp,symbol, 1,price,volume, "stock");
            }
        });
        
        
        DataStream<StockEvent> inputEventStream =  env.fromCollection(events);
//        DataStream<AlarmEvent> inputEventStream = env
//                .addSource(new AlarmEventSource(PAUSE, TEMP_STD, TEMP_MEAN))
//                .assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<AlarmEvent>() {
//
//        			@Override
//        			public long extractTimestamp(AlarmEvent event, long currentTimestamp) {
//        				return event.getEventTime();
//        			}
//
//        			@Override
//        			public Watermark checkAndGetNextWatermark(AlarmEvent lastElement, long extractedTimestamp) {
//        				return new Watermark(extractedTimestamp);
//        			}
//
//        		});
        
        //Continuously prints the input events
       System.out.println( inputEventStream.print());    
    

        // Wait for 3 seconds and then decide if the event is really a critical issue
        // in the network element, I have used larger pause time between the event
        // to simulate time-out
        Pattern<StockEvent, ?> alarmPattern = Pattern.<StockEvent>begin("first")
                .where(evt -> evt.getPrice()>10)
                .next("second")
                .where(evt -> evt.getVolume()>20)
                .within(Time.seconds(3));

       
        		 PatternStream<StockEvent> patternStream = CEP.pattern(inputEventStream, alarmPattern);

        		DataStream<String> alerts = patternStream.select(new PatternSelectFunction<StockEvent, String>() {

					@Override
					public String select(Map<String, StockEvent> pattern) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("blablka");
						return null;
					}
        		});
        		
        		   env.execute("CEP monitoring job");
//        		
//        		DataStream<Either<String, String>> result = CEP.pattern(inputEventStream, alarmPattern).
//                		select(new PatternTimeoutFunction<StockEvent, String>() {
//                			
//                			@Override
//            				public String timeout(Map<String, StockEvent> pattern, long timeoutTimestamp) throws Exception {
//                				System.out.println("Timeout "+pattern);
//            					return pattern.get("first").toString() + "";
//            				}
//                			
//                		},new PatternSelectFunction<StockEvent, String>() {
//                			public String select(Map<String, StockEvent> pattern) {
//            					StringBuilder builder = new StringBuilder();
//
//            					builder.append(pattern.get("first").toString());
//
//            					return builder.toString();
//            				}
//                			
//                		}); 
//
//                env.execute("CEP monitoring job");
        		
        		
        		
        		

}}
