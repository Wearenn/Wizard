package BeepBeep;
import java.text.NumberFormat;
import java.text.ParseException;

import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * Function that parses a line of text and creates the corresponding method
 * event
 * @author Sylvain Hallé
 */
public class StringToInteger extends UnaryFunction<String,Number>
{
    /**
     * A single instance of this function
     */
    public static final StringToInteger instance = new StringToInteger();

    public StringToInteger()
    {
        super(String.class, Number.class);
    }
    int i=0;

    @Override
    public Number getValue(String x)
    {
        try {
            return NumberFormat.getInstance().parse(x);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}