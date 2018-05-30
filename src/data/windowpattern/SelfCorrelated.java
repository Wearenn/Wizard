package data.windowpattern;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.tmf.Trim;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

import static ca.uqac.lif.cep.Connector.INPUT;
import static ca.uqac.lif.cep.Connector.OUTPUT;

public class SelfCorrelated {

    //valeurs par défault qui seront modifiées par la suite
    public SelfCorrelated(){
        QueueSource source = new QueueSource().setEvents(3,4,5,6,7,8);
        Fork fork = new Fork(2);
        Connector.connect(source,fork);
        Trim trim = new Trim(1);
        Connector.connect(fork,0,trim,INPUT);
        CountDecimate decp0 = new CountDecimate(3);
        Connector.connect(trim,OUTPUT,decp0,INPUT);
        Pullable p0 = fork.getPullableOutput(0);

        CountDecimate decp1 = new CountDecimate(3);
        Connector.connect(fork,1,decp1,INPUT);
        Pullable p1 = fork.getPullableOutput(1);

        ManhattanDistance md = new ManhattanDistance();
    }
}
