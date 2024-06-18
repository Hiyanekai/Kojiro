package com.example.Kojiro.entity;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public record SignUp (String user_id, String password,int role, Timestamp created_at){
}
