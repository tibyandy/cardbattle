# CardBattle

## 1. Informações gerais
### 1.1. Testes Unitários
Atualmente, os testes unitários cobrem apenas a lógica de execução da batalha.

### 1.2. Histórico de versões
O histórico de versões pode ser visualizado em:
https://raw.githubusercontent.com/tibyandy/cardbattle/master/web/version_history.txt


## 2. Configuração e execução do Servlet
Para executar o Servlet do CardBattle via **Jetty no Eclipse**, siga os passos abaixo:

1. Eclipse: Help > Eclipse Marketplace
2. Search: "Jetty"
3. Instale "Eclipse Jetty 3.9.0" e reinicie o Eclipse
4. Right-click no projeto "CardBattle" > Run As > Run with Jetty
5. No seu navegador, acesse a URL: **http://localhost:8080**
6. Deverá aparecer "CardBattleServer is up and running".

**Qualquer alteração no projeto deve interromper e reiniciar o Jetty para surtir efeito.**
Não é necessário se estiver em modo debug (hot-swap), exceto quando a estrutura das classes é alterada.

(Opcional) Preferível se for tornar a URL pública: após certificar que está OK, altere a porta desse servidor.

1. Right-click no projeto "CardBattle" > Run As > Run configurations...
2. Selecione Jetty Webapp > CardBattle (1)
3. Na aba WebApp, altere HTTP / HTTPs Port para **8080** ou alguma outra porta específica.
4. Right-click no projeto "CardBattle" > Run As > Run with Jetty
5. No seu navegador, acesse a URL: http://localhost:**8080**
6. Deverá aparecer "CardBattleServer is up and running".

## 3. Configuração do roteador para tornar a URL pública
Esses passos são opcionais para tornar a URL privada do seu servlet pública.

### 3.1. Tornando o IP da sua máquina estático
Essas configurações são para um roteador TP-Link.

1. Entre na configuração do seu roteador (ex: http://192.168.1.1)
2. Copie o endereço MAC da placa de rede do seu PC. (Basic Settings > Network > MAC Clone)
3. Mapeie o IP da sua máquina na rede como estático, e não dinâmico
    1. Advanced Settings > IP & MAC Binding
    2. Add New...
        1. Bind: OK
        2. MAC Address: endereço MAC da placa de rede seu PC, ex: AB-CD-12-34-56-78
        3. IP Address: algum IP fixo na sua rede, ex: 192.168.1.**199**
        4. Save
4. Desconecte seu PC da sua rede (Wifi ou não) e reconecte.
5. Verifique se seu PC está com o IP designado com IPCONFIG:
6. Eclipse: Right-click no projeto "CardBattle" > Run As > Run with Jetty
7. No seu navegador, acesse a URL: http://**192.168.1.199**:8080
8. Deverá aparecer "CardBattleServer is up and running".

 
### 3.2. Configurando Port Redirect
Essas configurações são para um roteador TP-Link.

1. Ainda na configuração do seu roteador (http://192.168.1.1)...
2. Advanced Settings > Forwarding > Virtual Servers
3. Add New...
    1. Service Port: o número de porta específico do seu Servlet (Ex: **8080**)
    2. IP Address: o IP da sua máquina (Ex: **192.168.1.199**)
    3. Protocol: ALL
    4. Status: ENABLED
    5. Common Service Port: NÃO MEXER
    6. Save
4. Verifique qual é o IP público do seu roteador (Menu: "Status", WAN: IP Address, ex: **179.159.2.145**)
5. Eclipse: Right-click no projeto "CardBattle" > Run As > Run with Jetty
6. No seu navegador, acesse a URL: http://**179.159.2.145:8080**
7. Deverá aparecer "CardBattleServer is up and running".