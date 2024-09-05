package com.gugawag.pdist.ejbs;

import com.gugawag.pdist.model.Mensagem;
import com.gugawag.pdist.model.Usuario;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless(name = "usuarioService")
@Remote
public class UsuarioService {

    @EJB
    private UsuarioDAO usuarioDao;

    @EJB
    private MensagemDAO mensagemDao;

    private static final Set<String> PALAVROES = new HashSet<>(Arrays.asList(
            "foo", "bar", "baz", "qux", "quux", "corge","grault", "garply","waldo", "duff"
    ));

    public List<Usuario> listar() {
        return usuarioDao.listar();
    }
    public List<Mensagem> listarMensagem() {
        return mensagemDao.listar();
    }

    public void inserir(long id, String nome) {
        Usuario novoUsuario = new Usuario(id, nome);
        usuarioDao.inserir(novoUsuario);

//       mensagemDao.inserir(mensagem);
        if (id==3L) {
            throw new RuntimeException("Menor de idade não permitido!");
        }
        if (id == 4L) {
            novoUsuario.setNome(nome + " alterado");
        }
    }

    public void inserirMensagem(long id, String mensagem) throws IOException {
        Mensagem novaMensagem = new Mensagem(id, mensagem);
        mensagemDao.inserir(novaMensagem);

        System.out.println(contemPalavrao(mensagem));

        if (contemPalavrao(mensagem)){
            throw new RuntimeException("Palavrão detectado!!");
        }

    }

    public static boolean contemPalavrao(String texto) throws IOException {
        String[] palavras = texto.toLowerCase().split("\\s+");

        for (String palavra : palavras) {
            if (PALAVROES.contains(palavra)) {
                return true;
            }
        }
        return false;
    }
}
