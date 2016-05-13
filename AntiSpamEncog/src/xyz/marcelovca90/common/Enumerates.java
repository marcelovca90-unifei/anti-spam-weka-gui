/**
 * 
 */
package xyz.marcelovca90.common;

/**
 * @author marcelovca90
 *
 */
public class Enumerates {
	
	public static enum MessageLabel {
		
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
	
	public static enum Method {

		MLP_BPROP("Multilayer Perceptron (Backpropagation)"),
		MLP_RPROP("Multilayer Perceptron (Resilient Propagation)"),
		NEAT("Neuroevolution of Augmenting Topologies"),
		RBF_QPROP("Radial Basis Function (Quick Propagation)"),
		SVM("Support Vector Machine");

		private final String name;

		Method(String name) {
			this.name = name; 
		}

		public String getName() {
			return name;
		}
	}

}
