package xyz.marcelovca90.data;

/**
 * @author marcelovca90
 *
 */
public enum MessageType {
	
	HAM(new double[] { 1, 0 }), 
	
	SPAM(new double[] { 0, 1 });
	
	private final double[] value;
	
	MessageType(double[] value) {
		this.value = value; 
	}

	public double[] getValue() {
		return value;
	}

}
