package com.example.adpotme_api.util;

import com.example.adpotme_api.entity.animal.Animal;
import org.springframework.stereotype.Component;

import java.util.List;


public class Recursao {
    public static Integer buscarQuantidade(List<?> lista, int i){
        if(i == lista.size()){
            return i;
        }

        return buscarQuantidade(lista, i+1);

    }
}
