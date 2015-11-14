package storybook.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import storybook.model.hbn.entity.Person;

public class EntityUtilTests {
    private Person person;
    private GregorianCalendar cal;
    
    @Before
    public void setup() {
        person = new Person();
        
        cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 1963);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DATE, 21);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
    }
    
    @Test
    public void nullDeatOfDeathMeansPersonIsAlive() {
        Boolean actual = person.isDead(null);
        assertFalse(actual);
    }
    
    @Test
    public void nowBeforeDateOfDeathIsAlive() {
        Date dateOfDeath = cal.getTime();
        person.setDayofdeath(dateOfDeath);
        
        cal.add(Calendar.DATE, -2);
        Date now = cal.getTime();
        
        Boolean actual = person.isDead(now);
        assertFalse(actual);
    }
    
    @Test
    public void nowAfterDateOfDeathIsDead() {
        Date dateOfDeath = cal.getTime();
        person.setDayofdeath(dateOfDeath);
        
        cal.add(Calendar.DATE, 2);
        Date now = cal.getTime();
        
        Boolean actual = person.isDead(now);
        assertTrue(actual);
    }

    @Test
    public void nullBirthdayReturnsAgeOfNegativeOne() {
        int actual = person.calculateAge(cal.getTime());
        assertEquals("Null birthday returns -1", -1, actual);
    }
    
    @Test
    public void oneYearMinusOneDayIsAgeZero() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, -1);
        int actual = person.calculateAge(cal.getTime());
        assertEquals(0, actual);
    }
    
    @Test
    public void oneYearIsAgeOne() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 1);
        int actual = person.calculateAge(cal.getTime());
        assertEquals(1, actual);
    }
    
    @Test
    public void deadAtOneYearMinusOneDayNowIsOneHundradYearsAfterDeathAgeEqualsZero() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, -1);
        
        person.setDayofdeath(cal.getTime());
        
        cal.add(Calendar.YEAR, 100);
        
        int actual = person.calculateAge(cal.getTime());
        assertEquals(0, actual);
    }
    
    @Test
    public void deadAtOneYearNowIsOneHundradYearsAfterDeathAgeEqualsOne() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, 1);
        
        person.setDayofdeath(cal.getTime());
        
        cal.add(Calendar.YEAR, 100);
        
        int actual = person.calculateAge(cal.getTime());
        assertEquals(1, actual);
    }
    
}
