package Lab3;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TimeInterval implements Serializable {
    private Date begin;
    private Date end;

    public Date getBegin()
    {
        return begin;
    }
    public Date getEnd()
    {//якщо end==null, то цей інтервал часу ще триває - вертаємо поточну дату
        if(end==null) return new Date();
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    public void setEnd() {
        this.end = new Date();
    }


    public TimeInterval(Date begin, Date end)
    {
        this.begin=begin;
        this.end=end;
    }

    public boolean includes(TimeInterval interval)
    {
        return (this.getBegin().before(interval.getBegin()) || this.getBegin().equals(interval.getBegin())) && (this.getEnd().after(interval.getEnd()) || this.getEnd().equals(interval.getEnd()));
    }

    public long getDurationInHours()
    {
        if(this.end==null)
            return (new Date().getTime() - this.begin.getTime())/3600000;
        return (this.end.getTime() - this.begin.getTime())/3600000;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("[dd.MM.yy HH:mm]");
        if(end==null)
        {
            return  "\t З " + df.format(begin) +
                    " ДО " + df.format(new Date());
        }
        return  "\t З " + df.format(begin) +
                " ДО " + df.format(end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return begin.equals(that.begin) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}
