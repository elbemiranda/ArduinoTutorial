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


Figura 1: Esquema das conexões dos sensores com o Arduino.

Os testes foram realizados primeiramente em um notebook com sistema MacOs. A comunicação com a IDE do Arduino funcionou, porém foram encontradas dificuldades na comunicação do Java e Javino. Dessa forma, outros teste foram realizados em um notebook com Linux, realizadas as mesmas configurações com sucesso.
Sendo assim, foi possível estabelecer a comunicação entre o Java, Javino e Arduino, coletando os dados dos sensores de temperatura ambiente e luminosidade local, e gerado os gráficos.
