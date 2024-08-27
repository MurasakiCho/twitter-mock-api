package com.cooksystems.GroupProject1.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {

    private String firstName;
    private String lastName;

    @NonNull
    private String email;

    private String phone;
}
