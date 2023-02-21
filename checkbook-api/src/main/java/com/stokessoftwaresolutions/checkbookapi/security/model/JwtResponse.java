package com.stokessoftwaresolutions.checkbookapi.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type;
    private long id;
    private String username;
    private String email;
    private Date expirationDate;
    private List<String> roles;

}
