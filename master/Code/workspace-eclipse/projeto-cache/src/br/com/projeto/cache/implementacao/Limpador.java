package br.com.projeto.cache.implementacao;

import java.util.ArrayList;
import java.util.List;

public class Limpador implements Runnable{

	private static boolean ativo;
	private static Limpador l = new Limpador();
	private static List<Clean> cleans = new ArrayList<Clean>();
	public static long INTERVALO_EXECUCAO = 20000l;
	
	static{
		
		Thread t = new Thread(l);
		Limpador.ativo=true;
		t.start();
	}
	
	public static void incluirServico(Clean clean){
		
		if(Limpador.ativo==true){
			
			cleans.add(clean);
		}
	}

	@Override
	public void run() {
		
		
		while (Limpador.ativo==true) {
			
			
			try {
				Thread.sleep(INTERVALO_EXECUCAO);
				System.out.println(Limpador.class +" quantiade objeto cache para limpar: " + cleans.size());
				System.out.println(Limpador.class +" INTERVALO_EXECUCAO: " + INTERVALO_EXECUCAO);
				for (Clean c :cleans) {
					
					
					  c.limpar();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
