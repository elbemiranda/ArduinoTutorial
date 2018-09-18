# ArduinoTutorial
Um tutorial simples de como iniciar com arduino medindo a temperatura e a luminosidade do ambiente.

# Medição de temperatura e luminosidade
 
Foi desenvolvido um projeto com Arduino para medir a temperatura ambiente como também a luminosidade local. Para isso, foi utilizado o midleware Javino que se comunica com o Arduino de forma serial (PySerial). Para iniciar esta comunicação, foi criada uma aplicação em Java que solicitar os dados de temperatura e luminosidade, e posteriormente gerar um gráfico dos resultados. Este projeto foi iniciado na aula de Computação Ubiqua do dia 04 de setembro de 2018, e foram realizados testes em um sistema MacOS e no Linux.
 
Primeiramente é necessário entendermos o que é o Arduino, que nada mais é do que uma placa composta por um microcontrolador Atmel programável, conectada a circuitos de entrada e saída. Esta placa pode ser conectada e programada utilizando um computador utilizando uma porta de comunicação e uma IDE (Integrated Development Environment). A linguagem da IDE do Arduino é baseada em C/C++, sem a necessidade de equipamentos extras além de um cabo USB.

Como mencionado anteriormente, para a comunicação com Arduino e leitura dos dados, foi utilizado o Javino, que é um protocolo que permite a troca de mensagem entre o Arduino e linguagens de programação de alto nível. É composto por uma biblioteca que realiza a comunicação serial, com suporte à detecção de erros através de uma rotina de verificação (protocolo de verificação) da recepção de dados. A comunicação estabelecida em um nível mais inferior, é estabelecida utilizando-se a biblioteca PySerial do Python versão 2.7.

Foi utilizada a IDE do Arduino para programar a placa controladora, chamar o Javino para estabelecer a comunicação Hardware com o Java, e capturar os dados de temperatura como também de luminosidade do ambiente.

Para este projeto foram utilizados diversos equipamentos além dos softwares já mencionados, a saber:
+ Placa Uno R3 + Cabo USB para Arduino
+ Placa Protoboard
+ Resistor 470 ohm
+ Fios
+ Capacitor
+ Sensor de Temperatura LM35DZ
+ Sensor de Luminosidade LDR 5mm
+ Computador

Os sensores foram implantados na protoboard, obedecendo às suas arquiteturas básicas de funcionamento, e conectados ao Arduino por fios. Foi utilizado um capacitor conectado ao GND e ao reset, e ligado após o carregamento do programa para o microcontrolador. Este procedimento foi realizado para estabelecer a comunicação do Java com o Arduino. O esquema de montagem do projeto pode ser verificado na Figura 1.


![Esquema das conexões dos sensores com o Arduino](https://github.com/elbemiranda/ArduinoTutorial/blob/master/ArduinoTutorialProject/Setup.png?raw=true)

Os testes foram realizados primeiramente em um notebook com sistema MacOs. A comunicação com a IDE do Arduino funcionou, porém foram encontradas dificuldades na comunicação do Java e Javino. Dessa forma, outros teste foram realizados em um notebook com Linux, realizadas as mesmas configurações com sucesso.
Sendo assim, foi possível estabelecer a comunicação entre o Java, Javino e Arduino, coletando os dados dos sensores de temperatura ambiente e luminosidade local, e gerado os gráficos.
# Tutorial
Um tutorial simples de como iniciar com arduino medindo a temperatura e a luminosidade do ambiente.
# 1. Instalação do Arduino:
Acesse a página de download do Arduino em: https://www.arduino.cc/en/main/software e escolha o instalador adequado ao seu sistema operacional;
# 2. Instalação do Python 2.7:
Instale a versão 2.7 do Python disponível em https://www.python.org/downloads/
Para verificar se o Python foi instalado com sucesso, digite o comando:
```
python --version
```
Se o comando retornar a versão do Python, tudo foi instalado corretamente!
Após a instalção do Python, instale o pip.
Agora, usando o pip, execute o seguinte comando para instalar o pySerial:
```
pip install pySerial
```
# 3. Configurado o Arduino
Abra o Arduino e vá no menu *Sketch*, *Incluir Biblioteca*, *Adicionar Biblioteca .Zip*;
Escolha o arquivo **Javino1.1.zip** e clique em OK;
Vá novamente no menu *Sketch*, *Incluir Biblioteca* e selecione **Javino**;
Agora vá ao menu *Ferramentas*, *Placas* e selecione o modelo da sua placa;
Por fim vá ao menu *Ferramentas*, *Porta* e selecione a porta serial onde o hardware está conectado.

# 4. Criando um novo Sketch
Digite o seguinte código na janela de código aberta do sketch:
```cpp
#include <Javino.h>
Javino j;

int LM35 = A5;
int LDR = A1;

void setup() {
  Serial.begin(9600);
}

void loop() {

   String message = "";

   if(j.availablemsg()) {
    message = j.getmsg();
   }

   if (message == "reqTemp") {
    j.sendmsg(getTemp());
    
   }

   
   if (message == "reqLight") {
    //j.sendmsg("passou");
    j.sendmsg(getLight());
    
   }
}

String getLight() {
  return String(analogRead(LDR));
}
  
String getTemp() {
  double tempSensor = (analogRead(LM35)*10)*0.0488759;
  char tempString[10];
  dtostrf(tempSensor,3,2,tempString);
  return tempString;
}
```
Agora vá ao menu *Ferramentas*, *Monitor Serial* e digite o código:
```
fffe07reqTemp
```
Você deverá obter um valor de saída da temperatura medida pelo sensor.
Digite agora:

```
fffe08reqLight
```
Novamente, a saída deverá apresentar um resultado contendo a leitura do sensor de luz.
# Criando um projeto Arduino Java usando o JAVINO
Agora vamos criar um novo projeto no NetBeans, do tipo **Aplicação Java**
Insira a biblioteca **javino_stable_1.1.jar**
Insira a biblioteca **xchart-3.5.2.jar**
Na classe principal, digite o seguinte código:

```java
import br.pro.turing.javino.Javino;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class ArduinoTutorialProject {
    
    public static Javino javino = new Javino("/usr/bin");
    public static String port = "/dev/ttyACM2";
    
    public static void main(String[] args) {
        
        double[][] lightInitData = getArduinoData("reqLight");
        double[][] tempInitData = getArduinoData("reqTemp");

        // Create Light Chart
        final XYChart lightChart = QuickChart.getChart("Arduino Light Monitor", "Time", "Light", "light", lightInitData[0], lightInitData[1]);

        // Show Light Chart
        final SwingWrapper<XYChart> swLight = new SwingWrapper<XYChart>(lightChart);
        swLight.displayChart();

        // Create Temperature Chart
        final XYChart tempChart = QuickChart.getChart("Arduino Temperature Monitor", "Time", "Temp", "temp", tempInitData[0], tempInitData[1]);

        // Show Temperature Chart
        final SwingWrapper<XYChart> swTemp = new SwingWrapper<XYChart>(tempChart);
        swTemp.displayChart();
        
        while (true) {
            final double[][] lightData = getArduinoData("reqLight");
            lightChart.updateXYSeries("light", lightData[0], lightData[1], null);
            swLight.repaintChart();
            
            final double[][] TempData = getArduinoData("reqTemp");
            tempChart.updateXYSeries("temp", TempData[0], TempData[1], null);
            swTemp.repaintChart();
        }
    }
    
    private static double[][] getArduinoData(String command) {
        
        double[] xData = new double[100];
        double[] yData = new double[100];
        for (int i = 0; i < xData.length; i++) {
            javino.sendCommand(port, command);
            javino.listenArduino(port);
            String data = javino.getData();
            if (data != null) {
                xData[i] = i;
                yData[i] = Double.parseDouble(data);
                System.out.println(data);
            }
        }
        return new double[][]{xData, yData};
    }
}

```
Agora clique em *Executar* para ver o seguinte resultado:
![Light](https://github.com/elbemiranda/ArduinoTutorial/blob/master/ArduinoTutorialProject/Light.png)
![Temperature](https://github.com/elbemiranda/ArduinoTutorial/blob/master/ArduinoTutorialProject/Temp.png)
