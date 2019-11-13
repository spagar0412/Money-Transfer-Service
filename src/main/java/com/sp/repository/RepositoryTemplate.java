package com.sp.repository;

public interface RepositoryTemplate<T> 
{
    int size();
    T getById(String id);
}

