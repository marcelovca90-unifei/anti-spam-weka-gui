#### 1. Clonando e construindo o projeto principal ([anti-spam-weka](https://github.com/marcelovca90/anti-spam-weka)):

`git clone https://github.com/marcelovca90/anti-spam-weka`

`cd anti-spam-weka/AntiSpamWeka && mvn clean install`

#### 2. Clonando e extraindo os conjuntos de dados ([anti-spam-weka-data](https://github.com/marcelovca90/anti-spam-weka-data)):

`git clone https://github.com/marcelovca90/anti-spam-weka-data`

`cd anti-spam-weka-data/2017_BASE2 && cat 2017_BASE2.zip.part-* > 2017_BASE2.zip && unzip 2017_BASE2.zip`

`cd anti-spam-weka-data/2017_MULT10 && cat 2017_MULT10.zip.part-* > 2017_MULT10.zip && unzip 2017_MULT10.zip`

#### 4. [LINUX] Instalando a biblioteca de álgebra linear de alta performance [netlib-java](https://github.com/fommil/netlib-java):

`sudo apt-get install libatlas3-base libopenblasbase`

`sudo update-alternatives --config libblas.so`

`sudo update-alternatives --config libblas.so.3`

`sudo update-alternatives --config liblapack.so`

`sudo update-alternatives --config liblapack.so.3`

#### 3. Configurando e executando os treinamentos e/ou testes

`cd anti-spam-weka/AntiSpamWeka/script && vi run.sh && ./run.sh`
