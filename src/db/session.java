/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Mochamad Fajar Sodik
 */
public class session {
    
    private static int iduser;
    private static String akses;

    public static int getIduser() {
        return iduser;
    }

    public static void setIduser(int iduser) {
        session.iduser = iduser;
    }

    public static String getAkses() {
        return akses;
    }

    public static void setAkses(String akses) {
        session.akses = akses;
    }
    
}
