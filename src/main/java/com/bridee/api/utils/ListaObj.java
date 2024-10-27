package com.bridee.api.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class ListaObj<T> {

    protected T[] vetor;
    protected int size;
    protected static final int CAPACIDADE_PADRAO = 16;

    public ListaObj() {
        this.vetor = (T[]) new Object[CAPACIDADE_PADRAO];
        this.size = 0;
    }

    public ListaObj(int capacidade){
        this.vetor = (T[]) new Object[capacidade];
        size = 0;
    }

    public void add(T t){
        if (size > vetor.length) {
            System.out.println("Não há mais posições disponíveis no vetor");
        }
        vetor[size] = t;
        size++;
    }

    public void exibe(){
        for (int i =0 ; i < size; i++){
            System.out.println(vetor[i]);
        }
    }

    public int get(T t){
        for (int i = 0; i < size; i++){
            if (vetor[i].equals(t)){
                return i;
            }
        }
        return -1;
    }

    public T get(int indice){
        if (indice < 0 || indice >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
        return vetor[indice];
    }

    public boolean removePorIndice(int indice){
        if (indice < 0 || indice > size){
            return false;
        }
        for (int i = indice; i < size; i++){
            vetor[i] = vetor[i+1];
        }
        vetor[size] = null;
        size--;
        return true;
    }

    public boolean removerElemento(T t){
        int numeroDeDelecoes = 0;
        while(get(t) != -1){
            removePorIndice(get(t));
            numeroDeDelecoes++;
        }
        return numeroDeDelecoes > 0;
    }

    public void substitui(T antigo, T novo){
        for (int i = 0; i < size; i++){
            if (vetor[i].equals(antigo)){
                vetor[i] = novo;
                return;
            }
        }
        System.out.println("Valor não encontrado");
    }

    public int contaOcorrencias(T valor){
        int total = 0;
        for (int i = 0; i < size; i++){
            if (vetor[i].equals(valor)){
                total++;
            }
        }
        return total;
    }

    public void adicionaNoIncio(T valor){
        if (size == vetor.length){
            System.out.println("Lista cheia");
        }
        size++;
        for (int i = size - 1; i > 0; i--){
            vetor[i] = vetor[i-1];
        }
        vetor[0] = valor;
    }

    public ListaObj<T> fromArray(T[] t){
        if (t.length > vetor.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        ListaObj<T> listaEstatica = new ListaObj<>();
        for (T valor: t){
            listaEstatica.add(valor);
        }
        return listaEstatica;
    }

    public T[] toArray(ListaObj<T> lista){
        if (lista.isEmpty()){
            return (T[]) new Object[0];
        }
        T[] newArray = (T[]) new Object[vetor.length];
        for (int i = 0; i < vetor.length; i++) {
            newArray[i] = vetor[i];
        }
        return newArray;
    }

    public <R> ListaObj<R> map (Function<T, R> function){
        ListaObj<R> newLista = new ListaObj<>();
        fromArray(vetor).forEach(valor -> {
            newLista.add(function.apply(valor));
        });
        return newLista;
    }

    public void forEach(Consumer<T> consumer){
        int contador = 0;
        while(vetor[contador] != null){
            consumer.accept(vetor[contador++]);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
