package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Table(name="empleados")
public class EmpleadosEntity {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Date fechaContratacion;
    private String idTrabajo;
    private BigDecimal salario;
    private BigDecimal comision;
    private Integer idDirector;
    private Integer idDepartamento;
    @Id
    private Long id;


    public EmpleadosEntity(int idEmpleado, String nombre, String apellido, String email, String telefono, Date fechaCon, String idTrabajo, double salario, double comision, Integer idDirector, Integer idDepartamento) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaContratacion=fechaCon;
        this.idTrabajo = idTrabajo;
        BigDecimal salarioDef= new BigDecimal(salario);
        this.salario = salarioDef;
        BigDecimal comisionDef= new BigDecimal(comision);
        this.comision = comisionDef;
        this.idDirector = idDirector;
        this.idDepartamento = idDepartamento;
    }

    public EmpleadosEntity() {

    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {

      this.fechaContratacion=fechaContratacion;
    }


    public String getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(String idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Integer getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        EmpleadosEntity that = (EmpleadosEntity) object;

        if (idEmpleado != that.idEmpleado) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (apellido != null ? !apellido.equals(that.apellido) : that.apellido != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null) return false;
        if (fechaContratacion != null ? !fechaContratacion.equals(that.fechaContratacion) : that.fechaContratacion != null)
            return false;
        if (idTrabajo != null ? !idTrabajo.equals(that.idTrabajo) : that.idTrabajo != null) return false;
        if (idDirector != null ? !idDirector.equals(that.idDirector) : that.idDirector != null) return false;
        if (idDepartamento != null ? !idDepartamento.equals(that.idDepartamento) : that.idDepartamento != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEmpleado;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (fechaContratacion != null ? fechaContratacion.hashCode() : 0);
        result = 31 * result + (idTrabajo != null ? idTrabajo.hashCode() : 0);
        result = 31 * result + (idDirector != null ? idDirector.hashCode() : 0);
        result = 31 * result + (idDepartamento != null ? idDepartamento.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmpleadosEntity{" +
                "idEmpleado=" + idEmpleado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaContratacion=" + fechaContratacion +
                ", idTrabajo='" + idTrabajo + '\'' +
                ", salario=" + salario +
                ", comision=" + comision +
                ", idDirector=" + idDirector +
                ", idDepartamento=" + idDepartamento +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
