package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.dto.animal.*;
import com.example.adpotme_api.dto.requisicao.RequisicaoDto;
import com.example.adpotme_api.entity.animal.*;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.personalidade.Personalidade;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.integration.CloudinaryService;
import com.example.adpotme_api.mapper.AnimalMapper;
import com.example.adpotme_api.mapper.PersonalidadeMapper;
import com.example.adpotme_api.repository.*;
import com.example.adpotme_api.util.Recursao;
import com.example.adpotme_api.util.Sorting;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private OngRepository ongRepository;
    @Autowired
    private PersonalidadeRepository personalidadeRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private RequisicaoRepository requisicaoRepository;
    @Autowired
    private FormularioRepository formularioRepository;

    @Transactional
    public Animal cadastrarCachorro(CachorroCreateDto cachorroDto, MultipartFile fotoPerfil1, MultipartFile fotoPerfil2, MultipartFile fotoPerfil3, MultipartFile fotoPerfil4, MultipartFile fotoPerfil5) {
        List<MultipartFile> fotos = new ArrayList<>();
        fotos.add(fotoPerfil2);
        fotos.add(fotoPerfil3);
        fotos.add(fotoPerfil4);
        fotos.add(fotoPerfil5);
        Set<ConstraintViolation<CachorroCreateDto>> violations = validator.validate(cachorroDto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<CachorroCreateDto> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, sb.toString());
        }
        Optional<Ong> ongOpt = ongRepository.findById(cachorroDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Cachorro cachorro = new Cachorro(cachorroDto);
        cachorro.setTaxaAdocao(cachorroDto.getTaxaAdocao());
        Personalidade personalidade = PersonalidadeMapper.toEntity(cachorroDto.getPersonalidade());
        Ong ong = ongOpt.get();
        cachorro.setOng(ong);

        cachorro.setPersonalidade(personalidade);
        if(fotoPerfil1 != null && !fotoPerfil1.isEmpty()){
            try {
                Image image = cloudinaryService.upload(fotoPerfil1);
                cachorro.setFotoPerfil(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

for(MultipartFile fotoPerfil : fotos) {
    if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
        try {
            Image image = cloudinaryService.upload(fotoPerfil);
            cachorro.getFotos().add(image);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

        personalidadeRepository.save(personalidade);

        return animalRepository.save(cachorro);
    }

    @Transactional
    public Animal cadastrarGato(GatoCreateDto gatoDto, MultipartFile fotoPerfil1, MultipartFile fotoPerfil2, MultipartFile fotoPerfil3, MultipartFile fotoPerfil4, MultipartFile fotoPerfil5) {
        List<MultipartFile> fotosGato = new ArrayList<>();
        fotosGato.add(fotoPerfil2);
        fotosGato.add(fotoPerfil3);
        fotosGato.add(fotoPerfil4);
        fotosGato.add(fotoPerfil5);
        Set<ConstraintViolation<GatoCreateDto>> violations = validator.validate(gatoDto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<GatoCreateDto> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, sb.toString());
        }
        Optional<Ong> ongOpt = ongRepository.findById(gatoDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Gato gato = new Gato(gatoDto);
        Personalidade personalidade = PersonalidadeMapper.toEntity(gatoDto.getPersonalidade());

        Ong ong = ongOpt.get();
        gato.setPersonalidade(personalidade);
        gato.setOng(ong);
        gato.setTaxaAdocao(gatoDto.getTaxaAdocao());
        for(MultipartFile fotoPerfil : fotosGato) {
            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                try {
                    Image image = cloudinaryService.upload(fotoPerfil);
                    gato.getFotos().add(image);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(fotoPerfil1 != null && !fotoPerfil1.isEmpty()){
            try {
                Image image = cloudinaryService.upload(fotoPerfil1);
                gato.setFotoPerfil(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        personalidadeRepository.save(personalidade);
        return animalRepository.save(gato);
    }

    public List<Animal> recuperarAnimais() {
        return animalRepository.findAll();
    }

    public Animal recuperarAnimalPorId(Long id) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        return animalOpt.get();
    }

    @Transactional
    public Animal atualizarCachorro(Long id, AnimalUpdateDto cachorroAtualizado) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cachorro não encontrado");
        }
        Cachorro cachorro = (Cachorro) animalOpt.get();



        cachorro.setNome(cachorroAtualizado.getNome());
        cachorro.setRaca(cachorroAtualizado.getRaca());
        cachorro.setIsVisible(cachorroAtualizado.getIsVisible());
        cachorro.setTaxaAdocao(cachorroAtualizado.getTaxaAdocao());

        return animalRepository.save(cachorro);
    }

    @Transactional
    public Animal atualizarGato(Long id, AnimalUpdateDto gatoAtualizado) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gato não encontrado");
        }
        Gato gato = (Gato) animalOpt.get();
        gato.setNome(gatoAtualizado.getNome());
        gato.setRaca(gatoAtualizado.getRaca());
        gato.setIsVisible(gatoAtualizado.getIsVisible());
        gato.setTaxaAdocao(gatoAtualizado.getTaxaAdocao());


        return animalRepository.save(gato);
    }

    @Transactional
    public void deletarAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        List<Requisicao> requisicoes = requisicaoRepository.findByAnimal(animalOpt.get());
        for(Requisicao requisicao : requisicoes) {
            if(requisicao.getStatus() == Status.APROVADO || requisicao.getStatus() == Status.REVISAO || requisicao.getStatus() == Status.DOCUMENTACAO || requisicao.getStatus() == Status.NOVA || requisicao.getStatus() == Status.CONCLUIDO) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível deletar um animal com aplicações ativas");
            }

            requisicaoRepository.delete(requisicao);

        }
        animalRepository.deleteById(id);
    }


    public Integer recuperarQuantidadeAnimaisPorOng(Long ongId) {
        Optional<Ong> ongOpt = ongRepository.findById(ongId);
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();
        List<Animal> animalOpt = animalRepository.findByOng(ong);


        return Recursao.buscarQuantidade(animalOpt, 0);


    }

    public Animal[] recuperarAnimaisPorPersonalidade(String personalidade) {
        List<Animal> animais = animalRepository.findAll();
        return Sorting.selectionSortPetPorPersonalidade(animais, personalidade);
    }

    public void importarAnimais(MultipartFile file) {


    }

    public void exportarAnimais(Writer writer, Long id) {
        Ong ong = ongRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada"));
        List<Animal> animais = animalRepository.findByOng(ong);
        try {
            // Escrevendo cabeçalho
            writer.write("id;nome;anoNascimento;sexo;especie;raca;dataAbrigo;cadastro;isCastrado;descricao;isVisible;isAdotado;porte;isVermifugado;taxaAdocao;\n");

            // Escrevendo corpo (dados)
            for (Animal animalEntity : animais) {
                AnimalCsvDto animal = AnimalMapper.toAnimalCsvDto(animalEntity);
                writer.write(animal.getId() + ";" +
                        animal.getNome() + ";" +
                        animal.getAnoNascimento() + ";" +
                        animal.getSexo() + ";" +
                        animal.getEspecie() + ";" +
                        animal.getRaca() + ";" +
                        animal.getDataAbrigo() + ";" +
                        animal.getCadastro() + ";" +
                        animal.getIsCastrado() + ";" +
                        animal.getDescricao() + ";" +
                        animal.getIsVisible() + ";" +
                        animal.getIsAdotado() + ";" +
                        animal.getPorte() + ";" +
                        animal.getIsVermifugado() + ";" +
                        animal.getTaxaAdocao() + ";" + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao exportar arquivo CSV: " + e.getMessage());
        }
    }

    public List<AnimalOngResponseDto> recuperarAnimaisComPersonalidade() {
        List<Animal> animais = animalRepository.findAll();
        List<AnimalOngResponseDto> animaisDto = new ArrayList<>();
        for(Animal animal : animais) {
            if(animal.getIsVisible()) {
                AnimalOngResponseDto animalDaVez = AnimalMapper.toAnimalOngResponseDto(animal);
                animaisDto.add(animalDaVez);
            }
        }
        return animaisDto;
    }

    public List<AnimalAplicacaoDto> recuperarAnimaisPelaAplicacaoPorOng(Long ongId) {
        Optional<Ong> ongOpt = ongRepository.findById(ongId);
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }

        Ong ong = ongOpt.get();
        List<Animal> animais = animalRepository.findByOng(ong);
        List<Requisicao> requisicoes = requisicaoRepository.findAll();

        List<AnimalAplicacaoDto> animaisDto = new ArrayList<>();

        for (Animal animal : animais) {
            int qtdAplicacoes = 0;
            String situacao = "Sem aplicação";
            LocalDateTime enviado = null;

            boolean isAprovado = false;
            boolean isAdotado = false;

            for (Requisicao requisicao : requisicoes) {
                if (requisicao.getAnimal().equals(animal)) {
                    qtdAplicacoes++;
                    enviado = requisicao.getDataRequisicao();

                    if (requisicao.getStatus() == Status.CONCLUIDO) {
                        situacao = "Adotado";
                        isAdotado = true;
                        break;
                    }

                    if (requisicao.getStatus() == Status.APROVADO) {
                        isAprovado = true;
                    } else if (!isAprovado && (requisicao.getStatus() == Status.REVISAO || requisicao.getStatus() == Status.NOVA)) {
                        situacao = "Revisão";
                    }
                }
            }


            if (isAdotado) {
                situacao = "Adotado";
            } else if (isAprovado) {
                situacao = "Aprovado";
            }

            AnimalAplicacaoDto animalDto = AnimalMapper.toAnimalAplicacaoDto(animal, qtdAplicacoes, situacao, enviado);
            animaisDto.add(animalDto);
        }

        return animaisDto;
    }



    public AnimalListaAplicacaoDto recuperarAnimalComListaAplicacao(Long idAnimal) {
        Animal animal = animalRepository.findById(idAnimal).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado"));
        List<Requisicao> requisicoes = requisicaoRepository.findByAnimal(animal);
        List<RequisicaoDto> requisicoesDto = new ArrayList<>();
        for(Requisicao requisicao : requisicoes) {
            RequisicaoDto requisicaoDto = AnimalMapper.toRequisicaoDto(requisicao);
            requisicoesDto.add(requisicaoDto);
        }
        return AnimalMapper.toAnimalListaAplicacaoDto(animal, requisicoesDto);
    }

    public AnimalOngResponseDto recuperarAnimalComPersonalidade(Long idAnimal) {
        Animal animal = animalRepository.findById(idAnimal).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado"));
        return AnimalMapper.toAnimalOngResponseDto(animal);
    }
}

