package br.com.projeto.cache.implementacao;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import br.com.projeto.cache.common.CacheWrapper;
import br.com.projeto.cache.common.interfaces.Cache;

public class CachePrimeiroNivel extends AbstractCache{

	public static final long TEMPO_MAXIMO_CACHE = 20000;


	private Map<Long, CacheWrapper> mapaInstancia_ = new Hashtable<Long, CacheWrapper>();

	

	public CachePrimeiroNivel(Long identificadorCache,Cache cacheSegundoNivel) {

		super.identificadorCache=identificadorCache;
		super.proximoNivelCache = cacheSegundoNivel;
		
	}

	@Override
	public synchronized boolean adicionar(Long identificador, CacheWrapper objeto) {

		if (obter(identificador) == null) {
			
			System.out.println("Cache primeiro nivel nao existia o objeto");
			this.mapaInstancia_.put(identificador, objeto);
			System.out.println("Cache primeiro nivel adicionado objeto");
			return true;

		}
		
		return false;

	}

	@Override
	public Object obter(Long identificador) {
		
		//primeira busca
		System.out.println("primeira busca");
		Object o = this.mapaInstancia_.get(identificador);
		if (o == null) {
			//segunda busca
			System.out.println("segunda busca");
			o = super.proximoNivelCache.obter(identificador);
			if(o!=null){
				
			System.out.println("Objeto obtido em segundo nivel");
			if(o instanceof CacheWrapper){
				
				this.mapaInstancia_.put(identificador, (CacheWrapper) o);
				System.out.println("Objeto colocado novamente em primeiro nivel");
				
			}
	
			}
			
		}else{
			
			System.out.println("Objeto achado em primeiro nivel");
			
		}
		return o;
	}

	

	public boolean passivar() {
		
		System.out.println("Cache primeir nivel passivar");
		Set<Long> identificadores = this.mapaInstancia_.keySet();
		int tamanhoInical = identificadores.size();
		for (Long identificador : identificadores) {
			
			CacheWrapper sw = this.mapaInstancia_.get(identificador);
			long tempoCorrente = Calendar.getInstance().getTimeInMillis();  
			long tempoMomentoSincronismoObjeto = sw.getCalendar_().getTimeInMillis();
			System.out.println((tempoCorrente - tempoMomentoSincronismoObjeto));
			if((tempoCorrente - tempoMomentoSincronismoObjeto) > TEMPO_MAXIMO_CACHE){
			
				System.out.println("Cache primeiro nivel nivel removido objeto");
				if(super.proximoNivelCache.adicionar(identificador, sw)){
					
					mapaInstancia_.remove(identificador);
					System.out.println("Objeto removido primeiro nivel nivel");
					
				}
			}
		}

		return identificadores.size() < tamanhoInical ? true : false;
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
			
			((Clean)super.proximoNivelCache).limpar();
			
		}
		return passivar();
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
			
			((Clean)super.proximoNivelCache).limpar();
			
		}
		return passivar();
	}


}
