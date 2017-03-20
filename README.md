# Universidade Federal de Itajubá
## Programa de Pós-graduação em Ciência da Computação

**Área de Concentração:** Matemática da Computação

**Linha de Pesquisa:** Inteligência Artificial

**Projeto:** Estudo e Pesquisa em Sistemas Anti-Spam

**Orientador:** Otávio Augusto Salgado Carpinteiro

Implementação baseada na biblioteca de aprendizado de máquina [Weka (Waikato Environment for Knowledge Analysis)](http://www.cs.waikato.ac.nz/ml/weka/).

Geral:
- [x] Ler trabalhos anteriores
- [x] Ler artigos sobre o assunto
- [x] Estudar a biblioteca Weka
- [x] Salvar e carregar modelos do Weka
- [ ] Investigar aleatoriedade dos resultados
- [x] ~~Implementar k-Fold Cross-Validation~~
- [ ] Comparar os resultados com trabalhos anteriores
- [ ] Escrever dissetação no LaTeX
- [ ] "Extrair" artigo da dissertação

Bases de dados:
- [x] LingSpam (com 8, 16, 32, 64, 128, 256 e 512 features - CHI2, DF e MI)
- [x] SpamAssassin (com 8, 16, 32, 64, 128, 256 e 512 features - CHI2, DF e MI)
- [x] TREC (com 8, 16, 32, 64, 128, 256 e 512 features - CHI2, DF e MI)
- [x] Unifei 2017 (com 8, 16, 32, 64, 128, 256 e 512 features - CHI2, DF e MI)
- [x] ~~Quantidade de features proporcional aos padrões zerados~~

Métodos de classificação:
- [x] [A1DE](http://weka.sourceforge.net/packageMetaData/AnDE/index.html) - Averaged 1-Dependence Estimators
- [x] ~~[A2DE](http://weka.sourceforge.net/packageMetaData/AnDE/index.html) - Averaged 2-Dependence Estimators~~
- [x] ~~[BFTREE](http://weka.sourceforge.net/doc.packages/bestFirstTree/weka/classifiers/trees/BFTree.html) - Best-first tree~~
- [x] [DTNB](http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/DTNB.html) - Decision Table/Naive Bayes hybrid classifier
- [x] ~~[FURIA](http://weka.sourceforge.net/packageMetaData/fuzzyUnorderedRuleInduction/index.html) - Fuzzy Unordered Rule Induction Algorithm~~
- [x] ~~[HP](http://weka.sourceforge.net/doc.packages/hyperPipes/weka/classifiers/misc/HyperPipes.html) - HyperPipe classifier~~
- [x] [J48](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html) - C4.5 decision tree
- [x] [J48C](http://weka.sourceforge.net/packageMetaData/J48Consolidated/index.html) - C4.5 consolidated decision tree
- [x] [J48G](http://weka.sourceforge.net/doc.packages/J48graft/weka/classifiers/trees/J48graft.html) - C4.5 grafted decision tree
- [x] ~~[JRIP](http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/JRip.html) - Repeated Incremental Pruning to Produce Error Reduction~~
- [x] ~~[MLPCS](http://weka.sourceforge.net/doc.packages/multilayerPerceptronCS/weka/classifiers/functions/MultilayerPerceptronCS.html) - Multilayer perceptron with context-sensitive Multiple Task Learning~~
- [x] [MLP](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html) - Multilayer perceptron
- [x] [NBTREE](http://weka.sourceforge.net/doc.stable/weka/classifiers/trees/NBTree.html) - Decision tree with naive Bayes classifiers at the leaves
- [x] [RBF](http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFClassifier.html) - Radial basis function network
- [x] [RC](http://weka.sourceforge.net/doc.dev/weka/classifiers/meta/RandomCommittee.html) - Random committee
- [x] [RF](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomForest.html) - Random forests
- [x] [SGD](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html) - Stochastic gradient descent
- [x] [SPEGASOS](http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/SPegasos.html) - Stochastic Primal Estimated sub-GrAdient SOlver for SVM
- [x] [SVM](http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html) - Support vector machine

Métricas:
- [x] Porcentagem de acertos na classificação de hams (Ham Precision)
- [x] Porcentagem de revocação na classificação de hams (Ham Recall)
- [x] Porcentagem de acertos na classificação de spams (Spam Precision)
- [x] Porcentagem de revocação na classificação de hams (Spam Recall)
- [x] Tempo de treinamento (Training Time)
- [x] Tempo de classificação (Test Time)
- [x] ~~Uso de memória (Memory Usage)~~
