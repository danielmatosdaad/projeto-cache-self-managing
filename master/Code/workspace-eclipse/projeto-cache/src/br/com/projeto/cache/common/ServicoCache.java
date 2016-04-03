package br.com.projeto.cache.common;

import br.com.projeto.cache.template.GerenteCache;

public class ServicoCache {


	private static ServicoCache instancia = new ServicoCache();
	private GerenteCache gerenteCache;
	
	private ServicoCache(){
		
		gerenteCache = new GerenteCache();
	}
	public boolean add(Class clazz,Object objeto, long identificador) {

		CacheWrapper sw = new CacheWrapper(objeto, objeto.getClass());
		
		return gerenteCache.getCacheClass(clazz).adicionar(identificador, sw);
	}

	public Object get(Class clazz,long identificador) {
		
		CacheWrapper sw =(CacheWrapper)gerenteCache.getCacheClass(clazz).obter(identificador);
		return sw.getObjetoSincronizado_();
	}

	public static ServicoCache getInstancia() {

		return instancia;
	}
}
