# Universidade Federal de Itajubá [![Build Status](https://travis-ci.org/marcelovca90/anti-spam-weka.svg?branch=master)](https://travis-ci.org/marcelovca90/anti-spam-weka)
## Programa de Pós-graduação em Ciência da Computação

**Área de Concentração:** Matemática da Computação

**Linha de Pesquisa:** Inteligência Artificial

**Projeto:** Estudo e Pesquisa em Sistemas Anti-Spam

**Orientador:** Otávio Augusto Salgado Carpinteiro

- - - -

Biblioteca de aprendizado de máquina:
- [Weka (Waikato Environment for Knowledge Analysis)](http://www.cs.waikato.ac.nz/ml/weka/)

Bases de dados:
- [2017_BASE2](https://github.com/marcelovca90/anti-spam-weka-data/tree/master/2017_BASE2) (com 8, 16, 32, 64, 128, 256 e 512 características - CHI2, DF e MI)
- [2017_MULT10](https://github.com/marcelovca90/anti-spam-weka-data/tree/master/2017_MULT10) (com 10, 20, 30, 40, 50, 60, 70, 80, 90 e 100 características - CHI2, DF e MI)

Métodos de classificação:
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

Métricas:
- Porcentagem de acertos na classificação de hams (*Ham Precision*)
- Porcentagem de revocação na classificação de hams (*Ham Recall*)
- Porcentagem de acertos na classificação de spams (*Spam Precision*)
- Porcentagem de revocação na classificação de hams (*Spam Recall*)
- Área sobre a Curva Precisão-Revocação de Ham (*Area Under Ham PR Curve*)
- Área sobre a Curva Precisão-Revocação de Spam (*Area Under Spam PR Curve*)
- Área sobre a Curva Característica de Operação do Receptor de Ham (*Area Under Ham ROC Curve*)
- Área sobre a Curva Característica de Operação do Receptor de Spam (*Area Under Spam ROC Curve*)
- Tempo de treinamento (*Training Time*)
- Tempo de classificação (*Test Time*)
