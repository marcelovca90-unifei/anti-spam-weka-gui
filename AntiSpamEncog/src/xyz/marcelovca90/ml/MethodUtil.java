package xyz.marcelovca90.ml;

public enum MethodUtil {

	MLP_BPROP("Multilayer Perceptron (Backpropagation)"),
	MLP_RPROP("Multilayer Perceptron (Resilient Propagation)"),
	NEAT("Neuroevolution of Augmenting Topologies"),
	RBF_QPROP("Radial Basis Function (Quick Propagation)"),
	SVM("Support Vector Machine");
	
	private final String name;
	
	MethodUtil(String name) {
		this.name = name; 
	}

	public String getName() {
		return name;
	}
	
}
