import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel{
	
	public LaminaMarcoCliente(){
		
		nick=new JTextField(5);
		
		add(nick);
	
		JLabel texto=new JLabel("CHAT");
		
		add(texto);
		
		ip=new JTextField(8);
		
		add(ip);
		
		chat=new JTextArea(12,20);
		
		add(chat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		EnviaTexto mievento=new EnviaTexto();
		
		miboton.addActionListener(mievento);
		
		add(miboton);	
		
	}
	
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.out.println(campo1.getText());
			try {
				Socket misocket=new Socket("192.168.56.1", 9999);
				
				Envio mensaje=new Envio();
				
				/*captura lo que haya dentro del cuadro de texto
				  nick y lo pasa a la variable nick. Lo mismo pasa con Ip y Texto
				 */
				mensaje.setNick(nick.getText());
				
				mensaje.setIp(ip.getText());
				
				mensaje.setTexto(campo1.getText());
				
				//Crea un flujo de datos para poder enviarlo al servidor
				ObjectOutputStream mensaje_envio=new ObjectOutputStream(misocket.getOutputStream());
				
				//Escribe los datos contenidos en las variables en el flujo
				mensaje_envio.writeObject(mensaje);
				
				misocket.close();
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			
		}
		
	}
	
	
	
		
		
		
	private JTextField campo1,nick,ip;
	
	private JTextArea chat;
	
	private JButton miboton;
	
}
/*Esta clase empaqueta todos los datos para así
  poder enviarlos
  Se serelializa para poder enviar los datos a traves de la red*/
class Envio implements Serializable{
	
	private String nick,ip,texto;
	
	/*getters y setters almacenan y permiten obtener 
	los datos contanidos en la variable*/

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String mensaje) {
		this.texto = mensaje;
	}
	
	
	
	
	
}

