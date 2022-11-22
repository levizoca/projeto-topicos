package br.gov.sp.fatec.projetotopicos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.projetotopicos.entity.Anotacao;
import br.gov.sp.fatec.projetotopicos.entity.Usuario;
import br.gov.sp.fatec.projetotopicos.repository.AnotacaoRepository;
import br.gov.sp.fatec.projetotopicos.repository.UsuarioRepository;

@Service
public class AnotacaoServiceImpl implements AnotacaoService {

    @Autowired
    private AnotacaoRepository repo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public List<Anotacao> todas() {
        return repo.findAll();
    }

    @Override
    public List<Anotacao> buscarNoTexto(String texto) {
        return repo.findByTextoContainsIgnoreCase(texto);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Anotacao salvar(Anotacao anotacao, String nomeUsuario) {
        if(nomeUsuario == null ||
                nomeUsuario.isBlank()) {
            throw new AnotacaoException("Não é possível criar anotação! Usuário não informado!");
        }
        Usuario usuario = usuarioRepo.findByNome(nomeUsuario);
        if(usuario == null) {
            throw new AnotacaoException("Não é possível criar anotação! Usuário não encontrado!");
        }
        anotacao.setUsuario(usuario);
        return repo.save(anotacao);
    }
    
}
