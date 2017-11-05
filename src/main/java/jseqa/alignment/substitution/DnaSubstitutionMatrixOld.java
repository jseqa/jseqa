package jseqa.alignment.substitution;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic scoring matrix with +5 for matches, -4 for mismatches.
 *
 */
@Deprecated
public class DnaSubstitutionMatrixOld extends AbstractSubstitutionMatrix implements NtIndexer {

	private int[][] matrix = { 
			{ 5,-4,-4,-4,-2}, 
			{-4, 5,-4,-4,-2},
			{-4,-4, 5,-4,-2}, 
			{-4,-4,-4, 5,-2},
			{-2,-2,-2,-2,-1}
			};
	
	private int[] flatMatrix = {
			5,-4,-4,-4,-2,
			-4, 5,-4,-4,-2,
			-4,-4, 5,-4,-2, 
			-4,-4,-4, 5,-2,
			-2,-2,-2,-2,-1};
	
//	private int[] flatSparceMatrix;
//	
//	private int[] ntLookup;
//	private Map<Character,Integer> ntMap;
//	
//	private Map<Integer,Integer> packMap;
	
//	private static final int MATCH_VAL = 5;
//	private static final int MISMATCH_VAL = -4;
//	private static final int N_NT_VAL = -2;
//	private static final int N_N_VAL = -1;
	
	public DnaSubstitutionMatrixOld() {
		this(-5, -1);
	}
	
	public DnaSubstitutionMatrixOld(int gap) {
		this(gap, gap);
	}
	
	public DnaSubstitutionMatrixOld(int gap, int ext) {
		super(gap, ext);
//		this.ntLookup = new int[128];
//		this.ntLookup['A'] = 0;
//		this.ntLookup['C'] = 1;
//		this.ntLookup['G'] = 2;
//		this.ntLookup['T'] = 3;
//		this.ntLookup['N'] = 4;
//		this.packMap = new HashMap<Integer,Integer>();
//		
//		this.flatSparceMatrix = new int[128*128];
//		char[] nucs = {'A', 'C', 'G', 'T', 'N'};
//		for(int i = 0; i < nucs.length; i++) {
//			for(int j = 0; j < nucs.length; j++) {
//				int val = 0;
//				if(nucs[i] == 'N' || nucs[j] == 'N') {
//					if(nucs[i] == 'N' && nucs[j] == 'N') {
//						val = -1; //N_N_VAL;
//					} else {
//						val = -2; //N_NT_VAL;
//					}
//				} else { // no N's
//					if(nucs[i] == nucs[j]) {
//						val = 5; //MATCH_VAL; //5;
//					} else {
//						val =-4; //MISMATCH_VAL; //-4;
//					}
//				}
//				this.flatSparceMatrix[nucs[i]*128 + nucs[j]] = val;
//			}
//		}
		
		
//		char[] nucs = {'A', 'C', 'G', 'T'};
//		for(int i = 0; i < nucs.length; i++) {
//			for(int j = 0; j < nucs.length; j++) {
//				if(nucs[i] == nucs[j]) {
//					this.packMap.put(this.getPackedDiNuc(nucs[i], nucs[j]), MATCH_VAL);
//				} else {
//					this.packMap.put(this.getPackedDiNuc(nucs[i], nucs[j]), MISMATCH_VAL);
//				}
//			}
//		}
//		for(int i = 0; i < nucs.length; i++) {
//			this.packMap.put(this.getPackedDiNuc(nucs[i], 'N'), N_NT_VAL);
//			this.packMap.put(this.getPackedDiNuc('N', nucs[i]), N_NT_VAL);
//		}
//		this.packMap.put(this.getPackedDiNuc('N', 'N'), N_N_VAL);
	}
	
//	public int getPackedDiNuc(char a, char b) {
//		return (a << 7) + b;
//	}
	
//	@Override
//	public int score(char a, char b) { // 12sec
//		return this.flatMatrix[this.ntLookup[a]*5+this.ntLookup[b]];
//	}

	@Override
	public int score(char a, char b) { // 12sec
		return this.flatMatrix[this.ntIndex(a)*5+this.ntIndex(b)];
	}
	
//	@Override
//	public int score(char a, char b) { // 5sec
//		return ((a ^ b) > 0 ? 1 : -1);
//	}
	
//	@Override
//	public int score(char a, char b) { // 11sec
////		return ((a ^ b) > 0 ? -4 : 5);  // 11sec
//		return ((a ^ b) > 0 ? -40 : 50); // 8sec
//	}
	
//	@Override
//	public int score(char a, char b) { // 32sec
//		return this.packMap.get(this.getPackedDiNuc(a, b));
//	}
	
//	@Override
//	public int score(char a, char b) { // 11sec
//		int aCh = (7 & a);
//		int bCh = (7 & b);
//		if(aCh == 6 || bCh == 6) {
//			if(aCh == 6 && bCh == 6) {
//				return -1; //N_N_VAL;
//			} else {
//				return -2; //N_NT_VAL;
//			}
//		} else { // no N's
//			if(aCh == bCh) {
//				return 5; //MATCH_VAL; //5;
//			} else {
//				return -4; //MISMATCH_VAL; //-4;
//			}
//		}
//	}
	
//	@Override
//	public int score(char a, char b) { // 12sec
//		return this.flatSparceMatrix[a*128 + b];
//	}
	
//	@Override
//	public int score(char a, char b) { // 11sec
//		if(a == 'N' || b == 'N') {
//			if(a == 'N' && b == 'N') {
//				return -1; //N_N_VAL;
//			} else {
//				return -2; //N_NT_VAL;
//			}
//		} else { // no N's
//			if(a == b) {
//				return 5; //MATCH_VAL; //5;
//			} else {
//				return -4; //MISMATCH_VAL; //-4;
//			}
//		}
//	}
	
//	@Override
//	public int score(char a, char b) {
//		int i = this.ntIndex(a);
//		int j = this.ntIndex(b);
//		return this.matrix[i][j];
//	}
	
//	@Override
//	public int score(char a, char b) { // 13sec
//		int i = this.ntLookup[a];
//		int j = this.ntLookup[b];
//		return this.matrix[i][j];
//	}

}
