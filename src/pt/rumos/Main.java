/**
  * CCReader é um utilitário concebido pela Rumos e disponibilizada à comunidade para demonstrar o uso da API do cartão do cidadão da República Portuguesa.
  * Desenvolvida por Gonçalo Silva.
  * 
  * Esta aplicação tambem guarda os dados do cartão em dois ficheiros no computador, na mesma pasta que o jar.
  * ---------------------------------------------------------------------------------------------------------------------------------------------------------------
  * CCReader is a utility designed for Rumos and made available to the community to demonstrate the use of the citizen card of the Portuguese Republic API.
  * Developed by Gonçalo Silva.
  *
  * This application also stores the card data in two files on the computer, in the same folder as the jar.
  */

package pt.rumos;

import java.awt.EventQueue;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class Main 
{
	protected JFrame mainFrame;
	private CartaoCidadao cardReader;
	private boolean cardPrestent = false;
	private JLabel text;

	private CardTerminal smartCardReader = null;
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Main window = new Main();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() { initialize(); }

	/**
	 * Responsável por inicializar a aplicação.
	 * ---------------------------------------------
	 * Responsible for initializing the application.
	 */
	private void initialize() 
	{
		cardReader = new CartaoCidadao(false);

		mainFrame = new JFrame("Leitor de Cartões do Cidadão");
		mainFrame.setResizable(false);
		mainFrame.setSize(350, 60);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Fecha todos os processos relacionados com esta aplicação
		mainFrame.getContentPane().setLayout(new MigLayout("", "[]", "[]"));
		
		text = new JLabel();
		
		text.setText("A aguardar por um cartão.");
		mainFrame.add(text);

		Timer clock = new Timer();
		
		TerminalFactory factory = TerminalFactory.getDefault();//Obtem acesso aos leitores de cartões presentes no computador.
		List<CardTerminal> terminals;
		try 
		{
			terminals = factory.terminals().list();//Cria uma lista com os leitores.
			smartCardReader = terminals.get(0);//Utiliza o primeiro leitor encontrado na lista.

			clock.schedule(new TimerTask() 
			{
				@Override
				public void run() 
				{
					try {
						if (!smartCardReader.isCardPresent()) 
						{//Se não existir nenhum cartão no leitor. 
							if (cardPrestent) cardReader.dataGetted = false;
							cardPrestent = false;
							text.setText("A aguardar por um cartão.");
						} else {
							if (!cardPrestent) 
							{//Se o último estádo do leitor for cartão não presente, evitando assim uma dupla leitura do cartão.
								text.setText("A lêr cartão.");
								cardPrestent = true;
								cardReader = new CartaoCidadao();//Lê os dados do cartão iníciando assim a classe conectora à API Portuguesa.
								if(cardReader.dataGetted)
								{
									cardReader.saveData("./");//Guarda os dados do cartão na mesma pasta onde se encontra o aplicativo jar.
									text.setText("Cartão lido com sucesso.");
								}								
							}
						}
					} catch (CardException exception) { 
						cardReader.errorMessage("Lertor de cartões removido.");
						Runtime.getRuntime().exit(0);					
					}
				}
			}, 0, 1000);//Verifica o estádo do leitor a cada segundo.
		} catch (CardException exception) { cardReader.errorCC(CartaoCidadao.NO_READERS_FOUND, exception.toString()); }
	}
}