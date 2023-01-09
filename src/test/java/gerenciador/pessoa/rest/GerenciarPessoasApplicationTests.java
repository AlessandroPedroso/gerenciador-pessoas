package gerenciador.pessoa.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import gerenciador.pessoa.rest.model.Endereco;
import gerenciador.pessoa.rest.model.Pessoa;
import gerenciador.pessoa.rest.repository.ApiJsonCep;
import gerenciador.pessoa.rest.repository.ApiJsonCepImpl;
import gerenciador.pessoa.rest.repository.EnderecoRepository;
import gerenciador.pessoa.rest.repository.PessoaRepository;

@SpringBootTest
class GerenciarPessoasApplicationTests {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	private ApiJsonCep apiJsonCep = new ApiJsonCepImpl();
	
	@Test
	public void insertPessoa() throws ParseException {
		
		String dataNascimento = "16/11/1994";
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Teste");
		pessoa.setData_nascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
		
		pessoaRepository.save(pessoa);
	}
	
	@Test
	public void consultaTodos() {
		
		 Iterable<Pessoa> iPessoa =	pessoaRepository.findAll();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
		 
		 for (Pessoa pessoa : iPessoa) {
			
			 System.out.println("nome: " + pessoa.getNome());
			 System.out.println("Data nascimento: " + formatter.format(pessoa.getData_nascimento()));
		}
	 
	}
	
	@Test
	public void editarUmaPessoa() throws ParseException {
		
		String dataNascimento = "10/08/1995";
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(16L);
		pessoa.setData_nascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
		pessoa.setNome("Lucas");
		
		pessoaRepository.save(pessoa);
	}
	
	@Test
	public void consultarUmaPessoa() {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(14L);
		
		System.out.println(pessoa.get());
		
	}
	
	@Test
	public void buscaPorNomeParam() {
		
		Pessoa pessoa = pessoaRepository.buscaPorNomeParm("Alessandro");
		
		System.out.println(pessoa.getNome());
	}
	
	@Test
	public void testeInsertEndereco() throws Exception {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(13L);
		
		Endereco endereco = apiJsonCep.buscaEndereco("94834820");
		endereco.setNumero("75");
		endereco.setPessoa(pessoa.get());
		endereco.setEnderecoPrincipal(true);
		enderecoRepository.save(endereco);
		
		System.out.println(endereco);
		
	}
	
	@Test
	public void consultaTodosEEndereco() {
		
		 Iterable<Pessoa> iPessoa =	(Iterable<Pessoa>) pessoaRepository.buscaPessoaEndereco();
		 
		 System.out.println(iPessoa);
		
	}
	
	
	@Test
	public void consultaTodosEEnderecoPrincipal() {
		
		 Iterable<Endereco> iPessoa =(Iterable<Endereco>) enderecoRepository.enderecosPrincipal(true);
		 
		 System.out.println(iPessoa);
		
	}
	
	@Test
	public void consultaTodosEEnderecoPrincipalUsuario() {
		
		 Iterable<Endereco> iPessoa =(Iterable<Endereco>) enderecoRepository.enderecosPrincipalUsuario(true,13L);
		 
		 System.out.println(iPessoa);
		
	}
	
	

}
