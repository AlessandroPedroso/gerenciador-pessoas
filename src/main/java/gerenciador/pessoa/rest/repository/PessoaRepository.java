package gerenciador.pessoa.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gerenciador.pessoa.rest.model.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
	
	@Query(value = "select p from Pessoa p where p.nome = :paramnome")
	public Pessoa buscaPorNomeParm(@Param("paramnome") String paramnome);
	
	@Query(value = "SELECT DISTINCT e FROM Pessoa e INNER JOIN e.enderecos t")
	public List<Pessoa> buscaPessoaEndereco();
	
}
