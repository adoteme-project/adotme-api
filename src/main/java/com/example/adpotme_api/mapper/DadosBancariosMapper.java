package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.dadosBancarios.DadosBancariosCreateDto;
import com.example.adpotme_api.dto.dadosBancarios.DadosBancariosDto;
import com.example.adpotme_api.entity.dadosBancarios.DadosBancarios;

public class DadosBancariosMapper {
    public static DadosBancariosDto toDto(DadosBancarios dadosBancarios) {
        DadosBancariosDto dto = new DadosBancariosDto();
        dto.setId(dadosBancarios.getId());
        dto.setBanco(dadosBancarios.getBanco());
        dto.setAgencia(dadosBancarios.getAgencia());
        dto.setConta(dadosBancarios.getConta());
        dto.setTipoConta(dadosBancarios.getTipoConta());
        dto.setChavePix(dadosBancarios.getChavePix());
        dto.setNomeTitular(dadosBancarios.getNomeTitular());
        dto.setQrCode(dadosBancarios.getQrCode() != null ? dadosBancarios.getQrCode().getUrl() : null);
        return dto;
    }
}
