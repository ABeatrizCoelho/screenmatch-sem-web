package br.com.bea.screenmatch.service;

public interface IConverteDados {
    // generics
    <T> T obterDados(String json, Class<T> classe);

}
