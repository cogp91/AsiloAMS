package sample.modelos;

import sample.objetos.Evento;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class ModelEvento {
    public boolean insertar(Evento evento) {
        Statement myStmt = GeneralModel.connect();
        String query = "INSERT INTO ";
        query += "evento ";
        String sql = new StringBuilder()
                .append("() VALUES (")
                .append(")")
                .toString();
        try{
            myStmt.executeUpdate(query);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    public Evento getEvento(Integer id) {
        String query = "select * from evento where id = " + id.toString();
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

            //Insertar informacion a objeto deseado
            Evento eventoAuxiliar = new Evento(myRs.getString(1),myRs.getString(2),myRs.getString(3));

            //Retornar objeto
            return eventoAuxiliar;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}