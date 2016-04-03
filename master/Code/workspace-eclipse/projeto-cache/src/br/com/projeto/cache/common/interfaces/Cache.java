package br.com.projeto.cache.common.interfaces;

import br.com.projeto.cache.common.CacheWrapper;

public interface Cache{

	public boolean adicionar(Long identificador,CacheWrapper objeto);
	public Object obter(Long identificador);
	
}
