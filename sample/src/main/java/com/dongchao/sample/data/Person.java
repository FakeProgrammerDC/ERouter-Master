package com.dongchao.sample.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Person implements Parcelable{

    private String username;
    private String nickname;
    private int age;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Person(String username, String nickname, int age) {
        super();
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }
    public Person() {
        super();
    }
    /**
     * 这里的读的顺序必须与writeToParcel(Parcel dest, int flags)方法中
     * 写的顺序一致，否则数据会有差错，比如你的读取顺序如果是：
     * nickname = source.readString();
     * username=source.readString();
     * age = source.readInt();
     * 即调换了username和nickname的读取顺序，那么你会发现你拿到的username是nickname的数据，
     * 而你拿到的nickname是username的数据
     * @param source
     */
    public Person(Parcel source) {
        username = source.readString();
        nickname=source.readString();
        age = source.readInt();
    }
    /**
     * 这里默认返回0即可
     */
    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * 把值写入Parcel中
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeInt(age);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {

        /**
         * 供外部类反序列化本类数组使用
         */
        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }

        /**
         * 从Parcel中读取数据
         */
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }
    };
}

