package gerenciador.pessoa.rest.repository;

import gerenciador.pessoa.rest.model.Endereco;

public interface ApiJsonCep {
	Endereco buscaEndereco(String cep) throws Exception;
}
