package com.notify.repo;

import com.notify.model.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

}
