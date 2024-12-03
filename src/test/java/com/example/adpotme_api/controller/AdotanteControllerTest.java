package com.example.adpotme_api.controller;

import com.example.adpotme_api.adapter.AdotanteTarget;
import com.example.adpotme_api.dto.adotante.*;
import com.example.adpotme_api.dto.animal.AnimalFavoritoDto;
import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.example.adpotme_api.dto.endereco.EnderecoResponseOngDto;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.dto.ong.OngFavoritaDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.mapper.AdotanteMapper;
import com.example.adpotme_api.mapper.FormularioMapper;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.OngRepository;
import com.example.adpotme_api.service.AdotanteService;
import com.example.adpotme_api.service.FormularioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testes da AdotanteController")
@ExtendWith(MockitoExtension.class) // teste unitário
class AdotanteControllerTest {

    @Mock
    private AdotanteService adotanteService;

    @Mock
    private FormularioService formularioService;

    @InjectMocks
    private AdotanteController adotanteController;

    @Mock
    private AdotanteTarget adotanteTarget;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testa a recuperação de adotantes vazio")
    public void testRecuperarAdotantes_Success() throws Exception {
        // Configuração
        Mockito.when(adotanteService.recuperarAdotantes()).thenReturn(Collections.emptyList());

        // Execução
        ResponseEntity<List<AdotanteResponseDto>> resposta = adotanteController.recuperarAdotantes();

        // Verificação
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Testa a recuperação de adotantes com um adotante")
    public void testRecuperarAdotantes() throws Exception {
        // Configuração
        List<AdotanteResponseDto> adotantes = List.of(
                AdotanteResponseDto.builder()
                        .id(1L)
                        .nome("João")
                        .email("joao@g.com")
                        .dtNasc(LocalDate.of(1990, 1, 1))
                        .senha("123")
                        .celular("123")
                        .cadastro(LocalDate.now())
                        .fotoPerfil("foto")
                        .build());

        Mockito.when(adotanteService.recuperarAdotantes()).thenReturn(adotantes);

        // Execução
        ResponseEntity<List<AdotanteResponseDto>> resposta = adotanteController.recuperarAdotantes();

        // Verificação

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        assertEquals(adotantes.get(0), resposta.getBody().get(0));

    }

    @Test
    @DisplayName("Testa preenchimento de formulário com sucesso")
    void testPreencherFormulario_Success() {
        // Configuração dos dados de entrada
        FormularioCreateDto dados = new FormularioCreateDto();
        dados.setTemCrianca(true);
        dados.setMoradoresConcordam(true);
        dados.setTemPet(false);
        dados.setSeraResponsavel(true);
        dados.setMoraEmCasa(true);
        dados.setIsTelado(false);
        dados.setCasaPortaoAlto(true);

        // Configuração da resposta esperada do serviço
        Formulario respostaDto = new Formulario();
        respostaDto.setTemCrianca(true);
        respostaDto.setMoradoresConcordam(true);
        respostaDto.setTemPet(false);
        respostaDto.setSeraResponsavel(true);
        respostaDto.setMoraEmCasa(true);
        respostaDto.setIsTelado(false);
        respostaDto.setCasaPortaoAlto(true);

        when(formularioService.preencherFormulario(dados, 1L)).thenReturn(respostaDto);

        // Execução
        ResponseEntity<FormularioResponseAdotanteDto> resposta = adotanteController.preencherFormulario(dados, 1L);


        // Verificação
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

    }

    @Test
    @DisplayName("Deve retornar 200 OK quando o adotante é encontrado")
    void testRecuperarAdotantePorId_Sucesso() {
        // Configuração
        Long id = 1L;
        Adotante adotante = new Adotante();
        adotante.setId(id);
        adotante.setNome("Maria");
        adotante.setEmail("maria@email.com");

        when(adotanteService.recuperarAdotantePorId(id)).thenReturn(adotante);

        // Execução
        ResponseEntity<Adotante> resposta = adotanteController.recuperarAdotantePorId(id);

        // Verificação
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(adotante, resposta.getBody());
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found quando o adotante não é encontrado")
    void testRecuperarAdotantePorId_NaoEncontrado() {
        // Configuração
        Long id = 2L;

        when(adotanteService.recuperarAdotantePorId(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado"));

        // Execução e Verificação
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> adotanteController.recuperarAdotantePorId(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Adotante não encontrado", exception.getReason());
    }

    @Test
    @DisplayName("Testa a recuperação de adotantes ordenados por estado com sucesso")
    void testRecuperarAdotantesOrdenadosPorEstado_Success() {
        // Configuração
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setEstado("SP");
        endereco.setCidade("São Paulo");
        endereco.setBairro("Centro");
        endereco.setRua("Rua A");
        endereco.setNumero("123");

        FormularioResponseAdotanteDto formulario = new FormularioResponseAdotanteDto();
        formulario.setTemCrianca(true);
        formulario.setMoradoresConcordam(true);
        formulario.setTemPet(false);
        formulario.setSeraResponsavel(true);
        formulario.setMoraEmCasa(true);
        formulario.setIsTelado(false);
        formulario.setCasaPortaoAlto(true);

        AdotanteResponseDto adotante = AdotanteResponseDto.builder()
                .id(1L)
                .nome("João Silva")
                .dtNasc(LocalDate.of(1990, 1, 1))
                .cadastro(LocalDate.now())
                .email("joao.silva@example.com")
                .senha("123456") // Lembre-se de evitar expor a senha em APIs reais
                .celular("999999999")
                .fotoPerfil("foto_url")
                .formulario(formulario)
                .endereco(endereco)
                .build();

        List<AdotanteResponseDto> adotantes = List.of(adotante);

        when(adotanteService.recuperarAdotantesOrdenadosPorEstado()).thenReturn(adotantes);

        // Execução
        ResponseEntity<List<AdotanteResponseDto>> resposta = adotanteController.recuperarAdotantesOrdenadosPorEstado();

        // Verificação
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(1, resposta.getBody().size());
        assertEquals("João Silva", resposta.getBody().get(0).getNome());
        assertEquals("SP", resposta.getBody().get(0).getEndereco().getEstado());
        assertEquals(true, resposta.getBody().get(0).getFormulario().getTemCrianca());
    }

    @Test
    @DisplayName("Testa a recuperação de adotantes ordenados por estado quando vazio")
    void testRecuperarAdotantesOrdenadosPorEstado_NoContent() {
        // Configuração
        when(adotanteService.recuperarAdotantesOrdenadosPorEstado()).thenReturn(Collections.emptyList());

        // Execução
        ResponseEntity<List<AdotanteResponseDto>> resposta = adotanteController.recuperarAdotantesOrdenadosPorEstado();

        // Verificação
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());

    }

    @Test
    @DisplayName("Deve atualizar um adotante com sucesso")
    void deveAtualizarAdotanteComSucesso() {
        // Configuração
        Long id = 1L;

        AdotanteUpdateDto adotanteUpdateDto = AdotanteUpdateDto.builder()
                .nome("Novo Nome")
                .email("novo.email@example.com")
                .celular("999999999")
                .build();

        Adotante adotanteAtualizado = new Adotante();
        adotanteAtualizado.setId(id);
        adotanteAtualizado.setNome("Novo Nome");
        adotanteAtualizado.setEmail("novo.email@example.com");
        adotanteAtualizado.setCelular("999999999");


        AdotanteResponseDto responseDto = AdotanteMapper.toResponseDto(adotanteAtualizado);

        when(adotanteService.atualizarAdotante(id, adotanteUpdateDto)).thenReturn(adotanteAtualizado);

        // Execução
        ResponseEntity<AdotanteResponseDto> resposta = adotanteController.atualizarAdotante(id, adotanteUpdateDto);

        // Verificação
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals("Novo Nome", resposta.getBody().getNome());
        assertEquals("novo.email@example.com", resposta.getBody().getEmail());
    }

    @Test
    @DisplayName("Deve retornar 404 quando o adotante não é encontrado")
    void deveRetornar404QuandoAdotanteNaoEncontrado() {
        // Configuração
        Long id = 999L; // ID inexistente
        AdotanteUpdateDto adotanteUpdateDto = AdotanteUpdateDto.builder()
                .nome("Nome")
                .email("email@example.com")
                .build();

        when(adotanteService.atualizarAdotante(id, adotanteUpdateDto))
                .thenThrow(new EntityNotFoundException("Adotante não encontrado"));

        // Execução
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                adotanteController.atualizarAdotante(id, adotanteUpdateDto)
        );

        // Verificação
        assertEquals("Adotante não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar o formulário com sucesso")
    void deveAtualizarFormComSucesso() {
        // Configuração
        Long id = 1L;

        FormularioCreateDto formularioCreateDto = FormularioCreateDto.builder()
                .temCrianca(true)
                .moradoresConcordam(true)
                .temPet(false)
                .seraResponsavel(true)
                .moraEmCasa(true)
                .isTelado(false)
                .casaPortaoAlto(true)
                .build();

        AdotanteResponseDto adotanteResponse = AdotanteResponseDto.builder()
                .id(id)
                .nome("João Silva")
                .formulario(FormularioResponseAdotanteDto.builder()
                        .temCrianca(true)
                        .moradoresConcordam(true)
                        .temPet(false)
                        .seraResponsavel(true)
                        .moraEmCasa(true)
                        .isTelado(false)
                        .casaPortaoAlto(true)
                        .build())
                .build();

        when(formularioService.atualizarFormulario(formularioCreateDto, id)).thenReturn(adotanteResponse);

        // Execução
        ResponseEntity<AdotanteResponseDto> resposta = adotanteController.atualizarFormulario(formularioCreateDto, id);

        // Verificação
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals(id, resposta.getBody().getId());
        assertTrue(resposta.getBody().getFormulario().getTemCrianca());
    }

    @Test
    @DisplayName("Deve retornar 404 quando o adotante não é encontrado")
    void deveRetornar404QuandoAdotanteFormularioNaoEncontrado() {
        // Configuração
        Long id = 999L; // ID inexistente
        FormularioCreateDto formularioCreateDto = FormularioCreateDto.builder()
                .temCrianca(true)
                .moradoresConcordam(true)
                .build();

        when(formularioService.atualizarFormulario(formularioCreateDto, id))
                .thenThrow(new EntityNotFoundException("Adotante não encontrado"));

        // Execução
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                adotanteController.atualizarFormulario(formularioCreateDto, id)
        );

        // Verificação
        assertEquals("Adotante não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar o formulário com sucesso")
    void deveAtualizarFormularioComSucesso() {
        // Configuração
        Long id = 1L;

        FormularioCreateDto formularioCreateDto = FormularioCreateDto.builder()
                .temCrianca(true)
                .moradoresConcordam(true)
                .temPet(false)
                .seraResponsavel(true)
                .moraEmCasa(true)
                .isTelado(false)
                .casaPortaoAlto(true)
                .build();

        AdotanteResponseDto adotanteResponseDto = AdotanteResponseDto.builder()
                .id(id)
                .nome("Maria Silva")
                .email("maria.silva@example.com")
                .celular("999999999")
                .build();

        when(formularioService.atualizarFormulario(formularioCreateDto, id)).thenReturn(adotanteResponseDto);

        // Execução
        ResponseEntity<AdotanteResponseDto> resposta = adotanteController.atualizarFormulario(formularioCreateDto, id);

        // Verificação
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals("Maria Silva", resposta.getBody().getNome());
        assertEquals("maria.silva@example.com", resposta.getBody().getEmail());
    }


    @Test
    @DisplayName("Deve retornar 200 OK quando o formulário do adotante é encontrado")
    void testRecuperarFormularioAdotante_Success() {
        // Configuração
        Long id = 1L;

        // Usando o builder do Lombok para criar a instância de forma mais limpa
        AdotanteFormularioDto adotanteFormularioDto = AdotanteFormularioDto.builder()
                .nome("João Silva")
                .email("joao.silva@example.com")
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .telefone("123456789")
                .fotoPerfil("fotoPerfilUrl")
                .endereco(EnderecoResponseOngDto.builder()
                        .id(1L)
                        .cep("12345-678")
                        .rua("Rua Exemplo")
                        .bairro("Bairro Exemplo")
                        .cidade("Cidade Exemplo")
                        .estado("EX")
                        .build())
                .formulario(FormularioResponseAdotanteDto.builder()
                        .temCrianca(true)
                        .moradoresConcordam(true)
                        .temPet(false)
                        .seraResponsavel(true)
                        .moraEmCasa(true)
                        .isTelado(false)
                        .casaPortaoAlto(true)
                        .build())
                .build();

        // Simulando a chamada do serviço
        when(formularioService.recuperarAdotanteForm(id)).thenReturn(adotanteFormularioDto);

        // Execução
        ResponseEntity<AdotanteFormularioDto> resposta = adotanteController.recuperarFormularioAdotante(id);

        // Verificação
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(adotanteFormularioDto, resposta.getBody());
    }

    @Test
    @DisplayName("Deve retornar 204 No Content quando um adotante é deletado com sucesso")
    void testDeletarAdotante_Success() {
        // Configuração
        Long id = 1L;

        // Simulando que o método do serviço foi chamado
        doNothing().when(adotanteService).deletarAdotante(id);

        // Execução
        ResponseEntity<Void> response = adotanteController.deletarAdotante(id);

        // Verificação
        verify(adotanteService, times(1)).deletarAdotante(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar 200 OK e o formulário do adotante quando o ID é válido")
    void testRecuperarFormularioAdotanteUser_Success() {
        // Configuração
        Long id = 1L;
        FormularioResponseAdotanteDto formularioDto = FormularioResponseAdotanteDto.builder()
                .temCrianca(true)
                .moradoresConcordam(true)
                .temPet(false)
                .seraResponsavel(true)
                .moraEmCasa(true)
                .isTelado(false)
                .casaPortaoAlto(true)
                .build();

        // Simulando a chamada do serviço
        when(formularioService.recuperarFormularioAdotante(id)).thenReturn(formularioDto);

        // Execução
        ResponseEntity<FormularioResponseAdotanteDto> response = adotanteController.recuperarFormularioAdotanteUser(id);

        // Verificação
        verify(formularioService, times(1)).recuperarFormularioAdotante(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(formularioDto, response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 200 OK e os dados do adotante com a URL da foto de perfil quando o ID é válido")
    void testRecuperarAdotanteDadosFoto_Success() {
        // Configuração
        Long id = 1L;
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("12345-678");
        enderecoDto.setEstado("SP");
        enderecoDto.setCidade("São Paulo");
        enderecoDto.setBairro("Centro");
        enderecoDto.setRua("Rua das Flores");
        enderecoDto.setNumero("123");

        AdotanteDadosFoto adotanteDadosFoto = AdotanteDadosFoto.builder()
                .nome("João Silva")
                .dataNascimeto(LocalDate.of(1990, 5, 15))
                .email("joao.silva@example.com")
                .telefone("123456789")
                .urlFoto("http://example.com/foto.jpg")
                .endereco(enderecoDto)
                .build();

        // Simulando a chamada do serviço
        when(adotanteService.recuperarAdotanteDadosFoto(id)).thenReturn(adotanteDadosFoto);

        // Execução
        ResponseEntity<AdotanteDadosFoto> response = adotanteController.recuperarAdotanteDadosFoto(id);

        // Verificação
        verify(adotanteService, times(1)).recuperarAdotanteDadosFoto(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adotanteDadosFoto, response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 200 OK e a lista de animais favoritos quando o ID do adotante é válido")
    void testRecuperarAnimaisFavoritosAdotante_Success() {
        // Configuração
        Long id = 1L;
        AnimalFavoritoDto animalFavorito1 = new AnimalFavoritoDto(1L, "Rex", 5, "Cachorro", "Macho", "Grande", 10, "http://example.com/rex.jpg");
        AnimalFavoritoDto animalFavorito2 = new AnimalFavoritoDto(2L, "Mimi", 3, "Gato", "Fêmea", "Pequeno", 5, "http://example.com/mimi.jpg");

        AnimalFavoritoUsuarioDto adotanteDto = AnimalFavoritoUsuarioDto.builder()
                .usuarioId(id)
                .animaisfavoritos(List.of(animalFavorito1, animalFavorito2))
                .build();

        // Simulando a chamada do serviço
        when(adotanteService.recuperarAnimaisFavoritosAdotante(id)).thenReturn(adotanteDto);

        // Execução
        ResponseEntity<AnimalFavoritoUsuarioDto> response = adotanteController.recuperarAnimaisFavoritosAdotante(id);

        // Verificação
        verify(adotanteService, times(1)).recuperarAnimaisFavoritosAdotante(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adotanteDto, response.getBody());
    }


    @Test
    @DisplayName("Deve retornar 403 Forbidden quando o adotante não estiver autenticado")
    void testAdotanteAutenticado_Failure() {
        // Simulando um Authentication não autenticado
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        AdotanteController controller = new AdotanteController(); // instância do controlador

        // Execução
        ResponseEntity<AdotanteUserDto> response = controller.adotanteAutenticado();

        // Verificação
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar 200 OK e a lista de requisições de adoção")
    void testListarRequisicoesAdotante_Success() {
        // Criação de uma lista de AdotanteDtoListaRequisicao de exemplo
        AdotanteDtoListaRequisicao requisicao1 = AdotanteDtoListaRequisicao.builder()
                .idRequisicao(1L)
                .idAnimal(101L)
                .nomePet("Rex")
                .imagem("url-imagem-rex.jpg")
                .dataAplicacao(LocalDateTime.now())
                .status("Aprovado")
                .motivoRecusa(null)
                .build();

        AdotanteDtoListaRequisicao requisicao2 = AdotanteDtoListaRequisicao.builder()
                .idRequisicao(2L)
                .idAnimal(102L)
                .nomePet("Fifi")
                .imagem("url-imagem-fifi.jpg")
                .dataAplicacao(LocalDateTime.now())
                .status("Em análise")
                .motivoRecusa(null)
                .build();

        List<AdotanteDtoListaRequisicao> listaRequisicoes = Arrays.asList(requisicao1, requisicao2);


        when(adotanteService.listarRequisicoesAdotante(1L)).thenReturn(listaRequisicoes);



        // Chamada do método a ser testado
        ResponseEntity<List<AdotanteDtoListaRequisicao>> response = adotanteController.listarRequisicoesAdotante(1L);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(requisicao1, response.getBody().get(0));
        assertEquals(requisicao2, response.getBody().get(1));

        // Verifica que o método do serviço foi chamado com o parâmetro correto
        verify(adotanteService).listarRequisicoesAdotante(1L);
        verifyNoMoreInteractions(adotanteService);
    }
    @Test
    @DisplayName("Deve retornar 200 OK e a lista de ONGs favoritas quando o adotante é encontrado")
    void testRecuperarOngsFavoritasAdotante_Success() {
        // Mock do serviço adotanteService

        // Criação de dados de teste
        Long idAdotante = 1L;
        AdotanteFavoritoOngDto adotanteDto = AdotanteFavoritoOngDto.builder()
                .usuarioId(idAdotante)
                .ongFavoritas(List.of(
                        OngFavoritaDto.builder()
                                .ongId(1L)
                                .nome("ONG Teste")
                                .imagem("imagem.jpg")
                                .endereco(EnderecoDto.builder()
                                        .cep("12345-678")
                                        .estado("SP")
                                        .cidade("São Paulo")
                                        .bairro("Centro")
                                        .rua("Rua Exemplo")
                                        .numero("123")
                                        .build())
                                .build()))
                .build();

        // Configuração do mock para simular a lógica de recuperação de dados
        when(adotanteService.recuperarOngsFavoritasAdotante(idAdotante)).thenReturn(adotanteDto);

        // Chamada do método a ser testado
        ResponseEntity<AdotanteFavoritoOngDto> response = adotanteController.recuperarOngsFavoritasAdotante(idAdotante);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(idAdotante, response.getBody().getUsuarioId());
        assertFalse(response.getBody().getOngFavoritas().isEmpty());

        // Verifica se o método do serviço foi chamado com o parâmetro correto
        verify(adotanteService).recuperarOngsFavoritasAdotante(idAdotante);
        verifyNoMoreInteractions(adotanteService);
    }



}
