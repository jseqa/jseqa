package jseqa.msa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	public MultipleAlignment(List<String> alignedSeqs) {
		this.rows = new LinkedList<List<MsaCell>>();
		for(int i = 0; i < alignedSeqs.size(); i++) {
			this.rows.add(i, new LinkedList<MsaCell>());
			for(int j = 0; j < alignedSeqs.get(i).length(); j++) {
				this.rows.get(i).add(j, new MsaCell(j, alignedSeqs.get(i).charAt(j)));
			}
		}
	}
	
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
	
	public String toString() {
		StringBuilder msa = new StringBuilder();
		for(int i = 0; i < this.rows.size(); i++) {
			msa.append(this.getRowSequence(i) + "\n");
		}
		return msa.toString();
	}
	
	public int numCols() {
		return this.rows.get(0).size();
	}
	
	public int numRows() {
		return this.rows.size();
	}
	
	public String getRowSequence(int i) {
		return this.rows.get(i).stream().map(cell -> ""+cell.ch()).collect(Collectors.joining(""));
	}
	
	/**
	 * Remove any columns that are identical
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
		
		for(int j = colsToRm.size()-1; j >= 0; j--) {
			int col = colsToRm.get(j);
			for(int i = 0; i < this.rows.size(); i++) {
				this.rows.get(i).remove(col);
			}
		}
	}
	
}