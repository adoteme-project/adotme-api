package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adocao.AdocaoDados;
import com.example.adpotme_api.entity.adocao.LogAdocao;
import com.example.adpotme_api.repository.LogAdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class AdocaoService {
    @Autowired
    private LogAdocaoRepository logAdocaoRepository;
    public List<LogAdocao> listarAdocoes(Long idOng) {
        List<LogAdocao> dados = logAdocaoRepository.findAllByOngId(idOng);
        List<LogAdocao> adocoes = new ArrayList<>();
        for(LogAdocao adocao : dados){
            if(adocao.getTipo().equals("adoção")){
                adocoes.add(adocao);
            }
        }
        return adocoes;
    }

    public List<AdocaoDados> listarAdocoesDashboard(Long idOng, Integer ano) {
        List<AdocaoDados> dadosAno = new ArrayList<>();
        List<LogAdocao> adocoes = logAdocaoRepository.findAllByOngId(idOng);

        Map<Month, List<LogAdocao>> dadosMes = new HashMap<>();
        Map<Month, Integer> quantidadeAplicacoes = new HashMap<>();
        Map<Month, Integer> quantidadeAdocoes = new HashMap<>();

        for (Month month : Month.values()) {
            dadosMes.put(month, new ArrayList<>());
            quantidadeAplicacoes.put(month, 0);
            quantidadeAdocoes.put(month, 0);
        }

        for (LogAdocao adocao : adocoes) {
            if (adocao.getData().getYear() == ano) {
                Month month = adocao.getData().getMonth();
                dadosMes.get(month).add(adocao);
            }
        }

        for (Month month : Month.values()) {
            for (LogAdocao adocao : dadosMes.get(month)) {
                if (adocao.getTipo().equals("aplicação")) {
                    quantidadeAplicacoes.put(month, quantidadeAplicacoes.get(month) + 1);
                } else if (adocao.getTipo().equals("adoção")) {
                    quantidadeAdocoes.put(month, quantidadeAdocoes.get(month) + 1);
                }
            }
            String mes = month.getDisplayName(TextStyle.FULL, Locale.getDefault());
            double taxaConversao = 0.0;
            if(quantidadeAplicacoes.get(month) != 0){
                taxaConversao = (double) quantidadeAdocoes.get(month) / quantidadeAplicacoes.get(month) * 100;
            }
            AdocaoDados dados = new AdocaoDados(mes, ano, quantidadeAplicacoes.get(month), quantidadeAdocoes.get(month), taxaConversao);
            dadosAno.add(dados);
        }

        return dadosAno;
    }
}

