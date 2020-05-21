package bot.repositories;

import bot.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Integer> {



    public List<Reminder> findByTime(Time time);


}
