package web;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import model.Pessoa;

public class RespostaAutocomplete implements Serializable {

	public String label;
	public String value;

	public RespostaAutocomplete(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RespostaAutocomplete other = (RespostaAutocomplete) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * Transforma uma lista de objetos qualquer em uma outra lista contendo objetos prontos para serem retornados no formato json	<br />
	 * seguindo o seguinte padrão: [{"label":label, "valor":valor},{"label":label, "valor":valor},...]								<br />
	 * O método utiliza os campos nomeDoCampoParaLabel e nomeDoCampoParaValor para localizar os métodos que irão retornar os 		<br />
	 * valores que serão utilizados nos campos label e valor dos objetos retornados na lista. Para que isso funcione corretamente	<br />
	 * é necessário que a classe que representa os objetos passados na lista declare os métodos get<nomeDoCampoParaLavel>			<br />
	 * e get<NomeDoCampoParaValor> para que estes sejam chamados durante a iteração da lista. 										<br />
	 * 
	 * @param lista contém os objetos que serão convertidos na nova lista
	 * @param nomeDoCampoParaLabel string que será utilizada para extrair o label de cada objeto na lista
	 * @param nomeDoCampoParaValor string que será utilizada para extrair o valor de cada objeto na lista
	 * @return lista contendo os objetos transformados. Cada objeto possui um campo chamado lavel e outro chamado valor				
	 * @throws RuntimeException caso ocorra algum problema na chamada aos métodos get<nomeDoCampoParaLavel> ou get<nomeDoCampoParaValor>
	 * @throws IllegalArgumentException caso não sejam informados os parâmetros lista, nomedoCampoParaLabel ou nomeDoCampoParaValor
	 * */
	public static List<RespostaAutocomplete> getResponstaAutoCompleteParaLista(List<?> lista, String nomeDoCampoParaLabel, String nomeDoCampoParaValor) {
		if(lista == null) {
			throw new IllegalArgumentException("O parâmetro lista deve ser informado!");
		}
		if(nomeDoCampoParaLabel == null) {
			throw new IllegalArgumentException("O parâmetro nomeDoCampoParaLabel deve ser informado!");
		}
		if(nomeDoCampoParaValor == null) {
			throw new IllegalArgumentException("O parâmetro nomeDoCampoParaValor deve ser informado!");
		}
		
		List<RespostaAutocomplete> listaRespostas = new ArrayList<RespostaAutocomplete>();
		for(int i = 0; i < lista.size(); i++){
			Object obj = lista.get(i);
			String label = "";
			String valor = "";
			Class classe = obj.getClass();
			try {
				label = "" + obj.getClass().getMethod("get" + nomeDoCampoParaLabel, null).invoke(obj, null);
				valor = "" + obj.getClass().getMethod("get" + nomeDoCampoParaValor, null).invoke(obj, null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			RespostaAutocomplete resp = new RespostaAutocomplete(label, valor);
			listaRespostas.add(resp);
		}	
		return listaRespostas;
	}
	
	
}
