# AutomationPractice

Exemplo de teste automatizado usando de exemplo o site [automationpractice](http://automationpractice.com/index.php).

## Instalação

Use o [apache-maven-3.5.3](https://archive.apache.org/dist/maven/maven-3/3.5.3/) para construir o projeto. Baixe uma versão apropriada do [ChromeDriver](http://chromedriver.chromium.org/downloads) conforme a versão do browser Chrome instalado em sua máquina e copie-a para a pasta ./drivers.

## Uso

Edite os arquivos de properties na pasta ./config para escolher executar:
- uma vez só ou em loop (loop.properties);
- em série, com 1 thread somente, ou em paralelo, definindo o número máximo de threads (loop.properties);
- localmente ou remotamente via SeleniumGrid (execution.properties);
- executar somente até que haja o primeiro sucesso, ou ignorar o resultado das execuções anteriores (execution.properties);

O projeto pode ser agendado para ser construído e executado via Jenkins com:

```bash
mvn clean verify
```

O projeto pode ser facilmente escalado para executar uma grande quantidade de testes, salvando capturas de tela em ./evidencias, e salvando os dados de entrada durante o teste em ./dados/CadastroDeCliente.xlsx.

Se em execution.properties, verificarResultadoAnterior=true, então não só será verificado o status **ok/nok** da última execução, mas também será registrado o status da presente execução.

Para testes que exigirem login de usuário, popule o arquivo ./dados/Usuarios.xlsx.

Com poucos ajustes, o projeto pode ser adaptado para ler massa de dados e registrar status de execução via banco de dados.
