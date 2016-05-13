package xyz.marcelovca90.data;

/**
 * @author marcelovca90
 *
 */
public class MessageData {
	
	private double[] data;
	
	private MessageType type;

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public MessageData(double[] data, MessageType type) {
		super();
		this.data = data;
		this.type = type;
	}

	@Override
	public String toString() {
		return "MessageData [data.length=" + data.length + ", type=" + type	+ "]";
	}
	
}
