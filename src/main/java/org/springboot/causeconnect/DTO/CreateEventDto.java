package org.springboot.causeconnect.DTO;

import org.springboot.causeconnect.entities.NGO;

import java.util.Date;

public class CreateEventDto {
    private String name;
    private String Description;
    private String address;
    private String city;
    private Date lastDateToRegister;
    private Date EventDate;

    private NGO Host;
}
