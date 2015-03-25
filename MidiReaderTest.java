
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Read midi file , obtain notes , pitches , and write them as a arff file
 * @author Christian Moreano
 */
public class MidiReaderTest
{
	public static void main(String[] args) throws FileNotFoundException
	{
		SimpleMidiReader smr = new SimpleMidiReader("Test_01.mid"); // test file		
		ArrayList<Integer> queue = new ArrayList<Integer>(); // will be users as queue for pitches
		ArrayList<Note> notes = smr.getNotes();	// array of note 
		PrintWriter out = new PrintWriter("chords.arff");		// instantiate the PrintWriter		
		String output = ""; // write or add up all strings here

		/*
		for( Note n : notes )  
		{
			System.out.println(n.ticksPerBeatSource + " " + n.pitchName + " " + n.pitch);
		}
		*/
		
		// BEGIN HEADER FILE
		output = "@RELATION chords\n\n";
		output = output + "@ATTRIBUTE NumberOfNotes NUMERIC\n";
		output = output + "@ATTRIBUTE Pitch1 NUMERIC\n";
		output = output + "@ATTRIBUTE Pitch2 NUMERIC\n";
		output = output + "@ATTRIBUTE Pitch3 NUMERIC\n";
		output = output + "@ATTRIBUTE Pitch4 NUMERIC\n";
		output = output + "@ATTRIBUTE Pitch5 NUMERIC\n\n";
		// END HEADER FILE
		output = output + "@DATA\n\n";
		  
		// BEGIN WRITING DATA
		
		queue.add(notes.get(0).pitch);			//Add first pitch to QUEUE
		int tempticSource = notes.get(0).endTickSource; // save notes for comparison
		
		for(int i =  1; i < notes.size(); i++)
		{
			
			// compare values of previous note , if true , add to queue
			if(tempticSource == notes.get(i).endTickSource && i != notes.size()-1)
			{
				queue.add(notes.get(i).pitch);
				//System.out.println(i);
	
			}
			
			else
				{
					// add last element to queue
					if( i == notes.size()-1)
					{
						queue.add(notes.get(i).pitch);
	
					}
					// write the size or number of notes
					output = output + queue.size();

					// begin writing pitches
					for(int j = 0 ; j < queue.size(); j++)
					{
						output =  output + "," + queue.get(j) ;
					}
					output = output + "\n";

					// clear queue for new data
					queue.clear();
					
					// save value for next comparison and add to queue
					tempticSource = notes.get(i).endTickSource;
					queue.add(notes.get(i).pitch);
				}

		}

		// WRITE STRING TO FILE
		out.print(output);
		out.close();

	} // end main
} // end class
