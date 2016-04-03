package br.com.projeto.cache.cliente.teste;

import br.com.projeto.cache.common.ServicoCache;

public class Main {

	
	public static void main(String args[]) throws InterruptedException{
		
		Criptografia c = new Criptografia();
		c.setId(1987);
		c.setDescricao("aniversario");
		long identificador =19l;
		ServicoCache.getInstancia().add(Criptografia.class,c,identificador);
		Thread.sleep(10000);

		Criptografia c2 = (Criptografia)ServicoCache.getInstancia().get(Criptografia.class, identificador);
		if(c2!=null){
			
			System.out.println(c2.getId());
			System.out.println(c2.getDescricao());
		}else{
			
			System.out.println("Objeto nullo");
		}
		
		
		Thread.sleep(30000);
		
		Criptografia c3 = (Criptografia)ServicoCache.getInstancia().get(Criptografia.class, identificador);	
		if(c3!=null){
				
				System.out.println(c3.getId());
				System.out.println(c3.getDescricao());
			}else{
				
				System.out.println("Objeto nullo");
			}
		
		Thread.sleep(10000);
	    Criptografia c4 = (Criptografia)ServicoCache.getInstancia().get(Criptografia.class, identificador);	
	if(c4!=null){
			
			System.out.println(c4.getId());
			System.out.println(c4.getDescricao());
		}else{
			
			System.out.println("Objeto nullo");
		}

	
}
}
