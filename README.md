# CardBattle
## 1. Testes Unit�rios
Atualmente, os testes unit�rios cobrem apenas a l�gica do CardBattle.

N�o s�o cobertos os casos de teste do Servlet.

## 2. Configura��o e execu��o do Servlet
Para executar o Servlet do CardBattle no **Eclipse**, siga os passos abaixo:

1. Eclipse: Help > Eclipse Marketplace
2. Search: "Jetty"
3. Instale "Eclipse Jetty 3.9.0" e reinicie o Eclipse
4. Right-click no projeto "CardBattle" > Run As > Run with Jetty
5. No seu navegador, acesse a URL: **http://localhost:8080/CardBattle**
6. Dever� aparecer "**CardBattle Server is running**" seguido da data e hora do servidor.

**Qualquer altera��o no projeto deve interromper e reiniciar o Jetty para surtir efeito.**

(Opcional) Prefer�vel se for tornar a URL p�blica: ap�s certificar que est� OK, altere a porta desse servidor.

1. Right-click no projeto "CardBattle" > Run As > Run configurations...
2. Selecione Jetty Webapp > CardBattle (1)
3. Na aba WebApp, altere HTTP / HTTPs Port para **666** ou alguma outra porta espec�fica.
4. Right-click no projeto "CardBattle" > Run As > Run with Jetty
5. No seu navegador, acesse a URL: http://localhost:**666**/CardBattle
6. Dever� aparecer "CardBattle Server is running" seguido da data e hora do servidor.

## 3. Configura��o do roteador para tornar a URL p�blica
Esses passos s�o opcionais para tornar a URL privada do seu servlet p�blica.

### 3.1. Tornando o IP da sua m�quina est�tico
Essas configura��es s�o para um roteador TP-Link.

1. Entre na configura��o do seu roteador (ex: http://192.168.1.1)
2. Mapeie o IP da sua m�quina na rede como est�tico, e n�o din�mico
    1. Advanced Settings > IP & MAC Binding
    2. Add New...
        1. Bind: OK
        2. MAC Address: endere�o MAC do seu PC, ex: AB-CD-12-34-56-78
  (Voc� pode ver o endere�o em Basic Settings > Network > MAC Clone.)
        3. IP Address: algum IP fixo na sua rede, ex: 192.168.1.**199**
        4. Save
3. Desconecte seu PC da sua rede wifi e reconecte.
4. Verifique se seu PC est� com o IP designado com IPCONFIG:
5. Eclipse: Right-click no projeto "CardBattle" > Run As > Run with Jetty
6. No seu navegador, acesse a URL: http://**192.168.1.199**:666/CardBattle
7. Dever� aparecer "CardBattle Server is running" seguido da data e hora do servidor.

 
### 3.2. Configurando Port Redirect
Essas configura��es s�o para um roteador TP-Link.

1. Ainda na configura��o do seu roteador (http://192.168.1.1)...
2. Advanced Settings > Forwarding > Virtual Servers
3. Add New...
    1. Service Port: o n�mero de porta espec�fico do seu Servlet (Ex: **666**)
    2. IP Address: o IP da sua m�quina (Ex: **192.168.1.199**)
    3. Protocol: ALL
    4. Status: ENABLED
    5. Common Service Port: N�O MEXER
    6. Save
4. Verifique qual � o IP p�blico do seu roteador (Menu: "Status", WAN: IP Address, ex: **179.159.2.145**)
5. Eclipse: Right-click no projeto "CardBattle" > Run As > Run with Jetty
6. No seu navegador, acesse a URL: http://**179.159.2.145:666**/CardBattle
7. Dever� aparecer "CardBattle Server is running" seguido da data e hora do servidor.