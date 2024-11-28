package com.app.csapp.models;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass // de biet no la thuc the cha
// BaseEntity dung de chua thuoc tinh ve thoi gian tao va cap nhat
public class BaseEntity{
    @Column(name = "create_time")
    private LocalDateTime createTime;

//    @Column(name = "update_at")
//    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        //updateTime = LocalDateTime.now();
    }
}