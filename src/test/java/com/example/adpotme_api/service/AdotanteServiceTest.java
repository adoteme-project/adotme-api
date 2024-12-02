package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.*;
import com.example.adpotme_api.dto.animal.AnimalCreateDto;
import com.example.adpotme_api.dto.animal.CachorroCreateDto;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.animal.Cachorro;
import com.example.adpotme_api.entity.animal.Especie;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.integration.CloudinaryService;
import com.example.adpotme_api.mapper.AdotanteMapper;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.repository.FormularioRepository;
import com.example.adpotme_api.repository.OngUserRepository;
import com.example.adpotme_api.util.Sorting;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdotanteServiceTest {
    @Mock
    private AdotanteRepository adotanteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private OngUserRepository ongUserRepository;

    @InjectMocks
    private AdotanteService adotanteService;

    @Mock
    private ViaCepService viaCepService;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private Validator validator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private FormularioRepository formularioRepository;





    @Test
    @DisplayName("Dado que, não tenho nada no banco, retorna lista vazia")
    void buscarTodosListaVazia() {
        // GIVEN/ARRANGE - Configuração "chumbar" no código.
        List<Adotante> lista = Collections.emptyList();

        // WHEN/ARRANGE - quando qualquer código abaixo dessa linha,
        // chamar um método que tenha findAll sem sua lógica
        when(adotanteRepository.findAll()).thenReturn(lista);

        // THEN/ACT
        // chama métodos que possível teriam findAll, ao serem chamados,
        // o findAll vai respeitar o comportamento configurado
        List<AdotanteResponseDto> resultado = adotanteService.recuperarAdotantes();

        // ASSERT/ASSERT
        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());

        verify(adotanteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho algo no banco, retorna lista com adotantes")
    void buscarTodosListaComConteudo() {
        // GIVEN
        List<Adotante> adotantes = List.of(
                new Adotante(AdotanteCreateDto.builder()
                        .nome("João")
                        .email("silvyo@hotmail.com")
                        .senha("123456")
                        .celular("11999999999")
                        .build()),
                new Adotante(AdotanteCreateDto.builder()
                        .nome("Maria")
                        .email("maria@hotmail.com")
                        .senha("123456")
                        .celular("11999999999")
                        .build())
        );

        // WHEN
        when(adotanteRepository.findAll()).thenReturn(adotantes);

        // THEN
        List<AdotanteResponseDto> resultado = adotanteService.recuperarAdotantes();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
        assertEquals(adotantes.get(0).getNome(), resultado.get(0).getNome());
        assertEquals(adotantes.get(1).getNome(), resultado.get(1).getNome());

        verify(adotanteRepository, times(1)).findAll();


    }

    @Test
    @DisplayName("Dado que, tenho um adotante pelo id, retorna corretamente")
    void buscarAdotantePorId() {
        // GIVEN
        Adotante adotante = new Adotante(AdotanteCreateDto.builder()
                .nome("João")
                .email("joao@hotmail.com")
                .senha("123456")
                .celular("11999999999")
                .build());

        // WHEN

        when(adotanteRepository.findById(1L)).thenReturn(java.util.Optional.of(adotante));

        // THEN

        Adotante resultado = adotanteService.recuperarAdotantePorId(1L);

        assertNotNull(resultado);

        assertEquals(adotante.getNome(), resultado.getNome());

        verify(adotanteRepository, times(1)).findById(1L);


    }

    @Test
    @DisplayName("Dado que, não tenho um adotante pelo id, lança uma exceção")
    void buscarAdotantePorIdException() {
        // GIVEN
        Long idInvalido = 999L;

        // WHEN
        when(adotanteRepository.findById(idInvalido)).thenReturn(Optional.empty());

        // THEN
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> adotanteService.recuperarAdotantePorId(idInvalido)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Adotante não encontrado", exception.getReason());

        verify(adotanteRepository, times(1)).findById(idInvalido);
    }

    @Test
    @DisplayName("Dado que, deleto um adotante existente, executa com sucesso")
    void deletarAdotanteSucesso() {
        // GIVEN
        Long idAdotante = 1L;

        when(adotanteRepository.existsById(idAdotante)).thenReturn(true);

        // WHEN
        assertDoesNotThrow(() -> adotanteService.deletarAdotante(idAdotante));

        // THEN
        verify(adotanteRepository, times(1)).deleteById(idAdotante);
    }

    @Test
    @DisplayName("Dado que, deleto um adotante inexistente, lança exceção")
    void deletarAdotanteNaoExistente() {
        // GIVEN
        Long idInvalido = 999L;

        when(adotanteRepository.existsById(idInvalido)).thenReturn(false);

        // WHEN
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> adotanteService.deletarAdotante(idInvalido)
        );

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Adotante não encontrado", exception.getReason());
        verify(adotanteRepository, Mockito.never()).deleteById(idInvalido);
    }

    @Test
    @DisplayName("Dado que, ao atualizar adotante, executa com sucesso")
    void atualizarAdotanteSucesso() {
        // GIVEN
        Long id = 1L;

        // Dados existentes no banco
        Adotante adotanteExistente = new Adotante();
        adotanteExistente.setId(id);
        adotanteExistente.setNome("João");
        adotanteExistente.setEmail("joao@hotmail.com");
        adotanteExistente.setSenha("123456");
        adotanteExistente.setCelular("11999999999");

        // Dados atualizados recebidos do DTO
        AdotanteUpdateDto adotanteAtualizadoDto = AdotanteUpdateDto.builder()
                .nome("João da Silva")
                .email("jaozinho@hotmail.com")
                .senha("654321")
                .celular("11988888888")
                .build();

        // Mockando o comportamento do repositório
        when(adotanteRepository.findById(id)).thenReturn(Optional.of(adotanteExistente));
        when(adotanteRepository.save(any(Adotante.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Adotante resultado = adotanteService.atualizarAdotante(id, adotanteAtualizadoDto);

        // THEN
        assertNotNull(resultado);
        assertEquals("João da Silva", resultado.getNome());
        assertEquals("jaozinho@hotmail.com", resultado.getEmail());
        assertEquals("654321", resultado.getSenha());
        assertEquals("11988888888", resultado.getCelular());

        // Verifica se os métodos do repositório foram chamados corretamente
        verify(adotanteRepository, times(1)).findById(id);
        verify(adotanteRepository, times(1)).save(any(Adotante.class));
    }

    @Test
    @DisplayName("Dado que, ao atualizar adotante, dê falha por ID inexistente")
    void atualizarAdotanteFalhaIdInexistente() {
        // GIVEN
        Long id = 1L;

        // Dados atualizados recebidos do DTO
        AdotanteUpdateDto adotanteAtualizadoDto = AdotanteUpdateDto.builder()
                .nome("João da Silva")
                .email("joaozinho@hotmail.com")
                .senha("654321")
                .celular("11988888888")
                .build();

        // Mockando o comportamento do repositório
        when(adotanteRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> adotanteService.atualizarAdotante(id, adotanteAtualizadoDto)
        );

        // THEN

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Adotante não encontrado", exception.getReason());

        // Verifica se os métodos do repositório foram chamados corretamente

        verify(adotanteRepository, times(1)).findById(id);
        verify(adotanteRepository, never()).save(any(Adotante.class));


    }

@Test
    @DisplayName("Dado que, ao tentar recuperar os dados do adotante com foto, retorna com sucesso")
public void testRecuperarAdotanteDadosFoto_Success() {

    Long id = 1L;
    Adotante adotante = new Adotante();

    when(adotanteRepository.findById(id)).thenReturn(Optional.of(adotante));


    AdotanteDadosFoto result = adotanteService.recuperarAdotanteDadosFoto(id);


    assertNotNull(result);
    ;

}
@Test
    @DisplayName("Dado que, ao tentar recuperar os dados do adotante com foto não existente, retorna not found")
    public void testRecuperarAdotanteDadosFoto_NotFound() {
        // Arrange: Simula que o repositório não encontra um Adotante com o ID fornecido
        Long id = 1L;
        when(adotanteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert: Verifica se a exceção ResponseStatusException é lançada
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> adotanteService.recuperarAdotanteDadosFoto(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Adotante não encontrado", exception.getReason());

        // Verifica que o método findById foi chamado com o ID correto
        verify(adotanteRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Dado que, ao tentar recuperar os dados do adotante com animais favoritos, retorna not found")
    public void testRecuperarAnimaisFavoritosAdotante_NotFound() {
        // Arrange: Simula que o repositório não encontra um Adotante com o ID fornecido
        Long id = 1L;
        when(adotanteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert: Verifica se a exceção ResponseStatusException é lançada
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> adotanteService.recuperarAnimaisFavoritosAdotante(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Adotante não encontrado", exception.getReason());

        // Verifica que o método findById foi chamado com o ID correto
        verify(adotanteRepository, times(1)).findById(id);
    }


}