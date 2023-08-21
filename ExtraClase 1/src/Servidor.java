
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.io.*;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread hilo=new Thread(this);
		
		hilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	/*Este metodo mantiene abierto el puerto y lo escucha
	  en segundo plano
	 */
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("ESCUCHANDO");
		try {
			//Abre el puerto
			ServerSocket servidor=new ServerSocket(9999);
			
			String nick,ip,texto;
			
			Envio mensaje_recibido;
			
			while(true) {
			
			//Acepta las conexiones
			Socket misocket=servidor.accept();
			//Recibe el flujo de datos
			ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
			//Mete los datos recibos dentro del flujo
			mensaje_recibido=(Envio) flujo_entrada.readObject();
			//Se obtienen los datos contenidos en el flujo
			nick=mensaje_recibido.getNick();
			ip=mensaje_recibido.getIp();
			texto=mensaje_recibido.getTexto();
			
			areatexto.append("\n"+nick+": "+texto+" (para "+ip+")");
			
			//Socket para enviar los datos al destinatario
			Socket destinatario=new Socket(ip,9090);
			//Para enviar el paquete
			ObjectOutputStream mensaje_saliente=new ObjectOutputStream(destinatario.getOutputStream());
			//Mete los datos recibidos en el paquete saliente
			mensaje_saliente.writeObject(mensaje_recibido);
			//Cierra el socket que comunica el puerto de salida
			destinatario.close();
			
			//Cierra la conexion
			misocket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

