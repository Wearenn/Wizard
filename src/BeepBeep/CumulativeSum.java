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

import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.util.Numbers;

/**
 * Use a cumulative processor to compute the sum of all events
 * received so far.
 * Graphically, this chain of processors can be represented as:
 * <p>
 * <img src="{@docRoot}/doc-files/basic/CumulativeSum.png" alt="Processor graph"> 
 * 
 * @author Sylvain Hallé
 * @difficulty Easy
 */
public class CumulativeSum extends GroupProcessor{

	public CumulativeSum() {
		super(1, 1);
        Cumulate sum = new Cumulate(
                new CumulativeFunction<Number>(Numbers.addition));
        addProcessors(sum);
        associateInput(0, sum, 0);
        associateOutput(0, sum, 0);
	}
}
