# anti-spam-weka [![Build Status](https://travis-ci.org/marcelovca90/anti-spam-weka.svg?branch=master)](https://travis-ci.org/marcelovca90/anti-spam-weka)

Project of my master's degree in Computer Science ("Study and Research in Anti-Spam Systems").

For instructions on how to clone, build and run the project, please refer to the ["How To"](https://github.com/marcelovca90/anti-spam-weka/blob/master/HOW-TO.md).

- - - -

Machine learning library:
- [Weka (Waikato Environment for Knowledge Analysis)](http://www.cs.waikato.ac.nz/ml/weka/)

Data sets:
- [2017_BASE2](https://github.com/marcelovca90/anti-spam-weka-data/tree/master/2017_BASE2) (com 8, 16, 32, 64, 128, 256 e 512 características - CHI2, DF e MI)
- [2017_MULT10](https://github.com/marcelovca90/anti-spam-weka-data/tree/master/2017_MULT10) (com 10, 20, 30, 40, 50, 60, 70, 80, 90 e 100 características - CHI2, DF e MI)

Classification methods:
- [A1DE](http://weka.sourceforge.net/packageMetaData/AnDE/index.html) - Averaged 1-Dependence Estimator
- [A2DE](http://weka.sourceforge.net/packageMetaData/AnDE/index.html) - Averaged 2-Dependence Estimator
- [BFTREE](http://weka.sourceforge.net/doc.packages/bestFirstTree/weka/classifiers/trees/BFTree.html) - Best-first tree
- [DTNB](http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/DTNB.html) - Decision Table/Naive Bayes Hybrid Classifier
- [FURIA](http://weka.sourceforge.net/packageMetaData/fuzzyUnorderedRuleInduction/index.html) - Fuzzy Unordered Rule Induction Algorithm
- [HP](http://weka.sourceforge.net/doc.packages/hyperPipes/weka/classifiers/misc/HyperPipes.html) - HyperPipe Classifier
- [J48](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html) - C4.5 Decision Tree
- [J48C](http://weka.sourceforge.net/packageMetaData/J48Consolidated/index.html) - C4.5 Consolidated Decision Tree
- [J48G](http://weka.sourceforge.net/doc.packages/J48graft/weka/classifiers/trees/J48graft.html) - C4.5 Grafted Decision Tree
- [JRIP](http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/JRip.html) - Repeated Incremental Pruning to Produce Error Reduction
- [MLPCS](http://weka.sourceforge.net/doc.packages/multilayerPerceptronCS/weka/classifiers/functions/MultilayerPerceptronCS.html) - Multilayer perceptron with context-sensitive Multiple Task Learning
- [MLP](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html) - Multilayer Perceptron
- [NB](http://weka.sourceforge.net/doc.dev/weka/classifiers/bayes/NaiveBayes.html) - Naive Bayes classifier
- [NBTREE](http://weka.sourceforge.net/doc.stable/weka/classifiers/trees/NBTree.html) - Decision Tree with Naive Bayes Classifiers at the leaves
- [RBF](http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFNetwork.html) - Radial Basis Function network
- [RF](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomForest.html) - Random Forest
- [RT](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomTree.html) - Random Tree
- [SGD](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html) - Stochastic Gradient Gescent
- [SMO](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SMO.html) - Sequential Minimal Optimization Algorithm
- [SPEGASOS](http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/SPegasos.html) - Stochastic Primal Estimated sub-GrAdient SOlver for SVM
- [SVM](http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html) - Support Vector Machine

Metrics:
- Ham and Spam Precision `(TP / (TP + FP))`
- Ham and Spam Recall `(TP / (TP + FN))`
- Area Under Ham and Spam Precision-Recall (PR) Curves
- Area Under Ham and Spam Receiver Operating Characteristic (ROC) Curves
- Training and Testing Times
