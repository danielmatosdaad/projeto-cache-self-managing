package br.com.projeto.cache.implementacao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.projeto.cache.common.CacheWrapper;
import br.com.projeto.cache.implementacao.objeto.passivado.SerializacaoObjeto;

public class CacheSegundoNivel extends AbstractCache {

	public static final long TEMPO_MAXIMO_CACHE = 30000;
	public Map<Long,TempoSerelizacaoClass> objetoPassivados = new HashMap<Long,TempoSerelizacaoClass>();

	public CacheSegundoNivel(Long identificadorCache) {

		super.identificadorCache = identificadorCache;

	}

	private  List<CacheWrapper> carregarTodosObjetosSerizalizados() {

		List<CacheWrapper> listaCacheWrapper= new ArrayList<CacheWrapper>();
		
		for(Entry<Long,TempoSerelizacaoClass> entry: objetoPassivados.entrySet()){
			CacheWrapper cw =(CacheWrapper) obter(entry.getKey());
			
			if(cw!=null){
				listaCacheWrapper.add(cw);
			}
			
		}
		objetoPassivados.clear();
		return listaCacheWrapper;
	}
	
	public void excluirTodosObjetosSerizalizados() {

		SerializacaoObjeto.deletarTodos();
		this.objetoPassivados.clear();
		
	}

	@Override
	public synchronized boolean adicionar(Long identificador, CacheWrapper objeto) {

		if (!objetoPassivados.containsKey(identificador)) {

			boolean isSucesso = passivar(objeto.getObjetoSincronizado_(), identificador);
			if (isSucesso) {
				TempoSerelizacaoClass tsc = new TempoSerelizacaoClass( objeto.getClazz_(),Calendar.getInstance());
				this.objetoPassivados.put(identificador, tsc);
				System.out.println("Cache segundo nivel adicionado objeto");
			}
			return isSucesso;
		}else{
			
			System.out.println("Objeto ja existe em segundo nivel");
		}

		return false;

	}

	@Override
	public Object obter(Long identificador) {

		if (objetoPassivados.containsKey(identificador)) {

			System.out.println("Cache segundo nivel  existi o objeto: " + identificador);
			try {

				Object objeto = SerializacaoObjeto.desserializar(objetoPassivados.get(identificador).getClazz_(),identificador);
				boolean isSucesso = objeto!=null ? true:false;

				System.out.println("Objeto desserilzado: " + isSucesso);
				if (isSucesso) {
					TempoSerelizacaoClass tsc = this.objetoPassivados.remove(identificador);
					SerializacaoObjeto.deletar(tsc.getClazz_(), identificador);
					System.out.println("Deletado objeto cache em segundo nivel");
					this.objetoPassivados.remove(identificador);
					System.out.println("Removendo objeto cache em segundo nivel");
					return new CacheWrapper(objeto, tsc.getClazz_());
				}
				
				return null;
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean limpar() {
		// TODO procurar todos objeto serializados que ultrapassaram o tempo maximo e deletar
		
		
		System.out.println("Cache segundo nivel limpar");
		for( Entry<Long,TempoSerelizacaoClass> entry :this.objetoPassivados.entrySet()){
			Long identificador =entry.getKey();
			TempoSerelizacaoClass tsc = entry.getValue();
			System.out.println("objeto em segundo nivel:" + identificador);
			long tempoCorrente = Calendar.getInstance().getTimeInMillis();  
			long tempoMomentoCacheObjeto = tsc.getDatetime_().getTimeInMillis();
			if((tempoCorrente - tempoMomentoCacheObjeto) > TEMPO_MAXIMO_CACHE){
			
				System.out.println("Cache segundo nivel acho objeto com tempo demais");
				if(SerializacaoObjeto.deletar(tsc.getClazz_(), identificador)){
					objetoPassivados.remove(identificador);
					System.out.println("Cache segundo nivel removido objeto");
					
				}
			}
			
		}
		return true;
	}

	public boolean passivar(Object objeto, Long identificador) {
		boolean isSucesso;
		try {
			isSucesso = SerializacaoObjeto.serizalizar(objeto,
					identificador);
			
			return isSucesso;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public long limiteTempo() {
		// TODO Auto-generated method stub
		return TEMPO_MAXIMO_CACHE;
	}

	@Override
	public boolean limpar(Class clazz,Long identeificador) {
		
		return SerializacaoObjeto.deletar(clazz,identeificador);
	}

	@Override
	public boolean limpar(Long identificador) {
		// TODO Auto-generated method stub
		
		
		if(SerializacaoObjeto.deletar(this.objetoPassivados.get(identificador).clazz_, identificador)){
			objetoPassivados.remove(identificador);
			return true;
		}
		return false;
	}


	class TempoSerelizacaoClass{
		
		private Class clazz_;
		private Calendar datetime_;
		
		public TempoSerelizacaoClass(Class clazz,Calendar datetime) {
			
			this.clazz_=clazz;
			this.datetime_=datetime;
		}
		public Class getClazz_() {
			return clazz_;
		}
		public void setClazz_(Class clazz_) {
			this.clazz_ = clazz_;
		}
		public Calendar getDatetime_() {
			return datetime_;
		}
		public void setDatetime_(Calendar datetime_) {
			this.datetime_ = datetime_;
		}
		
		
	}
}
