package com.example.adpotme_api.controller;

import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.service.EnderecoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("Testes da EnderecoController")
@ExtendWith(MockitoExtension.class)
class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    @Test
    @DisplayName("Deve atualizar o endereço de uma ONG com sucesso")
    void testAtualizarEnderecoParaOng() {
        // Arrange
        String cep = "12345678";
        Long idOng = 1L;

        Endereco enderecoMock = new Endereco();
        enderecoMock.setCep(cep);
        enderecoMock.setCidade("Cidade Teste");
        enderecoMock.setEstado("Estado Teste");
        enderecoMock.setRua("Rua Exemplo");

        when(enderecoService.atualizarEnderecoParaOng(eq(cep), eq(idOng))).thenReturn(enderecoMock);

        // Act
        ResponseEntity<Endereco> response = enderecoController.atualizarEnderecoParaOng(cep, idOng);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(enderecoMock, response.getBody());
        assertEquals("12345678", response.getBody().getCep());
        assertEquals("Cidade Teste", response.getBody().getCidade());
        assertEquals("Estado Teste", response.getBody().getEstado());
        assertEquals("Rua Exemplo", response.getBody().getRua());
    }


    @Test
    @DisplayName("Deve atualizar o endereço de um adotante com sucesso")
    void testAtualizarEnderecoParaAdotante() {
        // Arrange
        String cep = "98765432";
        String numero = "123";
        Long idAdotante = 2L;

        Endereco enderecoMock = new Endereco();
        enderecoMock.setCep(cep);
        enderecoMock.setCidade("Cidade Adotante");
        enderecoMock.setEstado("Estado Adotante");
        enderecoMock.setRua("Rua Adotante");

        when(enderecoService.atualizarEnderecoParaAdotante(eq(cep), eq(numero), eq(idAdotante))).thenReturn(enderecoMock);

        // Act
        ResponseEntity<Endereco> response = enderecoController.atualizarEnderecoParaAdotante(cep, numero, idAdotante);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(enderecoMock, response.getBody());
        assertEquals("98765432", response.getBody().getCep());
        assertEquals("Cidade Adotante", response.getBody().getCidade());
        assertEquals("Estado Adotante", response.getBody().getEstado());
        assertEquals("Rua Adotante", response.getBody().getRua());
    }
}
