package com.automation;

public class User {

    private String name;
    private String job;
    private String email;
    private String phone;
    private int age;

    // Constructor with all five fields
    public User(String name, String job, String email, String phone, int age) {
        this.name  = name;
        this.job   = job;
        this.email = email;
        this.phone = phone;
        this.age   = age;
    }

    // Constructor with just name and job — for simple tests
    public User(String email, String mail, String name, String job) {
        this.name = name;
        this.job  = job;
    }

    // Getters
    public String getName()  { return name;  }
    public String getJob()   { return job;   }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int    getAge()   { return age;   }

    // Setters
    public void setName(String name)   { this.name  = name;  }
    public void setJob(String job)     { this.job   = job;   }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAge(int age)        { this.age   = age;   }
}