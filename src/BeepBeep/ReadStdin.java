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
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.io.Print;
import ca.uqac.lif.cep.io.ReadStringStream;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSink;
import ca.uqac.lif.cep.tmf.QueueSource;

import java.util.ArrayList;
import java.util.Queue;

public class ReadStdin {

    public ReadStdin(){

		/* We create a stream reader, and instruct it to read bytes from
		 * the standard input (represented in Java by the System.in object).
		 * We must tell the reader that it is not reading from a file, and
		 * that it should push whatever it receives. */
		///
		ReadStringStream reader = new ReadStringStream(System.in);
		reader.setIsFile(false);

		/* We connect the reader to a pump, which will periodically ask
		 * the reader to read new characters from the input stream */
		Pump pump = new Pump(100);
		Thread pump_thread = new Thread(pump);
		Connector.connect(reader, pump);

		/* We connect the output of the stream reader to a Print processor,
		 * that will merely re-print to the standard output what was received
		 * from the standard input. */
        Print print = new Print();
        Connector.connect(pump, print);

        /* We need to call start() on the pump's thread so that it can start
		 * listening to its input stream. */
		pump_thread.start();
		
		/* Since our program does nothing else, it would stop right away.
		 * We put here an idle loop. You can stop it by pressing Ctrl+C. */
		while(true)
		{
			try {
                //Queue<Object> queue = sink.getQueue();
                //System.out.println("Events in the sink: " + queue);
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
