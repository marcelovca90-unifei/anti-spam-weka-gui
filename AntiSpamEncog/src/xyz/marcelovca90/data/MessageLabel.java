package xyz.marcelovca90.data;


/**
 * @author marcelovca90
 *
 */
public enum MessageLabel {
	
	HAM(new Double[] { 1.0, 0.0 }), 
	
	SPAM(new Double[] { 0.0, 1.0 });
	
	private final Double[] value;
	
	MessageLabel(Double[] value) {
		this.value = value; 
	}

	public Double[] getValue() {
		return value;
	}

}
