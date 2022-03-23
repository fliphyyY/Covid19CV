package filip.ondrusek.uv.es;

import android.provider.BaseColumns;

public class ReportContract {

    private ReportContract() {}

    public static class ReportEntry implements BaseColumns {
        public static final String TABLE_NAME = "report";
        //public static final String COLUMN_ID= "_id";
        public static final String COLUMN_NAME_DIAGNOSTIC_CODE = "diagnostic_code";
        public static final String COLUMN_NAME_SYMPTOMS_START_DATE = "symptoms_start_date";
        public static final String COLUMN_NAME_FEVER_CHILLS = "fever_chills";
        public static final String COLUMN_NAME_COUGH = "cough";
        public static final String COLUMN_NAME_BREATHING = "breathing";
        public static final String COLUMN_NAME_FATIGUE = "fatigue";
        public static final String COLUMN_NAME_MUSCLE_BODY_ACHES = "muscle_body_aches";
        public static final String COLUMN_NAME_HEADACHE = "headache";
        public static final String COLUMN_NAME_TASTE_SMELL_LOSS = "taste_smell_loss";
        public static final String COLUMN_NAME_SORE_THROAT = "sore_throat";
        public static final String COLUMN_NAME_CONGESTION_RUNNY_NOSE = "congestion_runny_nose";
        public static final String COLUMN_NAME_NAUSEA_VOMITING = "nausea_vomiting";
        public static final String COLUMN_NAME_DIARRHEA = "diarrhea";
        public static final String COLUMN_NAME_CLOSE_CONTACT = "close_contact";
        public static final String COLUMN_NAME_MUNICIPALITY = "municipality";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ReportEntry.TABLE_NAME + " (" +
                    ReportEntry._ID + " INTEGER PRIMARY KEY," +
                    ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE + " INTEGER," +
                    ReportEntry.COLUMN_NAME_SYMPTOMS_START_DATE + " TEXT," +
                    ReportEntry.COLUMN_NAME_FEVER_CHILLS + " TEXT," +
                    ReportEntry.COLUMN_NAME_COUGH + " TEXT," +
                    ReportEntry.COLUMN_NAME_BREATHING + " TEXT," +
                    ReportEntry.COLUMN_NAME_FATIGUE + " TEXT," +
                    ReportEntry.COLUMN_NAME_MUSCLE_BODY_ACHES + " TEXT," +
                    ReportEntry.COLUMN_NAME_HEADACHE + " TEXT," +
                    ReportEntry.COLUMN_NAME_TASTE_SMELL_LOSS + " TEXT," +
                    ReportEntry.COLUMN_NAME_SORE_THROAT + " TEXT," +
                    ReportEntry.COLUMN_NAME_CONGESTION_RUNNY_NOSE + " TEXT," +
                    ReportEntry.COLUMN_NAME_NAUSEA_VOMITING + " TEXT," +
                    ReportEntry.COLUMN_NAME_DIARRHEA + " TEXT," +
                    ReportEntry.COLUMN_NAME_CLOSE_CONTACT + " TEXT," +
                    ReportEntry.COLUMN_NAME_MUNICIPALITY + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReportEntry.TABLE_NAME;
}
