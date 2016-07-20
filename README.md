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
- [x] [J48 - C4.5 decision tree](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html)
- [x] [MLP - Multilayer Perceptron com backpropagation e momentum] (http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html)
- [x] [RBF - Radial Basis Function Network usando o método BFGS para treinamento e K-Means para determinar os parâmetros iniciais das funções de base radial](http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFClassifier.html)
- [x] [RF - Random Forests](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html)
- [x] [SGD - Stochastic Gradient Descent](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html)
- [x] [SVM - Support Vector Machine, baseada na LibSVM] (http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html)

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
