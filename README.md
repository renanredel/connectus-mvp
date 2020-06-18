# ConnectUS - Protótipo

Projeto pessoal antigo (2017), que visa transmitir dados (redes sociais, e-mail, telefone etc.) entre duas pessoas através do NFC do aparelho.
Protótipo desenvolvido durante o primeiro semestre de 2019.

## Funcionamento

Cada usuário logado, gera um código aleatório único. Com esse código, é armazenado os dados cadastrados num servidor local. Ao efetuar a comunicação com outro dispositivo através do NFC, é transmitido o código único do usuário. Após recebido o código único, é realizado o acesso ao servidor e obtida as informações.
Para realizar a comunicação entre dois dispositivos, ambos devem ter o aplicativo instalado, e estarem conectados à internet.



### Créditos

| Autor | Biblioteca |
 ------ | ----------
 | gaoyangcr7 | [BiometricPromptDemo][a1]

   [a1]: <https://github.com/gaoyangcr7/BiometricPromptDemo>