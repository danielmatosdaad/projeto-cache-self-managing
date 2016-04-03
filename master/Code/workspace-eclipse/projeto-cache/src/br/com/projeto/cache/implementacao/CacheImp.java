package br.com.projeto.cache.implementacao;

import br.com.projeto.cache.common.CacheWrapper;
import br.com.projeto.cache.common.interfaces.Cache;

public class CacheImp extends AbstractCache{
	
	public static final long TEMPO_MAXIMO_CACHE = 20000;

	public CacheImp() {

		super.identificadorCache = IdentificadorCache.getInstancia().obterIdentificador();
		super.proximoNivelCache = new CachePrimeiroNivel(identificadorCache,new CacheSegundoNivel(identificadorCache));
		Limpador.incluirServico((Clean)this);
	}

	@Override
	public boolean adicionar(Long identificador, CacheWrapper objeto) {
		
		if(obter(identificador)==null){
			
			super.proximoNivelCache.adicionar(identificador, objeto);
			return true;
		
		}
		
		return false;
	}

	@Override
	public Object obter(Long identificador) {

		Object o =buscarEmPrimeiroNivel(identificador);
		
		return o;
	}
	
	private Object buscarEmPrimeiroNivel(Long identificador){
		
		return super.proximoNivelCache.obter(identificador);
	}

	@Override
	public long limiteTempo() {
		// TODO Auto-generated method stub
		return this.TEMPO_MAXIMO_CACHE;
	}

	@Override
	public boolean limpar() {

		System.out.println("Cache primeiro nivel limpar");
		if(super.proximoNivelCache instanceof Clean){
			
			return ((Clean)super.proximoNivelCache).limpar();
			
		}
		return false;
	}

	@Override
	public boolean limpar(Class clazz,Long identeificador) {
		

		System.out.println("Cache primeir nivel limpar");
		if(super.proximoNivelCache instanceof Clean){
			
			((Clean)super.proximoNivelCache).limpar(clazz,identeificador);
			
		}
		
		return false;
	}
	
	@Override
	public boolean limpar(Long identeificador) {
		
		System.out.println("Cache primeir nivel limpar");
		if(super.proximoNivelCache instanceof Clean){
			
			return ((Clean)super.proximoNivelCache).limpar();
			
		}
		return false;

	}	


}
