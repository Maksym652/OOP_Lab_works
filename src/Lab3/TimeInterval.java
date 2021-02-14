package Lab3;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeInterval implements Serializable {
    private Date begin;
    private Date end;
    Date getBegin()
    {
        return begin;
    }
    Date getEnd()
    {
        if(end==null) return new Date();
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    public void setEnd() {
        this.end = new Date();
    }

    TimeInterval(Date begin, Date end)
    {
        this.begin=begin;
        this.end=end;
    }
    TimeInterval(Date begin)
    {
        this.begin=begin;
    }
    TimeInterval()
    {
        this.begin=new Date();
    }

    long getDuration()
    {
        if(this.end==null)
            return (new Date().getTime() - this.begin.getTime())/3600000;
        return this.end.getTime() - this.begin.getTime();
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
}
