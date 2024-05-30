package com.dmiit3iy.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "librarians")
public class Librarian extends User {
    public Librarian(@NonNull String login, @NonNull String password, @NonNull String surname, @NonNull String name, @NonNull String patronymic) {
        super(login, password, surname, name, patronymic);
    }


}
