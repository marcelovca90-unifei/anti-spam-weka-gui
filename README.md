# Universidade Federal de Itajubá
## Programa de Pós-graduação em Ciência da Computação

**Área de Concentração:** Matemática da Computação

**Linha de Pesquisa:** Inteligência Artificial

**Projeto:** Estudo e Pesquisa em Sistemas Anti-Spam

**Orientador:** Otávio Augusto Salgado Carpinteiro

###TO-DO LIST:

Início:
- [x] Ler trabalhos anteriores
- [x] Ler artigos sobre o assunto
- [x] Estudar a biblioteca Weka
- [ ] Salvar e carregar modelos do Weka

Bases de dados:
- [x] LingSpam (de 10 a 100 features - CHI2, DF e MI)
- [x] SpamAssassin (de 10 a 100 features - CHI2, DF e MI)
- [x] TREC (de 10 a 100 features - CHI2, DF e MI)
- [x] Unifei (de 10 a 100 features - CHI2, DF e MI)
- [ ] Quantidade de features proporcional aos padrões zerados

Métodos de classificação:
- [x] [J48](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html) - C4.5 decision tree
- [x] [MLP](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html) - Multilayer Perceptron com backpropagation e momentum
- [x] [RBF](http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFClassifier.html) - Radial Basis Function Network com os algoritmos BFGS e K-Means
- [x] [RF](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html) - Random Forests
- [x] [SGD](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html) - Stochastic Gradient Descent
- [x] [SVM](http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html) - Support Vector Machine, baseada na LibSVM

Implementar as seguintes métricas:
- [x] Porcentagem de acertos na classificação de hams
- [x] Porcentagem de acertos na classificação de spams
- [x] Tempo de treinamento
- [x] Tempo de classificação

Fim:
- [ ] **Melhorar e investigar aleatoriedade dos resultados**
- [ ] Comparar os resultados com trabalhos anteriores
- [ ] Escrever artigo
- [ ] Escrever dissertação
