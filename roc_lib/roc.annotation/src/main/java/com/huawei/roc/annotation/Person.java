
package com.huawei.roc.annotation;

public class Person {
    @Name(value = "cool_summer_moon")
    public String name;

    public String age;

    @Sex(gender1 = Sex.GenderType.Male)
    public String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
