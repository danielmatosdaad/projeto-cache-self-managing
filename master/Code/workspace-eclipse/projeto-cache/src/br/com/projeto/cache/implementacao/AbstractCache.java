package br.com.projeto.cache.implementacao;

import br.com.projeto.cache.common.interfaces.Cache;

public abstract class AbstractCache implements Cache, Clean{

	protected Cache proximoNivelCache;
	protected Long identificadorCache;
	
	public void setIdentificadorCache(Long identificadorCache) {
		this.identificadorCache = identificadorCache;
	}

	public void setProximoNivelCache(Cache proximoNivelCache) {
		this.proximoNivelCache = proximoNivelCache;
	}
	
	public Cache getProximoNivelCache() {
		return proximoNivelCache;
	}

	public Long getIdentificadorCache() {
		return identificadorCache;
	}

	
	
}
