    package com.example.a1bitirmeprofil2;

    import androidx.annotation.NonNull;
    import androidx.room.ColumnInfo;
    import androidx.room.Entity;

    @Entity(primaryKeys = {"email", "password"})
    public class User {
        @NonNull
        public String getEmail() {
            return email;
        }

        public void setEmail(@NonNull String  email) {
            this.email = email;
        }

        @NonNull
        public String getPassword() {
            return password;
        }

        public void setPassword(@NonNull String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @NonNull
        public String email;
        @NonNull
        public String password;

        @ColumnInfo(name = "first_name")
        public String firstName;

        @ColumnInfo(name = "last_name")
        public String lastName;
    }
