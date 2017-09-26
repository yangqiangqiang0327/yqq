package lhc.tste.flink;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import cep.itcase.Event;

/**
 * This class represents the stock event.
 * 
 * @author haopeng
 *
 */
public class StockEvent {
	/**
	 * Event id
	 */
	int id;

	/**
	 * Event timestamp
	 */
	int timestamp;

	/**
	 * Event type
	 */
	String eventType;

	/**
	 * Symbol, an attribute
	 */
	int symbol;

	/**
	 * Price, an attribute
	 */
	int price;

	/**
	 * Volume, an attribute
	 */
	int volume;

	// postponing
	boolean safe;

	/**
	 * Constructor
	 */
	public StockEvent(int id, int ts, int symbol, int price, int volume) {
		this.id = id;
		this.timestamp = ts;
		this.symbol = symbol;
		this.price = price;
		this.volume = volume;
		this.eventType = "stock";
	}

	/**
	 * Another constructor
	 * 
	 * @param id
	 * @param ts
	 * @param symbol
	 * @param price
	 * @param volume
	 * @param type
	 */
	public StockEvent(int id, int ts, int symbol, int price, int volume, String type) {
		this.id = id;
		this.timestamp = ts;
		this.symbol = symbol;
		this.price = price;
		this.volume = volume;
		this.eventType = type;
	}

	/**
	 * Returns the attribute value for the given attribute
	 * 
	 * @param attributeName
	 *            The name of the attribute to be returned
	 */
	public int getAttributeByName(String attributeName) {
		if (attributeName.equalsIgnoreCase("symbol"))
			return this.symbol;
		if (attributeName.equalsIgnoreCase("price"))
			return price;
		if (attributeName.equalsIgnoreCase("volume"))
			return this.volume;
		if (attributeName.equalsIgnoreCase("id"))
			return this.id;
		if (attributeName.equalsIgnoreCase("timestamp"))
			return this.timestamp;

		return 0;

	}

	public String toCSV() {
		return null;
	}

	public String attributeNameToCSV() {
		return null;
	}

	public String getEventType() {
		// TODO Auto-generated method stub
		return this.eventType;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public long getTimestamp() {
		// TODO Auto-generated method stub
		return this.timestamp;

	}

	public String toString() {
		// return id+";"+timestamp+";"+symbol+";"+price+";"+volume;
		return "ID = " + id + "\teventType=" + this.eventType + "\tTimestamp = " + timestamp + "\tSymbol = "
				+ this.symbol + "\tPrice = " + price + "\tVolume = " + volume;
	}

	/**
	 * @return the symbol
	 */
	public int getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Clones the event
	 */
	public Object clone() {
		StockEvent o = null;
		try {
			o = (StockEvent) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umass.cs.sase.mvc.model.Event#getAttributeByNameDouble(java.lang.
	 * String)
	 */
	public double getAttributeByNameDouble(String attributeName) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umass.cs.sase.mvc.model.Event#getAttributeByNameString(java.lang.
	 * String)
	 */
	public String getAttributeByNameString(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umass.cs.sase.mvc.model.Event#getAttributeValueType(java.lang.String)
	 */
	public int getAttributeValueType(String attributeName) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public long getUpperBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLowerBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getProbabilityForPoint(long timestamp) {
		// TODO Auto-generated method stub
		return 1;
	}

	public double getProbabilityForRange(int lower, int upper) {
		return 1;
	}

	public void setOriginalEventId(int originalEventId) {
		// TODO Auto-generated method stub

	}

	public String toStringSelectedContentOnly() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getProbabilityForRange(long lower, long upper) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String[] toStringArray() {
		String[] content = new String[6];
		content[0] = "" + this.id;
		content[1] = "" + this.timestamp;
		content[2] = "" + this.eventType;
		content[3] = "" + this.symbol;
		content[4] = "" + this.price;
		content[5] = "" + this.volume;
		return content;
	}

	public String[] attributeNameToStringArray() {
		String[] header = new String[6];
		header[0] = "id";
		header[1] = "timestamp";
		header[2] = "eventType";
		header[3] = "symbol";
		header[4] = "price";
		header[5] = "volume";
		return header;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + id;
		result = prime * result + price;
		result = prime * result + (safe ? 1231 : 1237);
		result = prime * result + symbol;
		result = prime * result + timestamp;
		result = prime * result + volume;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockEvent other = (StockEvent) obj;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		if (id != other.id)
			return false;
		if (price != other.price)
			return false;
		if (safe != other.safe)
			return false;
		if (symbol != other.symbol)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (volume != other.volume)
			return false;
		return true;
	}
	public static TypeSerializer<StockEvent> createTypeSerializer() {
		TypeInformation<StockEvent> typeInformation = (TypeInformation<StockEvent>) TypeExtractor.createTypeInfo(StockEvent.class);

		return typeInformation.createSerializer(new ExecutionConfig());
	}
}
