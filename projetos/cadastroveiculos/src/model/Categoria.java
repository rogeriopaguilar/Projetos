package model;

import javax.persistence.Entity;

@Entity
public class Categoria extends EntidadeBase {
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
