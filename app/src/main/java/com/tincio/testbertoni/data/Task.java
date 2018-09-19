package com.tincio.testbertoni.data;

/**
 * Created by tania on 9/18/18.
 */

public class Task {
    // delimiter between city,region and country when constructing name
    private static final String NAME_DELIMITER = ", ";

    public int id;
    public String identifier;

    public String description;
    public String status;

    public static Task newInstance(String descripcion,String status){
        Task mTask = new Task();
        mTask.setDescription(descripcion);
        mTask.setStatus(status);
        return mTask;
    }


    /**
     * Returns the description
     */
    public String getPrintName() {
        StringBuilder s = new StringBuilder();

     /*   if (region != null) {
            s.append(NAME_DELIMITER);
            s.append(region);
        }

        if (country != null) {
            s.append(NAME_DELIMITER);
            s.append(country);
        }*/

        return s.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
