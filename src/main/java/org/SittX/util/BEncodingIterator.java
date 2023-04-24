package org.SittX.util;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BEncodingIterator<E> implements ListIterator<E> {
    private List<E> list;
    private int index;

    public BEncodingIterator(List<E> list, int index) {
        this.list = list;
        this.index = index;
    }

    public E current(){
        if(index < 0 || index == list.size()){
            throw new NoSuchElementException();
        }
        return list.get(index);
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public E next() {
        if(!hasNext()){
           throw new NoSuchElementException();
        }
        index = index + 1;
        return list.get(index);
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public E previous() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        index = index - 1;
        return list.get(--index);
    }

    @Override
    public int nextIndex() {
        return index + 1;
    }

    public int currentIndex(){
        return index;
    }

    @Override
    public int previousIndex() {
        return index -1;
    }


    @Override
    public void remove() {
        list.remove(index);
    }

    @Override
    public void set(E o) {
        list.set(index, o);
    }

    @Override
    public void add(E o) {
        list.add(index, o);
        index++;
    }
}
