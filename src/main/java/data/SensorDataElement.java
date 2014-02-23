package data;

import org.arangodb.objectmapper.jackson.ArangoDbDocument;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Sven Ruppert on 16.02.14.
 */
public class SensorDataElement extends ArangoDbDocument {

    private String masterUID;      //master abc
    private String brickletUID;    // sensor xyz
    private String brickletType;   // Temperatur
    private Date date;
    private Integer sensorValue;

    public String getMasterUID() {
        return masterUID;
    }

    public void setMasterUID(String masterUID) {
        this.masterUID = masterUID;
    }

    public String getBrickletUID() {
        return brickletUID;
    }

    public void setBrickletUID(String brickletUID) {
        this.brickletUID = brickletUID;
    }

    public String getBrickletType() {
        return brickletType;
    }

    public void setBrickletType(String brickletType) {
        this.brickletType = brickletType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(int sensorValue) {
        this.sensorValue = sensorValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(masterUID, brickletUID, brickletType, date, sensorValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SensorDataElement other = (SensorDataElement) obj;
        return Objects.equals(this.masterUID, other.masterUID)
                && Objects.equals(this.brickletUID, other.brickletUID)
                && Objects.equals(this.brickletType, other.brickletType)
                && Objects.equals(this.date, other.date)
                && Objects.equals(this.sensorValue, other.sensorValue);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SensorDataElement{");
        sb.append("masterUID='").append(masterUID).append('\'');
        sb.append(", brickletUID='").append(brickletUID).append('\'');
        sb.append(", brickletType='").append(brickletType).append('\'');
        sb.append(", date=").append(date);
        sb.append(", sensorValue=").append(sensorValue);
        sb.append('}');
        return sb.toString();
    }
}
