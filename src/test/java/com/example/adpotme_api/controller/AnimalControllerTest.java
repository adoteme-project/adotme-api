package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.animal.*;
import com.example.adpotme_api.dto.personalidade.PersonalidadeCreateDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.animal.Cachorro;
import com.example.adpotme_api.entity.animal.Especie;
import com.example.adpotme_api.entity.animal.Gato;
import com.example.adpotme_api.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes da AnimalController")
@ExtendWith(MockitoExtension.class) // teste unitário
class AnimalControllerTest {

        @InjectMocks
        private AnimalController animalController;
    @Mock
    private AnimalService animalService;

    @Mock
    private MultipartFile file;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 OK quando o animal for encontrado e a lista de aplicação for retornada com sucesso")
    void testRecuperarAnimalComListaAplicacao_Success() {

        Long idAnimal = 1L;

        // Criando um DTO de resposta simulado
        AnimalListaAplicacaoDto animalListaAplicacaoDto = AnimalListaAplicacaoDto.builder()
                .id(idAnimal)
                .nome("Rex")
                .descricao("Cachorro amigável")
                .taxaAdocao(100.0)
                .especie("Cachorro")
                .raca("Labrador")
                .sexo("M")
                .idade(5)
                .dataAbrigo("2020-01-01")
                .build();

        // Configurando o mock para retornar o DTO simulado
        when(animalService.recuperarAnimalComListaAplicacao(idAnimal))
                .thenReturn(animalListaAplicacaoDto);

        // Chamada do método a ser testado
        ResponseEntity<AnimalListaAplicacaoDto> response = animalController.recuperarAnimalComListaAplicacao(idAnimal);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(idAnimal, response.getBody().getId());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(animalService).recuperarAnimalComListaAplicacao(idAnimal);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve retornar 200 OK quando animais forem encontrados")
    void testRecuperarAnimaisPelaAplicacaoPorOng_Success() {

        // ID da ONG para o teste
        Long ongId = 1L;

        // Criando um DTO de resposta simulado
        AnimalAplicacaoDto animal = AnimalAplicacaoDto.builder()
                .id(1L)
                .nome("Rex")
                .qtdAplicacoes(5)
                .enviado(LocalDateTime.now())
                .taxaAdocao(100.0)
                .dataEntrada(LocalDate.now())
                .situacao("Disponível")
                .build();

        List<AnimalAplicacaoDto> animais = List.of(animal);

        // Configurando o mock para retornar a lista de animais simulada
        when(animalService.recuperarAnimaisPelaAplicacaoPorOng(ongId))
                .thenReturn(animais);

        // Chamada do método a ser testado
        ResponseEntity<List<AnimalAplicacaoDto>> response = animalController.recuperarAnimaisPelaAplicacaoPorOng(ongId);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(animal.getNome(), response.getBody().get(0).getNome());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(animalService).recuperarAnimaisPelaAplicacaoPorOng(ongId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve retornar 204 No Content quando não houver animais cadastrados")
    void testRecuperarAnimaisPelaAplicacaoPorOng_NoContent() {

        // ID da ONG para o teste
        Long ongId = 1L;

        // Configurando o mock para retornar uma lista vazia
        when(animalService.recuperarAnimaisPelaAplicacaoPorOng(ongId))
                .thenReturn(Collections.emptyList());

        // Chamada do método e verificação da resposta
        ResponseEntity<List<AnimalAplicacaoDto>> response = animalController.recuperarAnimaisPelaAplicacaoPorOng(ongId);

        // Verificações
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(animalService).recuperarAnimaisPelaAplicacaoPorOng(ongId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve retornar 200 OK quando o animal for encontrado com personalidade")
    void testRecuperarAnimalComPersonalidade_Success() {


        // ID do animal para o teste
        Long idAnimal = 1L;

        // Criando um DTO de resposta simulado com dados de personalidade
        AnimalOngResponseDto animal = new AnimalOngResponseDto();
        animal.setId(idAnimal);
        animal.setNome("Rex");
        animal.setIdade(5);
        animal.setImagem("imagem1.jpg");
        animal.setPersonalidade(new PersonalidadeCreateDto(5, 4, 3, 2, 5, 4));

        // Configurando o mock para retornar o DTO de animal simulado
        when(animalService.recuperarAnimalComPersonalidade(idAnimal)).thenReturn(animal);

        // Chamada do método e verificação da resposta
        ResponseEntity<AnimalOngResponseDto> response = animalController.recuperarAnimalComPersonalidade(idAnimal);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(animal.getId(), response.getBody().getId());
        assertEquals(animal.getNome(), response.getBody().getNome());
        assertNotNull(response.getBody().getPersonalidade());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(animalService).recuperarAnimalComPersonalidade(idAnimal);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve retornar 200 OK quando houver animais com personalidade")
    void testRecuperarAnimaisComPersonalidade_Success() {


        // Criando uma lista de DTOs simulados
        AnimalOngResponseDto animal1 = new AnimalOngResponseDto();
        animal1.setId(1L);
        animal1.setNome("Rex");
        animal1.setIdade(5);
        animal1.setImagem("imagem1.jpg");
        animal1.setPersonalidade(null); // Preenchendo com null ou dados simulados conforme necessário

        AnimalOngResponseDto animal2 = new AnimalOngResponseDto();
        animal2.setId(2L);
        animal2.setNome("Bella");
        animal2.setIdade(3);
        animal2.setImagem("imagem2.jpg");
        animal2.setPersonalidade(null); // Preenchendo com null ou dados simulados conforme necessário

        List<AnimalOngResponseDto> animais = List.of(animal1, animal2);

        // Configurando o mock para retornar a lista de animais simulada
        when(animalService.recuperarAnimaisComPersonalidade()).thenReturn(animais);

        // Chamada do método e verificação da resposta
        ResponseEntity<List<AnimalOngResponseDto>> response = animalController.recuperarAnimaisComPersonalidade();

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(animal1.getId(), response.getBody().get(0).getId());
        assertEquals(animal2.getId(), response.getBody().get(1).getId());

        // Verifica se o método do serviço foi chamado
        verify(animalService).recuperarAnimaisComPersonalidade();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve retornar 204 No Content quando não houver animais com personalidade")
    void testRecuperarAnimaisComPersonalidade_NoContent() {

        // Configurando o mock para retornar uma lista vazia
        when(animalService.recuperarAnimaisComPersonalidade()).thenReturn(Collections.emptyList());

        // Chamada do método e verificação da resposta
        ResponseEntity<List<AnimalOngResponseDto>> response = animalController.recuperarAnimaisComPersonalidade();

        // Verificações
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        // Verifica se o método do serviço foi chamado
        verify(animalService).recuperarAnimaisComPersonalidade();
        verifyNoMoreInteractions(animalService);
    }


    @Test
    @DisplayName("Deve retornar 204 No Content quando um animal for deletado com sucesso")
    void testDeletarAnimal_Success() {
        // Configurando o mock para simular a exclusão
        doNothing().when(animalService).deletarAnimal(1L);

        // Chamada do método de exclusão
        ResponseEntity<Void> response = animalController.deletarAnimal(1L);

        // Verificações
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(animalService).deletarAnimal(1L);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve retornar 200 OK quando o arquivo for importado com sucesso")
    void testImportarAnimais_Success() {
        // Configurando o mock do serviço para não fazer nada quando importar
        doNothing().when(animalService).importarAnimais(file);

        // Chamada do método de importação
        ResponseEntity<Void> response = animalController.importarAnimais(file);

        // Verificações
        assertEquals(200, response.getStatusCodeValue());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(animalService).importarAnimais(file);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Deve exportar dados de animais com sucesso")
    void testExportarAnimais_Success() throws IOException {
        // Configurando o mock para o PrintWriter
        when(response.getWriter()).thenReturn(writer);

        // Configurando o serviço para não lançar exceção e simular o processo de exportação
        doNothing().when(animalService).exportarAnimais(writer, 1L);

        // Chamada do método de exportação
        animalController.exportarAnimais(1L, response);

        // Verificações
        verify(response).setContentType("text/csv");
        verify(response).setHeader("Content-Disposition", "attachment; filename=animais.csv");
        verify(animalService).exportarAnimais(writer, 1L);


    }

    @Test
    void testAtualizarCachorro_Success() {
        // Setup
        Long id = 1L;
        AnimalUpdateDto cachorroAtualizado = new AnimalUpdateDto();
        cachorroAtualizado.setNome("Rex");
        cachorroAtualizado.setAnoNascimento(2020);
        cachorroAtualizado.setSexo("Macho");
        cachorroAtualizado.setEspecie(Especie.CACHORRO);
        cachorroAtualizado.setRaca("Labrador");
        cachorroAtualizado.setIsCastrado(true);
        cachorroAtualizado.setDescricao("Cachorro muito amigável.");
        cachorroAtualizado.setIsVisible(true);
        cachorroAtualizado.setIsAdotado(false);
        cachorroAtualizado.setPorte("Grande");
        cachorroAtualizado.setTaxaAdocao(200.0);

        Cachorro cachorro = new Cachorro();
        cachorro.setId(id);
        cachorro.setNome("Rex");
        cachorro.setAnoNascimento(2020);
        cachorro.setSexo("Macho");
        cachorro.setEspecie(Especie.CACHORRO);

        // Configuração do mock
        when(animalService.atualizarCachorro(eq(id), any(AnimalUpdateDto.class))).thenReturn(cachorro);

        // Chamada do método
        ResponseEntity<Cachorro> response = animalController.atualizarCachorro(id, cachorroAtualizado);

        // Verificações
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Rex", response.getBody().getNome());

        // Verifica se o método do serviço foi chamado corretamente
        verify(animalService, times(1)).atualizarCachorro(eq(id), any(AnimalUpdateDto.class));
    }

    @Test
    void testAtualizarGato_Success() {
        // Setup
        Long id = 1L;
        AnimalUpdateDto gatoAtualizado = new AnimalUpdateDto();
        gatoAtualizado.setNome("Whiskers");
        gatoAtualizado.setAnoNascimento(2018);
        gatoAtualizado.setSexo("Fêmea");
        gatoAtualizado.setEspecie(Especie.GATO);
        gatoAtualizado.setRaca("Siamês");
        gatoAtualizado.setIsCastrado(true);
        gatoAtualizado.setDescricao("Gato muito carinhoso.");
        gatoAtualizado.setIsVisible(true);
        gatoAtualizado.setIsAdotado(false);
        gatoAtualizado.setPorte("Médio");
        gatoAtualizado.setTaxaAdocao(150.0);

        Gato gato = new Gato();
        gato.setId(id);
        gato.setNome("Whiskers");
        gato.setAnoNascimento(2018);
        gato.setSexo("Fêmea");
        gato.setEspecie(Especie.GATO);

        // Configuração do mock
        when(animalService.atualizarGato(eq(id), any(AnimalUpdateDto.class))).thenReturn(gato);

        // Chamada do método
        ResponseEntity<Gato> response = animalController.atualizarGato(id, gatoAtualizado);

        // Verificações
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Whiskers", response.getBody().getNome());

        // Verifica se o método do serviço foi chamado corretamente
        verify(animalService, times(1)).atualizarGato(eq(id), any(AnimalUpdateDto.class));
    }

    @Test
    void testCadastrarCachorro_Success() throws IOException {
        // Setup
        String cachorroJson = "{\"nome\":\"Rex\",\"anoNascimento\":2019,\"sexo\":\"Macho\",\"dataAbrigo\":\"2022-01-01\",\"especie\":\"CACHORRO\",\"raca\":\"Labrador\",\"isCastrado\":true,\"descricao\":\"Cachorro amigável.\",\"isVisible\":true,\"isAdotado\":false,\"porte\":\"Grande\",\"isVermifugado\":true,\"taxaAdocao\":100.0,\"isDestaque\":false,\"ongId\":1}";

        MultipartFile fotoPerfil1 = new MockMultipartFile("fotoPerfil1", "foto1.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil2 = new MockMultipartFile("fotoPerfil2", "foto2.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil3 = new MockMultipartFile("fotoPerfil3", "foto3.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil4 = new MockMultipartFile("fotoPerfil4", "foto4.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil5 = new MockMultipartFile("fotoPerfil5", "foto5.jpg", "image/jpeg", new byte[0]);

        CachorroCreateDto cachorroCreateDto = new CachorroCreateDto();
        cachorroCreateDto.setNome("Rex");
        cachorroCreateDto.setAnoNascimento(2019);
        cachorroCreateDto.setSexo("Macho");
        cachorroCreateDto.setEspecie(Especie.CACHORRO);
        cachorroCreateDto.setRaca("Labrador");
        cachorroCreateDto.setIsCastrado(true);
        cachorroCreateDto.setDescricao("Cachorro amigável.");
        cachorroCreateDto.setIsVisible(true);
        cachorroCreateDto.setIsAdotado(false);
        cachorroCreateDto.setPorte("Grande");
        cachorroCreateDto.setIsVermifugado(true);
        cachorroCreateDto.setTaxaAdocao(100.0);
        cachorroCreateDto.setIsDestaque(false);
        cachorroCreateDto.setOngId(1L);

        // Configuração do mock do objectMapper
        when(objectMapper.readValue(cachorroJson, CachorroCreateDto.class)).thenReturn(cachorroCreateDto);

        // Configuração do mock do serviço
        Cachorro cachorro = new Cachorro();
        cachorro.setNome("Rex");
        when(animalService.cadastrarCachorro(eq(cachorroCreateDto), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class)))
                .thenReturn(cachorro);

        // Chamada do método
        ResponseEntity<Animal> response = animalController.cadastrarCachorro(cachorroJson, fotoPerfil1, fotoPerfil2, fotoPerfil3, fotoPerfil4, fotoPerfil5);

        // Verificações
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Rex", response.getBody().getNome());

        // Verifica se o método do serviço foi chamado corretamente
        verify(animalService, times(1)).cadastrarCachorro(eq(cachorroCreateDto), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class));
    }

    @Test
    void testCadastrarGato_Success() throws IOException {
        // Setup
        String gatoJson = "{\"nome\":\"Whiskers\",\"anoNascimento\":2020,\"sexo\":\"Macho\",\"dataAbrigo\":\"2023-05-10\",\"especie\":\"GATO\",\"raca\":\"Siamês\",\"isCastrado\":true,\"descricao\":\"Gato muito carinhoso.\",\"isVisible\":true,\"isAdotado\":false,\"porte\":\"Médio\",\"isVermifugado\":true,\"taxaAdocao\":50.0,\"isDestaque\":false,\"ongId\":1}";

        MultipartFile fotoPerfil1 = new MockMultipartFile("fotoPerfil1", "foto1.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil2 = new MockMultipartFile("fotoPerfil2", "foto2.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil3 = new MockMultipartFile("fotoPerfil3", "foto3.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil4 = new MockMultipartFile("fotoPerfil4", "foto4.jpg", "image/jpeg", new byte[0]);
        MultipartFile fotoPerfil5 = new MockMultipartFile("fotoPerfil5", "foto5.jpg", "image/jpeg", new byte[0]);

        GatoCreateDto gatoCreateDto = new GatoCreateDto();
        gatoCreateDto.setNome("Whiskers");
        gatoCreateDto.setAnoNascimento(2020);
        gatoCreateDto.setSexo("Macho");
        gatoCreateDto.setEspecie(Especie.GATO);
        gatoCreateDto.setRaca("Siamês");
        gatoCreateDto.setIsCastrado(true);
        gatoCreateDto.setDescricao("Gato muito carinhoso.");
        gatoCreateDto.setIsVisible(true);
        gatoCreateDto.setIsAdotado(false);
        gatoCreateDto.setPorte("Médio");
        gatoCreateDto.setIsVermifugado(true);
        gatoCreateDto.setTaxaAdocao(50.0);
        gatoCreateDto.setIsDestaque(false);
        gatoCreateDto.setOngId(1L);

        // Configuração do mock do objectMapper
        when(objectMapper.readValue(gatoJson, GatoCreateDto.class)).thenReturn(gatoCreateDto);

        // Configuração do mock do serviço
        Gato gato = new Gato();
        gato.setNome("Whiskers");
        when(animalService.cadastrarGato(eq(gatoCreateDto), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class)))
                .thenReturn(gato);

        // Chamada do método
        ResponseEntity<Animal> response = animalController.cadastrarGato(gatoJson, fotoPerfil1, fotoPerfil2, fotoPerfil3, fotoPerfil4, fotoPerfil5);

        // Verificações
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Whiskers", response.getBody().getNome());

        // Verifica se o método do serviço foi chamado corretamente
        verify(animalService, times(1)).cadastrarGato(eq(gatoCreateDto), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class));
    }

}