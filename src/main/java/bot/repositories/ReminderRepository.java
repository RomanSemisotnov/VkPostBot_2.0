package bot.repositories;

import bot.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Integer> {

  //  public Reminder findByUserIdAndDayNumberAndTime(Integer userId, Integer dayNumber, Time time);

}
