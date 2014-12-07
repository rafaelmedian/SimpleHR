package modelo;

import javafx.beans.property.SimpleStringProperty;

public class Contacto {

	private SimpleStringProperty departamento = new SimpleStringProperty();
	private SimpleStringProperty puesto = new SimpleStringProperty();
	private SimpleStringProperty nombre = new SimpleStringProperty();
	private SimpleStringProperty apellido = new SimpleStringProperty();
	private SimpleStringProperty fechaNacimiento = new SimpleStringProperty();
	private SimpleStringProperty telefono = new SimpleStringProperty();
	private SimpleStringProperty email = new SimpleStringProperty();
	private SimpleStringProperty direccion = new SimpleStringProperty();
	
	
	
	
	
	
	
	/* *********************************************************************************************************************
	 *                                              CONSTRUCTOR DEL MODELO
	 **********************************************************************************************************************/
	
	public Contacto(String departamento, String puesto,String nombre,String apellido,String fechaNacimiento,String telefono,String email,String direccion) {
		this.departamento = new SimpleStringProperty(departamento);
		this.puesto = new SimpleStringProperty(puesto);
		this.nombre = new SimpleStringProperty(nombre);
		this.apellido = new SimpleStringProperty(apellido);
		this.fechaNacimiento = new SimpleStringProperty(fechaNacimiento);
		this.telefono = new SimpleStringProperty(telefono);
		this.email = new SimpleStringProperty(email);
		this.direccion = new SimpleStringProperty(direccion);
	}
	
	
	
	/* ***********************************************************************************************************************
	 *                                   SETTERS PARA LA EDICION DE CONTACTOS EN LA TABLA
	 ************************************************************************************************************************/
	

	///

	public void setPuesto(String puesto)
	{
		this.puesto.set(puesto);
	}
	public void setDepartamento(String departamento)
	{
		this.departamento.set(departamento);
	}
	public void setNombre(String nombre)
	{
		this.nombre.set(nombre);
	}
	public void setApellido(String apellido)
	{
		this.apellido.set(apellido);
	}
	public void setFechaNacimiento(String fechaNacimiento)
	{
		this.fechaNacimiento.set(fechaNacimiento);
	}
	public void setTelefono(String telefono)
	{
		this.telefono.set(telefono);
	}
	public void setEmail(String email)
	{
		this.email.set(email);
	}
	public void setDireccion(String direccion)
	{
		this.direccion.set(direccion);
	}
	
	
	
	/* ***********************************************************************************************************************
	 *                                                 GETTERS DEL MODELO
	 ************************************************************************************************************************/
	
	public String getDepartamento()
	{
		return this.departamento.get();
	}
	public String getPuesto()
	{
		return this.puesto.get();
	}
	public String getNombre()
	{
		return this.nombre.get();
	}
	public String getApellido()
	{
		return this.apellido.get();
	}
	public String getFechaNacimiento()
	{
		return this.fechaNacimiento.get();
	}
	public String getTelefono()
	{
		return this.telefono.get();
	}
	public String getEmail()
	{
		return this.email.get();
	}
	public String getDireccion()
	{
		return this.direccion.get();
	}
}
