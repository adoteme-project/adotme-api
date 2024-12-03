package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.animalPerdido.*;
import com.example.adpotme_api.entity.animal.Especie;
import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;
import com.example.adpotme_api.entity.animalPerdido.CachorroPerdido;
import com.example.adpotme_api.entity.animalPerdido.GatoPerdido;
import com.example.adpotme_api.service.AnimalPerdidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Testes da AnimalController")
@ExtendWith(MockitoExtension.class)

class AnimalPerdidoControllerTest {

    @Mock
    private AnimalPerdidoService animalPerdidoService;
    @InjectMocks
    private AnimalPerdidoController animalPerdidoController;
    @Mock
    private MultipartFile file;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @Mock
    private ObjectMapper objectMapper;


    @Test
    void testCadastrarCachorroPerdido_Success() throws IOException, JsonProcessingException {
        // Setup
        String cachorroPerdidoJson = "{\"nome\":\"Rex\",\"sexo\":\"Macho\",\"cep\":\"12345-678\",\"especie\":\"Cachorro\",\"posicao\":{\"latitude\":-23.5505,\"longitude\":-46.6333},\"raca\":\"Labrador\",\"descricao\":\"Perdido próximo ao parque\",\"isEncontrado\":false,\"porte\":\"Grande\",\"tamanhoPelo\":\"Curto\",\"castrado\":true,\"ongId\":1}";

        MultipartFile fotoPerfil1 = new MockMultipartFile("fotoPerfil1", "foto1.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil2 = new MockMultipartFile("fotoPerfil2", "foto2.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil3 = new MockMultipartFile("fotoPerfil3", "foto3.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil4 = new MockMultipartFile("fotoPerfil4", "foto4.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil5 = new MockMultipartFile("fotoPerfil5", "foto5.jpg", "image/jpeg", new byte[0]);

        // Criação do DTO de entrada
        CachorroPerdidoCreateDto cachorroPerdidoCreateDto = new CachorroPerdidoCreateDto();
        cachorroPerdidoCreateDto.setNome("Rex");
        cachorroPerdidoCreateDto.setSexo("Macho");
        cachorroPerdidoCreateDto.setEspecie(String.valueOf(Especie.CACHORRO));
        cachorroPerdidoCreateDto.setRaca("Labrador");
        cachorroPerdidoCreateDto.setDescricao("Perdido próximo ao parque");
        cachorroPerdidoCreateDto.setPorte("Grande");
        cachorroPerdidoCreateDto.setTamanhoPelo("Curto");
        cachorroPerdidoCreateDto.setCastrado(true);
        cachorroPerdidoCreateDto.setOngId(1L);

        // Configuração do mock do objectMapper
        when(objectMapper.readValue(cachorroPerdidoJson, CachorroPerdidoCreateDto.class)).thenReturn(cachorroPerdidoCreateDto);

        // Configuração do mock do serviço
        CachorroPerdido cachorroPerdido = new CachorroPerdido();
        cachorroPerdido.setNomePet("Rex");
        when(animalPerdidoService.cadastrarCachorroPerdido(eq(cachorroPerdidoCreateDto), any(MultipartFile.class))).thenReturn(cachorroPerdido);

        // Chamada do método
        ResponseEntity<AnimalPerdido> response = animalPerdidoController.cadastrarCachorroPerdido(cachorroPerdidoJson, fotoPerfil1);

        // Verificações
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Rex", response.getBody().getNomePet());

        // Verifica se o método do serviço foi chamado corretamente
        verify(animalPerdidoService, times(1)).cadastrarCachorroPerdido(eq(cachorroPerdidoCreateDto), any(MultipartFile.class));
    }

    @Test
    void testCadastrarGatoPerdido_Success() throws IOException, JsonProcessingException {
        // Setup
        String gatoPerdidoJson = "{\"nome\":\"Frajola\",\"sexo\":\"Macho\",\"cep\":\"12345-678\",\"especie\":\"Gato\",\"posicao\":{\"latitude\":-23.5505,\"longitude\":-46.6333},\"raca\":\"Persa\",\"descricao\":\"Perdido próximo ao parque\",\"isEncontrado\":false,\"porte\":\"Pequeno\",\"tamanhoPelo\":\"Curto\",\"castrado\":true,\"ongId\":1}";

        MultipartFile fotoPerfil1 = new MockMultipartFile("fotoPerfil1", "foto1.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil2 = new MockMultipartFile("fotoPerfil2", "foto2.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil3 = new MockMultipartFile("fotoPerfil3", "foto3.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil4 = new MockMultipartFile("fotoPerfil4", "foto4.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil5 = new MockMultipartFile("fotoPerfil5", "foto5.jpg", "image/jpeg", new byte[0]);

        // Criação do DTO de entrada
        GatoPerdidoCreateDto gatoPerdidoCreateDto = new GatoPerdidoCreateDto();
        gatoPerdidoCreateDto.setNome("Frajola");
        gatoPerdidoCreateDto.setSexo("Macho");
        gatoPerdidoCreateDto.setEspecie(String.valueOf(Especie.GATO));
        gatoPerdidoCreateDto.setRaca("Persa");
        gatoPerdidoCreateDto.setDescricao("Perdido próximo ao parque");
        gatoPerdidoCreateDto.setPorte("Pequeno");
        gatoPerdidoCreateDto.setTamanhoPelo("Curto");
        gatoPerdidoCreateDto.setCastrado(true);
        gatoPerdidoCreateDto.setOngId(1L);

        // Configuração do mock do objectMapper
        when(objectMapper.readValue(gatoPerdidoJson, GatoPerdidoCreateDto.class)).thenReturn(gatoPerdidoCreateDto);

        // Configuração do mock do serviço
        GatoPerdido gatoPerdido = new GatoPerdido();
        gatoPerdido.setNomePet("Frajola");
        when(animalPerdidoService.cadastrarGatoPerdido(eq(gatoPerdidoCreateDto), any(MultipartFile.class))).thenReturn(gatoPerdido);

        // Chamada do método
        ResponseEntity<AnimalPerdido> response = animalPerdidoController.cadastrarGatoPerdido(gatoPerdidoJson, fotoPerfil1);

        // Verificações

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Frajola", response.getBody().getNomePet());

        // Verifica se o método do serviço foi chamado corretamente
        verify(animalPerdidoService, times(1)).cadastrarGatoPerdido(eq(gatoPerdidoCreateDto), any(MultipartFile.class));

    }

    @Test
    void testDeletarAnimal_Success() {
        // Arrange
        Long id = 1L;
        doNothing().when(animalPerdidoService).deletarAnimal(id); // Mock do serviço sem exceções

        // Act
        ResponseEntity<Void> response = animalPerdidoController.deletarAnimal(id);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue()); // Código HTTP 204 - No Content
        verify(animalPerdidoService, times(1)).deletarAnimal(id); // Verifica se o método foi chamado uma vez
    }


        @Test
        void testRecuperarAnimaisPerdidosEAchados_Success() {
            // Arrange
            List<AnimalPerdidoAchadoPerdidoDto> animais = new ArrayList<>();
            animais.add(AnimalPerdidoAchadoPerdidoDto.builder()
                    .id(1L)
                    .apelido("Bolt")
                    .sexo("Macho")
                    .raca("Labrador")
                    .especie("Cachorro")
                    .porte("Grande")
                    .imagem("imagem_url")
                    .dataResgate(LocalDate.of(2024, 11, 10))
                    .descricao("Cachorro perdido encontrado na rua.")
                    .nomeOng("ONG Pet Amor")
                    .ruaPerdido("Rua das Flores")
                    .bairroPerdido("Centro")
                    .cidadePerdido("São Paulo")
                    .estadoPerdido("SP")
                    .email("contato@petamor.com")
                    .telefone("11999999999")
                    .latitude(-23.5505)
                    .longitude(-46.6333)
                    .build());

            when(animalPerdidoService.recuperarAnimaisPerdidosEAchados()).thenReturn(animais);

            // Act
            ResponseEntity<List<AnimalPerdidoAchadoPerdidoDto>> response = animalPerdidoController.recuperarAnimaisPerdidosEAchados();

            // Assert
            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertFalse(response.getBody().isEmpty());
            assertEquals(1, response.getBody().size());
            assertEquals("Bolt", response.getBody().get(0).getApelido());

            verify(animalPerdidoService, times(1)).recuperarAnimaisPerdidosEAchados();
        }

        @Test
        void testRecuperarAnimaisPerdidosEAchados_NoContent() {
            // Arrange
            List<AnimalPerdidoAchadoPerdidoDto> animais = new ArrayList<>();
            when(animalPerdidoService.recuperarAnimaisPerdidosEAchados()).thenReturn(animais);

            // Act
            ResponseEntity<List<AnimalPerdidoAchadoPerdidoDto>> response = animalPerdidoController.recuperarAnimaisPerdidosEAchados();

            // Assert
            assertNotNull(response);
            assertEquals(204, response.getStatusCodeValue());
            assertNull(response.getBody());

            verify(animalPerdidoService, times(1)).recuperarAnimaisPerdidosEAchados();
        }

    @Test
    void testRecuperarAnimaisPerdidosEAchadosPorOng_Success() {
        // Arrange
        Long idOng = 1L;
        List<AnimalPerdidoAchadoPerdidoDto> animais = new ArrayList<>();
        animais.add(AnimalPerdidoAchadoPerdidoDto.builder()
                .id(1L)
                .apelido("Bolt")
                .sexo("Macho")
                .raca("Labrador")
                .especie("Cachorro")
                .porte("Grande")
                .imagem("imagem_url")
                .dataResgate(LocalDate.of(2024, 11, 10))
                .descricao("Cachorro perdido encontrado na rua.")
                .nomeOng("ONG Pet Amor")
                .ruaPerdido("Rua das Flores")
                .bairroPerdido("Centro")
                .cidadePerdido("São Paulo")
                .estadoPerdido("SP")
                .email("contato@petamor.com")
                .telefone("11999999999")
                .latitude(-23.5505)
                .longitude(-46.6333)
                .build());

        when(animalPerdidoService.recuperarAnimaisPerdidosPorOng(idOng)).thenReturn(animais);

        // Act
        ResponseEntity<List<AnimalPerdidoAchadoPerdidoDto>> response = animalPerdidoController.recuperarAnimaisPerdidosEAchadosPorOng(idOng);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals("Bolt", response.getBody().get(0).getApelido());

        verify(animalPerdidoService, times(1)).recuperarAnimaisPerdidosPorOng(idOng);
    }

    @Test
    void testRecuperarAnimaisPerdidosEAchadosPorOng_NoContent() {
        // Arrange
        Long idOng = 1L;
        List<AnimalPerdidoAchadoPerdidoDto> animais = new ArrayList<>();
        when(animalPerdidoService.recuperarAnimaisPerdidosPorOng(idOng)).thenReturn(animais);

        // Act
        ResponseEntity<List<AnimalPerdidoAchadoPerdidoDto>> response = animalPerdidoController.recuperarAnimaisPerdidosEAchadosPorOng(idOng);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(animalPerdidoService, times(1)).recuperarAnimaisPerdidosPorOng(idOng);
    }

    @Test
    void testRecuperarAnimaisPerdidosResumidoPorOng_Success() {
        // Arrange
        Long idOng = 1L;
        List<AnimalPerdidoAchadoPerdidoTabelaDto> animais = new ArrayList<>();
        animais.add(AnimalPerdidoAchadoPerdidoTabelaDto.builder()
                .id(1L)
                .apelido("Bolt")
                .sexo("Macho")
                .raca("Labrador")
                .especie("Cachorro")
                .porte("Grande")
                .dataResgate(LocalDate.of(2024, 11, 10))
                .ruaPerdido("Rua das Flores")
                .bairroPerdido("Centro")
                .cidadePerdido("São Paulo")
                .estadoPerdido("SP")
                .build());

        when(animalPerdidoService.recuperarAnimaisPerdidosPorOngTabela(idOng)).thenReturn(animais);

        // Act
        ResponseEntity<List<AnimalPerdidoAchadoPerdidoTabelaDto>> response = animalPerdidoController.recuperarAnimaisPerdidosResumidoPorOng(idOng);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals("Bolt", response.getBody().get(0).getApelido());

        verify(animalPerdidoService, times(1)).recuperarAnimaisPerdidosPorOngTabela(idOng);
    }

    @Test
    void testRecuperarAnimaisPerdidosResumidoPorOng_NoContent() {
        // Arrange
        Long idOng = 1L;
        List<AnimalPerdidoAchadoPerdidoTabelaDto> animais = new ArrayList<>();
        when(animalPerdidoService.recuperarAnimaisPerdidosPorOngTabela(idOng)).thenReturn(animais);

        // Act
        ResponseEntity<List<AnimalPerdidoAchadoPerdidoTabelaDto>> response = animalPerdidoController.recuperarAnimaisPerdidosResumidoPorOng(idOng);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(animalPerdidoService, times(1)).recuperarAnimaisPerdidosPorOngTabela(idOng);
    }

    @Test
    void testRecuperarAnimalPerdidoCardPorId_Success() {
        // Arrange
        Long idAnimalPerdido = 1L;

        AnimalPerdidoCardDto animal = AnimalPerdidoCardDto.builder()
                .id(1L)
                .apelido("Bolt")
                .sexo("Macho")
                .raca("Labrador")
                .especie("Cachorro")
                .porte("Grande")
                .imagem("http://example.com/image.jpg")
                .latitude(-23.5505)
                .longitude(-46.6333)
                .dataResgate(LocalDate.of(2024, 11, 10))
                .ruaPerdido("Rua das Flores")
                .bairroPerdido("Centro")
                .cidadePerdido("São Paulo")
                .estadoPerdido("SP")
                .rua("Rua das Rosas")
                .bairro("Jardins")
                .cidade("Campinas")
                .estado("SP")
                .descricao("Animal perdido na região central.")
                .build();

        when(animalPerdidoService.recuperarAnimalPerdidoCardPorId(idAnimalPerdido)).thenReturn(animal);

        // Act
        ResponseEntity<AnimalPerdidoCardDto> response = animalPerdidoController.recuperarAnimalPerdidoCardPorId(idAnimalPerdido);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Bolt", response.getBody().getApelido());

        verify(animalPerdidoService, times(1)).recuperarAnimalPerdidoCardPorId(idAnimalPerdido);
    }



    }
