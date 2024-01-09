import entidades.DepartamentosEntity;
import entidades.EmpleadosEntity;
import entidades.LocalizacionesEntity;
import entidades.TrabajosEntity;
import exceptions.ConstraintsViolationException;
import exceptions.SqlExecutionException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.*;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainClass {
    static List<String> constraints = new ArrayList<>();

    public static void main(String[] args) {

        List<DepartamentosEntity> listdepartmentsFinal = new ArrayList<>();
        List<EmpleadosEntity> listEmployeesFinal = new ArrayList<>();
        DepartamentosEntity[] array = new DepartamentosEntity[3];
        EmpleadosEntity[] arrayEmployers = new EmpleadosEntity[5];

        array[0] = new DepartamentosEntity(90, "Formación", 100, 1300);
        array[1] = new DepartamentosEntity(100, "Bienestar", 100, 1300);
        array[2] = new DepartamentosEntity(110, "Cooperación Social", 301, 1300);

        Date dateOne = getDate("2006-06-16");
        Date dateTwo = getDate("2008-05-02");
        Date dateThree = getDate("2015-08-22");
        Date dateFour = getDate("2010-07-16");
        Date dateFive = getDate("2012-05-15");

        arrayEmployers[0] = new EmpleadosEntity(103, "Andrea", "Tellez", "at@scoot.com", "910254874", dateOne, "IT_PROG", 9000, 0.20, 405, 90);
        arrayEmployers[1] = new EmpleadosEntity(104, "Pedro", "Flores", "pf@scoot.com", "910254874", dateTwo, "AD_PRES", 20000.00, 0.20, 405, 90);
        arrayEmployers[2] = new EmpleadosEntity(105, "Teresa", "Rodriguez", "tr@scoot.com", "910254874", dateThree, "AC_ACCOUNT", 6000.00, 0.20, 405, 100);
        arrayEmployers[3] = new EmpleadosEntity(106, "Rodrigo", "Arrieta", "ra@scoot.com", "910254874", dateFour, "ST_CLERK", 2000.00, 0.20, 405, 110);
        arrayEmployers[4] = new EmpleadosEntity(107, "Margarita", "Mendoza", "mm@scoot.com", "910254874", dateFive, "ST_MAN", 7000.00, 0.20, 405, 110);

        try {
            SessionFactory sf = SessionFactoryUtil.getSessionFactory();
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();

            // What do I want to do? Exercise 1. ==============================
            // insertDepartmentsLocation(array, session, tx);

            // What do I want to do? Exercise 2 ===============================
            for (int i = 0; i < arrayEmployers.length; i++) {
                //  insertEmployersDepartment(arrayEmployers[i], session);
            }

            //What do I want to do? Exercise 3 =================================
            // Modify employee 300
            // modifyEmploye(301, session, tx);
            //Change the location of department 20 to the new address
            // modifyDepartment(20, 4000, session, tx);

            // What I want to achieve in Exercise 4: =============================
            //delete with constraints
            //boolean eliminado = deleteDepartmentTwo(60, session, tx);
            //System.out.println(eliminado);
            //delete without constraints
            //eliminado = deleteDepartment(80, session, tx);
            //System.out.println(eliminado);
            //  boolean eliminado=deleteDepartmentTwo("Ventas", session,tx);
            //  System.out.println("Departamento Eliminado ?  "+eliminado);

            //What I want to achieve in exercise 5: ===============================
            //Charge the employee with your ID and use load(); if the employee exists, also charge their department.
            // chargeEmployee(606, session, tx);

            //What I want to achieve in exercise 6: ================================
            //Charge all the employees in the ArrayList and all the departments in another ArrayList, verify if they are unique in the list and not repeated.
            listEmployeesFinal = arrayEmployees(session, tx);
            listdepartmentsFinal = arrayDepartments(session, tx, listEmployeesFinal);
            //  chargeEmployeeArray(session, tx, listEmployeesFinal, listdepartmentsFinal);
            System.out.println("================EMPLOYEES====================");
            for (int i = 0; i < listEmployeesFinal.size(); i++) {
                System.out.println(listEmployeesFinal.get(i).getNombre() + " " + listEmployeesFinal.get(i).getApellido());
            }
            System.out.println("================DEPARTMENTS====================");
            for (int i = 0; i < listdepartmentsFinal.size(); i++) {
                System.out.println(listdepartmentsFinal.get(i).getNombreDepartamento());
            }
            //When you finish all the exercises, make a commit and complete the process.
            tx.commit();
            //Closed the session
            session.close();
        } catch (Throwable t) {
            System.err.println("ERROR: " + t.getCause() +
                    " : " + t.getMessage());
        }
    }//Exercise six

    private static void chargeEmployeeArray(Session session, Transaction tx, List<EmpleadosEntity> listEmployeesFinal, List<DepartamentosEntity> listdepartmentsFinal) {
        if (listEmployeesFinal == null) {
            listEmployeesFinal = new ArrayList<>();
        }
        listEmployeesFinal = arrayEmployees(session, tx);

        if (listEmployeesFinal.size() > 0) {
            listdepartmentsFinal = arrayDepartments(session, tx, listEmployeesFinal);
        }
    }

    private static List<DepartamentosEntity> arrayDepartments(Session session, Transaction tx, List<EmpleadosEntity> listEmployeesFinal) {
        List<DepartamentosEntity> dptosArray = new ArrayList<>();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }
        for (int i = 0; i < listEmployeesFinal.size(); i++) {
            try {
                if (listEmployeesFinal.get(i).getIdDepartamento() != null) {
                    int idDepartment = listEmployeesFinal.get(i).getIdDepartamento();
                    boolean idExists = dptosArray.stream().anyMatch(
                            department -> department.getIdDepartamento() == idDepartment);
                    if (!idExists) {
                        DepartamentosEntity chargeDepartment = session.load(DepartamentosEntity.class, idDepartment);
                        if (chargeDepartment == null) {
                            System.out.println("Empleado sin departamento asignado");
                        } else {
                            if (!dptosArray.contains(chargeDepartment)) {
                                dptosArray.add(chargeDepartment);
                            }
                        }
                    }
                }
            } catch (ObjectNotFoundException onfe) {
                System.out.println("Error cargando departamento para el empleado: " + onfe.getMessage());
            }
        }


        return dptosArray;
    }

    private static List<EmpleadosEntity> arrayEmployees(Session session, Transaction tx) {
        List<EmpleadosEntity> listEmployees = new ArrayList<>();
        session = SessionFactoryUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        String consultation = "FROM EmpleadosEntity";
        Query query = session.createQuery(consultation);
        listEmployees = query.list();

        return listEmployees;
    }

    //exercise five
    private static void chargeEmployee(int idEmployee, Session session, Transaction tx) {

        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }
        EmpleadosEntity checkEmployee = session.get(EmpleadosEntity.class, idEmployee);
        if (checkEmployee == null) {
            System.out.println("El empleado no existe!!");
        } else {
            System.out.println("Empleado nombre : " + checkEmployee.getNombre() + " " + checkEmployee.getApellido());
            int id_department = checkEmployee.getIdDepartamento();
            DepartamentosEntity departmentEmployee = session.load(DepartamentosEntity.class, id_department);
            if (departmentEmployee == null) {
                System.out.println("El empleado no está asociado a ningún departamento!!");
            }
            System.out.println("Trabaja en el departamento : " + departmentEmployee.getNombreDepartamento());
        }
    }

    //exercise four

    private static boolean deleteDepartmentTwo(String name, Session session, Transaction tx) {
        boolean removed = false;
        try {
            if (hasConstraints("departamentos", session, tx)) {
                System.out.println("No se puede elimminar al tener relación con otras tablas");
                for (int i = 0; i < constraints.size(); i++) {
                    System.out.println(constraints.get(i));
                }
                throw new ConstraintsViolationException("Cannot delete department due to constraints");
            }
            session.beginTransaction();
            String hql = "DELETE FROM DepartamentosEntity WHERE nombreDepartamento= :name";
            Query<DepartamentosEntity> query = session.createQuery(hql);
            query.setParameter("name", name);
            int filasAfectadas = query.executeUpdate();
            if (filasAfectadas > 0) {
                removed = true;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new SqlExecutionException("Error executing SQL query", e);
        }
        return removed;
    }

    private static boolean deleteDepartmentTwo(int idDepartment, Session session, Transaction tx) {
        boolean removed = false;
        try {
            if (tx == null || !tx.isActive()) {
                tx = session.beginTransaction();
            }
            if (hasConstraints("departamentos", session, tx)) {
                System.out.println("No se puede elimminar al tener relación con otras tablas");
                for (int i = 0; i < constraints.size(); i++) {
                    System.out.println(constraints.get(i));
                }
                throw new ConstraintsViolationException("Cannot delete department due to constraints");
            } else {
                deleteDepartment(idDepartment, session, tx);
            }
        } catch (ConstraintsViolationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Throwable t) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SqlExecutionException("Error executing SQL query", t);
        } finally {
            if (tx != null && tx.isActive()) {
                tx.commit();
            }
        }

        return removed;
    }

    private static boolean hasConstraints(String entity, Session session, Transaction tx) {
        boolean hasConstraints = false;
        try {
            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    DatabaseMetaData metaData = connection.getMetaData();
                    ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, entity);
                    while (foreignKeys.next()) {
                        String fkName = foreignKeys.getString("FK_NAME");
                        String fkTable = foreignKeys.getString("FKTABLE_NAME");
                        String fkColumn = foreignKeys.getString("FKCOLUMN_NAME");
                        String pkTable = foreignKeys.getString("PKTABLE_NAME");
                        String pkColumn = foreignKeys.getString("PKCOLUMN_NAME");

                        String constraint = "Foreign Key Constraint '" + fkName +
                                "' on table '" + fkTable +
                                "' column '" + fkColumn +
                                "' references table '" + pkTable +
                                "' column '" + pkColumn + "'";
                        constraints.add(constraint);
                    }
                }
            });
            if (constraints.size() > 0) {
                hasConstraints = true;
            }
        } catch (Throwable t) {
            System.err.println("ERROR: " + t.getCause() + " : " + t.getMessage());
        }
        return hasConstraints;
    }

//Buscar el departamento por nombre y dar id
    public int confirmarIdDpt(Session session, Transaction tx, String nombreDpto){
        int idDpto=0;
        List<DepartamentosEntity> listDepartments= new ArrayList<>();
        session = SessionFactoryUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        String consultation = "FROM DepartamentosEntity ";
        Query query = session.createQuery(consultation);
        listDepartments = query.list();

        for (int i = 0; i <listDepartments.size() ; i++) {
            if(listDepartments.get(i).getNombreDepartamento().equals(nombreDpto)){
                idDpto=listDepartments.get(i).getIdDepartamento();
            }
        }

        return idDpto;
    }

    private static boolean deleteDepartment(int idDepartment, Session session, Transaction tx) {
        boolean removed = false;
        try {
            if (tx == null || !tx.isActive()) {
                tx = session.beginTransaction();
            }
            DepartamentosEntity deleteDepartment = session.get(DepartamentosEntity.class, idDepartment);
            if (deleteDepartment == null) {
                throw new EntityNotFoundException("Department with id " + idDepartment + " not found");
            }
            session.delete(deleteDepartment);
            removed = true;
        } catch (Throwable t) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new SqlExecutionException("Error executing SQL query", t);
        } finally {
            if (tx != null && tx.isActive()) {
                tx.commit();
            }

        }
        return removed;
    }

    //Exercise three
    private static void modifyDepartment(int departmentId, int newLocationId, Session session, Transaction transaction) {
        try {
            if (transaction == null || !transaction.isActive()) {
                transaction = session.beginTransaction();
            }
            DepartamentosEntity dep = session.get(DepartamentosEntity.class, (int) departmentId);
            if (dep == null) {
                throw new EntityNotFoundException("Department with id " + departmentId + " not found");
            }
            dep.setIdLocalizacion(newLocationId);
            session.update(dep);
            transaction.commit();
        } catch (Throwable t) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SqlExecutionException("Error executing SQL query", t);
        }
    }

    private static void modifyEmploye(int idEmployee, Session session, Transaction transaction) {
        try {
            if (transaction == null || !transaction.isActive()) {
                transaction = session.beginTransaction();
            }
            //select employee 301
            EmpleadosEntity employee = session.get(EmpleadosEntity.class, idEmployee);
            if (employee == null) {
                throw new EntityNotFoundException("Employee with id " + idEmployee + " not found");
            }
            //charge id employee
            String idJob = employee.getIdTrabajo();
            //Verify if the salary is within the maximum salary range for the job.
            TrabajosEntity job = session.get(TrabajosEntity.class, idJob);

            if (job == null) {
                System.out.println("No existe esta categoría");
                return;
            }

            BigDecimal maxSallary = BigDecimal.valueOf(job.getMaxSalario());
            BigDecimal minSallary = BigDecimal.valueOf(job.getMinSalario());
            BigDecimal salario = employee.getSalario();
            BigDecimal aumento = salario.multiply(new BigDecimal("0.05")); // 5% de aumento
            salario = salario.add(aumento);

            if (salario.compareTo(maxSallary) <= 0 || salario.compareTo(minSallary) >= 0) {
                employee.setSalario(salario);
            } else if (salario.compareTo(minSallary) < 0) {
                employee.setSalario(minSallary);
            } else if (salario.compareTo(maxSallary) > 0) {
                employee.setSalario(maxSallary);
            }
            session.update(employee);
        } catch (Throwable t) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SqlExecutionException("Error executing SQL query", t);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }

    //Exercise two
    private static Date getDate(String fecha) {
        Date dateOne;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateOne = sdf.parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dateOne;
    }

    private static void insertEmployersDepartment(EmpleadosEntity arrayEmployer, Session session) {
        EmpleadosEntity employer = new EmpleadosEntity();
        employer.setIdEmpleado(arrayEmployer.getIdEmpleado());
        employer.setNombre(arrayEmployer.getNombre());
        employer.setApellido(arrayEmployer.getApellido());
        employer.setEmail(arrayEmployer.getEmail());
        employer.setFechaContratacion(arrayEmployer.getFechaContratacion());
        employer.setIdTrabajo(arrayEmployer.getIdTrabajo());
        employer.setSalario(arrayEmployer.getSalario());
        employer.setComision(arrayEmployer.getComision());
        employer.setIdDirector(arrayEmployer.getIdDirector());
        employer.setIdDepartamento(arrayEmployer.getIdDepartamento());
        session.persist(employer);
    }

    //Exercise one
    private static LocalizacionesEntity exerciceOne(LocalizacionesEntity tx) {
        tx.setIdLocalizacion(1300);
        tx.setDireccion("Calle Guadarrama 25");
        tx.setCodigoPostal("28260");
        tx.setCiudad("Galapagar");
        tx.setProvincia("Madrid");
        tx.setIdPais("ES");
        return tx;
    }

    private static DepartamentosEntity exerciceOne(DepartamentosEntity tx) {
        DepartamentosEntity transaction = new DepartamentosEntity();
        transaction.setIdDepartamento(tx.getIdDepartamento());
        transaction.setNombreDepartamento(tx.getNombreDepartamento());
        transaction.setIdDirector(tx.getIdDirector());
        transaction.setIdLocalizacion(tx.getIdLocalizacion());
        return transaction;
    }

    public static void insertDepartmentsLocation(DepartamentosEntity[] array, Session session, Transaction transaction) {
        try {
            if (transaction == null || !transaction.isActive()) {
                // Si no hay transacción activa, iniciar una nueva
                transaction = session.beginTransaction();
            }
            LocalizacionesEntity newLocal = new LocalizacionesEntity();
            newLocal = exerciceOne(newLocal);
            session.persist(newLocal);
            for (int i = 0; i < array.length; i++) {
                DepartamentosEntity newDepartment = exerciceOne(array[i]);
                session.persist(newDepartment);
            }
            transaction.commit();
        } catch (Throwable t) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("ERROR: " + t.getCause() + " : " + t.getMessage());
        }
    }
}
