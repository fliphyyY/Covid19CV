package filip.ondrusek.uv.es;

import java.io.Serializable;
import java.util.Comparator;

public class Municipality implements Serializable {

    public int id;
    public String codeMunicipality;
    public String municipality;
    public int casesPCR;
    public String cumulativeIncidence;
    public int casesPCR14;
    public String casesPCR14cumulativeIncidence;
    public int deaths;
    public String deathRate;

    public Municipality(int id, String codeMunicipality, String municipality, int casesPCR, String cumulativeIncidence, int casesPCR14, String casesPCR14cumulativeIncidence, int deaths, String deathRate) {
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

    public String getCodeMunicipality() {
        return codeMunicipality;
    }

    public void setCodeMunicipality(String codeMunicipality) {
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

    public static Comparator<Municipality> OrderByCumulativeIncidenceAscending = new Comparator<Municipality>() {
        @Override
        public int compare(Municipality m1, Municipality m2) {
            return Double.compare(Double.parseDouble(m1.getCumulativeIncidence().trim().replace(",",".")), Double.parseDouble(m2.getCumulativeIncidence().trim().replace(",",".")));
        }
    };

    public static Comparator<Municipality> OrderByCumulativeIncidenceDescending = new Comparator<Municipality>() {
        @Override
        public int compare(Municipality m1, Municipality m2) {
            return Double.compare(Double.parseDouble(m2.getCumulativeIncidence().trim().replace(",",".")), Double.parseDouble(m1.getCumulativeIncidence().trim().replace(",",".")));
        }
    };

    public static Comparator<Municipality> OrderByMunicipalityAscending = new Comparator<Municipality>() {
        @Override
        public int compare(Municipality m1, Municipality m2) {
            return m1.getMunicipality().compareTo(m2.getMunicipality());
        }
    };

    public static Comparator<Municipality> OrderByMunicipalityDescending = new Comparator<Municipality>() {
        @Override
        public int compare(Municipality m1, Municipality m2) {
            return m2.getMunicipality().compareTo(m1.getMunicipality());
        }
    };
}
