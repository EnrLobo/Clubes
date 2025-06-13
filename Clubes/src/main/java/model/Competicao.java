package model;

public class Competicao {

	private int id;
	private String nome;
	private String nomeClube;
	private String cidade;
	
	public Competicao() {
		this(0);
	}
	
	
	public Competicao(int id) {
		this.id = id;
		setNome(" ");
		setNomeClube(" ");
		setCidade(" ");
	}
	
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeClube() {
		return nomeClube;
	}
	public void setNomeClube(String nomeClube) {
		this.nomeClube = nomeClube;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String clube) {
		this.cidade = clube;
	}
	
}
