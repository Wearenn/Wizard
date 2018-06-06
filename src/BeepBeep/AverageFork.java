/*
    BeepBeep, an event stream processor
    Copyright (C) 2008-2016 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package BeepBeep;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.functions.TurnInto;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;

import static ca.uqac.lif.cep.Connector.*;

/**
 * Compute the cumulative average of a list of numbers. The cumulative average
 * is the average of all the numbers processed so far.
 */
public class AverageFork extends GroupProcessor
{
	public AverageFork() {
		super(1, 1);
		Fork fork = new Fork(2);

		Cumulate sum_proc = new Cumulate(
				new CumulativeFunction<Number>(Numbers.addition));
		Connector.connect(fork, TOP, sum_proc, INPUT);

		TurnInto ones = new TurnInto(1);
		Connector.connect(fork, BOTTOM, ones, INPUT);
		Cumulate counter = new Cumulate(
				new CumulativeFunction<Number>(Numbers.addition));
		Connector.connect(ones, OUTPUT, counter, INPUT);

		ApplyFunction division = new ApplyFunction(Numbers.division);
		Connector.connect(sum_proc, OUTPUT, division, LEFT);
		Connector.connect(counter, OUTPUT, division, RIGHT);
		addProcessors(sum_proc,fork,ones,counter,division);
		associateInput(0,fork,0);
		associateOutput(0,division,0);
	}
}