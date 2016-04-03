package br.com.projeto.cache.implementacao;

import java.util.Random;

public class IdentificadorCache {

	private static IdentificadorCache instancia = new IdentificadorCache();
	private Random random = new Random();
	public static IdentificadorCache getInstancia() {

		return instancia;
	}
	
	public synchronized Long obterIdentificador() {
		
		return this.random.nextLong();
	}
}
