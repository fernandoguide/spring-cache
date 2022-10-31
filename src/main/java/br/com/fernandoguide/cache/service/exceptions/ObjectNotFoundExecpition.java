package br.com.fernandoguide.cache.service.exceptions;

public class ObjectNotFoundExecpition  extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ObjectNotFoundExecpition(String msg) {
        super(msg);
    }
}