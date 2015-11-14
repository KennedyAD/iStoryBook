package storybook.model;

import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import storybook.model.hbn.entity.Person;

public class GetToolTipTest {
    private Person person;
    private GregorianCalendar cal;
    private String reply;
    private String prefix = "<html><table width='300'><tr><td><div style='padding-top:2px;"
            + "padding-bottom:2px;padding-left:4px;padding-right:4px;margin-bottom:2px;'>"
            + "<b></b></div>";
    private String suffix = "Gender: null<br>Category: null<p style='margin-top:5px'>"
            + "</td></tr></table></html>";
    
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
    public void dateIsImplicitlyNull() {
        person.setBirthday(cal.getTime());
        
        reply = EntityUtil.getToolTip(person);
        assertEquals(prefix + suffix, reply);
    }
    
    @Test
    public void dateIsExplicitlyNull() {
        person.setBirthday(cal.getTime());
        
        reply = EntityUtil.getToolTip(person, null);
        assertEquals(prefix + suffix, reply);
    }
    
    @Test
    public void birthdayIsNull() {
        reply = EntityUtil.getToolTip(person, cal.getTime());
        assertEquals(prefix + suffix, reply);
    }
    
    @Test
    public void personIsTwenty() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 20);
        reply = EntityUtil.getToolTip(person, cal.getTime());
        assertEquals(prefix + "Age: 20<br>" + suffix, reply);
    }
    
    @Test
    public void personDiedAtTwentyNowIsThirty() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 20);
        person.setDayofdeath(cal.getTime());
        
        cal.add(Calendar.YEAR, 10);
        reply = EntityUtil.getToolTip(person, cal.getTime());
        assertEquals(prefix + "Age: 20+<br>" + suffix, reply);
    }
    
    @Test
    public void personDiedAtThirtyNowIsTwenty() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 30);
        person.setDayofdeath(cal.getTime());
        
        cal.add(Calendar.YEAR, -10);
        reply = EntityUtil.getToolTip(person, cal.getTime());
        assertEquals(prefix + "Age: 20<br>" + suffix, reply);
    }
    
    @Test
    public void personDiedAtTwentyMinusOneDayNowIsThirty() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 20);
        cal.add(Calendar.DATE, -1);
        person.setDayofdeath(cal.getTime());
        
        cal.add(Calendar.YEAR, 10);
        cal.add(Calendar.DATE, 1);
        reply = EntityUtil.getToolTip(person, cal.getTime());
        assertEquals(prefix + "Age: 19+<br>" + suffix, reply);
    }
    
    @Test
    public void personIsTwentyMinusOneDay() {
        person.setBirthday(cal.getTime());
        
        cal.add(Calendar.YEAR, 20);
        cal.add(Calendar.DATE, -1);
        
        reply = EntityUtil.getToolTip(person, cal.getTime());
        assertEquals(prefix + "Age: 19<br>" + suffix, reply);
    }
    
}
