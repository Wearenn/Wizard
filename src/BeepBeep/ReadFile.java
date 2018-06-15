package BeepBeep;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.util.Numbers;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class ReadFile {

    public ReadFile(String file, ArrayList<Number> arrayList) {
        InputStream is = ReadFile.class.getResourceAsStream(file);
        System.out.println(is.toString());
        ReadLines reader = new ReadLines(is);
        ApplyFunction cast = new ApplyFunction(Numbers.numberCast);
        Connector.connect(reader, cast);
        Pullable p = reader.getPullableOutput();
        while (p.hasNext()) {
            Number number = null;
            try {
                number = NumberFormat.getInstance().parse((String)p.next());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrayList.add(number);
        }
    }
}
