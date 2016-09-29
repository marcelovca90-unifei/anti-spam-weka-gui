package xyz.marcelo.method;

public enum MethodName
{
    J48("C4.5 algorithm"),

    MLP("Multilayer perceptron"),

    RBF("Radial basis function network"),

    RF("Random forests"),

    SGD("Stochastic gradient descent"),

    SVM("Support vector machine");

    @SuppressWarnings("unused")
    private final String value;

    private MethodName(final String value)
    {
        this.value = value;
    }

}
