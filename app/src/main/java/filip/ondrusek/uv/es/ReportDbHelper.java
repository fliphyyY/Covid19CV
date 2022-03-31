package filip.ondrusek.uv.es;

import static filip.ondrusek.uv.es.ReportContract.SQL_CREATE_ENTRIES;
import static filip.ondrusek.uv.es.ReportContract.SQL_DELETE_ENTRIES;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReportDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Reportdata.db";

    public ReportDbHelper( Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDelete(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertReport(SQLiteDatabase db, ContentValues reportValues)
    {
        db.insert(ReportContract.ReportEntry.TABLE_NAME, null, reportValues);

    }

    public void deleteReport(SQLiteDatabase db, String diagnosticCode)
    {
        db.delete(ReportContract.ReportEntry.TABLE_NAME,"diagnostic_code=?",new String[]{diagnosticCode});
    }

    public void updateReport(SQLiteDatabase db, ContentValues values, String diagnosticCode)
    {
        db.update(ReportContract.ReportEntry.TABLE_NAME, values, "diagnostic_code=?",new String[]{diagnosticCode});
    }
}
