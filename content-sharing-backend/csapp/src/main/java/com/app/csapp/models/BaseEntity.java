package com.app.csapp.models;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

//    @Column(name =  "update_time", nullable = false)
//    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate(){
        createTime = LocalDateTime.now();
    }

//    @PreUpdate
//    protected  void onUpdate(){
//        updateTime = LocalDateTime.now();
//    }
}
