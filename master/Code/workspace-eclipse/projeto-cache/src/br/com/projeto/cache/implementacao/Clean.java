package br.com.projeto.cache.implementacao;

public interface Clean {
	
	
	public boolean limpar();
	public boolean limpar(Long identeificador);
	public boolean limpar(Class clazz,Long identeificador);
	public long limiteTempo();
	

}
