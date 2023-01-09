package gerenciador.pessoa.rest.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

import gerenciador.pessoa.rest.model.Endereco;


public class ApiJsonCepImpl implements ApiJsonCep {

	@Override
	public Endereco buscaEndereco(String cep) throws Exception {
		
		Endereco endereco = new Endereco();
		
		URL url = new URL("https://viacep.com.br/ws/"+cep+"/json/");
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		String cepEndereco = "";
		StringBuilder jsoncep = new StringBuilder();
		
		while((cepEndereco = br.readLine()) !=null) {
			
			jsoncep.append(cepEndereco);
			
		}
		
		Endereco gson = new Gson().fromJson(jsoncep.toString(), Endereco.class);
		
		endereco.setLogradouro(gson.getLogradouro());
		endereco.setCep(gson.getCep());
		endereco.setLocalidade(gson.getLocalidade());
		
		return endereco;
	}

}
