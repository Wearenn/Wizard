/*
    BeepBeep, an event stream processor
    Copyright (C) 2008-2017 Sylvain Hallé

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
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.tmf.ReplaceWith;
import ca.uqac.lif.cep.tmf.Slice;
import ca.uqac.lif.cep.util.Numbers;

import static ca.uqac.lif.cep.Connector.INPUT;
import static ca.uqac.lif.cep.Connector.OUTPUT;

/**
 * Trend distance based on the statistical distribution of symbols in a
 * stream.
 */
public class SymbolDistribution extends GroupProcessor
{
	public SymbolDistribution() {
		super(1, 1);
		GroupProcessor counter = new GroupProcessor(1, 1);
		{
			ReplaceWith one = new ReplaceWith(new Constant(1));
			counter.associateInput(INPUT, one, INPUT);
			Cumulate sum_one = new Cumulate(new CumulativeFunction<Number>(Numbers.addition));
			Connector.connect(one, sum_one);
			counter.associateOutput(OUTPUT, sum_one, OUTPUT);
			counter.addProcessors(one, sum_one);
		}
		Slice slicer = new Slice(new IdentityFunction(1), counter);
		addProcessors(slicer);
		associateInput(0,slicer,0);
		associateOutput(0,slicer,0);
	}
}
