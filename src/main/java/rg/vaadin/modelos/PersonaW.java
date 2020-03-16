package rg.vaadin.modelos;

import java.time.LocalDate;

public class PersonaW {
	
    private int id;
    private String nombre;
    private String genero;
    private String email;
    private int edad;
    private LocalDate fechaNacimiento;
	private String cargo;
    private String about;
	
    public PersonaW() {
		
	}

	public PersonaW(int id, String nombre, String genero, String email, int edad, LocalDate fechaNacimiento,
			String cargo,String about) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.genero = genero;
		this.email = email;
		this.edad = edad;
		this.fechaNacimiento = fechaNacimiento;
		this.cargo = cargo;
		this.about = about;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
    public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

    

}
