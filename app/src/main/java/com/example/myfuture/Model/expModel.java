package com.example.myfuture.Model;

public class expModel extends expID {

    private String company, job, jobDes, startDate, endDate;

    public void setCompany(String company) {
        this.company = company;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setJobDes(String jobDes) {
        this.jobDes = jobDes;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        System.out.println(company);
        return company;
    }

    public String getJob() {
        System.out.println(job);
        return job;
    }

    public String getJobDes() {
        System.out.println(jobDes);
        return jobDes;
    }

    public String getStartDate() {
        System.out.println(startDate);
        return startDate;
    }

    public String getEndDate() {
        System.out.println(endDate);
        return endDate;
    }
}
