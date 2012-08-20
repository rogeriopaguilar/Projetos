package model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Veiculo extends EntidadeBase implements Serializable{

	private String placa;
	@ManyToOne(fetch=FetchType.LAZY)
	private Pessoa responsavel;
	@OneToMany(mappedBy="veiculo", cascade=CascadeType.REMOVE)
	private List<HistoricoResponsabilidade> historicoResponsaveis;
	@ManyToOne(fetch=FetchType.LAZY)
	private Categoria categoria;
	private int anoFabricacao;
	@ManyToOne(fetch=FetchType.LAZY)
	private Marca marca;
	@ManyToOne(fetch=FetchType.LAZY)
	private Modelo modelo;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}

	public List<HistoricoResponsabilidade> getHistoricoResponsaveis() {
		return historicoResponsaveis;
	}

	public void setHistoricoResponsaveis(
			List<HistoricoResponsabilidade> historicoResponsaveis) {
		this.historicoResponsaveis = historicoResponsaveis;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

}
