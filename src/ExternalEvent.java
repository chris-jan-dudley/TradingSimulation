
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * External events superclass constructs the Events object, which can be called on and implemented by the Stock Market.
 * Methods provide access to the different properties of the Event.
 *
 * @author cjd36
 */
abstract public class ExternalEvent {

    String nature, action;
    Date date, time;
    boolean isBuy;
    int fromTick, toTick, numDays;

    /**
     *
     * @param eventsFile ArrayList containing the properties of the event, as laid out in the rows of the provided CSV
     * document. Each item in the ArrayList is assigned to a class variable. In some cases, regular expressions are used
     * to access the information needed from the strings provided. The toTick and fromTick variables are calculated
     * working out time from the start of the simulation, discounting weekends, and determining the number of ticks this
     * corresponds to.
     * @throws java.text.ParseException
     */
    public ExternalEvent(ArrayList<String> eventsFile) throws ParseException {
        DateFormat df = new SimpleDateFormat("MMM d yyyy");
        DateFormat tf = new SimpleDateFormat("hh:mm");

        try {
            date = df.parse(eventsFile.get(0));
            time = tf.parse(eventsFile.get(1));
        } catch (ParseException e) {
        }
        nature = eventsFile.get(2);
        action = eventsFile.get(3);
        isBuy = eventsFile.get(3).contains("buy");

        Pattern pattern = Pattern.compile("\\s(\\d)\\s");
        Matcher matcher = pattern.matcher(eventsFile.get(3));
        if (matcher.find()) {
            numDays = Integer.parseInt(matcher.group().replaceAll(" ", ""));
        }

        Date startDate = df.parse("Jan 1 2017");
        Calendar startCal = Calendar.getInstance();
        Calendar eventCal = Calendar.getInstance();
        startCal.setTime(startDate);
        eventCal.setTime(date);

        int numberOfDays = 1;   //Include start day
        while (startCal.before(eventCal)) {
            if ((Calendar.SATURDAY != startCal.get(Calendar.DAY_OF_WEEK))
                    && (Calendar.SUNDAY != startCal.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            startCal.add(Calendar.DATE, 1);
        }

        Date startDayTime = tf.parse("09:00");
        Date startEventTime = time;
        long difference = startEventTime.getTime() - startDayTime.getTime();
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;
        fromTick = (int) ((numberOfDays * 28) + (diffHours * 4) + (diffMinutes / 15));
        toTick = fromTick + (numDays * 28);

    }

    /**
     * Gets the nature of the event
     *
     * @return nature describing what has caused the event
     */
    public String getNature() {
        return nature;
    }

    /**
     * Gets the Action to be taken
     *
     * @return action describing what action is to be taken by the different entities in the simulation in response to
     * this event
     */
    public String getAction() {
        return action;
    }

    /**
     * Gets the value of the starting tick of the simulation at which this event occurs, and when the action should
     * start to be implemented.
     *
     * @return fromTick
     */
    public int getFromTick() {
        return fromTick;
    }

    /**
     * Returns whether or not the action is associated with a buying or selling focus.
     *
     * @return isBuy
     */
    public boolean getIsBuys() {
        return isBuy;
    }

    /**
     *  Gets the value of the ending tick of the simulation, at which the event ends and the associated actions cease.
     * @return toTick
     */
    public int getToTick() {
        return toTick;
    }

    /**
     * Gets the total number of days the event runs for
     * @return numDays
     */
    public int getNumDays() {
        return numDays;
    }

    /**
     * Gets the date on which the event occurs.
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the time at which the event starts on the event date
     * @return time
     */
    public Date getTime() {
        return time;
    }
}
