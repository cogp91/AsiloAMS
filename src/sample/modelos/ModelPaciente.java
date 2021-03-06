package sample.modelos;

import sample.objetos.Paciente;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class ModelPaciente {
    public boolean insertar(Paciente p) {
        Statement myStmt = GeneralModel.connect();

        Integer num_cuarto;
        Integer num_cama;

        if(p.getNumero_cuarto() != null){
            num_cuarto = p.getNumero_cuarto().get();
        } else {
            num_cuarto = null;
        }

        if(p.getNumero_cama() != null){
            num_cama = p.getNumero_cama().get();
        } else {
            num_cama = null;
        }

        String query = "INSERT INTO ";
        query += "paciente ";
        String sql = new StringBuilder()
                .append("(sexo,fecha_nacimiento,nombre,apellido,ciudad,calle,codigo_postal,sangre,")
                .append("numero_cuarto,numero_cama,estatus,paciente_medicado_manana,paciente_medicado_tarde,paciente_medicado_noche,asilo_id) VALUES (")
                .append(p.getSexo()) // sexo
                .append(",'")
                .append(p.getFecha_nacimiento()) // fecha_nacimiento
                .append("','")
                .append(p.getNombre()) // nombre
                .append("','")
                .append(p.getApellido()) // apellido
                .append("','")
                .append(p.getCiudad()) // ciudad
                .append("','")
                .append(p.getCalle()) // calle
                .append("',")
                .append(p.getCodigo_postal()) // codigo_postal
                .append(",'")
                .append(p.getSangre()) // sangre
                .append("',")
                .append(num_cuarto) // numero_cuarto
                .append(",")
                .append(num_cama) // numero_cama
                .append(",")
                .append(p.isPaciente_medicado_manana()) // paciente_medicado_manana
                .append(",")
                .append(p.isPaciente_medicado_tarde()) // paciente_medicado_tarde
                .append(",")
                .append(p.isPaciente_medicado_noche()) // paciente_medicado_noche
                .append(",")
                .append(p.getEstatus()) // estatus
                .append(",")
                .append(p.getAsilo_id()) // asilo_id
                .append(")")
                .toString();
        query += sql;
        try {
            myStmt.executeUpdate(query);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Paciente getPaciente(int id){

        Paciente p = new Paciente();

        String query = "select * from paciente";
        ArrayList columnNames = new ArrayList();

        Statement myStmt = GeneralModel.connect();

        try {
            ResultSet myRs = myStmt.executeQuery(query);
            ResultSetMetaData md = myRs.getMetaData();
            int columns = md.getColumnCount();

            //Get column names
            for (int i = 1; i<= columns; i++){
                columnNames.add(md.getColumnName(i));
            }

            p.setSexo(myRs.getInt(1));
            p.setFecha_nacimiento(myRs.getString(2));
            p.setNombre(myRs.getString(3));
            p.setApellido(myRs.getString(4));
            p.setCiudad(myRs.getString(5));
            p.setCalle(myRs.getString(6));
            p.setCodigo_postal(myRs.getString(7));
            p.setSangre(myRs.getString(8));
            Optional<Integer> opAux = Optional.of(myRs.getInt(9));
            p.setNumero_cuarto(opAux);
            opAux = Optional.of(myRs.getInt(10));
            p.setNumero_cama(opAux);
            p.setPaciente_medicado_manana(myRs.getBoolean(11));
            p.setPaciente_medicado_tarde(myRs.getBoolean(12));
            p.setPaciente_medicado_noche(myRs.getBoolean(13));
            p.setEstatus(myRs.getInt(14));
            p.setAsilo_id(myRs.getInt(15));

            return p;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void despliegaTabla(String tabla){
        ArrayList data = new ArrayList();
        ArrayList columnNames = new ArrayList();

        String query = "select * from " + tabla;

        Statement myStmt = GeneralModel.connect();

        //Execute SQL query



        try {
            ResultSet myRs = myStmt.executeQuery(query);
            ResultSetMetaData md = myRs.getMetaData();
            int columns = md.getColumnCount();

            //Get column names
            for (int i = 1; i<= columns; i++){
                columnNames.add(md.getColumnName(i));
            }
            //Get row data
            while (myRs.next()){
                ArrayList row = new ArrayList(columns);
                for (int i = 1; i<= columns; i++){
                    System.out.println(myRs.getObject(i));
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
