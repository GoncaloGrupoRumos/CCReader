# CCReader - Leitor de Cartões do Cidadão
>Neste pequeno aplicativo podemos encontrar um método de utilização da API do cartão do Cidadão da República Portuguesa.
>De modo a facilitar a sua utilização para projetos em java, foram criadas duas classes em java:
>### Mail.java
>>Demonstra como utilizar as classes do javax:
>>* javax.smartcardio.CardException - responsável por reportar os problemas com a deteção do sensor.
>>* javax.smartcardio.CardTerminal - responsável por detetar os leitores que estão conectados no computador.
>>* javax.smartcardio.TerminalFactory - responsável por detetar o cartão no leitor.

>### CartaoCidadao.java
>>Esta classe pode ser utilizada para conectar-se ao middleware desenvolvido pela Républica Portuguesa, desenvolvida de modo a facilitar a >>útilização do cartão do cidadão e recolha dos seus dados.

>### [Codacy Review](https://www.codacy.com/app/Rumos/CCReader/dashboard)

## Requisitos
>* [Java Versão 8 ou Superior](https://www.java.com/en/download/)
>* [Software do Cartão do Cidadão](https://www.cartaodecidadao.pt/index.php_option=com_content&task=view&id=102&Itemid=44&lang=pt.html)

## Observações
>Também é explicado como se pode guardar uma imagem com o formato de jpeg200 convertendo-a para jpg, para tal foi utilizada a classe **com.idrsolutions.image.jpeg2000.Jpeg2000Decoder** da livraria jdeli.
```
Jpeg2000Decoder imageDecoder = new Jpeg2000Decoder();
BufferedImage bufferedPhoto = null;
try 
{
	bufferedPhoto = imageDecoder.read(picture);
} catch (Exception e) {
	e.printStackTrace();
}
```
