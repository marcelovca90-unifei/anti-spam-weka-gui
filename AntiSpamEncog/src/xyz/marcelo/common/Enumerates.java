/**
 *
 */
package xyz.marcelo.common;

/**
 * The Class Enumerates.
 *
 * @author marcelovca90
 */
public class Enumerates
{
    /**
     * Represents a type of message (ham or spam).
     */
    public static enum MessageLabel
    {

        /** Represents a ham. */
        HAM(new Double[] { 1.0, 0.0 }),

        /** Represents a spam. */
        SPAM(new Double[] { 0.0, 1.0 });

        /** The neural representation of the message. */
        private final Double[] value;

        /**
         * Instantiates a new message label.
         * 
         * @param value
         *            the value
         */
        MessageLabel(Double[] value)
        {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public Double[] getValue()
        {
            return value;
        }
    }

    /**
     * The Enum Method.
     */
    public static enum Method
    {

        /** The mlp bprop. */
        MLP_BPROP("Multilayer Perceptron (Backpropagation)"),

        /** The mlp rprop. */
        MLP_RPROP("Multilayer Perceptron (Resilient Propagation)"),

        /** The neat. */
        NEAT("Neuroevolution of Augmenting Topologies"),

        /** The rbf qprop. */
        RBF_QPROP("Radial Basis Function (Quick Propagation)"),

        /** The svm. */
        SVM("Support Vector Machine");

        /** The name. */
        private final String name;

        /**
         * Instantiates a new method.
         * 
         * @param name
         *            the name
         */
        Method(String name)
        {
            this.name = name;
        }

        /**
         * Gets the name.
         * 
         * @return the name
         */
        public String getName()
        {
            return name;
        }
    }

}
