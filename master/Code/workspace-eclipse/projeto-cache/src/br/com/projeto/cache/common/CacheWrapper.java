package br.com.projeto.cache.common;

import java.io.Serializable;
import java.util.Calendar;

public class CacheWrapper<T> implements Serializable{

	private T objetoSincronizado_;
	private Class<T> clazz_;
	private Calendar calendar_;

	public CacheWrapper(T objetoSincrozinado, Class<T> clazz) {

		this.objetoSincronizado_ = objetoSincrozinado;
		this.clazz_ = clazz;
		this.calendar_ = Calendar.getInstance();;

	}

	public T getObjetoSincronizado_() {
		return objetoSincronizado_;
	}

	public void setObjetoSincronizado_(T objetoSincronizado_) {
		this.objetoSincronizado_ = objetoSincronizado_;
	}

	public Class<T> getClazz_() {
		return clazz_;
	}

	public void setClazz_(Class<T> clazz_) {
		this.clazz_ = clazz_;
	}

	public Calendar getCalendar_() {
		return calendar_;
	}

	public void setCalendar_(Calendar calendar_) {
		this.calendar_ = calendar_;
	}

}
