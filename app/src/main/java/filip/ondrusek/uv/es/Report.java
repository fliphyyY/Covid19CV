package filip.ondrusek.uv.es;

import java.io.Serializable;

public class Report implements Serializable {
    private String diagnosticCode;
    private String date;
    private String feverChills;
    private String cough;
    private String breathing;
    private String fatigue;
    private String bodyAches;
    private String headache;
    private String lossTaste;
    private String soreThroat;
    private String congestionRunnyNose;
    private String nauseaVomiting;
    private String diarrhea;
    private String closeContact;
    private String municipality;

    public Report(String diagnosticCode, String date, String feverChills, String cough, String breathing, String fatigue, String bodyAches, String headache, String lossTaste, String soreThroat, String congestionRunnyNose, String nauseaVomiting, String diarrhea, String closeContact, String municipality) {
        this.diagnosticCode = diagnosticCode;
        this.date = date;
        this.feverChills = feverChills;
        this.cough = cough;
        this.breathing = breathing;
        this.fatigue = fatigue;
        this.bodyAches = bodyAches;
        this.headache = headache;
        this.lossTaste = lossTaste;
        this.soreThroat = soreThroat;
        this.congestionRunnyNose = congestionRunnyNose;
        this.nauseaVomiting = nauseaVomiting;
        this.diarrhea = diarrhea;
        this.closeContact = closeContact;
        this.municipality = municipality;
    }

    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeverChills() {
        return feverChills;
    }

    public void setFeverChills(String feverChills) {
        this.feverChills = feverChills;
    }

    public String getCough() {
        return cough;
    }

    public void setCough(String cough) {
        this.cough = cough;
    }

    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    public String getFatigue() {
        return fatigue;
    }

    public void setFatigue(String fatigue) {
        this.fatigue = fatigue;
    }

    public String getBodyAches() {
        return bodyAches;
    }

    public void setBodyAches(String bodyAches) {
        this.bodyAches = bodyAches;
    }

    public String getHeadache() {
        return headache;
    }

    public void setHeadache(String headache) {
        this.headache = headache;
    }

    public String getLossTaste() {
        return lossTaste;
    }

    public void setLossTaste(String lossTaste) {
        this.lossTaste = lossTaste;
    }

    public String getSoreThroat() {
        return soreThroat;
    }

    public void setSoreThroat(String soreThroat) {
        this.soreThroat = soreThroat;
    }

    public String getCongestionRunnyNose() {
        return congestionRunnyNose;
    }

    public void setCongestionRunnyNose(String congestionRunnyNose) {
        this.congestionRunnyNose = congestionRunnyNose;
    }

    public String getNauseaVomiting() {
        return nauseaVomiting;
    }

    public void setNauseaVomiting(String nauseaVomiting) {
        this.nauseaVomiting = nauseaVomiting;
    }

    public String getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(String diarrhea) {
        this.diarrhea = diarrhea;
    }

    public String getCloseContact() {
        return closeContact;
    }

    public void setCloseContact(String closeContact) {
        this.closeContact = closeContact;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}
