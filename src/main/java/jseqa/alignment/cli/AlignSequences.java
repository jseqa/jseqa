package jseqa.alignment.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import jseqa.alignment.Alignment;
import jseqa.alignment.FullAlignments;
import jseqa.alignment.substitution.BlosumSubstitutionMatrix;
import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Command line utility to align sequences.
 *
 */
public class AlignSequences {

	public static void main(String[] args) throws Exception {
		CommandLineParser clParser = new DefaultParser();
		Options opts = new Options();
		opts.addOption("h", "help", false, "display help");
		//
		opts.addOption("1", "seq_1", true, "First sequence to align");
		opts.addOption("2", "seq_2", true, "Second sequence to align");
		//
		opts.addOption("t", "type", true, "type of alignment: [global/local/fitting/affine_global/affine_local/affine_overlap], default=global");
		opts.addOption("a", "amino_acid", false, "flag to toggle use of amino acid substitution matrix BLOSUM");
		//
		opts.addOption("p", "indel_penalty", true, "indel penalty, default=-10");
		opts.addOption("e", "extension_penalty", true, "indel extenxion penalty used for affine only, default=-1");
		//
		String seqA = null;
		String seqB = null;
		String type = "global";
		boolean aminoacid = false;
		//
		int indelPenal = -10;
		int extensionPenal = -1;
		//
		HelpFormatter formatter = new HelpFormatter();
		try {
			CommandLine cmd = clParser.parse(opts, args);

			if( cmd.hasOption("help") || (cmd.getOptions().length == 0)) {
				formatter.printHelp("help", opts);
				System.exit(-1);
			}			
			//			
			if(cmd.hasOption("1")) {
				seqA = cmd.getOptionValue("1");
			} else {
				System.err.println("Must specify first sequence with -1");
				System.exit(-1);
			}
			if(cmd.hasOption("2")) {
				seqB = cmd.getOptionValue("2");
			} else {
				System.err.println("Must specify first sequence with -2");
				System.exit(-1);
			}
			//
			if(cmd.hasOption("p")) {
				indelPenal = Integer.parseInt(cmd.getOptionValue("p"));
			}
			if(cmd.hasOption("e")) {
				extensionPenal = Integer.parseInt(cmd.getOptionValue("e"));
			}
			//
			if(cmd.hasOption("t")) {
				type = cmd.getOptionValue("t").toLowerCase();
			}
			if(cmd.hasOption("a")) {
				aminoacid = true;
			}
			//
			
			SubstitutionMatrix subMat = null;
			
			if(aminoacid) {
				subMat = BlosumSubstitutionMatrix.BLOSUM62;
			} else {
				subMat = new DnaSubstitutionMatrix(indelPenal, extensionPenal);
			}
			
			Alignment aln = null;
			if(type.equals("global")) {
				aln = FullAlignments.globalAlign(seqA, seqB, subMat);
			} else if(type.equals("local")) {
				aln = FullAlignments.localAlign(seqA, seqB, subMat);
			} else if(type.equals("fitting")) {
				aln = FullAlignments.fittingAlign(seqA, seqB, subMat);
			} else if(type.equals("affine_global")) {
				aln = FullAlignments.affineGlobalAlign(seqA, seqB, subMat);
			} else if(type.equals("affine_local")) {
				aln = FullAlignments.affineLocalAlign(seqA, seqB, subMat);
			} else if(type.equals("affine_overlap")) {
				aln = FullAlignments.affineOverlapAlign(seqA, seqB, subMat);
			} else {
				System.err.println("Invalid type specified, look at help (-h)");
				System.exit(-1);
			}
			
			System.out.println(aln.toString(80));
			
		} catch (org.apache.commons.cli.ParseException e) { 
			e.printStackTrace(); 
		}
	}

	private AlignSequences() {}
}
