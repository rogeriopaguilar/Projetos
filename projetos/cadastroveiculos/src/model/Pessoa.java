package model;


import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Pessoa extends EntidadeBase implements Serializable {
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
