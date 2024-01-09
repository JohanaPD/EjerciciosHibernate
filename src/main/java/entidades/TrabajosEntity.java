package entidades;

public class TrabajosEntity {
    private String idTrabajo;
    private String nombreTrabajo;
    private Integer minSalario;
    private Integer maxSalario;

    public TrabajosEntity(String idTrabajo, String nombreTrabajo, Integer minSalario, Integer maxSalario) {
        this.idTrabajo = idTrabajo;
        this.nombreTrabajo = nombreTrabajo;
        this.minSalario = minSalario;
        this.maxSalario = maxSalario;
    }

    public TrabajosEntity() {

    }

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(String idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public Integer getMinSalario() {
        return minSalario;
    }

    public void setMinSalario(Integer minSalario) {
        this.minSalario = minSalario;
    }

    public Integer getMaxSalario() {
        return maxSalario;
    }

    public void setMaxSalario(Integer maxSalario) {
        this.maxSalario = maxSalario;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        TrabajosEntity that = (TrabajosEntity) object;

        if (idTrabajo != null ? !idTrabajo.equals(that.idTrabajo) : that.idTrabajo != null) return false;
        if (nombreTrabajo != null ? !nombreTrabajo.equals(that.nombreTrabajo) : that.nombreTrabajo != null)
            return false;
        if (minSalario != null ? !minSalario.equals(that.minSalario) : that.minSalario != null) return false;
        if (maxSalario != null ? !maxSalario.equals(that.maxSalario) : that.maxSalario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTrabajo != null ? idTrabajo.hashCode() : 0;
        result = 31 * result + (nombreTrabajo != null ? nombreTrabajo.hashCode() : 0);
        result = 31 * result + (minSalario != null ? minSalario.hashCode() : 0);
        result = 31 * result + (maxSalario != null ? maxSalario.hashCode() : 0);
        return result;
    }
}
