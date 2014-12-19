package coursework.mymerryxmas;

import java.io.Serializable;

/**
 * Created by stephen on 09/12/2014.
 */
public class mxMapData implements Serializable {

// *********************************************
// Declare variables etc.
// *********************************************

    private int YearsEmployed;
    private String Occupation;
    private String Firstname;

    private static final long serialVersionUID = 0L;

// *********************************************
// Declare getters and setters etc.
// *********************************************


    public int getYearsEmployed() {
        return YearsEmployed;
    }

    public void setYearsEmployed(int yearsEmployed) {
        this.YearsEmployed = yearsEmployed;
    }


    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        this.Firstname = firstname;
    }



    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        this.Occupation = occupation;
    }

//return data as string
    @Override
    public String toString() {
        String mapData;

        mapData = "[ Firstname=" + Firstname;
        mapData = ", Occupation=" + Occupation;
        mapData = ", YearsEmployed=" + YearsEmployed +"]";
        return mapData;
    }
}
