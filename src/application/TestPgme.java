package application;

import BeepBeep.*;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.ReadStringStream;
import ca.uqac.lif.cep.tmf.*;
import ca.uqac.lif.cep.util.Numbers;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static ca.uqac.lif.cep.Connector.*;

public class TestPgme {

    public static void main(String[] args) {
        /*String file = "../txt/value.txt";
        ArrayList<Number> arrayList = new ArrayList<Number>();
        new ReadFile(file, arrayList);

        QueueSource source = new QueueSource();
        for (Number element : arrayList) {
            source.setEvents(element);
        }*/

        /*Set<DoublePoint> pattern = new HashSet<DoublePoint>();
        pattern.add(new DoublePoint(new double[]{0.7, 0.3}));
        pattern.add(new DoublePoint(new double[]{0.3, 0.7}));

        QueueSource source = new QueueSource().setEvents(pattern);*/

        ReadStringStream reader = new ReadStringStream(System.in);
        StringToInteger stringToInteger = new StringToInteger();
        ApplyFunction source = new ApplyFunction(stringToInteger);
        connect(reader,source);
        reader.setIsFile(false);

        //Pump pump = new Pump(100);
        //Thread pump_thread = new Thread(pump);
        //connect(reader,pump);

        //QueueSource source = new QueueSource().setEvents(pump);
        /*for (Number element : arrayList) {
            source.setEvents(element);
        }*/


        GroupProcessor groupProcessor = new GroupProcessor(1, 1);
        {
            Fork fork = new Fork(2);
            groupProcessor.associateInput(INPUT, fork, INPUT);
            Trim trim = new Trim(6);
            Connector.connect(fork, TOP, trim, INPUT);
            Window win1 = new Window(new AverageFork().duplicate(), 3);
            Connector.connect(trim, win1);
            Window win2 = new Window(new AverageFork().duplicate(), 6);
            Connector.connect(fork, BOTTOM, win2, INPUT);
            ApplyFunction distance = new ApplyFunction(Numbers.subtraction);
            Connector.connect(win1, OUTPUT, distance, TOP);
            Connector.connect(win2, OUTPUT, distance, BOTTOM);
            ApplyFunction too_far = new ApplyFunction(new FunctionTree(Numbers.isLessThan,
                    new StreamVariable(0),
                    new Constant(0.5)
            ));
            Connector.connect(distance, too_far);
            groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);
            groupProcessor.addProcessors(fork, trim, win1, win2, distance, too_far);
        }
        Connector.connect(source, groupProcessor);
        Pullable p = groupProcessor.getPullableOutput();
        /*for (int i = 0; i < 10; i++) {
            System.out.println(p.pull());
        }*/
        for (Object o: p) {
            System.out.println(o);
        }
    }

    /*private static Object[] getListOfPoints() {
        Number point = 10D;
        return new Object[]{
                point,
                point,
                point,
                point,
                point,
                point,
                point,
                /*
                new Point(1, 8),
                new Point(2, 8),
                new Point(1, 8),
                new Point(2, 8),
                new Point(4, 5),
                new Point(9, 0),
                new Point(4, 5),
                new Point(2, 3),
                new Point(5, 3),
                new Point(6, 0),
                new Point(2, 8)
        };
    }*/
}
