package com.example.girasolv10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class GestionBD extends SQLiteOpenHelper {

    public static class DatosTabla implements BaseColumns {
        public static final String NOMBRE_TABLA = "ListaTareas";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMB_TAREA = "nombTarea";
        public static final String COLUMNA_NOMB_ICONO = "nombIcono";
        public static final String COLUMNA_POTENCIA = "potencia";
        public static final String COLUMNA_TIEMPO = "tiempo";
        public static final String COLUMNA_INTERACCION = "interaccion";
        public static final String COLUMNA_REPETICION = "repeticion";
        public static final String COLUMNA_VALOR = "valor";
        public static final String COLUMNA_ET_i = "energiaT";

        public static final String NOMBRE_TABLA2 = "ValoresHora";
        public static final String COLUMNA_ID_VH = "idVH";
        public static final String COLUMNA_Q_i = "Q_i";
        public static final String COLUMNA_E_i = "E_i";
        public static final String COLUMNA_I_ar = "I_ar";
        public static final String COLUMNA_V_ar = "V_ar";
        public static final String COLUMNA_P_ar = "P_ar";
        public static final String COLUMNA_Q_ar = "Q_ar";
        public static final String COLUMNA_E_i_ar = "E_ar";

        public static final String NOMBRE_TABLA3 = "DatosParam";
        public static final String COLUMNA_ID_DP = "idDP";
        public static final String COLUMNA_NOMB_ZONA = "nombreZona";
        public static final String COLUMNA_MES = "mes";
        public static final String COLUMNA_POT_SB = "Pot_SB";
        public static final String COLUMNA_TIEMPO_SB = "Tiempo_SB";
        public static final String COLUMNA_VC = "voltajeCarga";
        public static final String COLUMNA_CAP_USR = "capacUsuario";
        public static final String COLUMNA_GAMA = "gamaCelu";
        public static final String COLUMNA_NIVEL_BRILLO = "nivelBrillo";
        public static final String COLUMNA_HSP = "HSP";
        public static final String COLUMNA_P_MAX = "potMax";
        public static final String COLUMNA_OPCS_Q = "opcionesQ";
        //public static final String COLUMNA_ULT_HS = "horaultimalect";
        //public static final String COLUMNA_TEMP = "temperatura";
        public static final String COLUMNA_E_T = "energTotal";

        private static final String SQL_CREAR_TABLA1 =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA + " (" +
                        DatosTabla.COLUMNA_ID + " INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_NOMB_TAREA + " TEXT," +
                        DatosTabla.COLUMNA_NOMB_ICONO + " TEXT," +
                        DatosTabla.COLUMNA_POTENCIA + " TEXT," +
                        DatosTabla.COLUMNA_TIEMPO + " TEXT," +
                        DatosTabla.COLUMNA_INTERACCION + " TEXT," +
                        DatosTabla.COLUMNA_REPETICION + " TEXT," +
                        DatosTabla.COLUMNA_VALOR + " INTEGER," +
                        DatosTabla.COLUMNA_ET_i + " FLOAT)";

        private static final String SQL_CREAR_TABLA2 =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA2 + " (" +
                        DatosTabla.COLUMNA_ID_VH + " INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_Q_i + " FLOAT," +
                        DatosTabla.COLUMNA_E_i + " FLOAT," +
                        DatosTabla.COLUMNA_I_ar + " FLOAT," +
                        DatosTabla.COLUMNA_V_ar + " FLOAT," +
                        DatosTabla.COLUMNA_P_ar + " FLOAT," +
                        DatosTabla.COLUMNA_Q_ar + " FLOAT," +
                        DatosTabla.COLUMNA_E_i_ar + " FLOAT)";

        private static final String SQL_CREAR_TABLA3 =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA3 + " (" +
                        DatosTabla.COLUMNA_ID_DP + " INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_NOMB_ZONA + " TEXT," +
                        DatosTabla.COLUMNA_MES + " TEXT," +
                        DatosTabla.COLUMNA_POT_SB + " FLOAT," +
                        DatosTabla.COLUMNA_TIEMPO_SB + " INTEGER," +
                        DatosTabla.COLUMNA_VC + " FLOAT," +
                        DatosTabla.COLUMNA_CAP_USR + " INTEGER," +
                        DatosTabla.COLUMNA_GAMA + " INTEGER," +
                        DatosTabla.COLUMNA_NIVEL_BRILLO + " INTEGER," +
                        DatosTabla.COLUMNA_HSP + " FLOAT," +
                        DatosTabla.COLUMNA_P_MAX + " FLOAT," +
                        DatosTabla.COLUMNA_OPCS_Q + " INTEGER," +
                        //DatosTabla.COLUMNA_ULT_HS + " TEXT," +
                        //DatosTabla.COLUMNA_TEMP + " TEXT," +
                        DatosTabla.COLUMNA_E_T + " FLOAT)";


        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA;

        private static final String SQL_DELETE_ENTRIES2 =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA2;

        private static final String SQL_DELETE_ENTRIES3 =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA3;
    }

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Girasol_FR1.db";

    public GestionBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatosTabla.SQL_CREAR_TABLA1);
        sqLiteDatabase.execSQL(DatosTabla.SQL_CREAR_TABLA2);
        sqLiteDatabase.execSQL(DatosTabla.SQL_CREAR_TABLA3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DatosTabla.SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(DatosTabla.SQL_DELETE_ENTRIES2);
        sqLiteDatabase.execSQL(DatosTabla.SQL_DELETE_ENTRIES3);
        onCreate(sqLiteDatabase);
    }
}
