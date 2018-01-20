package jseqa.msa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jseqa.util.Interval;

/**
 * Represents a multiple sequence alignment (MSA). At each cell M[i,j], the position j within sequence i is maintained. 
 * The MSA can be sparse, i.e., values of j can be missing from range [0,MAX_LEN].
 *
 */
public class MultipleAlignment {

	private class MsaCell {
		private int pos;
		private char ch;
		public MsaCell(int pos, char ch) {
			this.pos = pos;
			this.ch = ch;
		}
		
		public int pos() {
			return this.pos;
		}
		
		public char ch() {
			return this.ch;
		}
		
		public String toString() {
			return "[" + this.pos + ":" + this.ch + "]";
		}
	}
	
	private List<List<MsaCell>> rows;
	
	/**
	 * Construct a multiple alignment from {@code String}s that are assumed to be already aligned.
	 * @param alignedSeqs
	 */
	public MultipleAlignment(List<String> alignedSeqs) {
		this.rows = new LinkedList<List<MsaCell>>();
		for(int i = 0; i < alignedSeqs.size(); i++) {
			this.rows.add(i, new LinkedList<MsaCell>());
			for(int j = 0; j < alignedSeqs.get(i).length(); j++) {
				this.rows.get(i).add(j, new MsaCell(j, alignedSeqs.get(i).charAt(j)));
			}
		}
	}
	
	/**
	 * Copy constructor.
	 * @param msaObj
	 */
	public MultipleAlignment(MultipleAlignment msaObj) {
		this.rows = new LinkedList<List<MsaCell>>();
		for(int i = 0; i < msaObj.rows.size(); i++) {
			this.rows.add(i, new LinkedList<MsaCell>(msaObj.rows.get(i)));
		}
	}
	
	private List<MsaCell> getRow(int i) {
		return this.rows.get(i);
	}
	
	private MsaCell getCell(int i, int j) {
		return this.rows.get(i).get(j);
	}
	
	public void printMatrix() {
		for(int i = 0; i < this.rows.size(); i++) {
			List<MsaCell> row = this.getRow(i);
			System.out.println(row);
		}
	}
	
	/**
	 * Get the {@code Interval} of the original positions given an {@code Interval} over 
	 * the current MSA columns.
	 * @param colIntval {@code Interval} of columns in current MSA.
	 * @param rowIdx int row index of original {@code Interval} to return.
	 * @return
	 */
	public Interval getTrueInterval(Interval colIntval, int rowIdx) {
		List<MsaCell> currRow = this.rows.get(rowIdx); 
		int strt = currRow.get(colIntval.start()).pos();
		int end  = (colIntval.end() < currRow.size() ? currRow.get(colIntval.end()).pos() : (currRow.get(currRow.size()-1).pos + 1));
		return new Interval(strt, end);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return this.toStringWithConsensus(false);
	}
	
	/**
	 * Output multpile alignment to {@code String} with a consensus row.
	 * @param consflag
	 * @return
	 */
	public String toStringWithConsensus(boolean consflag) {
		StringBuilder msa = new StringBuilder();
		for(int i = 0; i < this.rows.size(); i++) {
			msa.append(this.getRowSequence(i) + "\n");
		}
		
		if(consflag) {
			StringBuilder consensusStr = new StringBuilder();
			for(int j = 0; j < this.rows.get(0).size(); j++) {
				final int pos = j;
				int numUniqCh = (int) IntStream.range(0,this.rows.size()).map(i -> this.getRowSequence(i).charAt(pos)).distinct().count();
				char ch = numUniqCh == 1 ? ' ' : '.';
				consensusStr.append(ch);
			}
			msa.append(consensusStr.toString());
		}
		return msa.toString();
	}
	
	/**
	 * Output the multiple alignment to a {@code String} of a fixed width.
	 * @param colWidth int maximum width of alignment columns.
	 * @return
	 */
	public String toStringFixedWidth(int colWidth) {
		StringBuilder consensusStr = new StringBuilder();
		for(int j = 0; j < this.rows.get(0).size(); j++) {
			final int pos = j;
			int numUniqCh = (int) IntStream.range(0,this.rows.size()).map(i -> this.getRowSequence(i).charAt(pos)).distinct().count();
			char ch = numUniqCh == 1 ? ' ' : '.';
			consensusStr.append(ch);
		}
		
		StringBuilder msa = new StringBuilder();
		int ncols = this.rows.get(0).size();
		for(int i = 0; i < ncols; i += colWidth) {
			if(i > 0) {
				msa.append("\n");
			}
			int lclWidth = ((i+colWidth) < ncols) ? colWidth : (ncols-i); 
			for(int j = 0; j < this.rows.size(); j++) {
				msa.append(this.getRowSequence(j).substring(i,i+lclWidth) + "\n");
			}
			msa.append(consensusStr.subSequence(i, i+lclWidth) + "\n");
		}
		return msa.toString();
	}
	
	/**
	 * Number of columns in the multiple alignment.
	 * @return int number of columns.
	 */
	public int numCols() {
		return this.rows.get(0).size();
	}
	
	/**
	 * Number of rows int the multiple alignment, i.e., the number of sequences.
	 * @return int number of rows (sequences).
	 */
	public int numRows() {
		return this.rows.size();
	}
	
	/**
	 * Get the row from the multiple alignment.
	 * @param i int row index to retrieve.
	 * @return {@code String} of row i.
	 */
	public String getRowSequence(int i) {
		return this.rows.get(i).stream().map(cell -> ""+cell.ch()).collect(Collectors.joining(""));
	}
	
	/**
	 * Remove any columns that are identical, i.e., monomorphic.
	 */
	public void removeMonomorphicColumns() {
		List<Integer> colsToRm  = new LinkedList<Integer>();
		for(int j = 0; j < this.rows.get(0).size(); j++) {
			Set<Character> charsAtPos = new HashSet<Character>();
			for(int i = 0; i < this.rows.size(); i++) {
				charsAtPos.add(this.getCell(i, j).ch());
			}
			if(charsAtPos.size() == 1) {
				colsToRm.add(j);
			}
		}
		this.removeSelectColumns(colsToRm);
	}
	
	/**
	 * Remove any column with an indel, i.e., '-', at any row.
	 */
	public void removeIndelColumns() {
		List<Integer> colsToRm  = new LinkedList<Integer>();
		for(int j = 0; j < this.rows.get(0).size(); j++) {
			Set<Character> charsAtPos = new HashSet<Character>();
			for(int i = 0; i < this.rows.size(); i++) {
				charsAtPos.add(this.getCell(i, j).ch());
			}
			if(charsAtPos.contains('-')) {
				colsToRm.add(j);
			}
		}
		this.removeSelectColumns(colsToRm);
	}
	
	/**
	 * Column ids must be in increasing order.
	 * @param colsToRm {@code List<Integer>} columns to remove.
	 */
	private void removeSelectColumns(List<Integer> colsToRm) {
		for(int j = colsToRm.size()-1; j >= 0; j--) {
			int col = colsToRm.get(j);
			for(int i = 0; i < this.rows.size(); i++) {
				this.rows.get(i).remove(col);
			}
		}
	}
}