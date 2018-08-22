package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 17-08-2018.
 */

    public class CastandCrew {

        @SerializedName("cast")
        @Expose
        private List<Cast> cast = null;
        @SerializedName("crew")
        @Expose
        private List<Crew> crew = null;

        public List<Cast> getCast() {
            return cast;
        }

        public void setCast(List<Cast> cast) {
            this.cast = cast;
        }

        public List<Crew> getCrew() {
            return crew;
        }

        public void setCrew(List<Crew> crew) {
            this.crew = crew;
        }


        public class Cast {

            @SerializedName("cast_id")
            @Expose
            private Integer castId;
            @SerializedName("character")
            @Expose
            private String character;
            @SerializedName("credit_id")
            @Expose
            private String creditId;
            @SerializedName("gender")
            @Expose
            private Integer gender;
            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("order")
            @Expose
            private Integer order;
            @SerializedName("profile_path")
            @Expose
            private String profilePath;

            public Integer getCastId() {
                return castId;
            }

            public void setCastId(Integer castId) {
                this.castId = castId;
            }

            public String getCharacter() {
                return character;
            }

            public void setCharacter(String character) {
                this.character = character;
            }

            public String getCreditId() {
                return creditId;
            }

            public void setCreditId(String creditId) {
                this.creditId = creditId;
            }

            public Integer getGender() {
                return gender;
            }

            public void setGender(Integer gender) {
                this.gender = gender;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getOrder() {
                return order;
            }

            public void setOrder(Integer order) {
                this.order = order;
            }

            public String getProfilePath() {
                return profilePath;
            }

            public void setProfilePath(String profilePath) {
                this.profilePath = profilePath;
            }

        }


        public class Crew {

            @SerializedName("credit_id")
            @Expose
            private String creditId;
            @SerializedName("department")
            @Expose
            private String department;
            @SerializedName("gender")
            @Expose
            private Integer gender;
            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("job")
            @Expose
            private String job;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("profile_path")
            @Expose
            private Object profilePath;

            public String getCreditId() {
                return creditId;
            }

            public void setCreditId(String creditId) {
                this.creditId = creditId;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public Integer getGender() {
                return gender;
            }

            public void setGender(Integer gender) {
                this.gender = gender;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getProfilePath() {
                return profilePath;
            }

            public void setProfilePath(Object profilePath) {
                this.profilePath = profilePath;
            }

        }
    }

