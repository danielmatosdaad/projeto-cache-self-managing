package br.com.projeto.cache.template;

import java.util.HashMap;
import java.util.Map;

import br.com.projeto.cache.common.interfaces.Cache;
import br.com.projeto.cache.implementacao.CacheImp;

public class GerenteCache {

	private Map<Class, Cache> mapCache;

	public GerenteCache() {

		mapCache = new HashMap<Class, Cache>();

	}

	public Cache getCacheClass(Class clazz) {

		if (mapCache.containsKey(clazz)) {

			return mapCache.get(clazz);
		}
		Cache cache = new CacheImp();
		this.mapCache.put(clazz, cache);

		return cache;

	}

}
