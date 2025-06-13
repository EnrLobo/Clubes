package model;

public class Clube {

	private int id;
	private String nome;
	private String estado;
	private Competicao comp;
	
	public Clube() {
		this(0);
	}
	
	public Clube(int id) {
		this.id = id;
		
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Competicao getCompeticao() {
		return comp;
	}
	public void setCompeticao(Competicao comp) {
		this.comp = comp;
	}
	
	
	
	
}
