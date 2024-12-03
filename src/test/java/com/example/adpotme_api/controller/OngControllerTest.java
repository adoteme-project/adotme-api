package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ong.*;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.service.OngService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Testes da OngController")
@ExtendWith(MockitoExtension.class)
class OngControllerTest {

    @Mock
    private OngService ongService;

    @InjectMocks
    private OngController ongController;

    @Mock
    private ObjectMapper objectMapper;



    @Test
    @DisplayName("Deve retornar o nome e imagem da ONG com sucesso")
    void testRecuperarNomeImagemOng() {
        // Arrange
        Long idOng = 1L;
        OngNomeImagemDto ongNomeImagemMock = new OngNomeImagemDto();
        ongNomeImagemMock.setNome("ONG Teste");
        ongNomeImagemMock.setImagem("imagem_teste.jpg");

        when(ongService.recuperarNomeImagemOng(eq(idOng))).thenReturn(ongNomeImagemMock);

        // Act
        ResponseEntity<OngNomeImagemDto> response = ongController.recuperarNomeImagemOng(idOng);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ONG Teste", response.getBody().getNome());
        assertEquals("imagem_teste.jpg", response.getBody().getImagem());
    }

    @Test
    @DisplayName("Deve retornar uma lista de ONGs com animais e dados bancários com sucesso")
    void testListagemOngsComAnimaisDadosBancarios() {
        // Arrange
        OngDadosBancariosAnimalDto ongDadosBancariosMock = new OngDadosBancariosAnimalDto();
        ongDadosBancariosMock.setId(1L);
        ongDadosBancariosMock.setNome("ONG Teste");
        ongDadosBancariosMock.setEmail("teste@ong.com");

        List<OngDadosBancariosAnimalDto> ongsMock = new ArrayList<>();
        ongsMock.add(ongDadosBancariosMock);

        when(ongService.listagemOngsComAnimaisDadosBancarios()).thenReturn(ongsMock);

        // Act
        ResponseEntity<List<OngDadosBancariosAnimalDto>> response = ongController.listagemOngsComAnimaisDadosBancarios();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("ONG Teste", response.getBody().get(0).getNome());
        assertEquals("teste@ong.com", response.getBody().get(0).getEmail());
    }

    @Test
    @DisplayName("Deve retornar status 204 quando nenhuma ONG for encontrada")
    void testListagemOngsComAnimaisDadosBancariosSemResultados() {
        // Arrange
        List<OngDadosBancariosAnimalDto> ongsVazias = new ArrayList<>();
        when(ongService.listagemOngsComAnimaisDadosBancarios()).thenReturn(ongsVazias);

        // Act
        ResponseEntity<List<OngDadosBancariosAnimalDto>> response = ongController.listagemOngsComAnimaisDadosBancarios();

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Deve retornar uma lista de animais de uma ONG com sucesso")
    void testListagemAnimaisOng() {
        // Arrange
        Long idOng = 1L;
        OngAnimaisDto animalMock = new OngAnimaisDto();
        animalMock.setId(1L);
        animalMock.setNome("Animal Teste");
        animalMock.setRaca("Raça Teste");

        List<OngAnimaisDto> animaisMock = new ArrayList<>();
        animaisMock.add(animalMock);

        when(ongService.listagemAnimaisOng(idOng)).thenReturn(animaisMock);

        // Act
        ResponseEntity<List<OngAnimaisDto>> response = ongController.listagemAnimaisOng(idOng);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Animal Teste", response.getBody().get(0).getNome());
        assertEquals("Raça Teste", response.getBody().get(0).getRaca());
    }

    @Test
    @DisplayName("Deve retornar status 204 quando nenhum animal for encontrado")
    void testListagemAnimaisOngSemResultados() {
        // Arrange
        Long idOng = 1L;
        List<OngAnimaisDto> animaisVazios = new ArrayList<>();
        when(ongService.listagemAnimaisOng(idOng)).thenReturn(animaisVazios);

        // Act
        ResponseEntity<List<OngAnimaisDto>> response = ongController.listagemAnimaisOng(idOng);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Deve deletar uma ONG com sucesso")
    void testDeletarOng() {
        // Arrange
        Long idOng = 1L;

        // Act
        ResponseEntity<Void> response = ongController.deletarOng(idOng);

        // Assert
        verify(ongService, times(1)).deletarOng(idOng);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Deve atualizar uma ONG com sucesso")
    void testAtualizarOng() {
        // Arrange
        Long idOng = 1L;
        OngUpdateDto ongUpdateDto = new OngUpdateDto();
        ongUpdateDto.setNome("ONG Teste");
        ongUpdateDto.setEmail("ong@teste.com");
        ongUpdateDto.setTelefone("123456789");
        ongUpdateDto.setCnpj("12345678000195");

        Ong ongMock = new Ong();
        ongMock.setId(idOng);
        ongMock.setNome("ONG Teste");
        ongMock.setEmail("ong@teste.com");
        ongMock.setTelefone("123456789");
        ongMock.setCnpj("12345678000195");

        when(ongService.atualizar(eq(idOng), eq(ongUpdateDto))).thenReturn(ongMock);

        // Act
        ResponseEntity<Ong> response = ongController.atualizar(idOng, ongUpdateDto);

        // Assert
        verify(ongService, times(1)).atualizar(idOng, ongUpdateDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ongMock, response.getBody());
        assertEquals("ONG Teste", response.getBody().getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista de ONGs com dados bancários com sucesso")
    void testRecuperarOngsComDadosBancarios() {
        // Arrange
        List<OngResponseAllDto> ongsMock = new ArrayList<>();
        OngResponseAllDto ong = new OngResponseAllDto();
        ong.setId(1L);
        ong.setNome("ONG Teste");
        ong.setEmail("ong@teste.com");
        ong.setTelefone("123456789");
        ong.setCnpj("12345678000195");

        ongsMock.add(ong);

        when(ongService.recuperarOngsComDadosBancarios()).thenReturn(ongsMock);

        // Act
        ResponseEntity<List<OngResponseAllDto>> response = ongController.recuperarOngsComDadosBancarios();

        // Assert
        verify(ongService, times(1)).recuperarOngsComDadosBancarios();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("ONG Teste", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Deve retornar status 204 se não houver ONGs com dados bancários")
    void testRecuperarOngsComDadosBancariosNenhumEncontrado() {
        // Arrange
        List<OngResponseAllDto> ongsMock = new ArrayList<>();

        when(ongService.recuperarOngsComDadosBancarios()).thenReturn(ongsMock);

        // Act
        ResponseEntity<List<OngResponseAllDto>> response = ongController.recuperarOngsComDadosBancarios();

        // Assert
        verify(ongService, times(1)).recuperarOngsComDadosBancarios();
        assertEquals(204, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }


    @Test
    @DisplayName("Deve retornar uma ONG com sucesso para edição/visualização")
    void testRecuperarOngParaEdicaoVisualizacao() {
        // Arrange
        Long idOng = 1L;
        OngPutViewDto ongMock = new OngPutViewDto();
        ongMock.setNome("ONG Teste");
        ongMock.setEmail("ong@teste.com");
        ongMock.setCelular("987654321");
        ongMock.setSite("www.ongteste.com");
        ongMock.setInstagram("ong_teste");
        ongMock.setFacebook("facebook.com/ongteste");
        ongMock.setTelefone("123456789");
        ongMock.setDescricao("Descrição da ONG de teste");
        ongMock.setImagem("imagem.jpg");

        when(ongService.recuperarOngParaEdicaoVisualizacao(idOng)).thenReturn(ongMock);

        // Act
        ResponseEntity<OngPutViewDto> response = ongController.recuperarOngParaEdicaoVisualizacao(idOng);

        // Assert
        verify(ongService, times(1)).recuperarOngParaEdicaoVisualizacao(idOng);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ONG Teste", response.getBody().getNome());
        assertEquals("ong@teste.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Deve editar a ONG com sucesso")
    void testEditarOng() {
        // Arrange
        Long idOng = 1L;
        OngPutDto ongAtualizada = new OngPutDto("ONG Editada", "editada@ong.com", "987654321", "www.edicao.com", "ong_edicao", "facebook.com/ongeditada", "123456789", "Nova descrição");
        OngPutDto ongRetornada = new OngPutDto("ONG Editada", "editada@ong.com", "987654321", "www.edicao.com", "ong_edicao", "facebook.com/ongeditada", "123456789", "Nova descrição");

        when(ongService.editarOng(idOng, ongAtualizada)).thenReturn(ongRetornada);

        // Act
        ResponseEntity<OngPutDto> response = ongController.editarOng(idOng, ongAtualizada);

        // Assert
        verify(ongService, times(1)).editarOng(idOng, ongAtualizada);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ONG Editada", response.getBody().getNome());
        assertEquals("editada@ong.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Deve retornar lista de ONGs com sucesso")
    void testRecuperarOngs() {
        // Arrange
        List<OngResponseDto> ongs = new ArrayList<>();
        OngResponseDto ong1 = new OngResponseDto();
        ong1.setId(1L);
        ong1.setNome("ONG Teste");
        ong1.setEmail("teste@ong.com");
        ong1.setTelefone("123456789");
        ong1.setCnpj("12.345.678/0001-90");
        ongs.add(ong1);

        when(ongService.recuperarOngs()).thenReturn(ongs);

        // Act
        ResponseEntity<List<OngResponseDto>> response = ongController.recuperarOngs();

        // Assert
        verify(ongService, times(1)).recuperarOngs();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("ONG Teste", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Deve retornar status 204 quando não houver ONGs")
    void testRecuperarOngsSemResultados() {
        // Arrange
        List<OngResponseDto> ongs = Collections.emptyList();
        when(ongService.recuperarOngs()).thenReturn(ongs);

        // Act
        ResponseEntity<List<OngResponseDto>> response = ongController.recuperarOngs();

        // Assert
        verify(ongService, times(1)).recuperarOngs();
        assertEquals(204, response.getStatusCodeValue());
    }



}