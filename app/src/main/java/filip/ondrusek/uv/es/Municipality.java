package filip.ondrusek.uv.es;

import java.io.Serializable;

public class Municipality implements Serializable {

    public int id;
    public int codeMunicipality;
    public String municipality;
    public int casesPCR;
    public String cumulativeIncidence;
    public int casesPCR14;
    public String casesPCR14cumulativeIncidence;
    public int deaths;
    public String deathRate;

    public Municipality(int id, int codeMunicipality, String municipality, int casesPCR, String cumulativeIncidence, int casesPCR14, String casesPCR14cumulativeIncidence, int deaths, String deathRate) {
        this.id = id;
        this.codeMunicipality = codeMunicipality;
        this.municipality = municipality;
        this.casesPCR = casesPCR;
        this.cumulativeIncidence = cumulativeIncidence;
        this.casesPCR14 = casesPCR14;
        this.casesPCR14cumulativeIncidence = casesPCR14cumulativeIncidence;
        this.deaths = deaths;
        this.deathRate = deathRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodeMunicipality() {
        return codeMunicipality;
    }

    public void setCodeMunicipality(int codeMunicipality) {
        this.codeMunicipality = codeMunicipality;
    }

    public String getMunicipality() {return municipality;}
    public void setMunicipality (String municipality) {this.municipality = municipality;}

    public int getCasesPCR() {
        return casesPCR;
    }

    public void setCasesPCR(int casesPCR) {
        this.casesPCR = casesPCR;
    }

    public String getCumulativeIncidence() {
        return cumulativeIncidence;
    }

    public void setCumulativeIncidence(String cumulativeIncidence) {
        this.cumulativeIncidence = cumulativeIncidence;
    }

    public int getCasesPCR14() {
        return casesPCR14;
    }

    public void setCasesPCR14(int casesPCR14) {
        this.casesPCR14 = casesPCR14;
    }

    public String getCasesPCR14cumulativeIncidence() {
        return casesPCR14cumulativeIncidence;
    }

    public void setCasesPCR14cumulativeIncidence(String casesPCR14cumulativeIncidence) {
        this.casesPCR14cumulativeIncidence = casesPCR14cumulativeIncidence;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(String deathRate) {
        this.deathRate = deathRate;
    }
}
